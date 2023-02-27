package com.smart.recp.service.order.service.impl;

import cn.hutool.core.lang.UUID;
import com.alibaba.druid.sql.ast.statement.SQLForeignKeyImpl;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.goods.feign.service.IGoodsBuyerClient;
import com.smart.recp.service.goods.feign.service.IStockClient;
import com.smart.recp.service.goods.vo.GoodsSpecPriceVO;
import com.smart.recp.service.goods.vo.GoodsSpecVO;
import com.smart.recp.service.goods.vo.GoodsVO;
import com.smart.recp.service.order.dto.GenerateOrderDTO;
import com.smart.recp.service.order.dto.OrderCartDTO;
import com.smart.recp.service.order.entity.Order;
import com.smart.recp.service.order.entity.OrderItem;
import com.smart.recp.service.order.service.CartService;
import com.smart.recp.service.order.service.OrderBuyerService;
import com.smart.recp.service.order.service.OrderItemService;
import com.smart.recp.service.order.service.OrderService;
import com.smart.recp.service.order.vo.OrderCartVO;
import com.smart.recp.service.order.vo.OrderItemVO;
import com.smart.recp.service.user.feign.service.IReceiveAddressClient;
import com.smart.recp.service.user.vo.ReceiveAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ybq
 */
@Service
@Slf4j
public class OrderBuyerServiceImpl implements OrderBuyerService {
    @Resource
    OrderService orderService;

    @Resource
    OrderItemService orderItemService;

    @Resource
    CartService cartService;

    @Resource
    IGoodsBuyerClient goodsBuyerClient;

    @Resource
    IStockClient stockClient;

    @Resource
    IReceiveAddressClient receiveAddressClient;

    @Override
    public OrderItemVO getById(Integer orderItemId) throws BaseException {
        return orderItemService.getById(orderItemId);
    }

    @Override
    public PageResult<OrderItemVO> list(Integer page, Integer size, Integer buyerId, Integer delivery_status, Integer customer_status) throws BaseException {
        return orderItemService.list(page, size, null, buyerId, delivery_status, customer_status);
    }


    //TODO 分布式事务
    @Override
    public boolean generateOrder(GenerateOrderDTO generateOrderDTO) throws BaseException {
        try {
            if (ObjectUtils.isEmpty(generateOrderDTO.getReceiveId())) {
                log.error("失败：【generateOrder】生成订单失败,收货地址ID不能为空");
                throw new BaseException(ResultCode.ERROR.getStatus(), "请选择收货地址");
            }
            List<OrderCartDTO> orderCartDTOList = generateOrderDTO.getOrderCartDTOList();
            //查询购物信息
            List<OrderCartVO> orderCartVOList = new ArrayList<>();
            for (OrderCartDTO orderCartDTO : orderCartDTOList) {
                Integer cartId = orderCartDTO.getCartId();
                if (ObjectUtils.isNotEmpty(cartId)) {
                    OrderCartVO byId = cartService.getById(cartId);
                    orderCartVOList.add(byId);
                }
                OrderCartVO orderCartVO = new OrderCartVO();
                BeanUtils.copyProperties(orderCartDTO, orderCartVO);
                RestResult<GoodsVO> goodsVORestResult = goodsBuyerClient.get(orderCartVO.getGoodsId());
                if (!RestResult.isSuccess(goodsVORestResult) || ObjectUtils.isEmpty(goodsVORestResult.getData())) {
                    log.error("失败：【generateOrder】 根据ID获取商品信息失败，ID:{}", orderCartVO.getGoodsId());
                    throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID获取商品信息失败");
                }
                GoodsVO goodsVO = goodsVORestResult.getData();
                getCartByGoods(orderCartVO, goodsVO);
                orderCartVOList.add(orderCartVO);
            }

            Integer buyerId = orderCartVOList.stream().map(item -> item.getBuyerId()).findFirst().get();
            if (ObjectUtils.isEmpty(buyerId)) {
                log.error("失败：【generateOrder】生成订单失败,卖家ID为空");
            }
            //计算订单总金额
            BigDecimal orderMoney = new BigDecimal("0.00");
            orderCartVOList.forEach(item -> {
                BigDecimal price = item.getPrice();
                Integer goodsAmount = item.getGoodsAmount();
                orderMoney.add(price.multiply(BigDecimal.valueOf(goodsAmount)));
            });

            Order order = new Order();
            order.setBuyerId(buyerId);
            order.setOrderNumber(String.format("SMART-RECP-ORDER{}-{}", buyerId, UUID.fastUUID()));
            order.setOrderMoney(orderMoney);
            order.setGoodsTotalMoney(orderMoney);
            //添加订单
            Order add = orderService.add(order);
            Integer orderId = add.getOrderId();
            String orderNumber = add.getOrderNumber();
            List<OrderItem> orderItemList = new ArrayList<>();
            for (OrderCartVO orderCartVO : orderCartVOList) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderId(orderId);
                orderItem.setOrderNumber(orderNumber);
                orderItem.setOrderItemNumber(String.format("SMART-RECP-ORDER-ITEM{}-{}", buyerId, UUID.fastUUID()));
                BeanUtils.copyProperties(orderCartVO, orderItem);
                orderItem.setGoodsPrice(orderCartVO.getPrice());
                BigDecimal totalMoney = orderCartVO.getPrice().multiply(new BigDecimal(orderCartVO.getGoodsAmount()));
                orderItem.setTotalMoney(totalMoney);
                RestResult<ReceiveAddressVO> addressVORestResult = receiveAddressClient.get(generateOrderDTO.getReceiveId());
                if (ObjectUtils.isEmpty(addressVORestResult) || ObjectUtils.isEmpty(addressVORestResult.getData())) {
                    log.error("失败：【generateOrder】生成订单失败,查询收货地址失败");
                    throw new BaseException(ResultCode.ERROR.getStatus(), addressVORestResult.getMessage());
                }
                ReceiveAddressVO addressVO = addressVORestResult.getData();
                orderItem.setReceiveName(addressVO.getReceiveName());
                orderItem.setReceiveMobile(addressVO.getCustomerMobile());
                orderItem.setReceiveAddress(String.format("{}{}{}{}", addressVO.getProvince(), addressVO.getCity(), addressVO.getCounty(), addressVO.getDetailAddress()));
                //TODO 减库存加锁
                RestResult<Boolean> result = stockClient.subtract(orderCartVO.getSpecId(), orderCartVO.getGoodsAmount());
                if (ObjectUtils.isEmpty(result) || ObjectUtils.isEmpty(result.getData()) || !result.getData()) {
                    log.error("失败：【generateOrder】生成订单失败,扣除库存失败");
                    throw new BaseException(ResultCode.ERROR.getStatus(), result.getMessage());
                }
            }
            int insert = orderItemService.addList(orderItemList);
            if (insert != orderItemList.size()) {
                log.error("失败：【generateOrder】生成订单失败，添加订单子项目失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "生成订单失败");
            }
            //TODO 倒计时30分钟检查订单是否支付
            log.info("成功：【generateOrder】生成订单成功");
            return true;
        } catch (Exception e) {
            log.error("失败：【generateOrder】生成订单失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "生成订单失败");
        }

    }

    private void getCartByGoods(OrderCartVO orderCartVO, GoodsVO goodsVO) {
        orderCartVO.setGoodsName(goodsVO.getGoodsName());
        orderCartVO.setGoodsImage(goodsVO.getGoodsResourceVOList().stream().filter(item -> item.getIsMaster().equals(1)).findFirst().get().getLink());
        List<GoodsSpecVO> goodsSpecVOList = goodsVO.getGoodsSpecVOList();
        GoodsSpecVO goodsSpecVO = goodsSpecVOList.stream().filter(item -> item.getSpecId().equals(orderCartVO.getSpecId())).findFirst().get();
        orderCartVO.setSpecName(goodsSpecVO.getSpecName());
        //价格
        List<GoodsSpecPriceVO> goodsSpecPriceVOList = goodsSpecVO.getGoodsSpecPriceVOList();
        for (GoodsSpecPriceVO goodsSpecPriceVO : goodsSpecPriceVOList) {
            //零售
            if (goodsSpecPriceVO.getType().equals(1)) {
                if (orderCartVO.getGoodsAmount().equals(1)) {
                    orderCartVO.setPrice(goodsSpecPriceVO.getPrice());
                    break;
                }
                //初始赋值零售
                orderCartVO.setPrice(goodsSpecPriceVO.getPrice());
            }
            //批发
            if (goodsSpecPriceVO.getMin() < goodsSpecPriceVO.getMax()) {
                //批发范围内
                if (goodsSpecPriceVO.getMin() <= orderCartVO.getGoodsAmount() && orderCartVO.getGoodsAmount() <= goodsSpecPriceVO.getMax()) {
                    orderCartVO.setPrice(goodsSpecPriceVO.getPrice());
                    break;
                }
            } else {
                //最后一个批发
                if (goodsSpecPriceVO.getMin() <= orderCartVO.getGoodsAmount()) {
                    orderCartVO.setPrice(goodsSpecPriceVO.getPrice());
                    break;
                }
            }
        }
    }


}

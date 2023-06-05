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
import com.smart.recp.service.order.vo.OrderVO;
import com.smart.recp.service.user.feign.service.IReceiveAddressClient;
import com.smart.recp.service.user.vo.ReceiveAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.Redisson;
import org.redisson.RedissonRedLock;
import org.redisson.api.RLock;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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

    @Resource
    Redisson redisson;

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
    public Integer generateOrder(GenerateOrderDTO generateOrderDTO) throws BaseException {
        try {
            if (ObjectUtils.isEmpty(generateOrderDTO.getReceiveId())) {
                log.error("失败：【generateOrder】生成订单失败,收货地址ID不能为空");
                throw new BaseException(ResultCode.ERROR.getStatus(), "请选择收货地址");
            }
            List<OrderCartDTO> orderCartDTOList = generateOrderDTO.getOrderCartDTOList();
            //查询购物车信息及收货地址信息
            List<OrderCartVO> orderCartVOList = new ArrayList<>();
            for (OrderCartDTO orderCartDTO : orderCartDTOList) {
                Integer cartId = orderCartDTO.getCartId();
                if (ObjectUtils.isNotEmpty(cartId)) {
                    OrderCartVO byId = cartService.getById(cartId);
                    orderCartVOList.add(byId);
                    continue;
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
            for (OrderCartVO item : orderCartVOList) {
                BigDecimal price = item.getPrice();
                Integer goodsAmount = item.getGoodsAmount();
                orderMoney = orderMoney.add(price.multiply(BigDecimal.valueOf(goodsAmount)));
            }

            Order order = new Order();
            order.setBuyerId(buyerId);
            order.setOrderNumber(String.format("SMART-RECP-ORDER%s-%s", buyerId, UUID.fastUUID()));
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
                orderItem.setOrderItemNumber(String.format("SMART-RECP-ORDER-ITEM%s-%s", buyerId, UUID.fastUUID()));
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
                orderItem.setReceiveAddress(String.format("%s%s%s%s", addressVO.getProvince(), addressVO.getCity(), addressVO.getCounty(), addressVO.getDetailAddress()));
                //加锁
                String lockKey = String.format("goods::subtract::%s-%s", orderCartVO.getGoodsId(), orderCartVO.getSpecId());
                RLock lock = redisson.getLock(lockKey);
                RedissonRedLock redLock = new RedissonRedLock(lock);
                boolean flagLock = false;
                try {
                    flagLock = redLock.tryLock(30, TimeUnit.SECONDS);
                    if (flagLock) {
                        RestResult<Boolean> result = stockClient.subtract(orderCartVO.getSpecId(), orderCartVO.getGoodsAmount());
                        //模拟BUG
                        //int i = 1/0;
                        if (ObjectUtils.isEmpty(result) || ObjectUtils.isEmpty(result.getData()) || !result.getData()) {
                            log.error("失败：【generateOrder】生成订单失败,扣除库存失败");
                            throw new BaseException(ResultCode.ERROR.getStatus(), result.getMessage());
                        }
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    redLock.unlock();
                }
                orderItemList.add(orderItem);
            }
            //生成子订单订单
            int insert = orderItemService.addList(orderItemList);
            if (insert != orderItemList.size()) {
                log.error("失败：【generateOrder】生成订单失败，添加订单子项目失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "生成订单失败");
            }
            //删除购物车
            List<Integer> deleteCartIdList = orderCartVOList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getCartId())).map(item -> item.getCartId()).collect(Collectors.toList());
            if (ObjectUtils.isNotEmpty(deleteCartIdList) && deleteCartIdList.size() >= 1) {
                Integer delete = cartService.deleteByIdList(deleteCartIdList);
                if (delete != deleteCartIdList.size()) {
                    log.error("失败：【generateOrder】生成订单失败，删除购物车失败, CartIdList:{}", deleteCartIdList);
                    throw new BaseException(ResultCode.ERROR.getStatus(), "生成订单失败");
                }
            }
            //TODO 倒计时30分钟检查订单是否支付
            log.info("成功：【generateOrder】生成订单成功");
            return orderId;
        } catch (Exception e) {
            e.printStackTrace();
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
                continue;
            } else if (ObjectUtils.isNotEmpty(goodsSpecPriceVO.getMax()) && goodsSpecPriceVO.getMin() < goodsSpecPriceVO.getMax()) {
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


    @Override
    public OrderVO getPendingPayOrderById(Integer orderId) throws BaseException {
        OrderVO cascadeById = orderService.getCascadeById(orderId);
        if (!Integer.valueOf(0).equals(cascadeById.getPayStatus())) {
            throw new BaseException(ResultCode.ERROR.getStatus(), "获取待支付订单失败");
        }
        return cascadeById;
    }

    @Override
    public boolean pay(Integer buyerId, Integer orderId) throws BaseException {
        try {
            OrderVO orderVO = orderService.getCascadeById(orderId);
            //TODO 判断属不属于该买家
            if (!Integer.valueOf(0).equals(orderVO.getPayStatus()) || !Integer.valueOf(0).equals(orderVO.getTradeStatus())) {
                log.error("失败：【pay】支付失败，该订单不是订待支付状态：ID{}", orderId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "支付失败，该订单不是订待支付状态");
            }
            //TODO 支付：扣余额 等

            Order order = new Order();
            order.setOrderId(orderVO.getOrderId());
            order.setPayStatus(1);
            order.setTradeStatus(1);

            boolean flag = orderService.update(order);
            if (!flag) {
                throw new BaseException(ResultCode.ERROR);
            }
            /**
             * 支付成功修改支付状态
             */
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderVO.getOrderId());
            orderItem.setOrderStatus(1);
            int update = orderItemService.updateOrderStatusByOrderId(orderItem);

            log.info("成功：【pay】支付成功");
            return true;
        } catch (Exception e) {
            log.error("失败：【pay】支付失败，");
            throw new BaseException(ResultCode.ERROR.getStatus(), "支付失败，该订单不是订待支付状态");
        }
    }


    @Override
    public PageResult<OrderItemVO> listPendingPay(Integer page, Integer size, Integer buyerId) throws BaseException {
        try {
            PageResult<OrderVO> pageResult = orderService.listPendingPay(page, size, buyerId);
            PageResult<OrderItemVO> result = new PageResult<>();
            BeanUtils.copyProperties(pageResult, result);
            List<OrderItemVO> orderItemVOList = new ArrayList<>();
            for (OrderVO item : pageResult.getList()) {
                OrderVO orderVO = orderService.getCascadeById(item.getOrderId());
                OrderItemVO orderItemVO = orderVO.getOrderItemVOList().stream().findFirst().get();
                if (ObjectUtils.isNotEmpty(orderItemVO)) {
                    orderItemVOList.add(orderItemVO);
                }
            }
            result.setList(orderItemVOList);
            log.info("成功：【listPendingPay】根据买家ID:{}查询待支付订单列表成功", buyerId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【listPendingPay】根据买家ID:{}查询待支付订单列表失败", buyerId);
            throw new BaseException(ResultCode.ERROR, "查询待支付订单列表失败");
        }
    }
}

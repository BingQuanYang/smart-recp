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
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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

    @Override
    public OrderItemVO getById(Integer orderItemId) throws BaseException {
        return orderItemService.getById(orderItemId);
    }

    @Override
    public PageResult<OrderItemVO> list(Integer page, Integer size, Integer buyerId, Integer delivery_status, Integer customer_status) throws BaseException {
        return orderItemService.list(page, size, null, buyerId, delivery_status, customer_status);
    }


    //TODO ???????????????
    @Transactional
    @Override
    public Integer generateOrder(GenerateOrderDTO generateOrderDTO) throws BaseException {
        try {
            if (ObjectUtils.isEmpty(generateOrderDTO.getReceiveId())) {
                log.error("????????????generateOrder?????????????????????,????????????ID????????????");
                throw new BaseException(ResultCode.ERROR.getStatus(), "?????????????????????");
            }
            List<OrderCartDTO> orderCartDTOList = generateOrderDTO.getOrderCartDTOList();
            //?????????????????????
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
                    log.error("????????????generateOrder??? ??????ID???????????????????????????ID:{}", orderCartVO.getGoodsId());
                    throw new BaseException(ResultCode.ERROR.getStatus(), "??????ID????????????????????????");
                }
                GoodsVO goodsVO = goodsVORestResult.getData();
                getCartByGoods(orderCartVO, goodsVO);
                orderCartVOList.add(orderCartVO);
            }

            Integer buyerId = orderCartVOList.stream().map(item -> item.getBuyerId()).findFirst().get();
            if (ObjectUtils.isEmpty(buyerId)) {
                log.error("????????????generateOrder?????????????????????,??????ID??????");
            }
            //?????????????????????
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
            //????????????
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
                    log.error("????????????generateOrder?????????????????????,????????????????????????");
                    throw new BaseException(ResultCode.ERROR.getStatus(), addressVORestResult.getMessage());
                }
                ReceiveAddressVO addressVO = addressVORestResult.getData();
                orderItem.setReceiveName(addressVO.getReceiveName());
                orderItem.setReceiveMobile(addressVO.getCustomerMobile());
                orderItem.setReceiveAddress(String.format("%s%s%s%s", addressVO.getProvince(), addressVO.getCity(), addressVO.getCounty(), addressVO.getDetailAddress()));
                //TODO ???????????????
                RestResult<Boolean> result = stockClient.subtract(orderCartVO.getSpecId(), orderCartVO.getGoodsAmount());
                if (ObjectUtils.isEmpty(result) || ObjectUtils.isEmpty(result.getData()) || !result.getData()) {
                    log.error("????????????generateOrder?????????????????????,??????????????????");
                    throw new BaseException(ResultCode.ERROR.getStatus(), result.getMessage());
                }
                orderItemList.add(orderItem);
            }
            int insert = orderItemService.addList(orderItemList);
            if (insert != orderItemList.size()) {
                log.error("????????????generateOrder???????????????????????????????????????????????????");
                throw new BaseException(ResultCode.ERROR.getStatus(), "??????????????????");
            }
            List<Integer> deleteCartIdList = orderCartVOList.stream().filter(item -> ObjectUtils.isNotEmpty(item.getCartId())).map(item -> item.getCartId()).collect(Collectors.toList());
            if (ObjectUtils.isNotEmpty(deleteCartIdList) && deleteCartIdList.size() >= 1) {
                Integer delete = cartService.deleteByIdList(deleteCartIdList);
                if (delete != deleteCartIdList.size()) {
                    log.error("????????????generateOrder?????????????????????????????????????????????, CartIdList:{}", deleteCartIdList);
                    throw new BaseException(ResultCode.ERROR.getStatus(), "??????????????????");
                }
            }
            //TODO ?????????30??????????????????????????????
            log.info("????????????generateOrder?????????????????????");
            return orderId;
        } catch (Exception e) {
            log.error("????????????generateOrder?????????????????????");
            throw new BaseException(ResultCode.ERROR.getStatus(), "??????????????????");
        }

    }

    private void getCartByGoods(OrderCartVO orderCartVO, GoodsVO goodsVO) {
        orderCartVO.setGoodsName(goodsVO.getGoodsName());
        orderCartVO.setGoodsImage(goodsVO.getGoodsResourceVOList().stream().filter(item -> item.getIsMaster().equals(1)).findFirst().get().getLink());
        List<GoodsSpecVO> goodsSpecVOList = goodsVO.getGoodsSpecVOList();
        GoodsSpecVO goodsSpecVO = goodsSpecVOList.stream().filter(item -> item.getSpecId().equals(orderCartVO.getSpecId())).findFirst().get();
        orderCartVO.setSpecName(goodsSpecVO.getSpecName());
        //??????
        List<GoodsSpecPriceVO> goodsSpecPriceVOList = goodsSpecVO.getGoodsSpecPriceVOList();
        for (GoodsSpecPriceVO goodsSpecPriceVO : goodsSpecPriceVOList) {
            //??????
            if (goodsSpecPriceVO.getType().equals(1)) {
                if (orderCartVO.getGoodsAmount().equals(1)) {
                    orderCartVO.setPrice(goodsSpecPriceVO.getPrice());
                    break;
                }
                //??????????????????
                orderCartVO.setPrice(goodsSpecPriceVO.getPrice());
                continue;
            } else if (ObjectUtils.isNotEmpty(goodsSpecPriceVO.getMax()) && goodsSpecPriceVO.getMin() < goodsSpecPriceVO.getMax()) {
                //???????????????
                if (goodsSpecPriceVO.getMin() <= orderCartVO.getGoodsAmount() && orderCartVO.getGoodsAmount() <= goodsSpecPriceVO.getMax()) {
                    orderCartVO.setPrice(goodsSpecPriceVO.getPrice());
                    break;
                }
            } else {
                //??????????????????
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
            throw new BaseException(ResultCode.ERROR.getStatus(), "???????????????????????????");
        }
        return cascadeById;
    }

    @Override
    public boolean pay(Integer buyerId, Integer orderId) throws BaseException {
        try {
            OrderVO orderVO = orderService.getCascadeById(orderId);
            //TODO ???????????????????????????
            if (!Integer.valueOf(0).equals(orderVO.getPayStatus()) || !Integer.valueOf(0).equals(orderVO.getTradeStatus())) {
                log.error("????????????pay??????????????????????????????????????????????????????ID{}", orderId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "????????????????????????????????????????????????");
            }
            //TODO ?????????????????? ???

            Order order = new Order();
            order.setOrderId(orderVO.getOrderId());
            order.setPayStatus(1);
            order.setTradeStatus(1);

            boolean flag = orderService.update(order);
            if (!flag) {
                throw new BaseException(ResultCode.ERROR);
            }
            /**
             * ??????????????????????????????
             */
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderVO.getOrderId());
            orderItem.setOrderStatus(1);
            int update = orderItemService.updateOrderStatusByOrderId(orderItem);

            log.info("????????????pay???????????????");
            return true;
        } catch (Exception e) {
            log.error("????????????pay??????????????????");
            throw new BaseException(ResultCode.ERROR.getStatus(), "????????????????????????????????????????????????");
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
            log.info("????????????listPendingPay???????????????ID:{}?????????????????????????????????", buyerId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("????????????listPendingPay???????????????ID:{}?????????????????????????????????", buyerId);
            throw new BaseException(ResultCode.ERROR, "?????????????????????????????????");
        }
    }
}

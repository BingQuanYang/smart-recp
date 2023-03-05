package com.smart.recp.service.order.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.order.dto.GenerateOrderDTO;
import com.smart.recp.service.order.dto.OrderCartDTO;
import com.smart.recp.service.order.vo.OrderItemVO;
import com.smart.recp.service.order.vo.OrderVO;

import java.util.List;

public interface OrderBuyerService {
    OrderItemVO getById(Integer orderItemId) throws BaseException;

    PageResult<OrderItemVO> list(Integer page, Integer size, Integer buyerId, Integer delivery_status, Integer customer_status) throws BaseException;

    Integer generateOrder(GenerateOrderDTO generateOrderDTO) throws BaseException;

    OrderVO getPendingPayOrderById(Integer orderId) throws BaseException;

    boolean pay(Integer buyerId, Integer orderId) throws BaseException;

    PageResult<OrderItemVO> listPendingPay(Integer page, Integer size, Integer buyerId) throws BaseException;
}

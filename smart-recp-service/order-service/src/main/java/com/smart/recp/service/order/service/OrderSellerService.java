package com.smart.recp.service.order.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.order.dto.OrderItemDTO;
import com.smart.recp.service.order.vo.OrderItemVO;

public interface OrderSellerService {
    OrderItemVO getById(Integer orderItemId) throws BaseException;

    PageResult<OrderItemVO> list(Integer page, Integer size, Integer sellerId, Integer delivery_status, Integer customer_status) throws BaseException;

    boolean update(OrderItemDTO orderItemDTO) throws BaseException;

    boolean delivery(OrderItemDTO orderItemDTO) throws BaseException;
}

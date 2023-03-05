package com.smart.recp.service.order.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.order.dto.OrderItemDTO;
import com.smart.recp.service.order.entity.OrderItem;
import com.smart.recp.service.order.vo.OrderItemVO;

import java.util.List;

public interface OrderItemService {
    OrderItemVO getById(Integer orderItemId) throws BaseException;

    PageResult<OrderItemVO> list(Integer page, Integer size, Integer sellerId, Integer buyerId, Integer delivery_status, Integer customer_status) throws BaseException;

    boolean update(OrderItemDTO orderItemDTO) throws BaseException;

    int addList(List<OrderItem> orderItemList) throws BaseException;

    int updateOrderStatusByOrderId(OrderItem orderItem) throws BaseException;
}

package com.smart.recp.service.order.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.order.dto.OrderItemDTO;
import com.smart.recp.service.order.service.OrderItemService;
import com.smart.recp.service.order.service.OrderSellerService;
import com.smart.recp.service.order.vo.OrderItemVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @author ybq
 */
@Service
@Slf4j
public class OrderSellerServiceImpl implements OrderSellerService {

    @Resource
    OrderItemService orderItemService;

    @Override
    public OrderItemVO getById(Integer orderItemId) throws BaseException {
        return orderItemService.getById(orderItemId);
    }

    @Override
    public PageResult<OrderItemVO> list(Integer page, Integer size, Integer sellerId, Integer delivery_status, Integer customer_status) throws BaseException {
        return orderItemService.list(page, size, sellerId, null, delivery_status, customer_status);
    }

    @Override
    public boolean update(OrderItemDTO orderItemDTO) throws BaseException {
        return orderItemService.update(orderItemDTO);
    }

    @Override
    public boolean delivery(OrderItemDTO orderItemDTO) throws BaseException {

        if (ObjectUtils.isEmpty(orderItemDTO.getLogisticsPlatform()) || ObjectUtils.isEmpty(orderItemDTO.getLogisticsNumber())) {
            log.error("失败：【delivery】发货失败,{}", orderItemDTO);
            throw new BaseException(ResultCode.ERROR, "发货失败");
        }
        OrderItemDTO dto = new OrderItemDTO();
        dto.setOrderItemId(orderItemDTO.getOrderItemId());
        dto.setLogisticsPlatform(orderItemDTO.getLogisticsPlatform());
        dto.setLogisticsNumber(orderItemDTO.getLogisticsNumber());
        dto.setDeliveryStatus(1);
        dto.setSellerFinishTime(LocalDateTime.now());
        return orderItemService.update(dto);
    }
}

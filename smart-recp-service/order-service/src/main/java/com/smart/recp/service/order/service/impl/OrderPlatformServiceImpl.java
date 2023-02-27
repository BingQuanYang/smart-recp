package com.smart.recp.service.order.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.order.entity.Order;
import com.smart.recp.service.order.mapper.OrderMapper;
import com.smart.recp.service.order.service.OrderPlatformService;
import com.smart.recp.service.order.service.OrderService;
import com.smart.recp.service.order.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@Service
@Slf4j
public class OrderPlatformServiceImpl implements OrderPlatformService {

    @Resource
    OrderService orderService;

    @Override
    public OrderVO getCascadeById(Integer orderId) throws BaseException {
        return orderService.getCascadeById(orderId);
    }

    @Override
    public PageResult<OrderVO> list(Integer page, Integer size) throws BaseException {
        return orderService.list(page, size, null);
    }
}

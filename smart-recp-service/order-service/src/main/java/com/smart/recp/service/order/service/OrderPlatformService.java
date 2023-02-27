package com.smart.recp.service.order.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.order.vo.OrderVO;

public interface OrderPlatformService {
    OrderVO getCascadeById(Integer orderId) throws BaseException;

    PageResult<OrderVO> list(Integer page, Integer size) throws BaseException;
}

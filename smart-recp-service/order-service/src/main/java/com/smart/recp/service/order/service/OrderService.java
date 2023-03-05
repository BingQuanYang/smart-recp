package com.smart.recp.service.order.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.order.entity.Order;
import com.smart.recp.service.order.vo.OrderVO;

public interface OrderService {
    OrderVO getCascadeById(Integer orderId) throws BaseException;

    /**
     * 查询级联订单列表
     *
     * @param page
     * @param size
     * @param buyerId 可选有这根据买家ID查询
     * @return
     */
    PageResult<OrderVO> listCascade(Integer page, Integer size, Integer buyerId) throws BaseException;

    /**
     * 查询订单列表
     *
     * @param page
     * @param size
     * @param buyerId 可选有这根据买家ID查询
     * @return
     */
    PageResult<OrderVO> list(Integer page, Integer size, Integer buyerId) throws BaseException;


    Order add(Order order) throws BaseException;

    boolean update(Order order) throws BaseException;

    PageResult<OrderVO> listPendingPay(Integer page, Integer size, Integer buyerId) throws BaseException;
}

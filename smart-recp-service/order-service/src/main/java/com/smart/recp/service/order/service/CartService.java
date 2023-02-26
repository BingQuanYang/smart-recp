package com.smart.recp.service.order.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.service.order.dto.OrderCartDTO;
import com.smart.recp.service.order.vo.OrderCartVO;

import java.util.List;

/**
 * @author ybq
 */
public interface CartService {
    OrderCartVO getById(Integer cartId) throws BaseException;

    boolean add(OrderCartDTO orderCartDTO) throws BaseException;

    boolean update(OrderCartDTO orderCartDTO) throws BaseException;

    Integer deleteByIdList(List<Integer> cartIdList) throws BaseException;

    List<OrderCartVO> listByBuyerId(Integer buyerId) throws BaseException;
}

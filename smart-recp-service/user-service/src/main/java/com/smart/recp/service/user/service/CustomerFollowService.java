package com.smart.recp.service.user.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.user.dto.CustomerFollowDTO;
import com.smart.recp.service.user.vo.CustomerFollowVO;

import java.util.List;

public interface CustomerFollowService {
    CustomerFollowVO getById(Integer followId) throws BaseException;

    boolean add(CustomerFollowDTO customerFollowDTO) throws BaseException;

    boolean update(CustomerFollowDTO customerFollowDTO) throws BaseException;

    Integer deleteByIdList(List<Integer> followIdList) throws BaseException;

    PageResult<CustomerFollowVO> list(Integer page, Integer size) throws BaseException;

    PageResult<CustomerFollowVO> listBySellerId(Integer page, Integer size, Integer sellerId) throws BaseException;

    PageResult<CustomerFollowVO> listByBuyerId(Integer page, Integer size, Integer buyerId) throws BaseException;

    CustomerFollowVO getByBuyerIdAndSellerId(Integer buyerId, Integer sellerId) throws BaseException;
}

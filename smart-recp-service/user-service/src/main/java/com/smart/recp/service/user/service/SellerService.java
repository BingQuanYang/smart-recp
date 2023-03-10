package com.smart.recp.service.user.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.user.dto.SellerDTO;
import com.smart.recp.service.user.vo.SellerVO;

public interface SellerService {
    SellerVO getById(Integer sellerId) throws BaseException;

    SellerVO getByAccount(String account) throws BaseException;

    boolean add(SellerDTO sellerDTO) throws BaseException;

    boolean update(SellerDTO sellerDTO) throws BaseException;

    PageResult<SellerVO> list(Integer page, Integer size, SellerDTO sellerDTO) throws BaseException;
}

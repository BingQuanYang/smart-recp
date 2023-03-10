package com.smart.recp.service.user.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.user.vo.BuyerVO;
import com.smart.recp.service.user.vo.SellerVO;
import com.smart.recp.service.user.vo.UserVO;

public interface PlatformService {
    PageResult<SellerVO> listSeller(Integer page, Integer size, Integer sellerId, String search) throws BaseException;

    PageResult<BuyerVO> listBuyer(Integer page, Integer size, Integer buyerId, String search) throws BaseException;

    PageResult<UserVO> listUser(Integer page, Integer size, Integer userId, String account, Integer type) throws BaseException;

    SellerVO getSellerById(Integer sellerId) throws BaseException;

    BuyerVO getBuyerById(Integer buyerId) throws BaseException;

    UserVO getUserById(Integer userId) throws BaseException;
}

package com.smart.recp.service.user.service;


import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.service.user.dto.BuyerDTO;
import com.smart.recp.service.user.vo.BuyerVO;

public interface BuyerService {


    BuyerVO getById(Integer buyerId) throws BaseException;

    BuyerVO getByAccount(String account) throws BaseException;

    boolean add(BuyerDTO buyerDTO) throws BaseException;

    boolean update(BuyerDTO buyerDTO) throws BaseException;
}

package com.smart.recp.service.user.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.service.user.dto.ReceiveAddressDTO;
import com.smart.recp.service.user.vo.ReceiveAddressVO;

import java.util.List;

public interface ReceiveAddressService {
    ReceiveAddressVO getById(Integer receiveId) throws BaseException;

    boolean add(ReceiveAddressDTO receiveAddressDTO) throws BaseException;

    boolean update(ReceiveAddressDTO receiveAddressDTO) throws BaseException;

    Integer deleteByIdList(List<Integer> receiveIdList) throws BaseException;

    List<ReceiveAddressVO> listByBuyerId(Integer buyerId) throws BaseException;

    ReceiveAddressVO getDefaultByBuyerId(Integer buyerId) throws BaseException;
}

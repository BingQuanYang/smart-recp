package com.smart.recp.service.user.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.service.user.dto.UserDTO;
import com.smart.recp.service.user.vo.UserVO;

public interface UserService {
    UserVO getById(Integer userId) throws BaseException;

    UserVO getByAccount(String account) throws BaseException;

    UserVO getByMobile(String mobile) throws BaseException;

    boolean add(UserDTO userDTO) throws BaseException;

    boolean update(UserDTO userDTO) throws BaseException;
}

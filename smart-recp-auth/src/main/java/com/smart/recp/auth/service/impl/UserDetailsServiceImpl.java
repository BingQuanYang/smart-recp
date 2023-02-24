package com.smart.recp.auth.service.impl;

import com.smart.recp.auth.entity.AuthUserDetail;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.feign.service.IUserClient;
import com.smart.recp.service.user.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ybq
 */

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    IUserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
//        String clientId = RequestUtils.getAuthClientId();
        RestResult<UserVO> result = userClient.getByAccount(account);
        if (!RestResult.isSuccess(result)) {
            log.error("失败：登录失败" + result.getMessage() + "账号：{}", account);
        }
        UserVO userVO = result.getData();
        if (ObjectUtils.isEmpty(userVO)) {
            throw new UsernameNotFoundException(ResultCode.ACCOUNT_NOT_EXIST.getMessage());
        }
        AuthUserDetail userDetail = new AuthUserDetail(userVO);
        BeanUtils.copyProperties(userVO, userDetail);
        if (!userDetail.isEnabled()) {
            log.error("失败：登录失败-该账户已被禁用!-账号：{}", account);
            throw new DisabledException("该账户已被禁用!");
        } else if (!userDetail.isAccountNonLocked()) {
            log.error("失败：登录失败-该账号已被锁定!-账号：{}", account);
            throw new LockedException("该账号已被锁定!");
        } else if (!userDetail.isAccountNonExpired()) {
            log.error("失败：登录失败-该账号已过期!-账号：{}", account);
            throw new AccountExpiredException("该账号已过期!");
        }
        return userDetail;
    }

}

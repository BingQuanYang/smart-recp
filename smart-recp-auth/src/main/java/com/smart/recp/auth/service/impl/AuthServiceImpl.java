package com.smart.recp.auth.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.smart.recp.auth.service.AuthService;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.dto.BuyerDTO;
import com.smart.recp.service.user.dto.UserDTO;
import com.smart.recp.service.user.feign.service.IBuyerClient;
import com.smart.recp.service.user.feign.service.IUserClient;
import com.smart.recp.service.user.vo.UserVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Service;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author ybq
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    @Resource
    IUserClient userClient;
    @Resource
    IBuyerClient buyerClient;


    private TokenEndpoint tokenEndpoint;

    @Override
    public OAuth2AccessToken login(Principal principal, Map<String, String> parameters) throws BaseException {
        parameters.put("grant_type", "password");
        parameters.put("client_id", "client");
        parameters.put("client_secret", "123456");
        Integer method = Integer.valueOf(parameters.get("method"));
        Integer platform = Integer.valueOf(parameters.get("platform"));
        if (Integer.valueOf(1).equals(method)) {
            loginByAccountAndPassword(parameters, platform);
        } else {
            parameters = loginByMobile(parameters, platform);
        }
        try {
            return tokenEndpoint.postAccessToken(principal, parameters).getBody();
        } catch (HttpRequestMethodNotSupportedException e) {
            e.printStackTrace();
            log.error("失败：登录失败-参数：{}", parameters);
            throw new BaseException(ResultCode.AUTH_ERROR);
        }
    }

    @Override
    public OAuth2AccessToken register(Map<String, String> parameters) throws BaseException, HttpRequestMethodNotSupportedException {
        parameters.put("grant_type", "password");
        parameters.put("client_id", "client");
        parameters.put("client_secret", "123456");
        Integer method = Integer.valueOf(parameters.get("method"));
        Integer platform = Integer.valueOf(parameters.get("platform"));
        if (Integer.valueOf(1).equals(method)) {
            String username = parameters.get("username");
            String password = parameters.get("password");
            if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
                throw new BaseException(ResultCode.PARAM_IS_DEFICIENCY);
            }
            registerByAccountAndPassword(parameters);
        } else {
            parameters = registerByMobile(parameters);
        }
        User user = new User(parameters.get("client_id"), "", new ArrayList<GrantedAuthority>());
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        return tokenEndpoint.postAccessToken(principal, parameters).getBody();
    }

    private Map<String, String> registerByMobile(Map<String, String> parameters) throws BaseException {
        String captcha = parameters.get("captcha");
        String mobile = parameters.get("mobile");
        Integer platform = Integer.valueOf(parameters.get("platform"));
        if (ObjectUtils.isEmpty(mobile)) {
            log.error("失败：未输入手机号");
            throw new BaseException(ResultCode.AUTH_ERROR, "请输入手机号");
        }
        //TODO
        String getCaptcha = "123456";
        if (!captcha.equals(getCaptcha)) {
            throw new BaseException(ResultCode.ERROR, "验证码不正确");
        } else {
            RestResult<UserVO> userVORestResult = userClient.getByMobile(mobile);
            if (RestResult.isSuccess(userVORestResult) && ObjectUtils.isNotEmpty(userVORestResult.getData())) {
                UserVO data = userVORestResult.getData();
                parameters.put("password", data.getPassword());
                parameters.put("username", data.getAccount());
                return parameters;
            }
            String account = RandomUtil.randomString(8);
            String password = RandomUtil.randomString(8);
            UserDTO userDTO = new UserDTO();
            userDTO.setAccount(account);
            userDTO.setPassword(password);
            userDTO.setMobile(mobile);
            userDTO.setType(platform);
            RestResult<Boolean> add = userClient.add(userDTO);
            if (!RestResult.isSuccess(add)) {
                log.error("失败：添加用户失败");
                throw new BaseException(ResultCode.ERROR, "注册失败");
            }
            userVORestResult = userClient.getByMobile(mobile);
            addBuyer(platform, userVORestResult);
            parameters.put("username", account);
            parameters.put("password", password);
        }
        return parameters;
    }

    private void registerByAccountAndPassword(Map<String, String> parameters) throws BaseException {
        String username = parameters.get("username");
        String password = parameters.get("password");
        Integer platform = Integer.valueOf(parameters.get("platform"));
        RestResult<UserVO> userVORestResult = userClient.getByAccount(username);
        if (RestResult.isSuccess(userVORestResult) || ObjectUtils.isNotEmpty(userVORestResult.getData())) {
            log.error("查询用户失败");
            throw new BaseException(ResultCode.ACCOUNT_IS_EXISTENT);
        }
        UserDTO userDTO = new UserDTO();
        userDTO.setAccount(username);
        userDTO.setPassword(password);
        userDTO.setType(platform);
        RestResult<Boolean> add = userClient.add(userDTO);
        if (!RestResult.isSuccess(add)) {
            throw new BaseException(ResultCode.ERROR, "注册失败");
        }
        userVORestResult = userClient.getByAccount(username);
        addBuyer(platform, userVORestResult);
    }

    private void addBuyer(Integer platform, RestResult<UserVO> userVORestResult) throws BaseException {
        UserVO userVO = userVORestResult.getData();
        if (Integer.valueOf(1).equals(platform)) {
            BuyerDTO buyerDTO = new BuyerDTO();
            buyerDTO.setBuyerId(userVO.getUserId());
            buyerDTO.setBuyerAccount(userVO.getAccount());
            RestResult<Boolean> result = buyerClient.add(buyerDTO);
            if (!RestResult.isSuccess(result)) {
                log.error("失败：添加买家失败");
                throw new BaseException(ResultCode.ERROR, "注册失败");
            }
        }
    }

    private Map<String, String> loginByMobile(Map<String, String> parameters, Integer platform) throws BaseException {
        String captcha = parameters.get("captcha");
        String mobile = parameters.get("mobile");
        if (ObjectUtils.isEmpty(mobile)) {
            log.error("失败：未输入手机号");
            throw new BaseException(ResultCode.AUTH_ERROR, "请输入手机号");
        }
        //TODO
        String getCaptcha = "123456";
        if (!captcha.equals(getCaptcha)) {
            throw new BaseException(ResultCode.ERROR, "验证码不正确");
        } else {
            RestResult<UserVO> userVORestResult = userClient.getByMobile(mobile);
            if (!RestResult.isSuccess(userVORestResult) && ObjectUtils.isEmpty(userVORestResult.getData())) {
                //注册
                parameters = registerByMobile(parameters);
            } else {
                UserVO userVO = userVORestResult.getData();
                if (!platform.equals(userVO.getType())) {
                    log.error("该账号已在其他端注册了");
                    throw new BaseException(ResultCode.AUTH_ERROR, "该账号已在其他端注册了");
                }
                parameters.put("password", userVO.getPassword());
                parameters.put("username", userVO.getAccount());
            }
        }
        return parameters;
    }

    private void loginByAccountAndPassword(Map<String, String> parameters, Integer platform) throws BaseException {
        String username = parameters.get("username");
        RestResult<UserVO> userVORestResult = userClient.getByAccount(username);
        if (!RestResult.isSuccess(userVORestResult)) {
            log.error("查询用户失败");
            throw new BaseException(ResultCode.ACCOUNT_NOT_EXIST);
        }
        if (ObjectUtils.isEmpty(userVORestResult.getData())) {
            log.error("用户不存在");
            throw new BaseException(ResultCode.ACCOUNT_NOT_EXIST);
        }
        UserVO userVO = userVORestResult.getData();
        if (!platform.equals(userVO.getType())) {
            log.error("该账号已在其他端注册了");
            throw new BaseException(ResultCode.AUTH_ERROR, "该账号已在其他端注册了");
        }
    }
}

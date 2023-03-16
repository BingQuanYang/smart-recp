package com.smart.recp.auth.controller;

import com.smart.recp.auth.service.AuthService;
import com.smart.recp.auth.service.UserService;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.feign.service.IUserClient;
import com.smart.recp.service.user.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.Map;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/oauth")
@Slf4j
public class AuthController {

    @Resource
    IUserClient iUserClient;

    @Resource
    AuthService authService;


    @GetMapping
    @CrossOrigin
    public String test() {
        RestResult<UserVO> admin = iUserClient.getByAccount("admin");
        System.out.println(admin);
        return "test";
    }

    @ApiOperation(value = "OAuth2认证", notes = "login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token"),
            @ApiImplicitParam(name = "username", defaultValue = "admin", value = "登录用户名"),
            @ApiImplicitParam(name = "password", defaultValue = "123456", value = "登录密码")
    })
    @PostMapping("/token")
    public RestResult<OAuth2AccessToken> postAccessToken(Principal principal, @RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException, BaseException {
        return RestResult.success(authService.login(principal, parameters));
    }

    @ApiOperation(value = "OAuth2认证", notes = "register")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "grant_type", defaultValue = "password", value = "授权模式", required = true),
            @ApiImplicitParam(name = "client_id", defaultValue = "client", value = "Oauth2客户端ID", required = true),
            @ApiImplicitParam(name = "client_secret", defaultValue = "123456", value = "Oauth2客户端秘钥", required = true),
            @ApiImplicitParam(name = "refresh_token", value = "刷新token")
    })
    @PostMapping("/register")
    public RestResult<OAuth2AccessToken> register(@RequestParam Map<String, String> parameters) throws HttpRequestMethodNotSupportedException, BaseException {
        return RestResult.success(authService.register(parameters));
    }


}
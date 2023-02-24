package com.smart.recp.service.user.feign.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service", path = "/user/user")
@Component
public interface IUserClient {
    @GetMapping("/getByAccount")
    @ApiOperation("根据账号获取用户信息")
    @ApiImplicitParam(name = "account", value = "账号")
    RestResult<UserVO> getByAccount(@RequestParam("account") String account);

    @GetMapping("/getByMobile")
    @ApiOperation("根据手机号获取用户信息")
    @ApiImplicitParam(name = "mobile", value = "手机号")
    RestResult<UserVO> getByMobile(@RequestParam("mobile") String mobile);
}

package com.smart.recp.service.user.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.dto.UserDTO;
import com.smart.recp.service.user.service.UserService;
import com.smart.recp.service.user.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/user/user")
public class UserController {
    @Resource
    UserService userService;


    @GetMapping("/getById")
    @ApiOperation("根据用户ID获取用户信息")
    @ApiImplicitParam(name = "userId", value = "用户ID")
    public RestResult<UserVO> getById(@RequestParam("userId") Integer userId) throws BaseException {
        return RestResult.success(userService.getById(userId));
    }

    @GetMapping("/getByAccount")
    @ApiOperation("根据账号获取用户信息")
    @ApiImplicitParam(name = "account", value = "账号")
    public RestResult<UserVO> getByAccount(@RequestParam("account") String account) throws BaseException {
        return RestResult.success(userService.getByAccount(account));
    }

    @GetMapping("/getByMobile")
    @ApiOperation("根据手机号获取用户信息")
    @ApiImplicitParam(name = "mobile", value = "手机号")
    public RestResult<UserVO> getByMobile(@RequestParam("mobile") String mobile) throws BaseException {
        return RestResult.success(userService.getByMobile(mobile));
    }

    @PostMapping("/add")
    @ApiOperation("添加用户信息")
    public RestResult<Boolean> add(@RequestBody UserDTO userDTO) throws BaseException {
        return RestResult.success(userService.add(userDTO));
    }


    @PutMapping("/modify")
    @ApiOperation("修改用户信息")
    public RestResult<Boolean> update(@RequestBody UserDTO userDTO) throws BaseException {
        return RestResult.success(userService.update(userDTO));
    }

}

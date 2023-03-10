package com.smart.recp.service.user.controller;

/**
 * @author ybq
 */

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.mapper.BuyerMapper;
import com.smart.recp.service.user.service.PlatformService;
import com.smart.recp.service.user.vo.BuyerVO;
import com.smart.recp.service.user.vo.CustomerFollowVO;
import com.smart.recp.service.user.vo.SellerVO;
import com.smart.recp.service.user.vo.UserVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/user/platform")
public class PlatformController {
    @Resource
    PlatformService platformService;

    @GetMapping("/list-seller")
    @ApiOperation("查询卖家列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页展示多少条")
    })
    public RestResult<PageResult<SellerVO>> listSeller(@RequestParam Integer page, @RequestParam Integer size, @RequestParam(required = false) Integer sellerId, @RequestParam(required = false) String search) throws BaseException {
        return RestResult.success(platformService.listSeller(page, size, sellerId, search));
    }

    @GetMapping("/get-seller")
    @ApiOperation("根据卖家ID获取卖家信息")
    @ApiImplicitParam(name = "sellerId", value = "卖家ID")
    public RestResult<SellerVO> getSeller(@RequestParam("sellerId") Integer sellerId) throws BaseException {
        return RestResult.success(platformService.getSellerById(sellerId));
    }

    @GetMapping("/list-buyer")
    @ApiOperation("查询买家列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页展示多少条")
    })
    public RestResult<PageResult<BuyerVO>> listBuyer(@RequestParam Integer page, @RequestParam Integer size, @RequestParam(required = false) Integer buyerId, @RequestParam(required = false) String search) throws BaseException {
        return RestResult.success(platformService.listBuyer(page, size, buyerId, search));
    }

    @GetMapping("/get-buyer")
    @ApiOperation("根据卖家ID获取卖家信息")
    @ApiImplicitParam(name = "buyerId", value = "买家ID")
    public RestResult<BuyerVO> getBuyer(@RequestParam("buyerId") Integer buyerId) throws BaseException {
        return RestResult.success(platformService.getBuyerById(buyerId));
    }


    @GetMapping("/list-user")
    @ApiOperation("查询用户列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页展示多少条")
    })
    public RestResult<PageResult<UserVO>> listUser(@RequestParam Integer page, @RequestParam Integer size, @RequestParam(required = false) Integer userId, @RequestParam(required = false) String account, @RequestParam(required = false) Integer type) throws BaseException {
        return RestResult.success(platformService.listUser(page, size, userId, account, type));
    }


    @GetMapping("/get-user")
    @ApiOperation("根据卖家ID获取卖家信息")
    @ApiImplicitParam(name = "userId", value = "用户ID")
    public RestResult<UserVO> getUser(@RequestParam("userId") Integer userId) throws BaseException {
        return RestResult.success(platformService.getUserById(userId));
    }
}

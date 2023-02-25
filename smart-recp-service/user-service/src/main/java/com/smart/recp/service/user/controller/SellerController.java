package com.smart.recp.service.user.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;

import com.smart.recp.service.user.dto.SellerDTO;
import com.smart.recp.service.user.service.SellerService;
import com.smart.recp.service.user.vo.SellerVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/seller/seller")
public class SellerController {
    @Resource
    SellerService sellerService;

    @GetMapping("/getById")
    @ApiOperation("根据卖家ID获取卖家信息")
    @ApiImplicitParam(name = "sellerId", value = "卖家ID")
    public RestResult<SellerVO> getById(@RequestParam("sellerId") Integer sellerId) throws BaseException {
        return RestResult.success(sellerService.getById(sellerId));
    }

    @GetMapping("/getByAccount")
    @ApiOperation("根据账号获取卖家信息")
    @ApiImplicitParam(name = "account", value = "账号")
    public RestResult<SellerVO> getByAccount(@RequestParam("account") String account) throws BaseException {
        return RestResult.success(sellerService.getByAccount(account));
    }


    @PostMapping("/add")
    @ApiOperation("添加卖家信息")
    public RestResult<Boolean> add(@RequestBody SellerDTO sellerDTO) throws BaseException {
        return RestResult.success(sellerService.add(sellerDTO));
    }


    @PutMapping("/modify")
    @ApiOperation("修改卖家信息")
    public RestResult<Boolean> update(@RequestBody SellerDTO sellerDTO) throws BaseException {
        return RestResult.success(sellerService.update(sellerDTO));
    }



}

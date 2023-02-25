package com.smart.recp.service.user.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.dto.BuyerDTO;
import com.smart.recp.service.user.service.BuyerService;
import com.smart.recp.service.user.vo.BuyerVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/user/buyer")
public class BuyerController {
    @Resource
    BuyerService buyerService;

    @GetMapping("/getById")
    @ApiOperation("根据买家ID获取买家信息")
    @ApiImplicitParam(name = "buyerId", value = "买家ID")
    public RestResult<BuyerVO> getById(@RequestParam("buyerId") Integer buyerId) throws BaseException {
        return RestResult.success(buyerService.getById(buyerId));
    }

    @GetMapping("/getByAccount")
    @ApiOperation("根据账号获取买家信息")
    @ApiImplicitParam(name = "account", value = "账号")
    public RestResult<BuyerVO> getByAccount(@RequestParam("account") String account) throws BaseException {
        return RestResult.success(buyerService.getByAccount(account));
    }


    @PostMapping("/add")
    @ApiOperation("添加买家信息")
    public RestResult<Boolean> add(@RequestBody BuyerDTO buyerDTO) throws BaseException {
        return RestResult.success(buyerService.add(buyerDTO));
    }


    @PutMapping("/modify")
    @ApiOperation("修改买家信息")
    public RestResult<Boolean> update(@RequestBody BuyerDTO buyerDTO) throws BaseException {
        return RestResult.success(buyerService.update(buyerDTO));
    }
}

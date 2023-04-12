package com.smart.recp.service.user.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.dto.CustomerFollowDTO;
import com.smart.recp.service.user.service.CustomerFollowService;
import com.smart.recp.service.user.vo.CustomerFollowVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/user/follow")
public class CustomerFollowController {
    @Resource
    CustomerFollowService customerFollowService;

    @GetMapping("/get")
    @ApiOperation("根据ID获取客户关注信息")
    @ApiImplicitParam(name = "followId", value = "关注ID")
    public RestResult<CustomerFollowVO> get(@RequestParam Integer followId) throws BaseException {
        return RestResult.success(customerFollowService.getById(followId));
    }

    @PostMapping("/add")
    @ApiOperation("添加客户关注信息")
    public RestResult<Boolean> add(@RequestBody CustomerFollowDTO customerFollowDTO) throws BaseException {
        return RestResult.success(customerFollowService.add(customerFollowDTO));
    }

    @PutMapping("/modify")
    @ApiOperation("修改客户关注信息")
    public RestResult<Boolean> modify(@RequestBody CustomerFollowDTO customerFollowDTO) throws BaseException {
        return RestResult.success(customerFollowService.update(customerFollowDTO));
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除客户关注信息")
    @ApiImplicitParam(name = "followIdList", value = "关注ID列表")
    public RestResult<Integer> remove(@RequestParam List<Integer> followIdList) throws BaseException {
        return RestResult.success(customerFollowService.deleteByIdList(followIdList));
    }

    @GetMapping("/list")
    @ApiOperation("查询客户关注列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页展示多少条")
    })
    public RestResult<PageResult<CustomerFollowVO>> list(@RequestParam Integer page, @RequestParam Integer size) throws BaseException {
        return RestResult.success(customerFollowService.list(page, size));
    }


    @GetMapping("/seller/customer")
    @ApiOperation("根据卖家ID查客户关注列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页展示多少条"),
            @ApiImplicitParam(name = "sellerId", value = "卖家ID")
    })
    public RestResult<PageResult<CustomerFollowVO>> customer(@RequestParam Integer page, @RequestParam Integer size, @RequestParam Integer sellerId) throws BaseException {
        return RestResult.success(customerFollowService.listBySellerId(page, size, sellerId));
    }

    @GetMapping("/buyer/follow")
    @ApiOperation("根据买家ID查关注列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页展示多少条"),
            @ApiImplicitParam(name = "buyerId", value = "买家ID")
    })
    public RestResult<PageResult<CustomerFollowVO>> follow(@RequestParam Integer page, @RequestParam Integer size, @RequestParam Integer buyerId) throws BaseException {
        return RestResult.success(customerFollowService.listByBuyerId(page, size, buyerId));
    }

    @GetMapping("/buyer/get")
    @ApiOperation("根据买家和卖家ID获取关注信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buyerId", value = "买家ID"),
            @ApiImplicitParam(name = "sellerId", value = "卖家ID")
    })
    public RestResult<CustomerFollowVO> followOne(@RequestParam Integer buyerId, @RequestParam Integer sellerId) throws BaseException {
        return RestResult.success(customerFollowService.getByBuyerIdAndSellerId(buyerId, sellerId));
    }
}

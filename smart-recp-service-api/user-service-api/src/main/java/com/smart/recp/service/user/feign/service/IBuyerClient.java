package com.smart.recp.service.user.feign.service;

import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.dto.BuyerDTO;
import com.smart.recp.service.user.vo.BuyerVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-service", path = "/user/buyer",contextId = "BuyerClient")
@Component
public interface IBuyerClient {

    @GetMapping("/getById")
    @ApiOperation("根据买家ID获取买家信息")
    @ApiImplicitParam(name = "buyerId", value = "买家ID")
    RestResult<BuyerVO> getById(@RequestParam("buyerId") Integer buyerId);

    @GetMapping("/getByAccount")
    @ApiOperation("根据账号获取买家信息")
    @ApiImplicitParam(name = "account", value = "账号")
    RestResult<BuyerVO> getByAccount(@RequestParam("account") String account);

    @PostMapping("/add")
    @ApiOperation("添加买家信息")
    RestResult<Boolean> add(@RequestBody BuyerDTO buyerDTO);

    @PutMapping("/modify")
    @ApiOperation("修改买家信息")
    RestResult<Boolean> update(@RequestBody BuyerDTO buyerDTO);
}

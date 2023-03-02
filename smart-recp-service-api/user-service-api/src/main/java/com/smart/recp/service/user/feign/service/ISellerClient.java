package com.smart.recp.service.user.feign.service;


import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.dto.SellerDTO;
import com.smart.recp.service.user.vo.SellerVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "user-service", path = "/user/seller",contextId = "SellerClient")
@Component
public interface ISellerClient {

    @GetMapping("/getById")
    @ApiOperation("根据卖家ID获取卖家信息")
    @ApiImplicitParam(name = "sellerId", value = "卖家ID")
    RestResult<SellerVO> getById(@RequestParam("sellerId") Integer sellerId);

    @GetMapping("/getByAccount")
    @ApiOperation("根据账号获取卖家信息")
    @ApiImplicitParam(name = "account", value = "账号")
    RestResult<SellerVO> getByAccount(@RequestParam("account") String account);

    @PostMapping("/add")
    @ApiOperation("添加卖家信息")
    RestResult<Boolean> add(@RequestBody SellerDTO sellerDTO);

    @PutMapping("/modify")
    @ApiOperation("修改卖家信息")
    RestResult<Boolean> update(@RequestBody SellerDTO sellerDTO);
}

package com.smart.recp.service.user.feign.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.vo.ReceiveAddressVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user-service", path = "/user/receive-address/")
@Component
public interface IReceiveAddressClient {

    @GetMapping("/get")
    @ApiOperation("跟ID获取收货地址信息")
    @ApiImplicitParam(name = "receiveId", value = "收货地址ID")
    RestResult<ReceiveAddressVO> get(@RequestParam Integer receiveId);
}

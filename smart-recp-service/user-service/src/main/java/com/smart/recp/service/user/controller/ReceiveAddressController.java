package com.smart.recp.service.user.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.user.dto.ReceiveAddressDTO;
import com.smart.recp.service.user.service.ReceiveAddressService;
import com.smart.recp.service.user.vo.ReceiveAddressVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/user/receive-address/")
public class ReceiveAddressController {
    @Resource
    ReceiveAddressService receiveAddressService;

    @GetMapping("/get")
    @ApiOperation("跟ID获取收货地址信息")
    @ApiImplicitParam(name = "receiveId", value = "收货地址ID")
    public RestResult<ReceiveAddressVO> get(@RequestParam Integer receiveId) throws BaseException {
        return RestResult.success(receiveAddressService.getById(receiveId));
    }

    @PostMapping("/add")
    @ApiOperation("添加收货地址")
    public RestResult<Boolean> add(@RequestBody ReceiveAddressDTO receiveAddressDTO) throws BaseException {
        return RestResult.success(receiveAddressService.add(receiveAddressDTO));
    }

    @PutMapping("/modify")
    @ApiOperation("修改收货地址")
    public RestResult<Boolean> modify(@RequestBody ReceiveAddressDTO receiveAddressDTO) throws BaseException {
        return RestResult.success(receiveAddressService.update(receiveAddressDTO));
    }

    @DeleteMapping("/remove")
    @ApiOperation("删除收货地址")
    public RestResult<Integer> remove(@RequestParam List<Integer> receiveIdList) throws BaseException {
        return RestResult.success(receiveAddressService.deleteByIdList(receiveIdList));
    }

    @GetMapping("/list")
    @ApiOperation("根据买家ID获取收货地址列表")
    public RestResult<List<ReceiveAddressVO>> list(@RequestParam Integer buyerId) throws BaseException {
        return RestResult.success(receiveAddressService.listByBuyerId(buyerId));
    }
}

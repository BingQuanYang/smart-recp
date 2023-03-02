package com.smart.recp.service.order.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.order.dto.OrderCartDTO;
import com.smart.recp.service.order.service.CartService;
import com.smart.recp.service.order.vo.OrderCartVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/order/cart")
public class CartController {
    @Resource
    CartService cartService;

    @GetMapping("/get")
    @ApiOperation("根据ID获取购物车信息")
    @ApiImplicitParam(name = "cartId", value = "购物车ID")
    public RestResult<OrderCartVO> get(@RequestParam Integer cartId) throws BaseException {
        return RestResult.success(cartService.getById(cartId));
    }


    @PostMapping("/add")
    @ApiOperation("添加购物车")
    public RestResult<Boolean> add(@RequestBody OrderCartDTO orderCartDTO) throws BaseException {
        return RestResult.success(cartService.add(orderCartDTO));
    }


    @PutMapping("/modify")
    @ApiOperation("修改购物车")
    public RestResult<Boolean> modify(@RequestBody OrderCartDTO orderCartDTO) throws BaseException {
        return RestResult.success(cartService.update(orderCartDTO));
    }

    @DeleteMapping("/remove")
    @ApiOperation("根据购物车ID列表删除购物车")
    @ApiImplicitParam(name = "cartIdList", value = "购物车ID列表")
    public RestResult<Integer> remove(@RequestParam List<Integer> cartIdList) throws BaseException {
        return RestResult.success(cartService.deleteByIdList(cartIdList));
    }

    @GetMapping("/list")
    @ApiOperation("根据买家ID获取购物车列表")
    @ApiImplicitParam(name = "buyerId", value = "买家ID")
    public RestResult<List<OrderCartVO>> list(@RequestParam Integer buyerId) throws BaseException {
        return RestResult.success(cartService.listByBuyerId(buyerId));
    }


}

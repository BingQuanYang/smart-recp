package com.smart.recp.service.order.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.order.dto.OrderItemDTO;
import com.smart.recp.service.order.service.OrderSellerService;
import com.smart.recp.service.order.vo.OrderItemVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/order/seller")
public class OrderSellerController {
    @Resource
    OrderSellerService orderSellerService;

    @GetMapping("/get")
    @ApiOperation("根据ID获取订单信息")
    @ApiImplicitParam(name = "orderItemId", value = "订单ID")
    public RestResult<OrderItemVO> get(@RequestParam Integer orderItemId) throws BaseException {
        return RestResult.success(orderSellerService.getById(orderItemId));
    }

    @GetMapping("/list")
    @ApiOperation("根据ID获取订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页多少条"),
            @ApiImplicitParam(name = "sellerId", value = "卖家ID"),
            @ApiImplicitParam(name = "delivery_status", value = "配送状态"),
            @ApiImplicitParam(name = "customer_status", value = "卖家状态"),
    })
    public RestResult<PageResult<OrderItemVO>> list(@RequestParam Integer page, @RequestParam Integer size, @RequestParam Integer sellerId, @RequestParam(required = false) Integer deliveryStatus, @RequestParam(required = false) Integer customerStatus) throws BaseException {
        return RestResult.success(orderSellerService.list(page, size, sellerId, deliveryStatus, customerStatus));
    }


    @PutMapping("/modify")
    @ApiOperation("修改订单")
    public RestResult<Boolean> modify(@RequestBody OrderItemDTO orderItemDTO) throws BaseException {
        return RestResult.success(orderSellerService.update(orderItemDTO));
    }

    @PutMapping("/delivery")
    @ApiOperation("发货")
    public RestResult<Boolean> delivery(@RequestBody OrderItemDTO orderItemDTO) throws BaseException {
        return RestResult.success(orderSellerService.delivery(orderItemDTO));
    }
}

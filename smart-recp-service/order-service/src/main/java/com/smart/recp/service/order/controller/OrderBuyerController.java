package com.smart.recp.service.order.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.order.dto.GenerateOrderDTO;
import com.smart.recp.service.order.service.OrderBuyerService;
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
@RequestMapping("/order/buyer")
public class OrderBuyerController {
    @Resource
    OrderBuyerService orderBuyerService;

    @GetMapping("/get")
    @ApiOperation("根据ID获取订单信息")
    @ApiImplicitParam(name = "orderItemId", value = "订单ID")
    public RestResult<OrderItemVO> get(@RequestParam Integer orderItemId) throws BaseException {
        return RestResult.success(orderBuyerService.getById(orderItemId));
    }

    @GetMapping("/list")
    @ApiOperation("根据ID获取订单信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页多少条"),
            @ApiImplicitParam(name = "buyerId", value = "买家ID"),
            @ApiImplicitParam(name = "delivery_status", value = "配送状态"),
            @ApiImplicitParam(name = "customer_status", value = "卖家状态"),
    })
    public RestResult<PageResult<OrderItemVO>> list(@RequestParam Integer page, @RequestParam Integer size, @RequestParam Integer buyerId, @RequestParam Integer delivery_status, @RequestParam Integer customer_status) throws BaseException {
        return RestResult.success(orderBuyerService.list(page, size, buyerId, delivery_status, customer_status));
    }


    @PostMapping("/generate -order")
    @ApiOperation("生成订单")
    public RestResult<Integer> generateOrder(@RequestBody GenerateOrderDTO generateOrderDTO) throws BaseException {
        return RestResult.success(orderBuyerService.generateOrder(generateOrderDTO));
    }
}

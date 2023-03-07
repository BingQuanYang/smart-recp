package com.smart.recp.service.order.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.order.dto.GenerateOrderDTO;
import com.smart.recp.service.order.service.OrderBuyerService;
import com.smart.recp.service.order.vo.OrderItemVO;
import com.smart.recp.service.order.vo.OrderVO;
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
    public RestResult<PageResult<OrderItemVO>> list(@RequestParam Integer page, @RequestParam Integer size, @RequestParam Integer buyerId, @RequestParam(required = false) Integer delivery_status, @RequestParam(required = false) Integer customer_status) throws BaseException {
        return RestResult.success(orderBuyerService.list(page, size, buyerId, delivery_status, customer_status));
    }


    @PostMapping("/generate-order")
    @ApiOperation("生成订单")
    public RestResult<Integer> generateOrder(@RequestBody GenerateOrderDTO generateOrderDTO) throws BaseException {
        return RestResult.success(orderBuyerService.generateOrder(generateOrderDTO));
    }


    @GetMapping("/pending-payment")
    @ApiOperation("根据ID获取订单详情信息")
    @ApiImplicitParam(name = "orderId", value = "订单ID")
    public RestResult<OrderVO> getPendingPayOrderById(@RequestParam Integer orderId) throws BaseException {
        return RestResult.success(orderBuyerService.getPendingPayOrderById(orderId));
    }


    @PutMapping("/pay")
    @ApiOperation("根据买家ID获取购物车列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "buyerId", value = "买家ID"),
            @ApiImplicitParam(name = "orderId", value = "订单ID")
    })
    public RestResult<Boolean> pay(@RequestParam("buyerId") Integer buyerId, @RequestParam("orderId") Integer orderId) throws BaseException {
        return RestResult.success(orderBuyerService.pay(buyerId, orderId));
    }


    @GetMapping("/list-pending-pay")
    @ApiOperation("获取订单列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页多少天数据"),
            @ApiImplicitParam(name = "buyerId", value = "买家ID"),
    })
    public RestResult<PageResult<OrderItemVO>> listPendingPay(@RequestParam Integer page, @RequestParam Integer size, @RequestParam Integer buyerId) throws BaseException {
        return RestResult.success(orderBuyerService.listPendingPay(page, size, buyerId));
    }


}

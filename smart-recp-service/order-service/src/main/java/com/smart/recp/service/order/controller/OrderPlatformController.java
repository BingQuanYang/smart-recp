package com.smart.recp.service.order.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.order.service.OrderPlatformService;
import com.smart.recp.service.order.vo.OrderVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@RestController
@RequestMapping("/order/platform")
public class OrderPlatformController {
    @Resource
    OrderPlatformService orderPlatformService;

    @GetMapping("/get")
    @ApiOperation("根据ID获取订单详情信息")
    @ApiImplicitParam(name = "orderId", value = "订单ID")
    public RestResult<OrderVO> get(@RequestParam Integer orderId) throws BaseException {
        return RestResult.success(orderPlatformService.getCascadeById(orderId));
    }


    @GetMapping("/list")
    @ApiOperation("获取订单列表信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "一页多少天数据")
    })
    public RestResult<PageResult<OrderVO>> list(@RequestParam Integer page, @RequestParam Integer size) throws BaseException {
        return RestResult.success(orderPlatformService.list(page, size));
    }

}

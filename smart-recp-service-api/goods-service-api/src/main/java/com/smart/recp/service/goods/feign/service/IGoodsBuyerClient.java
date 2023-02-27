package com.smart.recp.service.goods.feign.service;


import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.goods.vo.GoodsVO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Component
@FeignClient(value = "goods-service", path = "/goods/buyer")
public interface IGoodsBuyerClient {

    @GetMapping("/get")
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParam(name = "goodsId", value = "商品ID")
    RestResult<GoodsVO> get(@RequestParam Integer goodsId);
}

package com.smart.recp.service.goods.feign.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.goods.dto.GoodsSpecDTO;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "goods-service", path = "/goods/stock",contextId = "StockClient")
@Component
public interface IStockClient {

    @PutMapping("/subtract")
    @ApiOperation("减库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "specId", value = "商品规格ID"),
            @ApiImplicitParam(name = "stock", value = "库存")
    })
    RestResult<Boolean> subtract(@RequestParam Integer specId, @RequestParam Integer stock);

    @PutMapping("/modify")
    @ApiOperation("修改库存")
    RestResult<Boolean> modify(@RequestBody GoodsSpecDTO goodsSpecDTO);
}

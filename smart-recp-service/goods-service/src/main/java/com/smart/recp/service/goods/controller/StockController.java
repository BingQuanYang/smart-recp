package com.smart.recp.service.goods.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.dto.GoodsSpecDTO;
import com.smart.recp.service.goods.service.StockService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author ybq
 */

@RestController
@RequestMapping("/goods/stock")
public class StockController {
    @Resource
    StockService stockService;

    @PutMapping("/subtract")
    @ApiOperation("减库存")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "specId", value = "商品规格ID"),
            @ApiImplicitParam(name = "stock", value = "库存")
    })
    public RestResult<Boolean> subtract(@RequestParam Integer specId, @RequestParam Integer stock) throws BaseException {
        return RestResult.success(stockService.subtract(specId, stock));
    }

    @PutMapping("/modify")
    @ApiOperation("修改库存")
    public RestResult<Boolean> modify(@RequestBody GoodsSpecDTO goodsSpecDTO) throws BaseException {
        return RestResult.success(stockService.update(goodsSpecDTO));
    }

}

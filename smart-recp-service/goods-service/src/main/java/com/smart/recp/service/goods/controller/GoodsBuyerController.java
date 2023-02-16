package com.smart.recp.service.goods.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.service.GoodsBuyerService;
import com.smart.recp.service.goods.service.GoodsSellerService;
import com.smart.recp.service.goods.vo.GoodsCategoryVO;
import com.smart.recp.service.goods.vo.GoodsVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 买家端
 *
 * @author ybq
 */
@Api(tags = "买家端")
@RestController
@RequestMapping("/goods/buyer")
public class GoodsBuyerController {
    @Resource
    GoodsBuyerService goodsBuyerService;

    @GetMapping("/list/")
    @ApiOperation(value = "获取商品列表")
    public RestResult<PageResult<GoodsVO>> list() throws BaseException {
        return RestResult.success(goodsBuyerService.list());
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParam(name = "goodsId", value = "商品ID")
    public RestResult<GoodsVO> get(@RequestParam Integer goodsId) throws BaseException {
        return RestResult.success(goodsBuyerService.getByGoodsId(goodsId));
    }


    @GetMapping("/category")
    @ApiOperation(value = "获取商品分类信息")
    public RestResult<List<GoodsCategoryVO>> category() throws BaseException {
        return RestResult.success(goodsBuyerService.category());
    }

}

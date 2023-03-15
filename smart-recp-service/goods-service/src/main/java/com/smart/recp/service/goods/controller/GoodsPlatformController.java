package com.smart.recp.service.goods.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.goods.dto.GoodsCategoryDTO;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.service.GoodsBuyerService;
import com.smart.recp.service.goods.service.GoodsPlatformService;
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
 * 平台端
 *
 * @author ybq
 */
@Api(tags = "平台端")
@RestController
@RequestMapping("/goods/platform")
public class GoodsPlatformController {
    @Resource
    GoodsPlatformService goodsPlatformService;

    @GetMapping("/list/")
    @ApiOperation(value = "获取商品列表")
    public RestResult<PageResult<GoodsVO>> list() throws BaseException {
        return RestResult.success(goodsPlatformService.list());
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParam(name = "goodsId", value = "商品ID")
    public RestResult<GoodsVO> get(@RequestParam Integer goodsId) throws BaseException {
        return RestResult.success(goodsPlatformService.getByGoodsId(goodsId));
    }


    @PutMapping("/modify")
    @ApiOperation(value = "修改商品信息")
    public RestResult<Boolean> modify(@RequestBody GoodsDTO goodsDTO) throws BaseException {
        return RestResult.success(goodsPlatformService.modify(goodsDTO));
    }


    @GetMapping("/category")
    @ApiOperation(value = "获取商品分类信息")
    public RestResult<List<GoodsCategoryVO>> category() throws BaseException {
        return RestResult.success(goodsPlatformService.category());
    }


    @PutMapping("/lower")
    @ApiOperation(value = "下架架商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID")
    })
    public RestResult<Boolean> lowerGoods(@RequestParam Integer goodsId) throws BaseException {
        return RestResult.success(goodsPlatformService.lowerByGoodsId(goodsId));
    }


    @PutMapping("/approved")
    @ApiOperation(value = "审核通过商品")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "goodsId", value = "商品ID")
    })
    public RestResult<Boolean> approvedGoods(@RequestParam Integer goodsId) throws BaseException {
        return RestResult.success(goodsPlatformService.approvedByGoodsId(goodsId));
    }

    @PostMapping("/add-category")
    @ApiOperation(value = "添加商品分类信息")
    public RestResult<Boolean> addCategory(@RequestBody GoodsCategoryDTO goodsCategoryDTO) throws BaseException {
        return RestResult.success(goodsPlatformService.addCategory(goodsCategoryDTO));
    }


    @PutMapping("/modify-category")
    @ApiOperation(value = "修改商品分类信息")
    public RestResult<Boolean> modifyCategory(@RequestBody GoodsCategoryDTO goodsCategoryDTO) throws BaseException {
        return RestResult.success(goodsPlatformService.modifyCategory(goodsCategoryDTO));
    }


    @DeleteMapping("/remove-category")
    @ApiOperation(value = "修改商品分类信息")
    public RestResult<Integer> removeCategory(@RequestParam List<Integer> categoryIdList) throws BaseException {
        return RestResult.success(goodsPlatformService.removeCategory(categoryIdList));
    }

    @GetMapping("/get-category")
    @ApiOperation(value = "根据Id获取商品分类信息")
    @ApiImplicitParam(name = "categoryId", value = "分类ID")
    public RestResult<GoodsCategoryVO> getCategory(@RequestParam Integer categoryId) throws BaseException {
        return RestResult.success(goodsPlatformService.getCategoryById(categoryId));
    }

}

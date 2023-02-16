package com.smart.recp.service.goods.controller;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
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
 * 卖家端
 *
 * @author ybq
 */
@Api(tags = "卖家端")
@RestController
@RequestMapping("/goods/seller")
public class GoodsSellerController {

    @Resource
    GoodsSellerService goodsSellerService;

    @GetMapping("/list/")
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParam(name = "sellerId", value = "卖家ID")
    public RestResult<PageResult<GoodsVO>> list(@RequestParam Integer sellerId) throws BaseException {
        return RestResult.success(goodsSellerService.list(sellerId));
    }

    @GetMapping("/get")
    @ApiOperation(value = "获取商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sellerId", value = "卖家ID"),
            @ApiImplicitParam(name = "goodsId", value = "商品ID")
    })
    public RestResult<GoodsVO> get(@RequestParam Integer sellerId, @RequestParam Integer goodsId) throws BaseException {
        return RestResult.success(goodsSellerService.getByGoodsId(sellerId, goodsId));
    }


    @PostMapping("/add")
    @ApiOperation(value = "添加商品信息")
    public RestResult<Boolean> add(@RequestBody GoodsDTO goodsDTO) throws BaseException {
        return RestResult.success(goodsSellerService.add(goodsDTO));
    }

    @PutMapping("/modify")
    @ApiOperation(value = "修改商品信息")
    public RestResult<Boolean> modify(@RequestBody GoodsDTO goodsDTO) throws BaseException {
        return RestResult.success(goodsSellerService.modify(goodsDTO));
    }

    @DeleteMapping("/delete")
    @ApiOperation(value = "删除商品信息")
    @ApiImplicitParam(name = "goodsIdList", value = "商品ID列表")
    public RestResult<Integer> delete(@RequestParam("goodsIdList") List<Integer> goodsIdList) throws BaseException {
        return RestResult.success(goodsSellerService.delete(goodsIdList));
    }

    @GetMapping("/category")
    @ApiOperation(value = "获取商品分类信息")
    public RestResult<List<GoodsCategoryVO>> category() throws BaseException {
        return RestResult.success(goodsSellerService.category());
    }
}

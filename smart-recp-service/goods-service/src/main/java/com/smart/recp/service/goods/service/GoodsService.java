package com.smart.recp.service.goods.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.vo.GoodsCategoryVO;
import com.smart.recp.service.goods.vo.GoodsResourceVO;
import com.smart.recp.service.goods.vo.GoodsVO;

import java.util.List;

public interface GoodsService {

    /**
     * 获取商品列表
     *
     * @param page     当前页
     * @param size     一页多少条数据
     * @param goodsDTO 商品信息
     * @return
     * @throws BaseException
     */
    PageResult<GoodsVO> list(int page, int size, GoodsDTO goodsDTO) throws BaseException;

    /**
     * 根据商品ID获取商品详情信息
     * 级联
     *
     * @param goodsId 　商品ID
     * @return
     */
    GoodsVO getCascadeByGoodsId(Integer goodsId) throws BaseException;

    /**
     * 添加商品信息
     *
     * @param goodsDTO
     * @return
     */
    Boolean add(GoodsDTO goodsDTO) throws BaseException;

    /**
     * 修改商品信息
     *
     * @param goodsDTO
     * @return
     */
    Boolean update(GoodsDTO goodsDTO) throws BaseException;

    /**
     * 删除商品信息
     *
     * @param goodsIdList 商品ID列表
     * @return
     */
    Integer delete(List<Integer> goodsIdList) throws BaseException;

    /**
     * 根据商品Id列表和是否为主图获取商品资源信息
     *
     * @param goodsIdList 商品Id列表
     * @param isMaster    商品Id列表
     * @return
     */
    List<GoodsResourceVO> getResourceByGoodsIdListAndIsMaster(List<Integer> goodsIdList, int isMaster) throws BaseException;

    /**
     * 获取商品分类信息
     *
     * @return
     */
    List<GoodsCategoryVO> getCategoryCascade() throws BaseException;

    Boolean updateById(GoodsDTO goodsDTO) throws BaseException;
}

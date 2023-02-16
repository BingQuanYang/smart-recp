package com.smart.recp.service.goods.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.vo.GoodsCategoryVO;
import com.smart.recp.service.goods.vo.GoodsVO;
import io.swagger.models.auth.In;

import java.util.List;

/**
 * 卖家端
 */
public interface GoodsSellerService {
    /**
     * 获取商品列表
     *
     * @param sellerId 卖家ID
     * @return
     * @throws BaseException
     */
    PageResult<GoodsVO> list(Integer sellerId) throws BaseException;

    /**
     * @param sellerId 卖家ID
     * @param goodsId  商品ID
     * @return
     */
    GoodsVO getByGoodsId(Integer sellerId, Integer goodsId) throws BaseException;

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
    Boolean modify(GoodsDTO goodsDTO) throws BaseException;

    /**
     * 删除商品信息
     *
     * @param goodsIdList 商品ID列表
     * @return
     */
    Integer delete(List<Integer> goodsIdList) throws BaseException;


    /**
     * 获取商品分类信息
     *
     * @return
     */
    List<GoodsCategoryVO> category() throws BaseException;
}

package com.smart.recp.service.goods.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.goods.vo.GoodsCategoryVO;
import com.smart.recp.service.goods.vo.GoodsVO;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface GoodsBuyerService {
    /**
     * 获取商品列表
     *
     * @return
     */
    PageResult<GoodsVO> list(Integer categoryId, String search) throws BaseException;

    /**
     * 根据ID获取商品信息
     *
     * @return
     */
    GoodsVO getByGoodsId(Integer goodsId) throws BaseException;

    /**
     * 获取商品分类信息
     *
     * @return
     */
    List<GoodsCategoryVO> category() throws BaseException;
}

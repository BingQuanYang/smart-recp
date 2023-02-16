package com.smart.recp.service.goods.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.service.GoodsPlatformService;
import com.smart.recp.service.goods.service.GoodsService;
import com.smart.recp.service.goods.vo.GoodsCategoryVO;
import com.smart.recp.service.goods.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ybq
 */
@Service
@Slf4j
public class GoodsPlatformServiceImpl implements GoodsPlatformService {
    @Resource
    GoodsService goodsService;

    /**
     * 获取商品列表
     *
     * @return
     */
    @Override
    public PageResult<GoodsVO> list() throws BaseException {
        return goodsService.list(1, 10, null);
    }


    /**
     * 根据ID获取商品信息
     *
     * @return
     */
    @Override
    public GoodsVO getByGoodsId(Integer goodsId) throws BaseException {
        return goodsService.getCascadeByGoodsId(goodsId);
    }

    /**
     * 修改商品信息
     *
     * @param goodsDTO
     * @return
     */
    @Override
    public Boolean modify(GoodsDTO goodsDTO) throws BaseException {
        return goodsService.update(goodsDTO);
    }

    /**
     * 获取商品分类信息
     *
     * @return
     */
    @Override
    public List<GoodsCategoryVO> category() throws BaseException {
        return goodsService.getCategoryCascade();
    }
}

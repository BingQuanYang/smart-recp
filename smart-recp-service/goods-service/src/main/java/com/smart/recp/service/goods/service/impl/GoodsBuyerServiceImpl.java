package com.smart.recp.service.goods.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.service.GoodsBuyerService;
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
public class GoodsBuyerServiceImpl implements GoodsBuyerService {

    @Resource
    GoodsService goodsService;

    /**
     * 获取商品列表
     *
     * @return
     */
    @Override
    public PageResult<GoodsVO> list() throws BaseException {
        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setStatus(1);
        PageResult<GoodsVO> goodsVOPageResult = goodsService.list(1, 10, goodsDTO);
        return goodsVOPageResult;
    }

    /**
     * 根据ID获取商品信息
     *
     * @return
     */
    @Override
    public GoodsVO getByGoodsId(Integer goodsId) throws BaseException {
        try {
            GoodsVO goodsVO = goodsService.getCascadeByGoodsId(goodsId);
            if (!goodsVO.getStatus().equals(1)) {
                log.error("买家端：商品未上架 => goodsId：{},goodsVO:{}", goodsId, goodsVO);
                throw new Exception();
            }
            return goodsVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("买家端：根据商品ID获取商品信息失败 => goodsId:{}", goodsId);
            throw new BaseException(ResultCode.ERROR);
        }
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

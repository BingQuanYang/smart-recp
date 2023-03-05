package com.smart.recp.service.goods.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.service.GoodsBuyerService;
import com.smart.recp.service.goods.service.GoodsService;
import com.smart.recp.service.goods.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        //TODO
        PageResult<GoodsVO> goodsVOPageResult = goodsService.list(1, 10, goodsDTO);
        if (ObjectUtils.isNotEmpty(goodsVOPageResult.getList())) {
            List<Integer> goodsIdList = goodsVOPageResult.getList().stream().map(item -> item.getGoodsId()).collect(Collectors.toList());

            List<GoodsResourceVO> goodsResourceVOList = goodsService.getResourceByGoodsIdListAndIsMaster(goodsIdList, 1);

            Map<Integer, String> map = goodsResourceVOList.stream().collect(Collectors.toMap(GoodsResourceVO::getGoodsId, GoodsResourceVO::getLink, (k, v) -> v));

            goodsVOPageResult.getList().forEach(item -> {
                item.setImage(map.get(item.getGoodsId()));
            });

            for (GoodsVO goodsVO : goodsVOPageResult.getList()) {
                goodsVO.setImage(map.get(goodsVO.getGoodsId()));
                GoodsVO vo = goodsService.getCascadeByGoodsId(goodsVO.getGoodsId());
                BigDecimal minPrice = null;
                minPrice = getMinPrice(vo, minPrice);
                goodsVO.setMinPrice(minPrice);
            }
        }
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
            BigDecimal minPrice = null;
            minPrice = getMinPrice(goodsVO, minPrice);
            goodsVO.setMinPrice(minPrice);
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

    private BigDecimal getMinPrice(GoodsVO goodsVO, BigDecimal minPrice) {
        for (GoodsSpecVO goodsSpecVO : goodsVO.getGoodsSpecVOList()) {
            for (GoodsSpecPriceVO goodsSpecPriceVO : goodsSpecVO.getGoodsSpecPriceVOList()) {
                if (minPrice == null || minPrice.compareTo(goodsSpecPriceVO.getPrice()) == 1) {
                    minPrice = goodsSpecPriceVO.getPrice();
                }
            }
        }
        return minPrice;
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

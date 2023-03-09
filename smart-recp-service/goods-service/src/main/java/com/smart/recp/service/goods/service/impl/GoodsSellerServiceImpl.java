package com.smart.recp.service.goods.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.service.GoodsSellerService;
import com.smart.recp.service.goods.service.GoodsService;
import com.smart.recp.service.goods.vo.GoodsCategoryVO;
import com.smart.recp.service.goods.vo.GoodsResourceVO;
import com.smart.recp.service.goods.vo.GoodsVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author ybq
 */
@Service
@Slf4j
public class GoodsSellerServiceImpl implements GoodsSellerService {


    @Resource
    GoodsService goodsService;

    /**
     * 获取商品列表
     *
     * @param sellerId 商家ID
     * @return
     * @throws BaseException
     */
    @Override
    public PageResult<GoodsVO> list(Integer sellerId) throws BaseException {

        GoodsDTO goodsDTO = new GoodsDTO();
        goodsDTO.setSellerId(sellerId);
        //TODO
        PageResult<GoodsVO> goodsVOPageResult = goodsService.list(1, 10, goodsDTO);
        if (ObjectUtils.isNotEmpty(goodsVOPageResult.getList())) {
            List<Integer> goodsIdList = goodsVOPageResult.getList().stream().map(item -> item.getGoodsId()).collect(Collectors.toList());

            List<GoodsResourceVO> goodsResourceVOList = goodsService.getResourceByGoodsIdListAndIsMaster(goodsIdList, 1);

            Map<Integer, String> map = goodsResourceVOList.stream().collect(Collectors.toMap(GoodsResourceVO::getGoodsId, GoodsResourceVO::getLink, (k, v) -> v));

            goodsVOPageResult.getList().forEach(item -> item.setImage(map.get(item.getGoodsId())));
        }
        return goodsVOPageResult;
    }


    /**
     * @param sellerId 卖家ID
     * @param goodsId  商品ID
     * @return
     */
    @Override
    public GoodsVO getByGoodsId(Integer sellerId, Integer goodsId) throws BaseException {
        try {
            GoodsVO goodsVO = goodsService.getCascadeByGoodsId(goodsId);
            if (!goodsVO.getSellerId().equals(sellerId)) {
                log.error("卖家根据商品ID获取商品信息失败，卖家ID不匹配: sellerId：{},goodsVO:{}", sellerId, goodsVO);
                throw new Exception();
            }
            return goodsVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("卖家根据商品ID获取商品信息失败: sellerId：{},goodsId:{}", sellerId, goodsId);
            throw new BaseException(ResultCode.ERROR);
        }
    }

    /**
     * 添加商品信息
     *
     * @param goodsDTO
     * @return
     */
    @Override
    public Boolean add(GoodsDTO goodsDTO) throws BaseException {
        return goodsService.add(goodsDTO);
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
     * 删除商品信息
     *
     * @param goodsIdList 商品ID列表
     * @return
     */
    @Override
    public Integer delete(List<Integer> goodsIdList) throws BaseException {
        return goodsService.delete(goodsIdList);
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

    @Override
    public Boolean upperByGoodsId(Integer goodsId) throws BaseException {
        try {
            GoodsVO byGoodsId = goodsService.getCascadeByGoodsId(goodsId);
            if (ObjectUtils.isEmpty(byGoodsId)) {
                log.error("失败：【upperByGoodsId】商品不存在");
                throw new BaseException(ResultCode.ERROR);
            }
            if (!Integer.valueOf(0).equals(byGoodsId.getStatus())) {
                log.error("失败：【upperByGoodsId】商品不是未上架状态");
                throw new BaseException(ResultCode.ERROR.getStatus(), "商品不是未上架状态");
            }
            GoodsDTO goodsDTO = new GoodsDTO();
            goodsDTO.setGoodsId(goodsId);
            goodsDTO.setStatus(2);
            Boolean flag = goodsService.updateById(goodsDTO);
            return flag;
        } catch (Exception e) {
            log.error("失败：【upperByGoodsId】上架商品失败");
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "上架商品失败" + e.getMessage());
        }

    }


    @Override
    public Boolean lowerByGoodsId(Integer goodsId) throws BaseException {
        try {
            GoodsVO byGoodsId = goodsService.getCascadeByGoodsId(goodsId);
            if (ObjectUtils.isEmpty(byGoodsId)) {
                log.error("失败：【lowerByGoodsId】商品不存在");
                throw new BaseException(ResultCode.ERROR);
            }
            if (!Integer.valueOf(1).equals(byGoodsId.getStatus())) {
                log.error("失败：【lowerByGoodsId】商品不是上架状态");
                throw new BaseException(ResultCode.ERROR.getStatus(), "商品不是上架状态");
            }
            GoodsDTO goodsDTO = new GoodsDTO();
            goodsDTO.setGoodsId(goodsId);
            goodsDTO.setStatus(0);
            Boolean flag = goodsService.updateById(goodsDTO);
            return flag;
        } catch (Exception e) {
            log.error("失败：【lowerByGoodsId】下架商品失败");
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "下架商品失败" + e.getMessage());
        }
    }
}

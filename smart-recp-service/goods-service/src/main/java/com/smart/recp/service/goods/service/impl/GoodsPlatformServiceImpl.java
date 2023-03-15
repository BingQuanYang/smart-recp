package com.smart.recp.service.goods.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.goods.dto.GoodsCategoryDTO;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.service.GoodsPlatformService;
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

        PageResult<GoodsVO> goodsVOPageResult = goodsService.list(1, 10, null);
        //TODO
        if (ObjectUtils.isNotEmpty(goodsVOPageResult.getList())) {
            List<Integer> goodsIdList = goodsVOPageResult.getList().stream().map(item -> item.getGoodsId()).collect(Collectors.toList());

            List<GoodsResourceVO> goodsResourceVOList = goodsService.getResourceByGoodsIdListAndIsMaster(goodsIdList, 1);

            Map<Integer, String> map = goodsResourceVOList.stream().collect(Collectors.toMap(GoodsResourceVO::getGoodsId, GoodsResourceVO::getLink, (k, v) -> v));

            goodsVOPageResult.getList().forEach(item -> item.setImage(map.get(item.getGoodsId())));
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

    @Override
    public Boolean approvedByGoodsId(Integer goodsId) throws BaseException {
        try {
            GoodsVO byGoodsId = goodsService.getCascadeByGoodsId(goodsId);
            if (ObjectUtils.isEmpty(byGoodsId)) {
                log.error("失败：【approvedByGoodsId】商品不存在");
                throw new BaseException(ResultCode.ERROR);
            }
            if (!Integer.valueOf(2).equals(byGoodsId.getStatus())) {
                log.error("失败：【approvedByGoodsId】商品不是审核状态");
                throw new BaseException(ResultCode.ERROR.getStatus(), "商品不是审核状态");
            }
            GoodsDTO goodsDTO = new GoodsDTO();
            goodsDTO.setGoodsId(goodsId);
            goodsDTO.setStatus(1);
            Boolean flag = goodsService.updateById(goodsDTO);
            return flag;
        } catch (Exception e) {
            log.error("失败：【approvedByGoodsId】审核通过商品失败");
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "审核通过商品失败" + e.getMessage());
        }
    }

    @Override
    public Boolean modifyCategory(GoodsCategoryDTO goodsCategoryDTO) throws BaseException {
        return goodsService.modifyCategory(goodsCategoryDTO);
    }


    @Override
    public Integer removeCategory(List<Integer> categoryIdList) throws BaseException {
        return goodsService.removeCategory(categoryIdList);
    }

    @Override
    public Boolean addCategory(GoodsCategoryDTO goodsCategoryDTO) throws BaseException {
        return goodsService.addCategory(goodsCategoryDTO);
    }

    @Override
    public GoodsCategoryVO getCategoryById(Integer categoryId) throws BaseException {
        return goodsService.getCategoryById(categoryId);
    }
}

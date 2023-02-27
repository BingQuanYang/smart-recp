package com.smart.recp.service.goods.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.service.goods.dto.GoodsSpecDTO;
import com.smart.recp.service.goods.entity.GoodsSpec;
import com.smart.recp.service.goods.mapper.GoodsSpecMapper;
import com.smart.recp.service.goods.service.StockService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@Service
@Slf4j
public class StockServiceImpl implements StockService {
    @Resource
    GoodsSpecMapper goodsSpecMapper;

    @Override
    public boolean subtract(Integer specId, Integer stock) throws BaseException {
        try {
            GoodsSpec goodsSpec = goodsSpecMapper.selectById(specId);
            if (ObjectUtils.isEmpty(goodsSpec) || stock <= 0) {
                log.error("失败：【subtract】扣库存失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "扣库存失败");
            }
            if (goodsSpec.getStock() < stock) {
                log.error("失败：【subtract】扣库存失败,库存不足");
                throw new BaseException(ResultCode.ERROR.getStatus(), "扣库存失败,库存不足");
            }
            goodsSpec.setStock(goodsSpec.getStock() - stock);
            int update = goodsSpecMapper.updateById(goodsSpec);
            if (update < 1) {
                log.error("失败：【subtract】扣库存失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "扣库存失败");
            }
            log.info("成功：【subtract】扣库存成功");
            return true;
        } catch (Exception e) {
            log.error("失败：【subtract】扣库存失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), e.getMessage());
        }
    }

    @Override
    public boolean update(GoodsSpecDTO goodsSpecDTO) throws BaseException {
        try {
            GoodsSpec goodsSpec = new GoodsSpec();
            int update = goodsSpecMapper.updateById(goodsSpec);
            if (update < 1) {
                log.error("失败：【update】修改库存失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "修改库存失败");
            }
            log.info("成功：【update】修改库存成功");
            return true;
        } catch (Exception e) {
            log.error("失败：【update】修改库存失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "修改库存失败");
        }
    }
}

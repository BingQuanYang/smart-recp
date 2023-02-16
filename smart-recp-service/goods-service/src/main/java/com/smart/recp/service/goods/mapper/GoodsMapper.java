package com.smart.recp.service.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.smart.recp.service.goods.dto.GoodsDTO;
import com.smart.recp.service.goods.entity.Goods;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface GoodsMapper extends BaseMapper<Goods> {

    IPage<Goods> selectGoodsList(IPage<?> page, @Param("goodsDTO") GoodsDTO goodsDTO);

    Goods getCascadeByGoodsId(@Param("goodsId") Integer goodsId);

    Integer deleteByGoodsIdList(@Param("goodsIdList") List<Integer> goodsIdList);
}
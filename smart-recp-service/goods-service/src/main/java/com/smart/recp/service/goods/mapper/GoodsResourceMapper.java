package com.smart.recp.service.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.recp.service.goods.entity.GoodsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsResourceMapper extends BaseMapper<GoodsResource> {
    List<GoodsResource> getResourceByGoodsIdListAndIsMaster(@Param("goodsIdList") List<Integer> goodsIdList,@Param("isMaster") int isMaster);
}
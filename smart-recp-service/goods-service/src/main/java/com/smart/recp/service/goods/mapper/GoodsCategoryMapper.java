package com.smart.recp.service.goods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.recp.service.goods.entity.GoodsCategory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GoodsCategoryMapper extends BaseMapper<GoodsCategory> {
    List<GoodsCategory> getCategoryCascadeByLevel(@Param("level") int level);
}
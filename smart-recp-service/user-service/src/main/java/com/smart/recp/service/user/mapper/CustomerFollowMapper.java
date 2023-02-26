package com.smart.recp.service.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.smart.recp.service.user.entity.CustomerFollow;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomerFollowMapper extends BaseMapper<CustomerFollow> {
    int deleteByIdList(@Param("followIdList") List<Integer> followIdList);
}

package com.smart.recp.service.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.recp.service.order.entity.Order;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper extends BaseMapper<Order> {
    Order getCascadeById(@Param("orderId") Integer orderId);

    IPage<Order> listCascade(Page<Order> orderPage, @Param("buyerId") Integer buyerId);
}

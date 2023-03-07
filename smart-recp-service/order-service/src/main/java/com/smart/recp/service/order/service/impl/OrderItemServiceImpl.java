package com.smart.recp.service.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.order.dto.OrderItemDTO;
import com.smart.recp.service.order.entity.OrderItem;
import com.smart.recp.service.order.mapper.OrderItemMapper;
import com.smart.recp.service.order.service.OrderItemService;
import com.smart.recp.service.order.vo.OrderItemVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ybq
 */
@Service
@Slf4j
public class OrderItemServiceImpl implements OrderItemService {


    @Resource
    OrderItemMapper orderItemMapper;

    @Override
    public OrderItemVO getById(Integer orderItemId) throws BaseException {
        try {
            OrderItem orderItem = orderItemMapper.selectById(orderItemId);
            if (ObjectUtils.isEmpty(orderItem) || orderItem.getIsDelete().equals(1)) {
                log.error("失败：【getById】根据ID获取订单信息失败,orderItemId:{}", orderItemId);
                throw new BaseException(ResultCode.ERROR, "根据ID获取订单信息失败");
            }
            OrderItemVO orderItemVO = new OrderItemVO();
            BeanUtils.copyProperties(orderItem, orderItemVO);
            log.info("成功：【getById】根据ID获取订单信息成功,{}", orderItemVO);
            return orderItemVO;
        } catch (Exception e) {
            log.error("失败：【getById】根据ID获取订单信息失败,orderItemId:{}", orderItemId);
            throw new BaseException(ResultCode.ERROR, "根据ID获取订单信息失败");
        }
    }

    @Override
    public PageResult<OrderItemVO> list(Integer page, Integer size, Integer sellerId, Integer buyerId, Integer delivery_status, Integer customer_status) throws BaseException {
        try {
            Page<OrderItem> itemPage = new Page<>(page, size);
            LambdaQueryWrapper<OrderItem> lambda = new QueryWrapper<OrderItem>().lambda();

            if (ObjectUtils.isNotEmpty(sellerId)) {
                lambda.eq(OrderItem::getSellerId, sellerId);
            }

            if (ObjectUtils.isNotEmpty(buyerId)) {
                lambda.eq(OrderItem::getBuyerId, buyerId);
            }

            if (ObjectUtils.isNotEmpty(delivery_status)) {
                lambda.eq(OrderItem::getDeliveryStatus, delivery_status);
            }
            if (ObjectUtils.isNotEmpty(customer_status)) {
                lambda.eq(OrderItem::getCustomerStatus, customer_status);
            }
            lambda.orderByDesc(OrderItem::getCreateTime);
            //不查待支付的
            lambda.ne(OrderItem::getOrderStatus, 0);
            IPage<OrderItem> resultPage = orderItemMapper.selectPage(itemPage, lambda);
            if (ObjectUtils.isEmpty(resultPage) || ObjectUtils.isEmpty(resultPage.getRecords())) {
                log.error("失败：【list】根据卖家ID获取订单列表信息失败");
                throw new BaseException(ResultCode.ERROR, "根据卖家ID获取订单列表信息失败");
            }

            PageResult<OrderItemVO> result = new PageResult<>();
            result.setPage(resultPage.getCurrent());
            result.setTotalCount(resultPage.getTotal());
            result.setPages(resultPage.getPages());
            result.setPageSize(resultPage.getSize());
            List<OrderItemVO> orderItemVOList = BeanCopyUtils.copyListProperties(resultPage.getRecords(), OrderItemVO::new);
            result.setList(orderItemVOList);
            log.info("失败：【list】根据卖家ID获取订单列表信息失败,列表：{}", result);
            return result;
        } catch (Exception e) {
            log.error("失败：【list】根据卖家ID获取订单列表信息失败,page:{},size:{},sellerId:{},buyerId:{},delivery_status:{},customer_status:{}", page, size, sellerId, buyerId, delivery_status, customer_status);
            throw new BaseException(ResultCode.ERROR, "根据卖家ID获取订单列表信息失败");
        }
    }

    @Override
    public boolean update(OrderItemDTO orderItemDTO) throws BaseException {
        try {
            OrderItem orderItem = new OrderItem();
            BeanUtils.copyProperties(orderItemDTO, orderItem);
            int update = orderItemMapper.updateById(orderItem);
            if (update < 1) {
                log.error("失败：【update】修改订单列表信息失败,{}", orderItemDTO);
                throw new BaseException(ResultCode.ERROR, "修改订单列表信息失败");
            }
            log.info("成功：【update】修改订单列表信息成功,{}", orderItemDTO);
            return true;
        } catch (Exception e) {
            log.error("失败：【update】修改订单列表信息失败,{}", orderItemDTO);
            throw new BaseException(ResultCode.ERROR, "修改订单列表信息失败");
        }
    }

    @Override
    public int addList(List<OrderItem> orderItemList) throws BaseException {
        try {
            int sum = 0;
            for (OrderItem orderItem : orderItemList) {
                int insert = orderItemMapper.insert(orderItem);
                if (insert < 1) {
                    log.error("失败：【addList】添加订单列表信息失败,orderItem：{}", orderItem);
                    throw new BaseException(ResultCode.ERROR, "添加订单列表信息失败");
                }
                sum += 1;
            }
            log.info("成功：【addList】添加订单列表信息成功");
            return sum;
        } catch (Exception e) {
            log.error("失败：【addList】添加订单列表信息失败,orderItemList：{}", orderItemList);
            throw new BaseException(ResultCode.ERROR, "添加订单列表信息失败");
        }
    }

    @Override
    public int updateOrderStatusByOrderId(OrderItem orderItem) throws BaseException {
        try {
            int update = orderItemMapper.update(orderItem, new QueryWrapper<OrderItem>().lambda().eq(OrderItem::getOrderId, orderItem.getOrderId()));
            if (update < 1) {
                log.error("失败：【updateOrderStatusByOrderId】修改订单列表信息失败,{}", orderItem);
                throw new BaseException(ResultCode.ERROR, "修改订单列表信息失败");
            }
            log.info("成功：【updateOrderStatusByOrderId】修改订单列表信息成功,{}", orderItem);
            return update;
        } catch (Exception e) {
            log.error("失败：【updateOrderStatusByOrderId】修改订单列表信息失败,{}", orderItem);
            throw new BaseException(ResultCode.ERROR, "修改订单列表信息失败");
        }
    }
}

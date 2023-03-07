package com.smart.recp.service.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.order.entity.Order;
import com.smart.recp.service.order.mapper.OrderMapper;
import com.smart.recp.service.order.service.OrderService;
import com.smart.recp.service.order.vo.OrderItemVO;
import com.smart.recp.service.order.vo.OrderVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ybq
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    OrderMapper orderMapper;


    @Override
    public OrderVO getCascadeById(Integer orderId) throws BaseException {
        try {
            Order order = orderMapper.getCascadeById(orderId);
            if (ObjectUtils.isEmpty(order) || order.getIsDelete().equals(1)) {
                log.error("失败：【getCascadeById】根据ID获取订单详情信息失败,ID:{}", orderId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID获取订单详情信息失败");
            }
            OrderVO orderVO = new OrderVO();
            BeanUtils.copyProperties(order, orderVO);
            List<OrderItemVO> orderItemVOList = BeanCopyUtils.copyListProperties(order.getOrderItemList(), OrderItemVO::new);
            orderVO.setOrderItemVOList(orderItemVOList);
            log.info("成功：【getCascadeById】根据ID获取订单详情信息成功,{}", orderVO);
            return orderVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【getCascadeById】根据ID获取订单详情信息失败,ID:{}", orderId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID获取订单详情信息失败");
        }
    }

    @Override
    public PageResult<OrderVO> listCascade(Integer page, Integer size, Integer buyerId) throws BaseException {
        try {
            Page<Order> orderPage = new Page<>(page, size);
            IPage<Order> resultPage = orderMapper.listCascade(orderPage, buyerId);
            if (ObjectUtils.isEmpty(resultPage) || ObjectUtils.isEmpty(resultPage.getRecords()) || ObjectUtils.isEmpty(resultPage.getRecords().size() < 1)) {
                log.error("失败：【listCascade】查询级联订单列表信息失败，page:{},size:{},buyerId:{}", page, size, buyerId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "查询级联订单列表信息失败");
            }
            PageResult<OrderVO> result = new PageResult<>();
            result.setPages(resultPage.getPages());
            result.setPageSize(resultPage.getSize());
            result.setTotalCount(resultPage.getTotal());
            result.setPage(resultPage.getCurrent());
            List<Order> records = resultPage.getRecords();
            List<OrderVO> orderVOList = records.stream().map(item -> {
                OrderVO orderVO = new OrderVO();
                BeanUtils.copyProperties(item, orderVO);
                List<OrderItemVO> orderItemVOList = BeanCopyUtils.copyListProperties(item.getOrderItemList(), OrderItemVO::new);
                orderVO.setOrderItemVOList(orderItemVOList);
                return orderVO;
            }).collect(Collectors.toList());
            result.setList(orderVOList);
            log.error("成功：【listCascade】查询级联订单列表信息成功,列表：{}", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【listCascade】查询级联订单列表信息失败，page:{},size:{},buyerId:{}", page, size, buyerId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "查询级联订单列表信息失败");
        }
    }


    @Override
    public PageResult<OrderVO> list(Integer page, Integer size, Integer buyerId) throws BaseException {
        try {
            Page<Order> orderPage = new Page<>(page, size);
            LambdaQueryWrapper<Order> queryWrapper = new QueryWrapper<Order>().lambda().eq(Order::getIsDelete, 0);
            if (ObjectUtils.isNotEmpty(buyerId)) {
                queryWrapper.eq(Order::getBuyerId, buyerId);
            }
            IPage<Order> resultPage = orderMapper.selectPage(orderPage, queryWrapper);
            if (ObjectUtils.isEmpty(resultPage) || ObjectUtils.isEmpty(resultPage.getRecords()) || ObjectUtils.isEmpty(resultPage.getRecords().size() < 1)) {
                log.error("失败：【list】查询订单列表信息失败，page:{},size:{},buyerId:{}", page, size, buyerId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "查询订单列表信息失败");
            }
            PageResult<OrderVO> result = new PageResult<>();
            result.setPages(resultPage.getPages());
            result.setPageSize(resultPage.getSize());
            result.setTotalCount(resultPage.getTotal());
            result.setPage(resultPage.getCurrent());
            List<OrderVO> orderVOList = BeanCopyUtils.copyListProperties(resultPage.getRecords(), OrderVO::new);
            result.setList(orderVOList);
            log.error("成功：【list】查询订单列表信息成功,列表：{}", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【list】查询订单列表信息失败，page:{},size:{},buyerId:{}", page, size, buyerId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "查询订单列表信息失败");
        }
    }

    @Override
    public Order add(Order order) throws BaseException {
        try {
            int insert = orderMapper.insert(order);
            if (insert < 1 || ObjectUtils.isEmpty(order.getOrderId())) {
                log.error("失败：【add】添加订单失败，{}", order);
                throw new BaseException(ResultCode.ERROR.getStatus(), "添加订单失败");
            }
            return order;
        } catch (Exception e) {
            log.error("失败：【add】添加订单失败，{}", order);
            throw new BaseException(ResultCode.ERROR.getStatus(), "添加订单失败");
        }
    }

    @Override
    public boolean update(Order order) throws BaseException {
        try {
            int update = orderMapper.updateById(order);
            if (update < 1 || ObjectUtils.isEmpty(order.getOrderId())) {
                log.error("失败：【update】修改订单失败，{}", order);
                throw new BaseException(ResultCode.ERROR.getStatus(), "修改订单失败");
            }
            return true;
        } catch (Exception e) {
            log.error("失败：【update】修改订单失败，{}", order);
            throw new BaseException(ResultCode.ERROR.getStatus(), "修改订单失败");
        }
    }

    @Override
    public PageResult<OrderVO> listPendingPay(Integer page, Integer size, Integer buyerId) throws BaseException {
        try {
            Page<Order> orderPage = new Page<>(page, size);
            LambdaQueryWrapper<Order> queryWrapper = new QueryWrapper<Order>()
                    .lambda().eq(Order::getIsDelete, 0)
                    .eq(Order::getTradeStatus, 0)
                    .eq(Order::getPayStatus, 0);
            if (ObjectUtils.isNotEmpty(buyerId)) {
                queryWrapper.eq(Order::getBuyerId, buyerId);
            }
            IPage<Order> resultPage = orderMapper.selectPage(orderPage, queryWrapper);
            if (ObjectUtils.isEmpty(resultPage) || ObjectUtils.isEmpty(resultPage.getRecords())) {
                log.error("失败：【listPendingPay】查询订单列表信息失败，page:{},size:{},buyerId:{}", page, size, buyerId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "查询订单列表信息失败");
            }
            PageResult<OrderVO> result = new PageResult<>();
            result.setPages(resultPage.getPages());
            result.setPageSize(resultPage.getSize());
            result.setTotalCount(resultPage.getTotal());
            result.setPage(resultPage.getCurrent());
            List<OrderVO> orderVOList = BeanCopyUtils.copyListProperties(resultPage.getRecords(), OrderVO::new);
            result.setList(orderVOList);
            log.error("成功：【listPendingPay】查询订单列表信息成功,列表：{}", result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【listPendingPay】查询订单列表信息失败，page:{},size:{},buyerId:{}", page, size, buyerId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "查询订单列表信息失败");
        }
    }
}

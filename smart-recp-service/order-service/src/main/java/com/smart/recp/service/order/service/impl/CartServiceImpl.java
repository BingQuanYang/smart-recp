package com.smart.recp.service.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.order.dto.OrderCartDTO;
import com.smart.recp.service.order.entity.OrderCart;
import com.smart.recp.service.order.mapper.OrderCartMapper;
import com.smart.recp.service.order.service.CartService;
import com.smart.recp.service.order.vo.OrderCartVO;
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
public class CartServiceImpl implements CartService {

    @Resource
    OrderCartMapper orderCartMapper;


    @Override
    public OrderCartVO getById(Integer cartId) throws BaseException {
        try {
            OrderCart orderCart = orderCartMapper.selectById(cartId);
            if (ObjectUtils.isEmpty(orderCart)) {
                log.error("失败：【getById】 根据ID获取购物车信息失败，ID:{}", cartId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据购物车ID获取购物车信息失败");
            }
            OrderCartVO orderCartVO = new OrderCartVO();
            BeanUtils.copyProperties(orderCart, orderCartVO);
            //TODO  购物车缺少商品信息


            log.info("成功：【getById】根据购物车ID获取购物车信息成功：{}", orderCartVO);
            return orderCartVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【getById】 根据购物车ID获取购物车信息失败，ID:{}", cartId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据购物车ID获取购物车信息失败");
        }
    }

    @Override
    public boolean add(OrderCartDTO orderCartDTO) throws BaseException {
        try {
            LambdaQueryWrapper<OrderCart> lambda = new QueryWrapper<OrderCart>().lambda()
                    .eq(OrderCart::getBuyerId, orderCartDTO.getBuyerId())
                    .eq(OrderCart::getGoodsId, orderCartDTO.getGoodsId())
                    .eq(OrderCart::getSpecId, orderCartDTO.getSpecId());
            OrderCart orderCart = orderCartMapper.selectOne(lambda);
            if (ObjectUtils.isNotEmpty(orderCart)) {
                orderCart.setGoodsAmount(orderCart.getGoodsAmount() + orderCartDTO.getGoodsAmount());
                int update = orderCartMapper.updateById(orderCart);
                if (update < 1) {
                    log.error("失败：【add】添加购物车失败");
                    throw new BaseException(ResultCode.ERROR.getStatus(), "添加购物车失败");
                }
            } else {
                orderCart = new OrderCart();
                BeanUtils.copyProperties(orderCartDTO, orderCart);
                int insert = orderCartMapper.insert(orderCart);
                if (insert < 1) {
                    log.error("失败：【add】添加购物车失败");
                    throw new BaseException(ResultCode.ERROR.getStatus(), "添加购物车失败");
                }
            }
            log.info("成功：【add】添加购物车成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【add】添加购物车失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "添加购物车失败");
        }
    }

    @Override
    public boolean update(OrderCartDTO orderCartDTO) throws BaseException {
        try {
            OrderCart orderCart = new OrderCart();
            BeanUtils.copyProperties(orderCartDTO, orderCart);
            int update = orderCartMapper.updateById(orderCart);
            if (update < 1) {
                log.error("失败：【update】修改购物车失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "修改购物车失败");
            }
            log.info("成功：【update】修改购物车成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【update】修改购物车失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "修改购物车失败");
        }
    }

    @Override
    public Integer deleteByIdList(List<Integer> cartIdList) throws BaseException {
        try {

            int deleteBatchIds = orderCartMapper.deleteBatchIds(cartIdList);
            if (deleteBatchIds < 1) {
                log.error("失败：【deleteByIdList】根据ID列表删除购物车失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID列表删除购物车失败");
            }
            log.info("成功：【deleteByIdList】根据ID列表删除购物车成功，{}", deleteBatchIds);
            return deleteBatchIds;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【deleteByIdList】根据ID列表删除购物车失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID列表删除购物车失败");
        }
    }

    @Override
    public List<OrderCartVO> listByBuyerId(Integer buyerId) throws BaseException {
        try {
            List<OrderCart> orderCartList = orderCartMapper.selectList(new QueryWrapper<OrderCart>().lambda().eq(OrderCart::getBuyerId, buyerId));
            if (ObjectUtils.isEmpty(orderCartList) || orderCartList.size() < 1) {
                log.error("失败：【listByBuyerId】 根据买家ID查询购物车失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据买家ID查询购物车失败");
            }
            List<OrderCartVO> orderCartVOList = BeanCopyUtils.copyListProperties(orderCartList, OrderCartVO::new);
            for (OrderCartVO orderCartVO : orderCartVOList) {
                //TODO  购物车缺少商品信息
            }
            log.info("成功：【listByBuyerId】 根据买家ID查询购物车成功，{}", orderCartVOList);
            return orderCartVOList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【listByBuyerId】 根据买家ID查询购物车失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据买家ID查询购物车失败");
        }
    }
}

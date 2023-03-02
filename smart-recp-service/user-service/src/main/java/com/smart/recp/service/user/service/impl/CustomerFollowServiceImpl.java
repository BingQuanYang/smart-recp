package com.smart.recp.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.user.dto.CustomerFollowDTO;
import com.smart.recp.service.user.entity.CustomerFollow;
import com.smart.recp.service.user.entity.ReceiveAddress;
import com.smart.recp.service.user.mapper.CustomerFollowMapper;
import com.smart.recp.service.user.service.BuyerService;
import com.smart.recp.service.user.service.CustomerFollowService;
import com.smart.recp.service.user.vo.BuyerVO;
import com.smart.recp.service.user.vo.CustomerFollowVO;
import com.smart.recp.service.user.vo.ReceiveAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ybq
 */
@Service
@Slf4j
public class CustomerFollowServiceImpl implements CustomerFollowService {

    @Resource
    CustomerFollowMapper customerFollowMapper;

    @Resource
    BuyerService buyerService;

    @Override
    public CustomerFollowVO getById(Integer followId) throws BaseException {
        try {
            CustomerFollow customerFollow = customerFollowMapper.selectById(followId);
            if (ObjectUtils.isEmpty(customerFollow)) {
                log.error("失败：【getById】 根据客户关注ID获取客户关注信息失败，ID:{}", followId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据客户关注ID获取客户关注信息失败");
            }
            CustomerFollowVO customerFollowVO = new CustomerFollowVO();
            BeanUtils.copyProperties(customerFollow, customerFollowVO);

            BuyerVO buyerVO = buyerService.getById(customerFollowVO.getBuyerId());
            BeanUtils.copyProperties(buyerVO, customerFollowVO);
            log.info("成功：根据客户关注ID获取客户关注信息成功：{}", customerFollowVO);
            return customerFollowVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【getById】 根据客户关注ID获取客户关注信息失败，ID:{}", followId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据客户关注ID获取客户关注信息失败");
        }
    }

    @Override
    public boolean add(CustomerFollowDTO customerFollowDTO) throws BaseException {
        try {
            CustomerFollow customerFollow = new CustomerFollow();
            BeanUtils.copyProperties(customerFollowDTO, customerFollow);
            int insert = customerFollowMapper.insert(customerFollow);
            if (insert < 1) {
                log.error("失败：【add】添加客户关注失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "添加客户关注失败");
            }
            log.info("成功：【add】添加客户关注成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【add】添加客户关注失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "添加客户关注失败");
        }
    }

    @Override
    public boolean update(CustomerFollowDTO customerFollowDTO) throws BaseException {
        try {
            CustomerFollow customerFollow = new CustomerFollow();
            BeanUtils.copyProperties(customerFollowDTO, customerFollow);
            int update = customerFollowMapper.updateById(customerFollow);
            if (update < 1) {
                log.error("失败：【update】修改客户关注失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "修改客户关注失败");
            }
            log.info("成功：【update】修改客户关注成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【update】修改客户关注失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "修改客户关注失败");
        }
    }

    @Override
    public Integer deleteByIdList(List<Integer> followIdList) throws BaseException {
        try {

            int deleteByIdList = customerFollowMapper.deleteByIdList(followIdList);
            if (deleteByIdList < 1) {
                log.error("失败：【deleteByIdList】根据ID列表删除客户关注失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID列表删除客户关注失败");
            }
            log.info("成功：【deleteByIdList】根据ID列表删除客户关注成功，{}", deleteByIdList);
            return deleteByIdList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【deleteByIdList】根据ID列表删除客户关注失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID列表删除客户关注失败");
        }
    }

    @Override
    public PageResult<CustomerFollowVO> list(Integer page, Integer size) throws BaseException {
        try {
            Page<CustomerFollow> followPage = new Page<>(page, size);
            LambdaQueryWrapper<CustomerFollow> queryWrapper = new QueryWrapper<CustomerFollow>().lambda().eq(CustomerFollow::getIsDelete, 0);
            return selectListByPage(followPage, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【listByBuyerId】 根据买家ID查询客户关注失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据买家ID查询客户关注失败");
        }
    }

    private PageResult<CustomerFollowVO> selectListByPage(Page<CustomerFollow> followPage, LambdaQueryWrapper<CustomerFollow> queryWrapper) throws BaseException {
        Page<CustomerFollow> selectPage = customerFollowMapper.selectPage(followPage, queryWrapper);
        if (ObjectUtils.isEmpty(selectPage) || ObjectUtils.isEmpty(selectPage.getRecords()) || selectPage.getRecords().size() < 1) {
            log.error("失败：【list】 查询客户关注列表失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "查询客户关注列表失败");
        }
        List<CustomerFollowVO> customerFollowVOList = BeanCopyUtils.copyListProperties(selectPage.getRecords(), CustomerFollowVO::new);
        customerFollowVOList = customerFollowVOList.stream().map(item -> {
            BuyerVO buyerVO = null;
            try {
                buyerVO = buyerService.getById(item.getBuyerId());
            } catch (BaseException e) {
                e.printStackTrace();
            }
            item.setNickname(buyerVO.getNickname());
            item.setHeadPortrait(buyerVO.getHeadPortrait());
            return item;
        }).collect(Collectors.toList());
        PageResult<CustomerFollowVO> result = new PageResult<>();
        result.setList(customerFollowVOList);
        result.setPageSize(selectPage.getSize());
        result.setTotalCount(selectPage.getTotal());
        result.setPages(selectPage.getPages());
        result.setPage(selectPage.getCurrent());
        log.info("成功：【list】 查询客户关注列表失败成功，{}", result);
        return result;
    }

    @Override
    public PageResult<CustomerFollowVO> listBySellerId(Integer page, Integer size, Integer sellerId) throws BaseException {
        try {
            Page<CustomerFollow> followPage = new Page<>(page, size);
            LambdaQueryWrapper<CustomerFollow> queryWrapper = new QueryWrapper<CustomerFollow>().lambda().eq(CustomerFollow::getIsDelete, 0).eq(CustomerFollow::getSellerId, sellerId);
            return selectListByPage(followPage, queryWrapper);
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【listByBuyerId】 根据买家ID查询客户关注失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据买家ID查询客户关注失败");
        }
    }
}

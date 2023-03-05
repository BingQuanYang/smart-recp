package com.smart.recp.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.user.dto.ReceiveAddressDTO;
import com.smart.recp.service.user.entity.ReceiveAddress;
import com.smart.recp.service.user.mapper.ReceiveAddressMapper;
import com.smart.recp.service.user.service.ReceiveAddressService;
import com.smart.recp.service.user.vo.ReceiveAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ybq
 */
@Service
@Slf4j
public class ReceiveAddressServiceImpl implements ReceiveAddressService {

    @Resource
    ReceiveAddressMapper receiveAddressMapper;

    @Override
    public ReceiveAddressVO getById(Integer receiveId) throws BaseException {
        try {
            ReceiveAddress receiveAddress = receiveAddressMapper.selectById(receiveId);
            if (ObjectUtils.isEmpty(receiveAddress)) {
                log.error("失败：【getById】 根据收货地址ID获取收货地址信息失败，ID:{}", receiveId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据收货地址ID获取收货地址信息失败");
            }
            ReceiveAddressVO receiveAddressVO = new ReceiveAddressVO();
            BeanUtils.copyProperties(receiveAddress, receiveAddressVO);
            log.info("成功：【getById】根据收货地址ID获取收货地址信息成功：{}", receiveAddressVO);
            return receiveAddressVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【getById】 根据收货地址ID获取收货地址信息失败，ID:{}", receiveId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据收货地址ID获取收货地址信息失败");
        }
    }

    @Override
    public boolean add(ReceiveAddressDTO receiveAddressDTO) throws BaseException {
        try {
            ReceiveAddress receiveAddress = new ReceiveAddress();
            BeanUtils.copyProperties(receiveAddressDTO, receiveAddress);
            if (Integer.valueOf(1).equals(receiveAddress.getDefaultStatus())) {
                ReceiveAddress temp = new ReceiveAddress();
                temp.setDefaultStatus(0);
                int len = receiveAddressMapper.update(temp
                        , new QueryWrapper<ReceiveAddress>().lambda().eq(ReceiveAddress::getBuyerId, receiveAddress.getBuyerId()));
            }
            int insert = receiveAddressMapper.insert(receiveAddress);
            if (insert < 1) {
                log.error("失败：【add】添加收货地址失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "添加收货地址失败");
            }
            log.info("成功：【add】添加收货地址成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【add】添加收货地址失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "添加收货地址失败");
        }
    }

    @Override
    public boolean update(ReceiveAddressDTO receiveAddressDTO) throws BaseException {
        try {
            ReceiveAddress receiveAddress = new ReceiveAddress();
            BeanUtils.copyProperties(receiveAddressDTO, receiveAddress);
            if (Integer.valueOf(1).equals(receiveAddress.getDefaultStatus())) {
                ReceiveAddress temp = new ReceiveAddress();
                temp.setDefaultStatus(0);
                int len = receiveAddressMapper.update(temp
                        , new QueryWrapper<ReceiveAddress>().lambda().eq(ReceiveAddress::getBuyerId, receiveAddress.getBuyerId()));
            }
            int update = receiveAddressMapper.updateById(receiveAddress);
            if (update < 1) {
                log.error("失败：【update】修改收货地址失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "修改收货地址失败");
            }
            log.info("成功：【update】修改收货地址成功");
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【update】修改收货地址失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "修改收货地址失败");
        }
    }

    @Override
    public Integer deleteByIdList(List<Integer> receiveIdList) throws BaseException {
        try {

            int deleteBatchIds = receiveAddressMapper.deleteBatchIds(receiveIdList);
            if (deleteBatchIds < 1) {
                log.error("失败：【deleteByIdList】根据ID列表删除收货地址失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID列表删除收货地址失败");
            }
            log.info("成功：【deleteByIdList】根据ID列表删除收货地址成功，{}", deleteBatchIds);
            return deleteBatchIds;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【deleteByIdList】根据ID列表删除收货地址失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据ID列表删除收货地址失败");
        }
    }

    @Override
    public List<ReceiveAddressVO> listByBuyerId(Integer buyerId) throws BaseException {
        try {
            List<ReceiveAddress> receiveAddressList = receiveAddressMapper.selectList(new QueryWrapper<ReceiveAddress>().lambda().eq(ReceiveAddress::getBuyerId, buyerId));
            if (ObjectUtils.isEmpty(receiveAddressList) || receiveAddressList.size() < 1) {
                log.error("失败：【listByBuyerId】 根据买家ID查询收货地址失败");
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据买家ID查询收货地址失败");
            }
            List<ReceiveAddressVO> receiveAddressVOList = BeanCopyUtils.copyListProperties(receiveAddressList, ReceiveAddressVO::new);
            log.info("成功：【listByBuyerId】 根据买家ID查询收货地址成功，{}", receiveAddressVOList);
            return receiveAddressVOList;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【listByBuyerId】 根据买家ID查询收货地址失败");
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据买家ID查询收货地址失败");
        }
    }

    @Override
    public ReceiveAddressVO getDefaultByBuyerId(Integer buyerId) throws BaseException {
        try {
            ReceiveAddress receiveAddress = receiveAddressMapper.selectOne(new QueryWrapper<ReceiveAddress>().lambda().eq(ReceiveAddress::getBuyerId, buyerId).eq(ReceiveAddress::getDefaultStatus, 1));
            if (ObjectUtils.isEmpty(receiveAddress)) {
                log.error("失败：【getDefaultByBuyerId】 根据买家ID获取默认收货地址信息失败，ID:{}", buyerId);
                throw new BaseException(ResultCode.ERROR.getStatus(), "根据买家ID获取默认收货地址信息失败");
            }
            ReceiveAddressVO receiveAddressVO = new ReceiveAddressVO();
            BeanUtils.copyProperties(receiveAddress, receiveAddressVO);
            log.info("成功：【getDefaultByBuyerId】根据买家ID获取默认收货地址信息成功：{}", receiveAddressVO);
            return receiveAddressVO;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("失败：【getDefaultByBuyerId】 根据买家ID获取默认收货地址信息失败，ID:{}", buyerId);
            throw new BaseException(ResultCode.ERROR.getStatus(), "根据买家ID获取默认收货地址信息失败");
        }
    }
}

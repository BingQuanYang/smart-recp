package com.smart.recp.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.service.user.dto.SellerDTO;
import com.smart.recp.service.user.entity.Seller;
import com.smart.recp.service.user.entity.User;
import com.smart.recp.service.user.mapper.SellerMapper;
import com.smart.recp.service.user.service.SellerService;
import com.smart.recp.service.user.vo.SellerVO;
import com.smart.recp.service.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@Service
@Slf4j
public class SellerServiceImpl implements SellerService {

    @Resource
    SellerMapper sellerMapper;

    @Override
    public SellerVO getById(Integer sellerId) throws BaseException {
        try {
            Seller seller = sellerMapper.selectById(sellerId);
            if (ObjectUtils.isEmpty(seller)) {
                log.error("失败：根据ID查询卖家失败，卖家不存在，ID：{}", sellerId);
                throw new BaseException(ResultCode.ERROR);
            } else if (seller.getIsDelete().equals(1)) {
                log.error("失败：根据ID查询卖家失败，该卖家已删除，ID：{}", sellerId);
                throw new BaseException(ResultCode.ERROR);
            }
            SellerVO sellerVO = new SellerVO();
            BeanUtils.copyProperties(seller, sellerVO);
            log.error("成功：根据ID查询卖家成功，卖家：{}", seller);
            return sellerVO;
        } catch (Exception e) {
            log.error("失败：根据ID查询卖家失败,ID：{}", sellerId);
            throw new BaseException(ResultCode.ERROR, String.format("失败：根据ID查询卖家失败,ID：{}", sellerId));
        }
    }

    @Override
    public SellerVO getByAccount(String account) throws BaseException {
        try {
            Seller seller = sellerMapper.selectOne(new QueryWrapper<Seller>().lambda().eq(Seller::getSellerAccount, account));
            if (ObjectUtils.isEmpty(seller)) {
                log.error("失败：根据账号查询卖家失败，卖家不存在，账号：{}", account);
                throw new BaseException(ResultCode.ERROR);
            } else if (seller.getIsDelete().equals(1)) {
                log.error("失败：根据账号查询卖家失败，该卖家已删除，账号：{}", account);
                throw new BaseException(ResultCode.ERROR);
            }
            SellerVO sellerVO = new SellerVO();
            BeanUtils.copyProperties(seller, sellerVO);
            log.error("成功：根据账号查询卖家成功，卖家：{}", seller);
            return sellerVO;
        } catch (Exception e) {
            log.error("失败：根据账号查询卖家失败,账号：{}", account);
            throw new BaseException(ResultCode.ERROR, String.format("失败：根据账号查询卖家失败,账号：{}", account));
        }
    }

    @Override
    public boolean add(SellerDTO sellerDTO) throws BaseException {
        try {
            Seller seller = sellerMapper.selectOne(new QueryWrapper<Seller>().lambda().eq(Seller::getSellerAccount, sellerDTO.getSellerAccount()));

            if (ObjectUtils.isNotEmpty(seller)) {
                log.error("失败：添加卖家失败,改账号已存在,sellerDTO：{}", sellerDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            seller = new Seller();
            BeanUtils.copyProperties(sellerDTO, seller);
            int insert = sellerMapper.insert(seller);
            if (insert < 1) {
                log.error("失败：添加卖家失败,添加数据库失败,sellerDTO：{}", sellerDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            return true;
        } catch (Exception e) {
            log.error("失败：添加卖家失败，sellerDTO：{}", sellerDTO);
            throw new BaseException(ResultCode.ERROR, String.format("失败：添加卖家失败，sellerDTO：{}", sellerDTO));
        }
    }

    @Override
    public boolean update(SellerDTO sellerDTO) throws BaseException {
        try {
            Seller seller = new Seller();
            BeanUtils.copyProperties(sellerDTO, seller);
            int update = sellerMapper.updateById(seller);
            if (update < 1) {
                log.error("失败：修改卖家失败,修改数据库失败,sellerDTO：{}", sellerDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            return true;
        } catch (Exception e) {
            log.error("失败：修改卖家失败,修改数据库失败,sellerDTO：{}", sellerDTO);
            throw new BaseException(ResultCode.ERROR, String.format("失败：修改卖家失败,修改数据库失败,sellerDTO：{}", sellerDTO));
        }
    }
}

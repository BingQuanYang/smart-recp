package com.smart.recp.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.user.dto.BuyerDTO;
import com.smart.recp.service.user.entity.Buyer;
import com.smart.recp.service.user.entity.Seller;
import com.smart.recp.service.user.mapper.BuyerMapper;
import com.smart.recp.service.user.service.BuyerService;
import com.smart.recp.service.user.vo.BuyerVO;
import com.smart.recp.service.user.vo.SellerVO;
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
public class BuyerServiceImpl implements BuyerService {
    @Resource
    BuyerMapper buyerMapper;


    @Override
    public BuyerVO getById(Integer buyerId) throws BaseException {
        try {
            Buyer buyer = buyerMapper.selectById(buyerId);
            if (ObjectUtils.isEmpty(buyer)) {
                log.error("失败：根据ID查询买家失败，买家不存在，ID：{}", buyerId);
                throw new BaseException(ResultCode.ERROR);
            } else if (buyer.getIsDelete().equals(1)) {
                log.error("失败：根据ID查询买家失败，该买家已删除，ID：{}", buyerId);
                throw new BaseException(ResultCode.ERROR);
            }
            BuyerVO buyerVO = new BuyerVO();
            BeanUtils.copyProperties(buyer, buyerVO);
            log.error("成功：根据ID查询买家成功，买家：{}", buyer);
            return buyerVO;
        } catch (Exception e) {
            log.error("失败：根据ID查询买家失败,ID：{}", buyerId);
            throw new BaseException(ResultCode.ERROR, String.format("失败：根据ID查询买家失败,ID：{}", buyerId));
        }
    }

    @Override
    public BuyerVO getByAccount(String account) throws BaseException {
        try {
            Buyer buyer = buyerMapper.selectOne(new QueryWrapper<Buyer>().lambda().eq(Buyer::getBuyerAccount, account));
            if (ObjectUtils.isEmpty(buyer)) {
                log.error("失败：根据账号查询买家失败，买家不存在，账号：{}", account);
                throw new BaseException(ResultCode.ERROR);
            } else if (buyer.getIsDelete().equals(1)) {
                log.error("失败：根据账号查询买家失败，该买家已删除，账号：{}", account);
                throw new BaseException(ResultCode.ERROR);
            }
            BuyerVO buyerVO = new BuyerVO();
            BeanUtils.copyProperties(buyer, buyerVO);
            log.error("成功：根据账号查询买家成功，买家：{}", buyer);
            return buyerVO;
        } catch (Exception e) {
            log.error("失败：根据账号查询买家失败,账号：{}", account);
            throw new BaseException(ResultCode.ERROR, String.format("失败：根据账号查询买家失败,账号：{}", account));
        }
    }

    @Override
    public boolean add(BuyerDTO buyerDTO) throws BaseException {
        try {
            Buyer buyer = buyerMapper.selectOne(new QueryWrapper<Buyer>().lambda().eq(Buyer::getBuyerAccount, buyerDTO.getBuyerAccount()));

            if (ObjectUtils.isNotEmpty(buyer)) {
                log.error("失败：添加买家失败,改账号已存在,BuyerDTO：{}", buyerDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            buyer = new Buyer();
            BeanUtils.copyProperties(buyerDTO, buyer);
            int insert = buyerMapper.insert(buyer);
            if (insert < 1) {
                log.error("失败：添加买家失败,添加数据库失败,BuyerDTO：{}", buyerDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            return true;
        } catch (Exception e) {
            log.error("失败：添加买家失败，BuyerDTO：{}", buyerDTO);
            throw new BaseException(ResultCode.ERROR, String.format("失败：添加买家失败，BuyerDTO：{}", buyerDTO));
        }
    }

    @Override
    public boolean update(BuyerDTO buyerDTO) throws BaseException {
        try {
            Buyer buyer = new Buyer();
            BeanUtils.copyProperties(buyerDTO, buyer);
            int update = buyerMapper.updateById(buyer);
            if (update < 1) {
                log.error("失败：修改买家失败,修改数据库失败,BuyerDTO：{}", buyerDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            return true;
        } catch (Exception e) {
            log.error("失败：修改买家失败,修改数据库失败,BuyerDTO：{}", buyerDTO);
            throw new BaseException(ResultCode.ERROR, String.format("失败：修改买家失败,修改数据库失败,BuyerDTO：{}", buyerDTO));
        }
    }

    @Override
    public PageResult<BuyerVO> list(Integer page, Integer size, BuyerDTO buyerDTO) throws BaseException {
        try {
            LambdaQueryWrapper<Buyer> lambda = new QueryWrapper<Buyer>().lambda();
            if (ObjectUtils.isNotEmpty(buyerDTO.getBuyerId())) {
                lambda.eq(Buyer::getBuyerId, buyerDTO.getBuyerId());
            } else if (ObjectUtils.isNotEmpty(buyerDTO.getNickname())) {
                lambda.like(Buyer::getNickname, buyerDTO.getNickname());
            }
            Page<Buyer> buyerPage = buyerMapper.selectPage(new Page<Buyer>(page, size), lambda);
            if (ObjectUtils.isEmpty(buyerPage)) {
                log.error("失败：【list】获取商家列表失败");
                throw new BaseException(ResultCode.ERROR);
            }
            PageResult<BuyerVO> result = new PageResult<>();
            result.setPage(buyerPage.getCurrent());
            result.setPageSize(buyerPage.getSize());
            result.setPages(buyerPage.getPages());
            result.setTotalCount(buyerPage.getTotal());
            List<BuyerVO> buyerVOS = BeanCopyUtils.copyListProperties(buyerPage.getRecords(), BuyerVO::new);
            result.setList(buyerVOS);
            return result;
        } catch (Exception e) {
            log.error("失败：【list】获取商家列表失败,page:{},size:{},buyerDTO：{}", page, size, buyerDTO);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "获取商家列表失败");
        }
    }
}

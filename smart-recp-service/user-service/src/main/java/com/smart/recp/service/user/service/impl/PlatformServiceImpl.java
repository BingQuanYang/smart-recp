package com.smart.recp.service.user.service.impl;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.service.user.dto.BuyerDTO;
import com.smart.recp.service.user.dto.SellerDTO;
import com.smart.recp.service.user.dto.UserDTO;
import com.smart.recp.service.user.service.BuyerService;
import com.smart.recp.service.user.service.PlatformService;
import com.smart.recp.service.user.service.SellerService;
import com.smart.recp.service.user.service.UserService;
import com.smart.recp.service.user.vo.BuyerVO;
import com.smart.recp.service.user.vo.SellerVO;
import com.smart.recp.service.user.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ybq
 */
@Service
@Slf4j
public class PlatformServiceImpl implements PlatformService {

    @Resource
    SellerService sellerService;

    @Resource
    BuyerService buyerService;

    @Resource
    UserService userService;

    @Override
    public PageResult<SellerVO> listSeller(Integer page, Integer size, Integer sellerId, String search) throws BaseException {
        try {
            SellerDTO sellerDTO = new SellerDTO();
            if (ObjectUtils.isNotEmpty(sellerId)) {
                sellerDTO.setSellerId(sellerId);
            } else if (ObjectUtils.isNotEmpty(search)) {
                sellerDTO.setSellerAlias(search);
                sellerDTO.setSellerName(search);
            }
            return sellerService.list(page, size, sellerDTO);
        } catch (Exception e) {
            log.error("失败：【listSeller】获取商家列表信息失败,page：{}，size:{},sellerId:{},search:{}", page, size, sellerId, search);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "获取商家列表信息失败");
        }
    }

    @Override
    public SellerVO getSellerById(Integer sellerId) throws BaseException {
        return sellerService.getById(sellerId);
    }

    @Override
    public PageResult<BuyerVO> listBuyer(Integer page, Integer size, Integer buyerId, String search) throws BaseException {
        try {
            BuyerDTO buyerDTO = new BuyerDTO();
            if (ObjectUtils.isNotEmpty(buyerId)) {
                buyerDTO.setBuyerId(buyerId);
            } else if (ObjectUtils.isNotEmpty(search)) {
                buyerDTO.setNickname(search);
            }
            return buyerService.list(page, size, buyerDTO);
        } catch (Exception e) {
            log.error("失败：【listBuyer】获取买家列表信息失败,page：{}，size:{},buyerId:{},search:{}", page, size, buyerId, search);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "获取买家列表信息失败");
        }
    }

    @Override
    public BuyerVO getBuyerById(Integer buyerId) throws BaseException {
        return buyerService.getById(buyerId);
    }

    @Override
    public PageResult<UserVO> listUser(Integer page, Integer size, Integer userId, String account, Integer type) throws BaseException {
        try {
            UserDTO userDTO = new UserDTO();
            if (ObjectUtils.isNotEmpty(userId)) {
                userDTO.setUserId(userId);
            } else if (ObjectUtils.isNotEmpty(account)) {
                userDTO.setAccount(account);
            } else if (ObjectUtils.isNotEmpty(type)) {
                userDTO.setType(type);
            }
            return userService.list(page, size, userDTO);
        } catch (Exception e) {
            log.error("失败：【listBuyer】获取用户账号列表信息失败,page：{}，size:{},userId:{},account:{},type:{}", page, size, userId, account, type);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "获取用户账号列表信息失败");
        }
    }

    @Override
    public UserVO getUserById(Integer userId) throws BaseException {
        return userService.getById(userId);
    }
}

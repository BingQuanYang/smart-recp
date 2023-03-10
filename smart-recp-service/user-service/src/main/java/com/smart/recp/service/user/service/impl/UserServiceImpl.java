package com.smart.recp.service.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.PageResult;
import com.smart.recp.common.core.util.BeanCopyUtils;
import com.smart.recp.service.user.dto.UserDTO;
import com.smart.recp.service.user.entity.Seller;
import com.smart.recp.service.user.entity.User;
import com.smart.recp.service.user.mapper.UserMapper;
import com.smart.recp.service.user.service.UserService;
import com.smart.recp.service.user.vo.SellerVO;
import com.smart.recp.service.user.vo.UserVO;
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
public class UserServiceImpl implements UserService {


    @Resource
    UserMapper userMapper;


    @Override
    public UserVO getById(Integer userId) throws BaseException {
        try {
            User user = userMapper.selectById(userId);
            if (ObjectUtils.isEmpty(user)) {
                log.error("失败：根据ID查询用户失败，用户不存在，ID：{}", userId);
                throw new BaseException(ResultCode.ERROR);
            } else if (user.getIsDelete().equals(1)) {
                log.error("失败：根据ID查询用户失败，该用户已删除，ID：{}", userId);
                throw new BaseException(ResultCode.ERROR);
            }
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            log.error("成功：根据ID查询用户成功，用户：{}", user);
            return userVO;
        } catch (Exception e) {
            log.error("失败：根据ID查询用户失败,ID：{}", userId);
            throw new BaseException(ResultCode.ERROR, String.format("失败：根据ID查询用户失败,ID：{}", userId));
        }
    }

    @Override
    public UserVO getByAccount(String account) throws BaseException {
        try {
            User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getAccount, account));
            if (ObjectUtils.isEmpty(user)) {
                log.error("失败：根据账号查询用户失败，用户不存在，账号：{}", account);
                throw new BaseException(ResultCode.ERROR);
            } else if (user.getIsDelete().equals(1)) {
                log.error("失败：根据账号查询用户失败，该用户已删除，账号：{}", account);
                throw new BaseException(ResultCode.ERROR);
            }
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            log.error("成功：根据账号查询用户成功，用户：{}", user);
            return userVO;
        } catch (Exception e) {
            log.error("失败：根据账号查询用户失败,账号：{}", account);
            throw new BaseException(ResultCode.ERROR, String.format("失败：根据账号查询用户失败,账号：{}", account));
        }
    }

    @Override
    public UserVO getByMobile(String mobile) throws BaseException {
        try {
            User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getMobile, mobile));
            if (ObjectUtils.isEmpty(user)) {
                log.error("失败：根据手机号查询用户失败，用户不存在，手机号：{}", mobile);
                throw new BaseException(ResultCode.ERROR);
            } else if (user.getIsDelete().equals(1)) {
                log.error("失败：根据手机号查询用户失败，该用户已删除，手机号：{}", mobile);
                throw new BaseException(ResultCode.ERROR);
            }
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            log.error("成功：根据手机号查询用户失败，用户：{}", user);
            return userVO;
        } catch (Exception e) {
            log.error("失败：根据手机号查询用户失败,手机号：{}", mobile);
            throw new BaseException(ResultCode.ERROR, String.format("失败：根据手机号查询用户失败,手机号：{}", mobile));
        }
    }

    @Override
    public boolean add(UserDTO userDTO) throws BaseException {
        try {
            User user = userMapper.selectOne(new QueryWrapper<User>().lambda().eq(User::getAccount, userDTO.getAccount()));

            if (ObjectUtils.isNotEmpty(user)) {
                log.error("失败：添加用户失败,改账号已存在,userDTO：{}", userDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            user = new User();
            BeanUtils.copyProperties(userDTO, user);
            int insert = userMapper.insert(user);
            if (insert < 1) {
                log.error("失败：添加用户失败,添加数据库失败,userDTO：{}", userDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            return true;
        } catch (Exception e) {
            log.error("失败：添加用户失败，userDTO：{}", userDTO);
            throw new BaseException(ResultCode.ERROR, String.format("失败：添加用户失败，userDTO：{}", userDTO));
        }
    }

    @Override
    public boolean update(UserDTO userDTO) throws BaseException {
        try {
            User user = new User();
            BeanUtils.copyProperties(userDTO, user);
            int update = userMapper.updateById(user);
            if (update < 1) {
                log.error("失败：修改用户失败,修改数据库失败,userDTO：{}", userDTO);
                throw new BaseException(ResultCode.ERROR);
            }
            return true;
        } catch (Exception e) {
            log.error("失败：修改用户失败,修改数据库失败,userDTO：{}", userDTO);
            throw new BaseException(ResultCode.ERROR, String.format("失败：修改用户失败,修改数据库失败,userDTO：{}", userDTO));
        }
    }


    @Override
    public PageResult<UserVO> list(Integer page, Integer size, UserDTO userDTO) throws BaseException {
        try {
            LambdaQueryWrapper<User> lambda = new QueryWrapper<User>().lambda();
            if (ObjectUtils.isNotEmpty(userDTO.getUserId())) {
                lambda.eq(User::getUserId, userDTO.getUserId());
            } else if (ObjectUtils.isNotEmpty(userDTO.getAccount())) {
                lambda.eq(User::getAccount, userDTO.getAccount());
            } else if (ObjectUtils.isNotEmpty(userDTO.getType())) {
                lambda.eq(User::getType, userDTO.getType());
            }
            Page<User> UserPage = userMapper.selectPage(new Page<User>(page, size), lambda);
            if (ObjectUtils.isEmpty(UserPage)) {
                log.error("失败：【list】获取用户列表失败");
                throw new BaseException(ResultCode.ERROR);
            }
            PageResult<UserVO> result = new PageResult<>();
            result.setPage(UserPage.getCurrent());
            result.setPageSize(UserPage.getSize());
            result.setPages(UserPage.getPages());
            result.setTotalCount(UserPage.getTotal());
            List<UserVO> UserVOS = BeanCopyUtils.copyListProperties(UserPage.getRecords(), UserVO::new);
            result.setList(UserVOS);
            return result;
        } catch (Exception e) {
            log.error("失败：【list】获取用户列表失败,page:{},size:{},UserDTO：{}", page, size, userDTO);
            e.printStackTrace();
            throw new BaseException(ResultCode.ERROR.getStatus(), "获取用户列表失败");
        }
    }
}

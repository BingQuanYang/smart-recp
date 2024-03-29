package com.smart.recp.service.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ybq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "用户")
public class UserDTO {

    /**
     * 用户ID
     */
    @ApiModelProperty(value="用户ID")
    private Integer userId;

    /**
     * 账号
     */
    @ApiModelProperty(value="账号")
    private String account;

    /**
     * 密码
     */
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 真实姓名
     */
    @ApiModelProperty(value="真实姓名")
    private String realName;

    /**
     * 身份证
     */
    @ApiModelProperty(value="身份证")
    private String identificationCard;

    /**
     * 手机号
     */
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 类型：1->买家,2->卖家,0->平台管理员
     */
    @ApiModelProperty(value="类型：1->买家,2->卖家,0->平台管理员")
    private Integer type;

    /**
     * 认证状态：0->未认证,1->已认证,2->认证中
     */
    @ApiModelProperty(value="认证状态：0->未认证,1->已认证,2->认证中")
    private Integer certStatus;

    /**
     * 最后一次登录时间
     */
    @ApiModelProperty(value="最后一次登录时间")
    private LocalDateTime lastLoginTime;


    /**
     * 原密码
     */
    @ApiModelProperty(value="最后一次登录时间")
    private String oldPassword;
}

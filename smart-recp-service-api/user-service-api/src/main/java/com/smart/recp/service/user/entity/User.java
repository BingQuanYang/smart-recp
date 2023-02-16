package com.smart.recp.service.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
    * 用户表
    */
@ApiModel(value="com-smart-recp-service-user-entity-User")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_user")
public class User {
    /**
     * 用户ID
     */
    @TableId(value = "user_id", type = IdType.AUTO)
    @ApiModelProperty(value="用户ID")
    private Integer userId;

    /**
     * 账号
     */
    @TableField(value = "account")
    @ApiModelProperty(value="账号")
    private String account;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    @ApiModelProperty(value="密码")
    private String password;

    /**
     * 真实姓名
     */
    @TableField(value = "real_name")
    @ApiModelProperty(value="真实姓名")
    private String realName;

    /**
     * 身份证
     */
    @TableField(value = "identification_card")
    @ApiModelProperty(value="身份证")
    private String identificationCard;

    /**
     * 手机号
     */
    @TableField(value = "mobile")
    @ApiModelProperty(value="手机号")
    private String mobile;

    /**
     * 类型：1->买家,2->卖家,0->平台管理员
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value="类型：1->买家,2->卖家,0->平台管理员")
    private Integer type;

    /**
     * 认证状态：0->未认证,1->已认证,2->认证中
     */
    @TableField(value = "cert_status")
    @ApiModelProperty(value="认证状态：0->未认证,1->已认证,2->认证中")
    private Integer certStatus;

    /**
     * 最后一次登录时间
     */
    @TableField(value = "last_login_time")
    @ApiModelProperty(value="最后一次登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    @ApiModelProperty(value="创建者")
    private Integer creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value="创建时间")
    private LocalDateTime createTime;

    /**
     * 修改者
     */
    @TableField(value = "modified_by")
    @ApiModelProperty(value="修改者")
    private Integer modifiedBy;

    /**
     * 修改时间
     */
    @TableField(value = "modified_time")
    @ApiModelProperty(value="修改时间")
    private LocalDateTime modifiedTime;

    /**
     * 软删除: 0->未删除,1->已删除
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value="软删除: 0->未删除,1->已删除")
    private Integer isDelete;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_ACCOUNT = "account";

    public static final String COL_PASSWORD = "password";

    public static final String COL_REAL_NAME = "real_name";

    public static final String COL_IDENTIFICATION_CARD = "identification_card";

    public static final String COL_MOBILE = "mobile";

    public static final String COL_TYPE = "type";

    public static final String COL_CERT_STATUS = "cert_status";

    public static final String COL_LAST_LOGIN_TIME = "last_login_time";

    public static final String COL_CREATOR = "creator";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";

    public static final String COL_IS_DELETE = "is_delete";
}
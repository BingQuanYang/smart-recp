package com.smart.recp.service.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 买家表
 */
@ApiModel(value = "买家表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_buyer")
public class Buyer {
    /**
     * 卖家ID,来源于userId
     */
    @TableId(value = "buyer_id", type = IdType.INPUT)
    @ApiModelProperty(value = "卖家ID,来源于userId")
    private Integer buyerId;

    /**
     * 卖家账号,来源于account
     */
    @TableField(value = "buyer_account")
    @ApiModelProperty(value = "卖家账号,来源于account")
    private String buyerAccount;

    /**
     * 昵称
     */
    @TableField(value = "nickname")
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 性别
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 个人简介
     */
    @TableField(value = "introduction")
    @ApiModelProperty(value = "个人简介")
    private String introduction;

    /**
     * 生日
     */
    @TableField(value = "birthday")
    @ApiModelProperty(value = "生日")
    private String birthday;

    /**
     * 头像
     */
    @TableField(value = "head_portrait")
    @ApiModelProperty(value = "头像")
    private String headPortrait;

    /**
     * 状态:1->正常，0->禁用
     */
    @TableField(value = "buyer_status")
    @ApiModelProperty(value = "状态:1->正常，0->禁用")
    private Integer buyerStatus;

    /**
     * 余额
     */
    @TableField(value = "balance_money")
    @ApiModelProperty(value = "余额")
    private BigDecimal balanceMoney;

    /**
     * 等级
     */
    @TableField(value = "buyer_level")
    @ApiModelProperty(value = "等级")
    private Integer buyerLevel;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改者
     */
    @TableField(value = "modified_by")
    @ApiModelProperty(value = "修改者")
    private Integer modifiedBy;

    /**
     * 修改时间
     */
    @TableField(value = "modified_time")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

    /**
     * 软删除: 0->未删除,1->已删除
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value = "软删除: 0->未删除,1->已删除")
    private Integer isDelete;

    public static final String COL_BUYER_ID = "buyer_id";

    public static final String COL_BUYER_ACCOUNT = "buyer_account";

    public static final String COL_NICKNAME = "nickname";

    public static final String COL_EMAIL = "email";

    public static final String COL_GENDER = "gender";

    public static final String COL_INTRODUCTION = "introduction";

    public static final String COL_BIRTHDAY = "birthday";

    public static final String COL_HEAD_PORTRAIT = "head_portrait";

    public static final String COL_BUYER_STATUS = "buyer_status";

    public static final String COL_BALANCE_MONEY = "balance_money";

    public static final String COL_BUYER_LEVEL = "buyer_level";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";

    public static final String COL_IS_DELETE = "is_delete";
}
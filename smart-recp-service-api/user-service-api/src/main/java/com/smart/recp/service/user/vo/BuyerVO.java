package com.smart.recp.service.user.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ybq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "买家")
public class BuyerVO {
    /**
     * 卖家ID,来源于userId
     */
    @ApiModelProperty(value = "卖家ID,来源于userId")
    private Integer buyerId;

    /**
     * 卖家账号,来源于account
     */
    @ApiModelProperty(value = "卖家账号,来源于account")
    private String buyerAccount;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 邮箱
     */
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 性别
     */
    @ApiModelProperty(value = "性别")
    private String gender;

    /**
     * 个人简介
     */
    @ApiModelProperty(value = "个人简介")
    private String introduction;

    /**
     * 生日
     */
    @ApiModelProperty(value = "生日")
    private String birthday;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headPortrait;

    /**
     * 状态:1->正常，0->禁用
     */
    @ApiModelProperty(value = "状态:1->正常，0->禁用")
    private Integer buyerStatus;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal balanceMoney;

    /**
     * 等级
     */
    @ApiModelProperty(value = "等级")
    private Integer buyerLevel;

    /**
     * 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    private Integer modifiedBy;

    /**
     * 修改时间
     */
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

    /**
     * 软删除: 0->未删除,1->已删除
     */
    @ApiModelProperty(value = "软删除: 0->未删除,1->已删除")
    private Integer isDelete;
}

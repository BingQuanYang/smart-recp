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
 * 客户关注表
 */
@ApiModel(value = "客户关注表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_customer_follow")
public class CustomerFollow {
    /**
     * ID
     */
    @TableId(value = "follow_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer followId;

    /**
     * 卖家ID
     */
    @TableField(value = "seller_id")
    @ApiModelProperty(value = "卖家ID")
    private Integer sellerId;

    /**
     * 买家ID
     */
    @TableField(value = "buyer_id")
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 手机号
     */
    @TableField(value = "customer_mobile")
    @ApiModelProperty(value = "手机号")
    private String customerMobile;

    /**
     * 会员状态：0->不是会员,1->会员
     */
    @TableField(value = "vip_status")
    @ApiModelProperty(value = "会员状态：0->不是会员,1->会员")
    private Integer vipStatus;

    /**
     * 加入会员时间
     */
    @TableField(value = "vip_create_time")
    @ApiModelProperty(value = "加入会员时间")
    private LocalDateTime vipCreateTime;

    /**
     * 购买次数
     */
    @TableField(value = "buy_count")
    @ApiModelProperty(value = "购买次数")
    private Integer buyCount;

    /**
     * 总购买金额
     */
    @TableField(value = "total_buy_money")
    @ApiModelProperty(value = "总购买金额")
    private BigDecimal totalBuyMoney;

    /**
     * 最后一次购买时间
     */
    @TableField(value = "last_buy_time")
    @ApiModelProperty(value = "最后一次购买时间")
    private LocalDateTime lastBuyTime;

    /**
     * 客户等级
     */
    @TableField(value = "customer_level")
    @ApiModelProperty(value = "客户等级")
    private Integer customerLevel;

    /**
     * 关注时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "关注时间")
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

    public static final String COL_FOLLOW_ID = "follow_id";

    public static final String COL_SELLER_ID = "seller_id";

    public static final String COL_BUYER_ID = "buyer_id";

    public static final String COL_CUSTOMER_MOBILE = "customer_mobile";

    public static final String COL_VIP_STATUS = "vip_status";

    public static final String COL_VIP_CREATE_TIME = "vip_create_time";

    public static final String COL_BUY_COUNT = "buy_count";

    public static final String COL_TOTAL_BUY_MONEY = "total_buy_money";

    public static final String COL_LAST_BUY_TIME = "last_buy_time";

    public static final String COL_CUSTOMER_LEVEL = "customer_level";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";

    public static final String COL_IS_DELETE = "is_delete";
}
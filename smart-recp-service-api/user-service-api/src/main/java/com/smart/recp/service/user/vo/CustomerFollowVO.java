package com.smart.recp.service.user.vo;

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
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("客户关注信息")
public class CustomerFollowVO {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer followId;

    /**
     * 卖家ID
     */
    @ApiModelProperty(value = "卖家ID")
    private Integer sellerId;

    /**
     * 买家ID
     */
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 昵称
     */
    @ApiModelProperty(value = "昵称")
    private String nickname;

    /**
     * 头像
     */
    @ApiModelProperty(value = "头像")
    private String headPortrait;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String customerMobile;

    /**
     * 会员状态：0->不是会员,1->会员
     */
    @ApiModelProperty(value = "会员状态：0->不是会员,1->会员")
    private Integer vipStatus;

    /**
     * 加入会员时间
     */
    @ApiModelProperty(value = "加入会员时间")
    private LocalDateTime vipCreateTime;

    /**
     * 购买次数
     */
    @ApiModelProperty(value = "购买次数")
    private Integer buyCount;

    /**
     * 总购买金额
     */
    @ApiModelProperty(value = "总购买金额")
    private BigDecimal totalBuyMoney;

    /**
     * 最后一次购买时间
     */
    @ApiModelProperty(value = "最后一次购买时间")
    private LocalDateTime lastBuyTime;

    /**
     * 客户等级
     */
    @ApiModelProperty(value = "客户等级")
    private Integer customerLevel;

    /**
     * 关注时间
     */
    @ApiModelProperty(value = "关注时间")
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

}

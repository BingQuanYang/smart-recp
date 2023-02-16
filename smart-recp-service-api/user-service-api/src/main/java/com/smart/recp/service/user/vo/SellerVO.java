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
@ApiModel(value = "卖家【店铺】")
public class SellerVO {
    /**
     * 卖家ID,来源于userId
     */
    @ApiModelProperty(value = "卖家ID,来源于userId")
    private Integer sellerId;

    /**
     * 卖家账号,来源于account
     */
    @ApiModelProperty(value = "卖家账号,来源于account")
    private String sellerAccount;

    /**
     * 店铺名称
     */
    @ApiModelProperty(value = "店铺名称")
    private String sellerName;

    /**
     * 店铺别名,简称
     */
    @ApiModelProperty(value = "店铺别名,简称")
    private String sellerAlias;

    /**
     * 店铺Logo
     */
    @ApiModelProperty(value = "店铺Logo")
    private String sellerLogo;

    /**
     * 营业执照
     */
    @ApiModelProperty(value = "营业执照")
    private String sellerLicence;

    /**
     * 店铺等级
     */
    @ApiModelProperty(value = "店铺等级")
    private Integer sellerRank;

    /**
     * 店铺评分
     */
    @ApiModelProperty(value = "店铺评分")
    private BigDecimal sellerGrade;

    /**
     * 店铺地址
     */
    @ApiModelProperty(value = "店铺地址")
    private String sellerAddress;

    /**
     * 关键字
     */
    @ApiModelProperty(value = "关键字")
    private String sellerKeyword;

    /**
     * 店铺联系方式
     */
    @ApiModelProperty(value = "店铺联系方式")
    private String sellerTel;

    /**
     * 余额
     */
    @ApiModelProperty(value = "余额")
    private BigDecimal balanceMoney;

    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 状态：1->正常,0->冻结
     */
    @ApiModelProperty(value = "状态：1->正常,0->冻结")
    private Integer sellerStatus;

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

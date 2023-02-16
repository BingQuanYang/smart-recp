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
    * 卖家表
    */
@ApiModel(value="com-smart-recp-service-user-entity-Seller")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_seller")
public class Seller {
    /**
     * 卖家ID,来源于userId
     */
    @TableId(value = "seller_id", type = IdType.INPUT)
    @ApiModelProperty(value="卖家ID,来源于userId")
    private Integer sellerId;

    /**
     * 卖家账号,来源于account
     */
    @TableField(value = "seller_account")
    @ApiModelProperty(value="卖家账号,来源于account")
    private String sellerAccount;

    /**
     * 店铺名称
     */
    @TableField(value = "seller_name")
    @ApiModelProperty(value="店铺名称")
    private String sellerName;

    /**
     * 店铺别名,简称
     */
    @TableField(value = "seller_alias")
    @ApiModelProperty(value="店铺别名,简称")
    private String sellerAlias;

    /**
     * 店铺Logo
     */
    @TableField(value = "seller_logo")
    @ApiModelProperty(value="店铺Logo")
    private String sellerLogo;

    /**
     * 营业执照
     */
    @TableField(value = "seller_licence")
    @ApiModelProperty(value="营业执照")
    private String sellerLicence;

    /**
     * 店铺等级
     */
    @TableField(value = "seller_rank")
    @ApiModelProperty(value="店铺等级")
    private Integer sellerRank;

    /**
     * 店铺评分
     */
    @TableField(value = "seller_grade")
    @ApiModelProperty(value="店铺评分")
    private BigDecimal sellerGrade;

    /**
     * 店铺地址
     */
    @TableField(value = "seller_address")
    @ApiModelProperty(value="店铺地址")
    private String sellerAddress;

    /**
     * 关键字
     */
    @TableField(value = "seller_keyword")
    @ApiModelProperty(value="关键字")
    private String sellerKeyword;

    /**
     * 店铺联系方式
     */
    @TableField(value = "seller_tel")
    @ApiModelProperty(value="店铺联系方式")
    private String sellerTel;

    /**
     * 余额
     */
    @TableField(value = "balance_money")
    @ApiModelProperty(value="余额")
    private BigDecimal balanceMoney;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 状态：1->正常,0->冻结
     */
    @TableField(value = "seller_status")
    @ApiModelProperty(value="状态：1->正常,0->冻结")
    private Integer sellerStatus;

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

    public static final String COL_SELLER_ID = "seller_id";

    public static final String COL_SELLER_ACCOUNT = "seller_account";

    public static final String COL_SELLER_NAME = "seller_name";

    public static final String COL_SELLER_ALIAS = "seller_alias";

    public static final String COL_SELLER_LOGO = "seller_logo";

    public static final String COL_SELLER_LICENCE = "seller_licence";

    public static final String COL_SELLER_RANK = "seller_rank";

    public static final String COL_SELLER_GRADE = "seller_grade";

    public static final String COL_SELLER_ADDRESS = "seller_address";

    public static final String COL_SELLER_KEYWORD = "seller_keyword";

    public static final String COL_SELLER_TEL = "seller_tel";

    public static final String COL_BALANCE_MONEY = "balance_money";

    public static final String COL_REMARK = "remark";

    public static final String COL_SELLER_STATUS = "seller_status";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";

    public static final String COL_IS_DELETE = "is_delete";
}
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
 * 收货地址表
 */
@ApiModel(value = "收货地址表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_receive_address")
public class ReceiveAddress {
    /**
     * ID
     */
    @TableId(value = "receive_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer receiveId;

    /**
     * 买家ID
     */
    @TableField(value = "buyer_id")
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 收货人名称
     */
    @TableField(value = "receive_name")
    @ApiModelProperty(value = "收货人名称")
    private String receiveName;

    /**
     * 手机号
     */
    @TableField(value = "customer_mobile")
    @ApiModelProperty(value = "手机号")
    private String customerMobile;

    /**
     * 是否为默认
     */
    @TableField(value = "default_status")
    @ApiModelProperty(value = "是否为默认")
    private Integer defaultStatus;

    /**
     * 省份/直辖市
     */
    @TableField(value = "province")
    @ApiModelProperty(value = "省份/直辖市")
    private String province;

    /**
     * 城市
     */
    @TableField(value = "city")
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 区/县
     */
    @TableField(value = "county")
    @ApiModelProperty(value = "区/县")
    private String county;

    /**
     * 详细地址(街道)
     */
    @TableField(value = "detail_address")
    @ApiModelProperty(value = "详细地址(街道)")
    private String detailAddress;

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

    public static final String COL_RECEIVE_ID = "receive_id";

    public static final String COL_BUYER_ID = "buyer_id";

    public static final String COL_RECEIVE_NAME = "receive_name";

    public static final String COL_CUSTOMER_MOBILE = "customer_mobile";

    public static final String COL_DEFAULT_STATUS = "default_status";

    public static final String COL_PROVINCE = "province";

    public static final String COL_CITY = "city";

    public static final String COL_COUNTY = "county";

    public static final String COL_DETAIL_ADDRESS = "detail_address";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";
}
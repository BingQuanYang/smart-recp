package com.smart.recp.service.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ybq
 */
@ApiModel(value = "收货地址信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveAddressVO {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer receiveId;

    /**
     * 买家ID
     */
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 收货人名称
     */
    @ApiModelProperty(value = "收货人名称")
    private String receiveName;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String customerMobile;

    /**
     * 是否为默认
     */
    @ApiModelProperty(value = "是否为默认")
    private Integer defaultStatus;

    /**
     * 省份/直辖市
     */
    @ApiModelProperty(value = "省份/直辖市")
    private String province;

    /**
     * 城市
     */
    @ApiModelProperty(value = "城市")
    private String city;

    /**
     * 区/县
     */
    @ApiModelProperty(value = "区/县")
    private String county;

    /**
     * 详细地址(街道)
     */
    @ApiModelProperty(value = "详细地址(街道)")
    private String detailAddress;

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
}

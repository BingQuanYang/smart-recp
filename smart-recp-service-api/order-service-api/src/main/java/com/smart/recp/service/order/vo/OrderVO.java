package com.smart.recp.service.order.vo;

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
@ApiModel(value = "订单信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer orderId;

    /**
     * 订单编号
     */
    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    /**
     * 买家ID
     */
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 交易状态：0->进行中,1->已完成,2->已取消,3->已超时,4->已结算
     */
    @ApiModelProperty(value = "交易状态：0->进行中,1->已完成,2->已取消,3->已超时,4->已结算")
    private Integer tradeStatus;

    /**
     * 支付状态：0->未付款,1->已付款
     */
    @ApiModelProperty(value = "支付状态：0->未付款,1->已付款")
    private Integer payStatus;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderMoney;

    /**
     * 付款金额
     */
    @ApiModelProperty(value = "付款金额")
    private BigDecimal payMoney;

    /**
     * 商品最终总金额
     */
    @ApiModelProperty(value = "商品最终总金额")
    private BigDecimal goodsTotalMoney;

    /**
     * 支付时间
     */
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    /**
     * 支付平台：0->余额,1->微信,2->支付宝
     */
    @ApiModelProperty(value = "支付平台：0->余额,1->微信,2->支付宝")
    private Integer payPlatform;

    /**
     * 交易编号:比如支付宝给我平台的订单号
     */
    @ApiModelProperty(value = "交易编号:比如支付宝给我平台的订单号")
    private String tradeNumber;

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

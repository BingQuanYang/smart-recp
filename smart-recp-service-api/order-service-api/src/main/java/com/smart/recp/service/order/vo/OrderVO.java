package com.smart.recp.service.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author ybq
 */
@ApiModel(value = "订单信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderVO {

    @ApiModelProperty(value = "ID")
    private Integer orderId;

    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    @ApiModelProperty(value = "交易状态：0->进行中,1->已完成,2->已取消,3->已超时,4->已结算")
    private Integer tradeStatus;

    @ApiModelProperty(value = "支付状态：0->未付款,1->已付款")
    private Integer payStatus;

    @ApiModelProperty(value = "订单总金额")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal orderMoney;

    @ApiModelProperty(value = "付款金额")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal payMoney;

    @ApiModelProperty(value = "商品最终总金额")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal goodsTotalMoney;

    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @ApiModelProperty(value = "支付平台：0->余额,1->微信,2->支付宝")
    private Integer payPlatform;

    @ApiModelProperty(value = "交易编号:比如支付宝给我平台的订单号")
    private String tradeNumber;

    @ApiModelProperty(value = "修改者")
    private Integer modifiedBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

    @ApiModelProperty(value = "订单子项目")
    private List<OrderItemVO> orderItemVOList;


}

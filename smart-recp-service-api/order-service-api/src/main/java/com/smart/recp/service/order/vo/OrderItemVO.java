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
@ApiModel(value = "订单子项目信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemVO {
    @ApiModelProperty(value = "ID")
    private Integer orderItemId;

    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    @ApiModelProperty(value = "所属卖家ID")
    private Integer sellerId;

    @ApiModelProperty(value = "订单ID")
    private Integer orderId;

    @ApiModelProperty(value = "订单编号，订单表的订单编号")
    private String orderNumber;

    @ApiModelProperty(value = "子订单编号,买卖家展示")
    private String orderItemNumber;

    @ApiModelProperty(value = "提交状态,0->未提交,1->已提交,2->已取消")
    private Integer orderStatus;

    @ApiModelProperty(value = "配送状态：0->待发货,1->待收货,2->已收货")
    private Integer deliveryStatus;

    @ApiModelProperty(value = "卖家状态：0->待收货,1->已确认收货,2->已签收,3->换货,4->退货")
    private Integer customerStatus;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "商品规格ID")
    private Integer specId;

    @ApiModelProperty(value = "商品规格价格ID")
    private Integer priceId;

    @ApiModelProperty(value = "商品数量")
    private Integer goodsAmount;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    private String goodsImage;

    @ApiModelProperty(value = "商品规格名称")
    private String specName;

    @ApiModelProperty(value = "价格")
    private BigDecimal goodsPrice;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "订单总金额")
    private BigDecimal deliveryMoney;

    @ApiModelProperty(value = "收货人名称")
    private String receiveName;

    @ApiModelProperty(value = "收货人手机号")
    private String receiveMobile;

    @ApiModelProperty(value = "收货地址")
    private String receiveAddress;

    @ApiModelProperty(value = "物流平台：0->顺丰,1->邮政,2->中通...")
    private Integer logisticsPlatform;

    @ApiModelProperty(value = "物流编号")
    private String logisticsNumber;

    @ApiModelProperty(value = "卖家已完成发货时间")
    private LocalDateTime sellerFinishTime;

    @ApiModelProperty(value = "配送完成时间，到货时间")
    private LocalDateTime deliveryFinishTime;

    @ApiModelProperty(value = "签收时间")
    private LocalDateTime deliveryReceiveTime;

    @ApiModelProperty(value = "确认收货时间")
    private LocalDateTime customerFinishTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private Integer modifiedBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

}

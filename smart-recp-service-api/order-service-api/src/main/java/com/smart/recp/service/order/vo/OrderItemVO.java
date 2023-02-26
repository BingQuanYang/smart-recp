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
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer orderItemId;

    /**
     * 买家ID
     */
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 所属卖家ID
     */
    @ApiModelProperty(value = "所属卖家ID")
    private Integer sellerId;

    /**
     * 订单ID
     */
    @ApiModelProperty(value = "订单ID")
    private Integer orderId;

    /**
     * 订单编号，订单表的订单编号
     */
    @ApiModelProperty(value = "订单编号，订单表的订单编号")
    private String orderNumber;

    /**
     * 子订单编号,买卖家展示
     */
    @ApiModelProperty(value = "子订单编号,买卖家展示")
    private String orderItemNumber;

    /**
     * 提交状态,0->未提交,1->已提交,2->已取消
     */
    @ApiModelProperty(value = "提交状态,0->未提交,1->已提交,2->已取消")
    private Integer orderStatus;

    /**
     * 配送状态：0->待发货,1->待收货,2->已收货
     */
    @ApiModelProperty(value = "配送状态：0->待发货,1->待收货,2->已收货")
    private Integer deliveryStatus;

    /**
     * 卖家状态：0->待收货,1->已确认收货,2->已签收,3->换货,4->退货
     */
    @ApiModelProperty(value = "卖家状态：0->待收货,1->已确认收货,2->已签收,3->换货,4->退货")
    private Integer customerStatus;

    /**
     * 商品ID
     */
    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    /**
     * 商品规格ID
     */
    @ApiModelProperty(value = "商品规格ID")
    private Integer specId;

    /**
     * 商品规格价格ID
     */
    @ApiModelProperty(value = "商品规格价格ID")
    private Integer priceId;

    /**
     * 商品数量
     */
    @ApiModelProperty(value = "商品数量")
    private Integer goodsAmount;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片")
    private String goodsImage;

    /**
     * 商品规格名称
     */
    @ApiModelProperty(value = "商品规格名称")
    private String specName;

    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal goodsPrice;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalMoney;

    /**
     * 订单总金额
     */
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal deliveryMoney;

    /**
     * 收货人名称
     */
    @ApiModelProperty(value = "收货人名称")
    private String receiveName;

    /**
     * 收货人手机号
     */
    @ApiModelProperty(value = "收货人手机号")
    private String receiveMobile;

    /**
     * 收货地址
     */
    @ApiModelProperty(value = "收货地址")
    private String receiveAddress;

    /**
     * 物流平台：0->顺丰,1->邮政,2->中通...
     */
    @ApiModelProperty(value = "物流平台：0->顺丰,1->邮政,2->中通...")
    private Integer logisticsPlatform;

    /**
     * 物流编号
     */
    @ApiModelProperty(value = "物流编号")
    private String logisticsNumber;

    /**
     * 卖家已完成发货时间
     */
    @ApiModelProperty(value = "卖家已完成发货时间")
    private LocalDateTime sellerFinishTime;

    /**
     * 配送完成时间，到货时间
     */
    @ApiModelProperty(value = "配送完成时间，到货时间")
    private LocalDateTime deliveryFinishTime;

    /**
     * 签收时间
     */
    @ApiModelProperty(value = "签收时间")
    private LocalDateTime deliveryReceiveTime;

    /**
     * 确认收货时间
     */
    @ApiModelProperty(value = "确认收货时间")
    private LocalDateTime customerFinishTime;

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

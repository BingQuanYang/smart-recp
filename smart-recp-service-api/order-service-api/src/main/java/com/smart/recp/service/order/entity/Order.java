package com.smart.recp.service.order.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 订单表
 */
@ApiModel(value = "订单信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_order")
public class Order {

    @TableId(value = "order_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer orderId;

    @TableField(value = "order_number")
    @ApiModelProperty(value = "订单编号")
    private String orderNumber;

    @TableField(value = "buyer_id")
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    @TableField(value = "trade_status")
    @ApiModelProperty(value = "交易状态：0->进行中,1->已完成,2->已取消,3->已超时,4->已结算")
    private Integer tradeStatus;

    @TableField(value = "pay_status")
    @ApiModelProperty(value = "支付状态：0->未付款,1->已付款")
    private Integer payStatus;

    @TableField(value = "order_money")
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal orderMoney;

    @TableField(value = "pay_money")
    @ApiModelProperty(value = "付款金额")
    private BigDecimal payMoney;

    @TableField(value = "goods_total_money")
    @ApiModelProperty(value = "商品最终总金额")
    private BigDecimal goodsTotalMoney;

    @TableField(value = "pay_time")
    @ApiModelProperty(value = "支付时间")
    private LocalDateTime payTime;

    @TableField(value = "pay_platform")
    @ApiModelProperty(value = "支付平台：0->余额,1->微信,2->支付宝")
    private Integer payPlatform;

    @TableField(value = "trade_number")
    @ApiModelProperty(value = "交易编号:比如支付宝给我平台的订单号")
    private String tradeNumber;

    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @TableField(value = "modified_by")
    @ApiModelProperty(value = "修改者")
    private Integer modifiedBy;

    @TableField(value = "modified_time")
    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;

    @TableField(value = "is_delete")
    @ApiModelProperty(value = "软删除: 0->未删除,1->已删除")
    private Integer isDelete;

    @TableField(exist = false)
    @ApiModelProperty(value = "订单子项目列表")
    private List<OrderItem> orderItemList;

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_ORDER_NUMBER = "order_number";

    public static final String COL_BUYER_ID = "buyer_id";

    public static final String COL_TRADE_STATUS = "trade_status";

    public static final String COL_PAY_STATUS = "pay_status";

    public static final String COL_ORDER_MONEY = "order_money";

    public static final String COL_PAY_MONEY = "pay_money";

    public static final String COL_GOODS_TOTAL_MONEY = "goods_total_money";

    public static final String COL_PAY_TIME = "pay_time";

    public static final String COL_PAY_PLATFORM = "pay_platform";

    public static final String COL_TRADE_NUMBER = "trade_number";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";

    public static final String COL_IS_DELETE = "is_delete";
}
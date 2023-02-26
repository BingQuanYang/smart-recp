package com.smart.recp.service.order.entity;

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
 * 订单子项目表
 */
@ApiModel(value = "订单子项目信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_order_item")
public class OrderItem {
    /**
     * ID
     */
    @TableId(value = "order_item_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer orderItemId;

    /**
     * 买家ID
     */
    @TableField(value = "buyer_id")
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 所属卖家ID
     */
    @TableField(value = "seller_id")
    @ApiModelProperty(value = "所属卖家ID")
    private Integer sellerId;

    /**
     * 订单ID
     */
    @TableField(value = "order_id")
    @ApiModelProperty(value = "订单ID")
    private Integer orderId;

    /**
     * 订单编号，订单表的订单编号
     */
    @TableField(value = "order_number")
    @ApiModelProperty(value = "订单编号，订单表的订单编号")
    private String orderNumber;

    /**
     * 子订单编号,买卖家展示
     */
    @TableField(value = "order_item_number")
    @ApiModelProperty(value = "子订单编号,买卖家展示")
    private String orderItemNumber;

    /**
     * 提交状态,0->未提交,1->已提交,2->已取消
     */
    @TableField(value = "order_status")
    @ApiModelProperty(value = "提交状态,0->未提交,1->已提交,2->已取消")
    private Integer orderStatus;

    /**
     * 配送状态：0->待发货,1->待收货,2->已收货
     */
    @TableField(value = "delivery_status")
    @ApiModelProperty(value = "配送状态：0->待发货,1->待收货,2->已收货")
    private Integer deliveryStatus;

    /**
     * 卖家状态：0->待收货,1->已确认收货,2->已签收,3->换货,4->退货
     */
    @TableField(value = "customer_status")
    @ApiModelProperty(value = "卖家状态：0->待收货,1->已确认收货,2->已签收,3->换货,4->退货")
    private Integer customerStatus;

    /**
     * 商品ID
     */
    @TableField(value = "goods_id")
    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    /**
     * 商品规格ID
     */
    @TableField(value = "spec_id")
    @ApiModelProperty(value = "商品规格ID")
    private Integer specId;

    /**
     * 商品规格价格ID
     */
    @TableField(value = "price_id")
    @ApiModelProperty(value = "商品规格价格ID")
    private Integer priceId;

    /**
     * 商品数量
     */
    @TableField(value = "goods_amount")
    @ApiModelProperty(value = "商品数量")
    private Integer goodsAmount;

    /**
     * 商品名称
     */
    @TableField(value = "goods_name")
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 商品图片
     */
    @TableField(value = "goods_image")
    @ApiModelProperty(value = "商品图片")
    private String goodsImage;

    /**
     * 商品规格名称
     */
    @TableField(value = "spec_name")
    @ApiModelProperty(value = "商品规格名称")
    private String specName;

    /**
     * 价格
     */
    @TableField(value = "goods_price")
    @ApiModelProperty(value = "价格")
    private BigDecimal goodsPrice;

    /**
     * 订单总金额
     */
    @TableField(value = "total_money")
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal totalMoney;

    /**
     * 订单总金额
     */
    @TableField(value = "delivery_money")
    @ApiModelProperty(value = "订单总金额")
    private BigDecimal deliveryMoney;

    /**
     * 收货人名称
     */
    @TableField(value = "receive_name")
    @ApiModelProperty(value = "收货人名称")
    private String receiveName;

    /**
     * 收货人手机号
     */
    @TableField(value = "receive_mobile")
    @ApiModelProperty(value = "收货人手机号")
    private String receiveMobile;

    /**
     * 收货地址
     */
    @TableField(value = "receive_address")
    @ApiModelProperty(value = "收货地址")
    private String receiveAddress;

    /**
     * 物流平台：0->顺丰,1->邮政,2->中通...
     */
    @TableField(value = "logistics_platform")
    @ApiModelProperty(value = "物流平台：0->顺丰,1->邮政,2->中通...")
    private Integer logisticsPlatform;

    /**
     * 物流编号
     */
    @TableField(value = "logistics_number")
    @ApiModelProperty(value = "物流编号")
    private String logisticsNumber;

    /**
     * 卖家已完成发货时间
     */
    @TableField(value = "seller_finish_time")
    @ApiModelProperty(value = "卖家已完成发货时间")
    private LocalDateTime sellerFinishTime;

    /**
     * 配送完成时间，到货时间
     */
    @TableField(value = "delivery_finish_time")
    @ApiModelProperty(value = "配送完成时间，到货时间")
    private LocalDateTime deliveryFinishTime;

    /**
     * 签收时间
     */
    @TableField(value = "delivery_receive_time")
    @ApiModelProperty(value = "签收时间")
    private LocalDateTime deliveryReceiveTime;

    /**
     * 确认收货时间
     */
    @TableField(value = "customer_finish_time")
    @ApiModelProperty(value = "确认收货时间")
    private LocalDateTime customerFinishTime;

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

    /**
     * 软删除: 0->未删除,1->已删除
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value = "软删除: 0->未删除,1->已删除")
    private Integer isDelete;

    public static final String COL_ORDER_ITEM_ID = "order_item_id";

    public static final String COL_BUYER_ID = "buyer_id";

    public static final String COL_SELLER_ID = "seller_id";

    public static final String COL_ORDER_ID = "order_id";

    public static final String COL_ORDER_NUMBER = "order_number";

    public static final String COL_ORDER_ITEM_NUMBER = "order_item_number";

    public static final String COL_ORDER_STATUS = "order_status";

    public static final String COL_DELIVERY_STATUS = "delivery_status";

    public static final String COL_CUSTOMER_STATUS = "customer_status";

    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_SPEC_ID = "spec_id";

    public static final String COL_PRICE_ID = "price_id";

    public static final String COL_GOODS_AMOUNT = "goods_amount";

    public static final String COL_GOODS_NAME = "goods_name";

    public static final String COL_GOODS_IMAGE = "goods_image";

    public static final String COL_SPEC_NAME = "spec_name";

    public static final String COL_GOODS_PRICE = "goods_price";

    public static final String COL_TOTAL_MONEY = "total_money";

    public static final String COL_DELIVERY_MONEY = "delivery_money";

    public static final String COL_RECEIVE_NAME = "receive_name";

    public static final String COL_RECEIVE_MOBILE = "receive_mobile";

    public static final String COL_RECEIVE_ADDRESS = "receive_address";

    public static final String COL_LOGISTICS_PLATFORM = "logistics_platform";

    public static final String COL_LOGISTICS_NUMBER = "logistics_number";

    public static final String COL_SELLER_FINISH_TIME = "seller_finish_time";

    public static final String COL_DELIVERY_FINISH_TIME = "delivery_finish_time";

    public static final String COL_DELIVERY_RECEIVE_TIME = "delivery_receive_time";

    public static final String COL_CUSTOMER_FINISH_TIME = "customer_finish_time";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";

    public static final String COL_IS_DELETE = "is_delete";
}
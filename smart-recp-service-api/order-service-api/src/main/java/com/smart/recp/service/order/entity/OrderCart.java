package com.smart.recp.service.order.entity;

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
 * 购物车表
 */
@ApiModel(value = "购物车信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_order_cart")
public class OrderCart {
    /**
     * ID
     */
    @TableId(value = "cart_id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID")
    private Integer cartId;

    /**
     * 买家ID
     */
    @TableField(value = "buyer_id")
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 卖家ID（商品属于那个卖家）
     */
    @TableField(value = "seller_id")
    @ApiModelProperty(value = "卖家ID（商品属于那个卖家）")
    private Integer sellerId;

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
     * 加入购物车商品数量
     */
    @TableField(value = "goods_amount")
    @ApiModelProperty(value = "加入购物车商品数量")
    private Integer goodsAmount;

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

    public static final String COL_CART_ID = "cart_id";

    public static final String COL_BUYER_ID = "buyer_id";

    public static final String COL_SELLER_ID = "seller_id";

    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_SPEC_ID = "spec_id";

    public static final String COL_PRICE_ID = "price_id";

    public static final String COL_GOODS_AMOUNT = "goods_amount";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";
}
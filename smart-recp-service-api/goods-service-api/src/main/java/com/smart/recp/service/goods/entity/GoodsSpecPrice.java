package com.smart.recp.service.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品规格价格表
 */
@ApiModel(value = "com-smart-recp-service-goods-entity-GoodsSpecPrice")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_goods_spec_price")
public class GoodsSpecPrice {
    /**
     * 价格ID
     */
    @TableId(value = "price_id", type = IdType.AUTO)
    @ApiModelProperty(value = "价格ID")
    private Integer priceId;

    /**
     * 规格ID
     */
    @TableField(value = "spec_id")
    @ApiModelProperty(value = "规格ID")
    private Integer specId;

    /**
     * 价格
     */
    @TableField(value = "price")
    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    /**
     * 类型:1->零售,2->批发,3->VIP
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value = "类型:1->零售,2->批发,3->VIP")
    private Integer type;

    /**
     * 区间最小值
     */
    @TableField(value = "`min`")
    @ApiModelProperty(value = "区间最小值")
    private Integer min;

    /**
     * 区间最大值
     */
    @TableField(value = "`max`")
    @ApiModelProperty(value = "区间最大值")
    private Integer max;

    public static final String COL_PRICE_ID = "price_id";

    public static final String COL_SPEC_ID = "spec_id";

    public static final String COL_PRICE = "price";

    public static final String COL_TYPE = "type";

    public static final String COL_MIN = "min";

    public static final String COL_MAX = "max";
}
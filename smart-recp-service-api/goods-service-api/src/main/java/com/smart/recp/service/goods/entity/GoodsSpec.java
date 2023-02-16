package com.smart.recp.service.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 商品规格表
 */
@ApiModel(value = "com-smart-recp-service-goods-entity-GoodsSpec")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_goods_spec")
public class GoodsSpec {
    /**
     * 规格ID
     */
    @TableId(value = "spec_id", type = IdType.AUTO)
    @ApiModelProperty(value = "规格ID")
    private Integer specId;

    /**
     * 商品ID
     */
    @TableField(value = "goods_id")
    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    /**
     * 规格名称
     */
    @TableField(value = "spec_name")
    @ApiModelProperty(value = "规格名称")
    private String specName;

    /**
     * 图片
     */
    @TableField(value = "image")
    @ApiModelProperty(value = "图片")
    private String image;

    /**
     * 库存
     */
    @TableField(value = "stock")
    @ApiModelProperty(value = "库存")
    private Integer stock;

    /**
     * 销量
     */
    @TableField(value = "sale")
    @ApiModelProperty(value = "销量")
    private Integer sale;

    /**
     * 重量
     */
    @TableField(value = "weight")
    @ApiModelProperty(value = "重量")
    private Integer weight;

    /**
     * 状态:0->禁用;1->启用
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态:0->禁用;1->启用")
    private Integer status;

    @TableField(exist = false)
    List<GoodsSpecPrice> goodsSpecPriceList;

    public static final String COL_SPEC_ID = "spec_id";

    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_SPEC_NAME = "spec_name";

    public static final String COL_IMAGE = "image";

    public static final String COL_STOCK = "stock";

    public static final String COL_SALE = "sale";

    public static final String COL_WEIGHT = "weight";

    public static final String COL_STATUS = "status";
}
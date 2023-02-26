package com.smart.recp.service.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author ybq
 */
@ApiModel(value = "购物车信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartDTO {
    /**
     * ID
     */
    @ApiModelProperty(value = "ID")
    private Integer cartId;

    /**
     * 买家ID
     */
    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    /**
     * 卖家ID（商品属于那个卖家）
     */
    @ApiModelProperty(value = "卖家ID（商品属于那个卖家）")
    private Integer sellerId;

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
     * 加入购物车商品数量
     */
    @ApiModelProperty(value = "加入购物车商品数量")
    private Integer goodsAmount;


    /**
     * 修改者
     */
    @ApiModelProperty(value = "修改者")
    private Integer modifiedBy;

}

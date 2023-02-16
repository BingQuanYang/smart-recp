package com.smart.recp.service.goods.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 商品规格价格表
 */
@ApiModel(value = "商品规格价格表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsSpecPriceVO {

    @ApiModelProperty(value = "价格ID")
    private Integer priceId;

    @ApiModelProperty(value = "规格ID")
    private Integer specId;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "类型:1->零售,2->批发,3->VIP")
    private Integer type;

    @ApiModelProperty(value = "区间最小值")
    private Integer min;

    @ApiModelProperty(value = "区间最大值")
    private Integer max;
}
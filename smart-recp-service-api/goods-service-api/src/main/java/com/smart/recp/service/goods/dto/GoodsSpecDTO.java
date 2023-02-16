package com.smart.recp.service.goods.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@ApiModel(value = "商品规格")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsSpecDTO {

    @ApiModelProperty(value = "规格ID")
    private Integer specId;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "规格名称")
    private String specName;

    @ApiModelProperty(value = "图片")
    private String image;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "重量")
    private Integer weight;

    @ApiModelProperty(value = "状态:0->禁用;1->启用")
    private Integer status;

    List<GoodsSpecPriceDTO> goodsSpecPriceDTOList;
}
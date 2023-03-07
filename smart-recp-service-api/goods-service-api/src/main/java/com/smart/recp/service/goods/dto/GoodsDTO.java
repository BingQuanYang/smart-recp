package com.smart.recp.service.goods.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@ApiModel(value = "商品信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsDTO {

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;

    @ApiModelProperty(value = "卖家ID")
    private Integer sellerId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "副标题")
    private String subTitle;


    @ApiModelProperty(value = "商品描述")
    private String description;


    @ApiModelProperty(value = "商品标签")
    private String tags;


    @ApiModelProperty(value = "商品详情")
    private String detail;

    @ApiModelProperty(value = "销售总量")
    private Integer saleTotal;

    @ApiModelProperty(value = "产地")
    private String generatedAddress;

    @ApiModelProperty(value = "计算单位(斤、公斤、个)")
    private String goodsUnit;

    @ApiModelProperty(value = "状态: 0->未上架;1->上架中;2->审核中;3->审核未通过;4->冻结")
    private Integer status;


    @ApiModelProperty(value = "新品状态: 0->不是新品;1->新品")
    private Integer newStatus;

    @ApiModelProperty(value = "推荐状态: 0->不推荐;1->推荐")
    private Integer recommendStatus;


    @ApiModelProperty(value = "创建者")
    private Integer creator;

    @ApiModelProperty(value = "更新者")
    private Integer modifiedBy;

    List<GoodsResourceDTO> goodsResourceDTOList;

    List<GoodsSpecDTO> goodsSpecDTOList;

    @ApiModelProperty(value = "搜索")
    private String search;

}
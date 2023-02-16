package com.smart.recp.service.goods.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 商品分类表
 *
 * @author ybq
 */
@ApiModel(value = "商品分类表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsCategoryDTO {
    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    private Integer categoryId;

    /**
     * 上级分类的编号
     */
    @ApiModelProperty(value = "上级分类的编号")
    private Integer parentId;

    /**
     * 分类名称
     */
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    /**
     * 分类级别：1->1级；2->2级
     */
    @ApiModelProperty(value = "分类级别：1->1级；2->2级")
    private Integer level;

    /**
     * 图标
     */
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 图片
     */
    @ApiModelProperty(value = "图片")
    private String image;

    /**
     * 显示状态：0->不显示；1->显示
     */
    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    private Integer status;

    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 描述
     */
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Integer creator;

    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private Integer modifiedBy;


}

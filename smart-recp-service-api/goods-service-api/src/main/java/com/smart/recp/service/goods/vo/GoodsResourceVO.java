package com.smart.recp.service.goods.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品资源表
 */
@ApiModel(value = "商品资源表")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GoodsResourceVO {

    @ApiModelProperty(value = "资源ID")
    private Integer resourceId;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "类型: 1->图片;2->视频")
    private Integer type;

    @ApiModelProperty(value = "资源地址")
    private String link;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "是否主图: 0->否;1->是")
    private Integer isMaster;
}
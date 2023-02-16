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

/**
    * 商品资源表
    */
@ApiModel(value="com-smart-recp-service-goods-entity-GoodsResource")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_goods_resource")
public class GoodsResource {
    /**
     * 资源ID
     */
    @TableId(value = "resource_id", type = IdType.AUTO)
    @ApiModelProperty(value="资源ID")
    private Integer resourceId;

    /**
     * 商品ID
     */
    @TableField(value = "goods_id")
    @ApiModelProperty(value="商品ID")
    private Integer goodsId;

    /**
     * 类型: 1->图片;2->视频
     */
    @TableField(value = "`type`")
    @ApiModelProperty(value="类型: 1->图片;2->视频")
    private Integer type;

    /**
     * 资源地址
     */
    @TableField(value = "link")
    @ApiModelProperty(value="资源地址")
    private String link;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value="排序")
    private Integer sort;

    /**
     * 是否主图: 0->否;1->是
     */
    @TableField(value = "is_master")
    @ApiModelProperty(value="是否主图: 0->否;1->是")
    private Integer isMaster;

    public static final String COL_RESOURCE_ID = "resource_id";

    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_TYPE = "type";

    public static final String COL_LINK = "link";

    public static final String COL_SORT = "sort";

    public static final String COL_IS_MASTER = "is_master";
}
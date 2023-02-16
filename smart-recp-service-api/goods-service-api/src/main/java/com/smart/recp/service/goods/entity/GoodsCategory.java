package com.smart.recp.service.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品分类表
 */
@ApiModel(value = "com-smart-recp-service-goods-entity-GoodsCategory")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_goods_category")
public class GoodsCategory {
    /**
     * 主键
     */
    @TableId(value = "category_id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Integer categoryId;

    /**
     * 上级分类的编号
     */
    @TableField(value = "parent_id")
    @ApiModelProperty(value = "上级分类的编号")
    private Integer parentId;

    /**
     * 分类名称
     */
    @TableField(value = "category_name")
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    /**
     * 分类级别：1->1级；2->2级
     */
    @TableField(value = "`level`")
    @ApiModelProperty(value = "分类级别：1->1级；2->2级")
    private Integer level;

    /**
     * 图标
     */
    @TableField(value = "icon")
    @ApiModelProperty(value = "图标")
    private String icon;

    /**
     * 图片
     */
    @TableField(value = "image")
    @ApiModelProperty(value = "图片")
    private String image;

    /**
     * 显示状态：0->不显示；1->显示
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "显示状态：0->不显示；1->显示")
    private Integer status;

    /**
     * 排序
     */
    @TableField(value = "sort")
    @ApiModelProperty(value = "排序")
    private Integer sort;

    /**
     * 描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value = "描述")
    private String description;

    /**
     * 创建者
     */
    @TableField(value = "creator")
    @ApiModelProperty(value = "创建者")
    private Integer creator;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    /**
     * 创建者
     */
    @TableField(value = "modified_by")
    @ApiModelProperty(value = "创建者")
    private Integer modifiedBy;

    /**
     * 创建时间
     */
    @TableField(value = "modified_time")
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime modifiedTime;

    /**
     * 子分类
     */
    @TableField(exist = false)
    private List<GoodsCategory> children;

    public static final String COL_CATEGORY_ID = "category_id";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_CATEGORY_NAME = "category_name";

    public static final String COL_LEVEL = "level";

    public static final String COL_ICON = "icon";

    public static final String COL_IMAGE = "image";

    public static final String COL_STATUS = "status";

    public static final String COL_SORT = "sort";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_CREATOR = "creator";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";
}
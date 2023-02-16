package com.smart.recp.service.goods.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品表
 */
@ApiModel(value = "com-smart-recp-service-goods-entity-Goods")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "recp_goods")
public class Goods {
    /**
     * 商品ID
     */
    @TableId(value = "goods_id", type = IdType.AUTO)
    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    /**
     * 分类ID
     */
    @TableField(value = "category_id")
    @ApiModelProperty(value = "分类ID")
    private Integer categoryId;

    /**
     * 分类名称
     */
    @TableField(value = "category_name")
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    /**
     * 卖家ID
     */
    @TableField(value = "seller_id")
    @ApiModelProperty(value = "卖家ID")
    private Integer sellerId;

    /**
     * 商品名称
     */
    @TableField(value = "goods_name")
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 副标题
     */
    @TableField(value = "sub_title")
    @ApiModelProperty(value = "副标题")
    private String subTitle;

    /**
     * 商品描述
     */
    @TableField(value = "description")
    @ApiModelProperty(value = "商品描述")
    private String description;

    /**
     * 商品标签
     */
    @TableField(value = "tags")
    @ApiModelProperty(value = "商品标签")
    private String tags;

    /**
     * 商品详情
     */
    @TableField(value = "detail")
    @ApiModelProperty(value = "商品详情")
    private String detail;

    /**
     * 销售总量
     */
    @TableField(value = "sale_total")
    @ApiModelProperty(value = "销售总量")
    private Integer saleTotal;

    /**
     * 产地
     */
    @TableField(value = "generated_address")
    @ApiModelProperty(value = "产地")
    private String generatedAddress;

    /**
     * 计算单位(斤、公斤、个)
     */
    @TableField(value = "goods_unit")
    @ApiModelProperty(value = "计算单位(斤、公斤、个)")
    private String goodsUnit;

    /**
     * 状态: 0->未上架;1->上架中;2->审核中;3->审核未通过;4->冻结
     */
    @TableField(value = "`status`")
    @ApiModelProperty(value = "状态: 0->未上架;1->上架中;2->审核中;3->审核未通过;4->冻结")
    private Integer status;

    /**
     * 新品状态: 0->不是新品;1->新品
     */
    @TableField(value = "new_status")
    @ApiModelProperty(value = "新品状态: 0->不是新品;1->新品")
    private Integer newStatus;

    /**
     * 推荐状态: 0->不推荐;1->推荐
     */
    @TableField(value = "recommend_status")
    @ApiModelProperty(value = "推荐状态: 0->不推荐;1->推荐")
    private Integer recommendStatus;

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
    @TableField(value = "modified_time",fill = FieldFill.UPDATE)
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime modifiedTime;

    /**
     * 软删除: 0->未删除,1->已删除
     */
    @TableField(value = "is_delete")
    @ApiModelProperty(value = "软删除: 0->未删除,1->已删除")
    private Integer isDelete;


    @TableField(exist = false)
    List<GoodsResource> goodsResourceList;

    @TableField(exist = false)
    List<GoodsSpec> goodsSpecList;

    public static final String COL_GOODS_ID = "goods_id";

    public static final String COL_CATEGORY_ID = "category_id";

    public static final String COL_CATEGORY_NAME = "category_name";

    public static final String COL_SELLER_ID = "seller_id";

    public static final String COL_GOODS_NAME = "goods_name";

    public static final String COL_SUB_TITLE = "sub_title";

    public static final String COL_DESCRIPTION = "description";

    public static final String COL_TAGS = "tags";

    public static final String COL_DETAIL = "detail";

    public static final String COL_SALE_TOTAL = "sale_total";

    public static final String COL_GENERATED_ADDRESS = "generated_address";

    public static final String COL_GOODS_UNIT = "goods_unit";

    public static final String COL_STATUS = "status";

    public static final String COL_NEW_STATUS = "new_status";

    public static final String COL_RECOMMEND_STATUS = "recommend_status";

    public static final String COL_CREATOR = "creator";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFIED_BY = "modified_by";

    public static final String COL_MODIFIED_TIME = "modified_time";

    public static final String COL_IS_DELETE = "is_delete";
}
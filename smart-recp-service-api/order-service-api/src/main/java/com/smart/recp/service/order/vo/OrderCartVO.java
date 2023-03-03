package com.smart.recp.service.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author ybq
 */
@ApiModel(value = "购物车信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCartVO {

    @ApiModelProperty(value = "ID")
    private Integer cartId;

    @ApiModelProperty(value = "买家ID")
    private Integer buyerId;

    @ApiModelProperty(value = "卖家ID（商品属于那个卖家）")
    private Integer sellerId;

    @ApiModelProperty(value = "商品ID")
    private Integer goodsId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    private String goodsImage;

    @ApiModelProperty(value = "商品规格ID")
    private Integer specId;

    @ApiModelProperty(value = "规格名称")
    private String specName;

    @ApiModelProperty(value = "商品规格价格ID")
    private Integer priceId;

    @ApiModelProperty(value = "价格")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal price;

    @ApiModelProperty(value = "加入购物车商品数量")
    private Integer goodsAmount;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改者")
    private Integer modifiedBy;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime modifiedTime;
}

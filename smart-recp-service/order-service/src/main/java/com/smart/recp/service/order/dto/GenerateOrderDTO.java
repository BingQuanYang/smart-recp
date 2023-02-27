package com.smart.recp.service.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author ybq
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateOrderDTO {
    @ApiModelProperty(value = "购物车列表")
    private List<OrderCartDTO> orderCartDTOList;
    @ApiModelProperty(value = "收货地址ID")
    private Integer receiveId;
}

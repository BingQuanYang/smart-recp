package com.smart.recp.service.goods.service;

import com.smart.recp.common.core.base.BaseException;
import com.smart.recp.service.goods.dto.GoodsSpecDTO;

public interface StockService {

    boolean subtract(Integer specId, Integer stock) throws BaseException;

    boolean update(GoodsSpecDTO goodsSpecDTO) throws BaseException;
}

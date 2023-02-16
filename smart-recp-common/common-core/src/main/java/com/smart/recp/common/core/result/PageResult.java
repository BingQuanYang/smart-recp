package com.smart.recp.common.core.result;

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
public class PageResult<T> {
    /**
     * 当前页
     */
    private Long page;

    /**
     * 每页记录数
     */
    private Long pageSize;

    /**
     * 总记录数
     */
    private Long totalCount;

    /**
     * 总页数
     */
    private Long pages;

    /**
     * 数据
     */
    private List<T> list;
}

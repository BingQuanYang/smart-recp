package com.smart.recp.common.core.util;

/**
 * @author ybq
 */
public interface BeanCopyUtilsCallBack<S, T> {
    /**
     * 回调接口
     * @param t
     * @param s
     */
    void callBack(S t, T s);
}

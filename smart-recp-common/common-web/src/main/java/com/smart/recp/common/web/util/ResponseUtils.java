package com.smart.recp.common.web.util;

import com.alibaba.fastjson.JSON;
import com.smart.recp.common.core.result.RestResult;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author ybq
 */
public class ResponseUtils {

    public static void responseToJson(HttpServletResponse response, RestResult<?> result) throws IOException {
        response.setContentType("application/json; charset=utf-8");
        response.getWriter().println(JSON.toJSONString(result));
    }
}

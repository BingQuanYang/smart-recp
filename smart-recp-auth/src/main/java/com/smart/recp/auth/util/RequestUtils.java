package com.smart.recp.auth.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.smart.recp.common.core.constant.AuthConstant;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Base64;

/**
 * @author ybq
 */
public class RequestUtils {
    /**
     * 获取登录认证的客户端ID
     * <p>
     * 兼容两种方式获取Oauth2客户端信息（client_id、client_secret）
     * 方式一：client_id、client_secret放在请求路径中
     * 方式二：放在请求头（Request Headers）中的Authorization字段，且经过加密，例如 Basic Y2xpZW50OnNlY3JldA== 明文等于 client:secret
     *
     * @return
     */
    @SneakyThrows
    public static String getAuthClientId() {
        String clientId;

        HttpServletRequest request = getRequest();

        // 从请求路径中获取
        clientId = request.getParameter(AuthConstant.CLIENT_ID_KEY);
        if (StrUtil.isNotBlank(clientId)) {
            return clientId;
        }

        // 从请求头获取
        String basic = request.getHeader(AuthConstant.AUTHORIZATION_KEY);
        basic = request.getHeader(AuthConstant.AUTHORIZATION_KEY);
        if (StrUtil.isNotBlank(basic) && basic.startsWith(AuthConstant.BASIC_PREFIX)) {
            basic = basic.replace(AuthConstant.BASIC_PREFIX, Strings.EMPTY);
            String basicPlainText = new String(Base64.getDecoder().decode(basic), "UTF-8");
            clientId = basicPlainText.split(":")[0]; //client:secret
        }
        return clientId;
    }

    public static HttpServletRequest getRequest() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }

    public static JSONObject getJwtPayload() {
        String jwtPayload = getRequest().getHeader(AuthConstant.JWT_PAYLOAD_KEY);
        JSONObject jsonObject = JSONUtil.parseObj(jwtPayload);
        return jsonObject;
    }
}

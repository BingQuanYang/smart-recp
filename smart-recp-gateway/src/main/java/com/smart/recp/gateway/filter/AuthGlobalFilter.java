package com.smart.recp.gateway.filter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nimbusds.jose.JWSObject;
import com.smart.recp.common.core.constant.AuthConstant;
import com.smart.recp.common.core.enums.ResultCode;
import com.smart.recp.common.core.result.RestResult;
import com.smart.recp.common.redis.service.RedisService;
import com.smart.recp.gateway.util.WebUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * 全局过滤器
 * @author ybq
 */

@Component
@Slf4j
public class AuthGlobalFilter implements GlobalFilter, Ordered {

    @Resource
    RedisService redisService;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("进入到全局过滤器了........");
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 非JWT或者JWT为空不作处理
        String token = request.getHeaders().getFirst(AuthConstant.AUTHORIZATION_KEY);
        if (StringUtils.isBlank(token) || !token.startsWith(AuthConstant.AUTHORIZATION_PREFIX)) {
            return chain.filter(exchange);
        }

        // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在拦截响应token失效
        token = token.replace(AuthConstant.AUTHORIZATION_PREFIX, Strings.EMPTY);
        JWSObject jwsObject = JWSObject.parse(token);
        String payload = jwsObject.getPayload().toString();
        JSONObject jsonObject = JSONUtil.parseObj(payload);
        String jti = jsonObject.getStr(AuthConstant.JWT_JTI);
        Boolean isBlack = redisService.hasKey(AuthConstant.TOKEN_BLACKLIST_PREFIX + jti);
        if (isBlack) {
            return WebUtils.writeFailedToResponse(response, RestResult.error(ResultCode.TOKEN_ACCESS_FORBIDDEN));
        }
        // 存在token且不是黑名单，request写入JWT的载体信息
        request = exchange.getRequest().mutate().header(AuthConstant.JWT_PAYLOAD_KEY, payload).build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return -100;
    }
}

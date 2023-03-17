package com.smart.recp.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.util.ObjectUtils;
import org.springframework.web.cors.reactive.CorsUtils;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author ybq
 */
@Configuration
public class CorsConfig {
    private static final String ALLOWED_HEADERS = "Accept,Authorization,Cache-Control,Content-Type,Pragma,DNT,If-Modified-Since,Keep-Alive,Origin,User-Agent,X-Mx-ReqToken,X-Requested-With,authorization,Authorization,Sign,AppId,AppKey,Timestamp";
    private static final String ALLOWED_METHODS = "POST,DELETE,PUT,GET,PATCH";
    private static final String ALLOWED_ORIGIN = "*";
    private static final String EXPOSE_HEADERS = "*";
    private static final String ALLOWED_CREDENTIALS = "true";
    private static final String MAX_AGE = "18000L";

    @Bean
    public WebFilter corsFilter() {
        return (ServerWebExchange ctx, WebFilterChain chain) -> {
            ServerHttpRequest request = ctx.getRequest();
            ServerHttpResponse response = ctx.getResponse();
            HttpHeaders headers = response.getHeaders();
            if (CorsUtils.isCorsRequest(request)) {
                if (ObjectUtils.isEmpty(headers.get(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN))) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, ALLOWED_ORIGIN);
//                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, request.getHeaders().getOrigin());
                }
                if (ObjectUtils.isEmpty(headers.get(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS))) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS, ALLOWED_METHODS);
                }
                if (ObjectUtils.isEmpty(headers.get(HttpHeaders.ACCESS_CONTROL_MAX_AGE))) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_MAX_AGE, MAX_AGE);
                }
                if (ObjectUtils.isEmpty(headers.get(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS))) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS, ALLOWED_HEADERS);
                }
                if (ObjectUtils.isEmpty(headers.get(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS))) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, EXPOSE_HEADERS);
                }
                if (ObjectUtils.isEmpty(headers.get(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS))) {
                    headers.add(HttpHeaders.ACCESS_CONTROL_ALLOW_CREDENTIALS, ALLOWED_CREDENTIALS);
                }
//                if (request.getMethod() == HttpMethod.OPTIONS) {
//                    response.setStatusCode(HttpStatus.OK);
//                    return Mono.empty();
//                }
            }
            return chain.filter(ctx);
        };
    }
}

package com.smart.recp.service.goods.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author ybq
 */
@Configuration
@ComponentScan("com.smart.recp.service.goods.feign.service")
@EnableFeignClients(basePackages = "com.smart.recp.service.goods")
public class FeignConfig {
}

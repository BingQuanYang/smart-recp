package com.smart.recp.service.order.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.smart.recp.service.order.feign.service")
@EnableFeignClients(basePackages = "com.smart.recp.service.order")
public class FeignConfig {
}
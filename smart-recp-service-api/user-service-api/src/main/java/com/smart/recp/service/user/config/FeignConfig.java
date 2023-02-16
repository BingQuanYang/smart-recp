package com.smart.recp.service.user.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.smart.recp.service.user.feign.service")
@EnableFeignClients
public class FeignConfig {
}
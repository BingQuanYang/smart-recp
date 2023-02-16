package com.smart.recp.service.user;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author ybq
 */
@SpringBootApplication
@MapperScan("com.smart.recp.service.user.mapper")
@Slf4j
public class UserApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
        log.info("启动：用户服务");
    }
}

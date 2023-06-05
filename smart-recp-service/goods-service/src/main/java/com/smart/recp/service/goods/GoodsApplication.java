package com.smart.recp.service.goods;

import io.seata.spring.annotation.datasource.EnableAutoDataSourceProxy;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author ybq
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
//@EnableAutoDataSourceProxy(useJdkProxy = true)
@Slf4j
@MapperScan("com.smart.recp.service.goods.mapper")
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
        log.info("启动：商品服务");
    }
}

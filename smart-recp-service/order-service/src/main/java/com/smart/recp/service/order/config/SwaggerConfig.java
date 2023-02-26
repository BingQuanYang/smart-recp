package com.smart.recp.service.order.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;


@Configuration
@EnableSwagger2
public class SwaggerConfig {


    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo()).select()
                .apis(RequestHandlerSelectors.any())
                .build().globalOperationParameters(setHeatherToken());
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("杨炳全毕设")
                .description("农村商品平台")
                .termsOfServiceUrl("")
                .version("1.0")
                .build();
    }

    /**
     * 设置swagger文档全局参数
     *
     * @return
     */
    private List<Parameter> setHeatherToken() {
        List<Parameter> pars = new ArrayList<>();
        ParameterBuilder userId = new ParameterBuilder();
        userId.name("token")
                .description("用户TOKEN")
                .modelRef(new ModelRef("String"))
                .required(false)
                .build();
        pars.add(userId.build());
        return pars;
    }

}

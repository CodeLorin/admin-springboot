package com.lorin.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


import java.util.ArrayList;

/**
 * TODO
 *
 * @author lorin
 * @date 2021/12/16 23:58
 */


@EnableKnife4j
@Configuration
public class SwaggerConfig {
    @Bean
    public Docket docket(Environment environment) {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())

                .groupName("1.0版本")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.lorin.controller"))
                .paths(PathSelectors.any())
                .build()
                ;
    }


    private ApiInfo apiInfo() {
        //相关信息
        return new ApiInfo("lorin-admin后台管理系统接口文档",
                "如有疑问，请联系开发工程师lorin",
                "1.0",
                "https://www.codelorin.cn",
                new Contact("CodeLorin", "https://www.codelorin.cn", "bylorin@163.com"),
                "Apache 2.0",
                "http://www.apache.org/licenses/LICENSE-2.0",
                new ArrayList());
    }


}


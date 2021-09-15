package com.blog.admin.config;

import com.blog.common.config.BaseSwaggerConfig;
import com.blog.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger API文档相关配置
 * Created by macro on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
//        System.out.println("-------------SwaggerConfig --> swaggerProperties--------------");
        return SwaggerProperties.builder()
                .apiBasePackage("com.blog.admin.controller")
                .title("blog后台系统")
                .description("blog后台相关接口文档")
                .contactName("MOLLY·HOMES")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}

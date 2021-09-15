package com.blog.portal.config;

import com.blog.common.config.BaseSwaggerConfig;
import com.blog.common.domain.SwaggerProperties;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2API文档的配置
 * Created by macro on 2018/4/26.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {

    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.blog.portal.controller")
                .title("blog前台系统")
                .description("blog前台相关接口文档")
                .contactName("molly")
                .version("1.0")
                .enableSecurity(true)
                .build();
    }
}

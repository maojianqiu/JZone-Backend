package com.blog.portal.config;


import com.blog.common.util.TencentCOSUtil;
import com.blog.security.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TecentCOSConfig {

    @Bean
    public TencentCOSUtil tencentCOSUtil() {
        return new TencentCOSUtil();
    }
}

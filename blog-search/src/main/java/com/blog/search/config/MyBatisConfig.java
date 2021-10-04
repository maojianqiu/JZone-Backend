package com.blog.search.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis相关配置
 * Created by macro on 2019/4/8.
 */
@Configuration
@MapperScan({"com.learn.demo.mapper", "com.learn.demo.dao"})
public class MyBatisConfig {
}

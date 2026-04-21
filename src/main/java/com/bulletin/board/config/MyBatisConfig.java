package com.bulletin.board.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.bulletin.board.mapper")
public class MyBatisConfig {
}
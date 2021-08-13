package com.alkaid.yygh.user.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-13 14:20
 * @ClassName UserConfig
 * @Description:
 */
@Configuration
@MapperScan("com.alkaid.yygh.user.mapper")
public class UserConfig {
}

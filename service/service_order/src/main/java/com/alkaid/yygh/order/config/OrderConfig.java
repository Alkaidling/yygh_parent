package com.alkaid.yygh.order.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-16 17:13
 * @ClassName OrderConfig
 * @Description:
 */
@Configuration
@MapperScan("com.alkaid.yygh.order.mapper")
public class OrderConfig {
}

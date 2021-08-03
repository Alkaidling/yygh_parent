package com.alkaid.yygh.hosp.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-03 16:42
 * @ClassName HospConfig
 * @Description:
 */
@Configuration
@MapperScan("com.alkaid.yygh.hosp.mapper")
public class HospConfig {
}

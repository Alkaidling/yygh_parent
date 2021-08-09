package com.alkaid.yygh.cmn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-06 15:28
 * @ClassName ServiceCmnApplication
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.alkaid")
@EnableDiscoveryClient
public class ServiceCmnApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceCmnApplication.class,args);
    }
}

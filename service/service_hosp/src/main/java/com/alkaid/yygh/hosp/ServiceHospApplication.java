package com.alkaid.yygh.hosp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created with IntelliJ IDEA.2020.2.3
 *
 * @Auther: Alkaid
 * @Date: 2021-08-03 16:05
 * @ClassName ServiceHospApplication
 * @Description:
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.alkaid")
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.alkaid")
public class ServiceHospApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class,args);
    }
}

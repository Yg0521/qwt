package com.yg.qwt.wechat;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.yg")
@MapperScan("com.yg.qwt.wechat.mapper")
@ComponentScan(basePackages = "com.yg")
public class WechatServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(WechatServiceApplication.class, args);
    }
}

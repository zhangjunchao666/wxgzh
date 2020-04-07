package com.wxgzh.wxgzh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author zhangjunchao
 */
@MapperScan(basePackages = "com.wxgzh.wxgzh.mapper")
@EnableScheduling
@SpringBootApplication
public class WxgzhApplication {

    public static void main(String[] args) {
        SpringApplication.run(WxgzhApplication.class, args);
    }

}

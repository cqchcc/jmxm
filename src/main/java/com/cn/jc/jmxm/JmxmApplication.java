package com.cn.jc.jmxm;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan(value = "com.cn.jc.jmxm.mapper")
@EnableScheduling
public class JmxmApplication {

    public static void main(String[] args) {
        SpringApplication.run(JmxmApplication.class, args);
    }

}

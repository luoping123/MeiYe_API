package com.sct.meiye;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

@MapperScan("com.sct.meiye.mapper")
public class MeiYeApplication {

    public static void main(String[] args) {
        SpringApplication.run(MeiYeApplication.class, args);
    }

}

package com.smile;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@MapperScan("com.smile.lua.mapper")
@SpringBootApplication
public class RedpacketLuaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedpacketLuaApplication.class, args);
    }

}

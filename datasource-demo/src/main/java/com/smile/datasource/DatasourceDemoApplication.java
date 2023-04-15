package com.smile.datasource;

import com.smile.datasource.anno.EnableDynamicDataSource;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.smile.datasource.mapper")
@EnableDynamicDataSource
@SpringBootApplication
public class DatasourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceDemoApplication.class, args);
    }

}

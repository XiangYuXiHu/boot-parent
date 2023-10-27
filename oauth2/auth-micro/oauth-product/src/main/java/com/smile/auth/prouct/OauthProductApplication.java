package com.smile.auth.prouct;

import com.smile.auth.micro.common.anno.EnableOauthResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableOauthResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class OauthProductApplication {

    public static void main(String[] args) {
        SpringApplication.run(OauthProductApplication.class, args);
    }

}

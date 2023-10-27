package com.smile.auth.order;

import com.smile.auth.micro.common.anno.EnableOauthFeignClients;
import com.smile.auth.micro.common.anno.EnableOauthResourceServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableOauthFeignClients
@EnableOauthResourceServer
@EnableDiscoveryClient
@SpringBootApplication
public class AuthOrderApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthOrderApplication.class, args);
    }

}

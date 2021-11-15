package org.shanzhaozhen.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SecurityApplication {

    public static void main(String[] args) {
        // 关闭druid ping 警告
        System.setProperty("druid.mysql.usePingMethod", "false");
        SpringApplication.run(SecurityApplication.class, args);
    }

}

package org.shanzhaozhen.authorize;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients("org.shanzhaozhen.uaa.feign")
public class AuthorizeApplication {

    public static void main(String[] args) {
        // 关闭druid ping 警告
        System.setProperty("druid.mysql.usePingMethod", "false");
        SpringApplication.run(AuthorizeApplication.class, args);
    }

}

package org.shanzhaozhen.uaa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.shanzhaozhen.uaa.mapper")
@ComponentScan("org.shanzhaozhen.*")
public class UaaApplication {

    public static void main(String[] args) {
        // 关闭druid ping 警告
        System.setProperty("druid.mysql.usePingMethod", "false");
        SpringApplication.run(UaaApplication.class, args);
    }

}

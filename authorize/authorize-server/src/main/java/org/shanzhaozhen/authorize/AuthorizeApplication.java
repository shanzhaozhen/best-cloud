package org.shanzhaozhen.authorize;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("org.shanzhaozhen.*")
@MapperScan("org.shanzhaozhen.uaa.mapper")
@EnableFeignClients("org.shanzhaozhen.uaa.feign")
@OpenAPIDefinition(info =
    @Info(title = "${springdoc.title}", version = "${springdoc.version}", description = "${springdoc.description}")
)
public class AuthorizeApplication {

    public static void main(String[] args) {
        // 关闭druid ping 警告
        System.setProperty("druid.mysql.usePingMethod", "false");
        SpringApplication.run(AuthorizeApplication.class, args);
    }

}

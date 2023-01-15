package org.shanzhaozhen.oauth;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("org.shanzhaozhen.oauth.mapper")
@ComponentScan("org.shanzhaozhen.*")
@OpenAPIDefinition(info =
@Info(title = "${springdoc.title}", version = "${springdoc.version}", description = "${springdoc.description}")
)
public class OAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApplication.class, args);
    }


}

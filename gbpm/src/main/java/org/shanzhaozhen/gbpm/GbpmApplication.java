package org.shanzhaozhen.gbpm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
//@OpenAPIDefinition(info =
//    @Info(title = "${springdoc.title}", version = "${springdoc.version}", description = "${springdoc.description}")
//)
public class GbpmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GbpmApplication.class, args);
    }

}

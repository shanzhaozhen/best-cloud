package org.shanzhaozhen.basicapi;

import org.springframework.boot.SpringApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringCloudApplication
@EnableCaching
@ComponentScan("org.shanzhaozhen")
public class BasicApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicApiApplication.class, args);
    }

}

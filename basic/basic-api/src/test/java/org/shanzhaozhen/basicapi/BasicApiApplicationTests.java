package org.shanzhaozhen.basicapi;

import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;

@SpringBootTest
public class BasicApiApplicationTests {

    @Autowired
    private ApplicationContext context;

    @Test
    void contextLoads() {
        HTTPBasicAuthFilter bean = context.getBean(HTTPBasicAuthFilter.class);
        System.out.println();
    }

}

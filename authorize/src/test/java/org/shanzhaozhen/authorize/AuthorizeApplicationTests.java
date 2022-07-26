package org.shanzhaozhen.authorize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedHashSet;

@SpringBootTest
public class AuthorizeApplicationTests {

    @Test
    void contextLoads() {

    }

    @Test
    public void testPath() throws IOException {
        LinkedHashSet<String> set = new LinkedHashSet<>();
        set.add("authorization_code");
        set.add(" account");
        set.add(" client_credentials");
    }

    @Test
    public void testPassword() throws IOException {
//        PasswordEncoder passwordEncoder = new DelegatingPasswordEncoder();
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        boolean matches = passwordEncoder.matches("secret", "$2a$10$eX5hrXVr5/QWXAEt1BzRneb.RJIPOYNVj6NjTBLPfRJbQhx4jjPU2");
        System.out.println(matches);

        String encode = passwordEncoder.encode("123456");
        System.out.println(encode);

    }

}

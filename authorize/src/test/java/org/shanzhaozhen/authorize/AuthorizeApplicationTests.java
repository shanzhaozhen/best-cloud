package org.shanzhaozhen.authorize;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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
        set.add(" password");
        set.add(" client_credentials");
    }

}

package org.shanzhaozhen.authorize.config.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.authorize.service.ResourceService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitResourceRolesCacheRunner implements CommandLineRunner {

    private final ResourceService resourceService;

    @Override
    public void run(String... args) throws Exception {
        resourceService.initResourceInfo();
    }

}

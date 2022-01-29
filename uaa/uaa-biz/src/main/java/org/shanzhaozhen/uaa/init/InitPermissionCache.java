package org.shanzhaozhen.uaa.init;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.service.PermissionService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-01-29
 * @Description: 加载权限缓存
 */
@Component
@RequiredArgsConstructor
public class InitPermissionCache implements CommandLineRunner {

    private final PermissionService permissionService;


    @Override
    public void run(String... args) {
        permissionService.refreshPermissionCache();
    }
}

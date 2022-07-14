package org.shanzhaozhen.uaa;

import org.junit.jupiter.api.Test;
import org.shanzhaozhen.uaa.pojo.dto.PermissionDTO;
import org.shanzhaozhen.uaa.pojo.dto.RoleDTO;
import org.shanzhaozhen.uaa.service.PermissionService;
import org.shanzhaozhen.uaa.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;

import static org.shanzhaozhen.common.core.enums.PermissionType.API;

@SpringBootTest
public class UaaApplicationTests {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Test
    void contextLoads() {

    }

    @Test
    public void loadPermission() {
        PermissionDTO permissionDTO = new PermissionDTO();
        permissionDTO.setName("测试").setPath("/test/**").setType(API.getValue());
        Long permissionId = permissionService.addPermission(permissionDTO);

        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setCode("TEST").setName("测试人员");
        String roleId = roleService.addRole(roleDTO);
        roleService.batchAddRolePermission(roleId, Collections.singletonList(permissionId));
    }

    @Test
    public void testCache() {
        permissionService.refreshPermissionCache();
    }

}

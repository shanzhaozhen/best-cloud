package org.shanzhaozhen.uaa.controller;

import org.shanzhaozhen.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.pojo.form.UserRoleForm;
import org.shanzhaozhen.uaa.service.UserRoleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "user-role", description = "用户角色接口")
@RestController
@RequiredArgsConstructor
public class UserRoleController {

    private static final String ADD_USER_ROLE = "/user-role";
    private static final String DELETE_USER_ROLE = "/user-role";

    private final UserRoleService userRoleService;

    @Operation(summary = "添加用户角色")
    @PostMapping(ADD_USER_ROLE)
    public R<List<Long>> addUserRoles(@RequestBody UserRoleForm userRoleForm) {
        return R.build(() -> userRoleService.batchAddUserRole(userRoleForm.getUserIds(), userRoleForm.getRoleId()));
    }

    @Operation(summary = "删除用户角色")
    @DeleteMapping(DELETE_USER_ROLE)
    public R<Integer> deleteUserRoles(@RequestBody UserRoleForm userRoleForm) {
        return R.build(() -> userRoleService.batchDeleteUserRole(userRoleForm.getUserIds(), userRoleForm.getRoleId()));
    }


}

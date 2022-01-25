package org.shanzhaozhen.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import org.shanzhaozhen.common.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.security.converter.UserConverter;
import org.shanzhaozhen.security.dto.UserDTO;
import org.shanzhaozhen.security.form.UserRoleForm;
import org.shanzhaozhen.security.service.UserRoleService;
import org.shanzhaozhen.security.vo.UserVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "user-role", description = "用户角色接口")
@RestController
@RequiredArgsConstructor
public class UserRoleController {

    private static final String GET_USER_ROLE_PAGE = "/user-role";
    private static final String ADD_USER_ROLE = "/user-role";
    private static final String DELETE_USER_ROLE = "/user-role";

    private final UserRoleService userRoleService;

    @Operation(summary = "获取用户角色信息（分页）")
    @GetMapping(GET_USER_ROLE_PAGE)
    public R<Page<UserVO>> getUserPageByRoleId(Page<UserDTO> page, Long roleId, String keyword) {
        return R.build(() -> UserConverter.toVO(userRoleService.getUserRolePage(page, roleId, keyword)));
    }

    @Operation(summary = "添加用户角色")
    @PostMapping(ADD_USER_ROLE)
    public R<List<Long>> addUserRole(@RequestBody UserRoleForm userRoleForm) {
        return R.build(() -> userRoleService.bathAddUserRole(userRoleForm.getUserIds(), userRoleForm.getRoleId()));
    }

    @Operation(summary = "删除用户角色")
    @DeleteMapping(DELETE_USER_ROLE)
    public R<Integer> deleteUserRoles(@RequestBody UserRoleForm userRoleForm) {
        return R.build(() -> userRoleService.batchDeleteUserRole(userRoleForm.getUserIds(), userRoleForm.getRoleId()));
    }


}

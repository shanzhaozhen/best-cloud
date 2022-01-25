package org.shanzhaozhen.security.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.common.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.security.config.security.CustomUserDetailsService;
import org.shanzhaozhen.security.converter.UserConverter;
import org.shanzhaozhen.security.dto.UserDTO;
import org.shanzhaozhen.security.form.UserDepartmentForm;
import org.shanzhaozhen.security.form.UserForm;
import org.shanzhaozhen.security.service.UserService;
import org.shanzhaozhen.security.vo.CurrentUser;
import org.shanzhaozhen.security.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "user", description = "用户信息接口")
@RestController
@RequiredArgsConstructor
public class UserController {

    private static final String GET_OAUTH_USER = "/user/oauth/{username}";
    private static final String GET_USER_INFO = "/user/current";
    private static final String LOGOUT = "/user/logout";
    private static final String GET_USER_PAGE = "/user/page";
    private static final String GET_USER_BY_ID = "/user/{userId}";
    private static final String ADD_USER = "/user";
    private static final String UPDATE_USER = "/user";
    private static final String DELETE_USER = "/user/{userId}";
    private static final String BATCH_DELETE_USER = "/user";
    private static final String GET_USER_ROLE_PAGE = "/user/role/page";
    private static final String GET_USER_DEPARTMENT_PAGE = "/user/department/page";
    private static final String BATCH_UPDATE_USER_DEPARTMENT = "/user/department";

    private final UserService userService;
    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping(GET_OAUTH_USER)
    @Operation(summary = "获取当前登录用户的个人和权限信息接口")
    public R<UserDTO> loadUserByUsername(@Parameter(description = "用户名", example = "username") @PathVariable("username") String username) {
        return R.build(() -> userService.getUserByUsername(username));
    }

    @GetMapping(GET_USER_INFO)
    @Operation(summary = "获取当前登录用户的个人和权限信息接口")
    public R<CurrentUser> getCurrentUserInfo() {
        return R.build(userService::getUserInfo);
    }

    @Operation(summary = "登出用户接口")
    @GetMapping(LOGOUT)
    public R<Boolean> logout() {
        return R.build(userService::logout);
    }

    @Operation(summary = "获取用户信息（分页）")
    @GetMapping(GET_USER_PAGE)
    public R<Page<UserVO>> getUserPage(Page<UserDTO> page, String keyword) {
        return R.build(() -> UserConverter.toVO(userService.getUserPage(page, keyword)));
    }

    @Operation(summary = "获取用户信息（通过用户id）")
    @GetMapping(GET_USER_BY_ID)
    public R<UserVO> getUserById(@Parameter(description = "用户id", example = "1") @PathVariable("userId") Long userId) {
        return R.build(() -> UserConverter.toVO(userService.getUserById(userId)));
    }

    @Operation(summary = "添加用户接口")
    @PostMapping(ADD_USER)
    public R<Long> addUser(@RequestBody @Validated({Insert.class}) UserForm userForm) {
        return R.build(() -> userService.addUser(UserConverter.toDTO(userForm)));
    }

    @Operation(summary = "更新用户接口")
    @PutMapping(UPDATE_USER)
    public R<Long> updateUser(@RequestBody @Validated({Update.class}) UserForm userForm) {
        return R.build(() -> userService.updateUser(UserConverter.toDTO(userForm)));
    }

    @Operation(summary = "删除用户接口")
    @DeleteMapping(DELETE_USER)
    public R<Long> deleteUser(@Parameter(description = "用户id", example = "[1, 2]") @PathVariable("userId") Long userId) {
        return R.build(() -> userService.deleteUser(userId));
    }

    @Operation(summary = "批量删除用户接口")
    @DeleteMapping(BATCH_DELETE_USER)
    public R<List<Long>> batchDeleteUser(@Parameter(description = "用户id", example = "[1, 2]") @RequestBody List<Long> userIds) {
        return R.build(() -> userService.batchDeleteUser(userIds));
    }


    @Operation(summary = "通过角色ID获取用户信息（分页）")
    @GetMapping(GET_USER_ROLE_PAGE)
    public R<Page<UserVO>> getUserPageByRoleId(Page<UserDTO> page, Long roleId, String keyword) {
        return R.build(() -> UserConverter.toVO(userService.getUserPageByRoleId(page, roleId ,keyword)));
    }

    @Operation(summary = "通过部门ID获取用户信息（分页）")
    @GetMapping(GET_USER_DEPARTMENT_PAGE)
    public R<Page<UserVO>> getUserPageByDepartmentId(Page<UserDTO> page, Long departmentId, String keyword) {
        return R.build(() -> UserConverter.toVO(userService.getUserPageByDepartmentId(page, departmentId, keyword)));
    }

    @Operation(summary = "批量更新用户部门接口")
    @PutMapping(BATCH_UPDATE_USER_DEPARTMENT)
    public R<List<Long>> batchUpdateUserDepartment(@RequestBody UserDepartmentForm userDepartmentForm) {
        return R.build(() -> userService.batchUpdateUserDepartment(userDepartmentForm));
    }

}

package org.shanzhaozhen.uaa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.common.core.result.R;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.uaa.converter.UserConverter;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.form.ChangePasswordForm;
import org.shanzhaozhen.uaa.pojo.form.UserForm;
import org.shanzhaozhen.uaa.pojo.form.UserPageParams;
import org.shanzhaozhen.uaa.service.UserService;
import org.shanzhaozhen.uaa.pojo.vo.CurrentUser;
import org.shanzhaozhen.uaa.pojo.vo.UserVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "user", description = "用户信息接口")
@RestController
@RequiredArgsConstructor
public class UserController {

    private static final String GET_ACCOUNT_USER = "/user/account/{username}";
    private static final String GET_PHONE_USER = "/user/phone/{phone}";
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

    private static final String UPDATE_PASSWORD = "/user/password";

    private final UserService userService;

    @GetMapping(GET_ACCOUNT_USER)
    @Operation(summary = "获取当前登录用户的个人和权限信息接口")
    public R<UserDTO> loadUserByUsername(@Parameter(description = "用户名", example = "username") @PathVariable("username") String username) {
        return R.build(() -> userService.getUserByUsername(username));
    }

    @GetMapping(GET_PHONE_USER)
    @Operation(summary = "获取当前登录用户的个人和权限信息接口")
    public R<UserDTO> loadUserByPhone(@Parameter(description = "手机号", example = "137xxxxxxxx") @PathVariable("phone") String phone) {
        return R.build(() -> userService.getUserByUsername(phone));
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
    @PostMapping(GET_USER_PAGE)
    public R<Page<UserVO>> getUserPage(@RequestBody UserPageParams<UserDTO> pageParams) {
        return R.build(() -> UserConverter.toVO(userService.getUserPage(pageParams.getPage(), pageParams.getKeyword())));
    }

    @Operation(summary = "获取用户信息（通过用户id）")
    @GetMapping(GET_USER_BY_ID)
    public R<UserVO> getUserById(@Parameter(description = "用户id", example = "1") @PathVariable("userId") String userId) {
        return R.build(() -> UserConverter.toVO(userService.getUserById(userId)));
    }

    @Operation(summary = "添加用户接口")
    @PostMapping(ADD_USER)
    public R<String> addUser(@RequestBody @Validated({Insert.class}) UserForm userForm) {
        return R.build(() -> userService.addUser(UserConverter.toDTO(userForm)));
    }

    @Operation(summary = "更新用户接口")
    @PutMapping(UPDATE_USER)
    public R<String> updateUser(@RequestBody @Validated({Update.class}) UserForm userForm) {
        return R.build(() -> userService.updateUser(UserConverter.toDTO(userForm)));
    }

    @Operation(summary = "删除用户接口")
    @DeleteMapping(DELETE_USER)
    public R<String> deleteUser(@Parameter(description = "用户id", example = "[1, 2]") @PathVariable("userId") String userId) {
        return R.build(() -> userService.deleteUser(userId));
    }

    @Operation(summary = "批量删除用户接口")
    @DeleteMapping(BATCH_DELETE_USER)
    public R<List<String>> batchDeleteUser(@Parameter(description = "用户id", example = "[1, 2]") @RequestBody List<String> userIds) {
        return R.build(() -> userService.batchDeleteUser(userIds));
    }


    @Operation(summary = "通过角色ID获取用户信息（分页）")
    @PostMapping(GET_USER_ROLE_PAGE)
    public R<Page<UserVO>> getUserPageByRoleId(@RequestBody UserPageParams<UserDTO> pageParams) {
        return R.build(() -> UserConverter.toVO(userService.getUserPageByRoleId(pageParams.getPage(), pageParams.getRoleId(), pageParams.getKeyword())));
    }

    @Operation(summary = "通过部门ID获取用户信息（分页）")
    @PostMapping(GET_USER_DEPARTMENT_PAGE)
    public R<Page<UserVO>> getUserPageByDepartmentId(@RequestBody UserPageParams<UserDTO> pageParams) {
        return R.build(() -> UserConverter.toVO(userService.getUserPageByDepartmentId(pageParams.getPage(), pageParams.getDepartmentId(), pageParams.getKeyword())));
    }

    @Operation(summary = "修改密码")
    @PostMapping(UPDATE_PASSWORD)
    public R<?> changePassword(@RequestBody ChangePasswordForm changePasswordForm) {
        userService.changePassword(changePasswordForm);
        return R.ok();
    }

}

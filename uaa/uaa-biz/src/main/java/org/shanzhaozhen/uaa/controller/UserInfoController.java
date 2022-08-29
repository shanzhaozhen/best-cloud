package org.shanzhaozhen.uaa.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.converter.UserConverter;
import org.shanzhaozhen.uaa.converter.UserInfoConverter;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.form.UserForm;
import org.shanzhaozhen.uaa.pojo.form.UserInfoForm;
import org.shanzhaozhen.uaa.pojo.form.UserPageParams;
import org.shanzhaozhen.uaa.pojo.vo.CurrentUser;
import org.shanzhaozhen.uaa.pojo.vo.UserVO;
import org.shanzhaozhen.uaa.service.UserInfoService;
import org.shanzhaozhen.uaa.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Tag(name = "user", description = "用户信息接口")
@RestController
@RequiredArgsConstructor
public class UserInfoController {

    private static final String GET_USER_INFO_BY_ID = "/user-info/{userId}";

    private static final String UPDATE_USER_INFO = "/user-info";

    private final UserInfoService userInfoService;

    @GetMapping(GET_USER_INFO_BY_ID)
    public R<UserInfoDTO> getUserInfoByUserId(@PathVariable("userId") String userId) {
        return R.build(() -> userInfoService.getUserInfoByUserId(userId));
    }

    @Operation(summary = "通过部门ID获取用户信息（分页）")
    @PostMapping(UPDATE_USER_INFO)
    public R<?> updateUserInfo(@RequestBody UserInfoForm userInfoForm) {
        userInfoService.updateUserInfo(UserInfoConverter.toDTO(userInfoForm));
        return R.ok();
    }

}

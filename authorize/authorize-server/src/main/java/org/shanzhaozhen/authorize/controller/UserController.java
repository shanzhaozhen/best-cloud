package org.shanzhaozhen.authorize.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.pojo.dto.SecurityInfo;
import org.shanzhaozhen.authorize.pojo.form.BindPhoneForm;
import org.shanzhaozhen.authorize.pojo.form.ChangePasswordForm;
import org.shanzhaozhen.oauth.converter.OAuth2UserInfoConverter;
import org.shanzhaozhen.authorize.service.OAuth2UserInfoService;
import org.shanzhaozhen.authorize.service.OAuth2UserService;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.oauth.pojo.form.OAuth2UserInfoForm;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2UserInfoVO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-26
 * @Description:
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final OAuth2UserService oAuth2UserService;
    private final OAuth2UserInfoService oAuth2UserInfoService;

    @GetMapping("/user/base")
    @Operation(summary = "获取用户信息")
    public R<OAuth2UserInfoVO> getCurrentUserinfo() {
        return R.build(() -> OAuth2UserInfoConverter.toVO(oAuth2UserInfoService.getCurrentUserInfo()));
    }

    @PostMapping("/user/base")
    @Operation(summary = "更新用户信息")
    public R<OAuth2UserInfoVO> updateUserinfo(@RequestBody OAuth2UserInfoForm oauth2UserInfoForm) {
        oAuth2UserInfoService.updateCurrentUserinfo(OAuth2UserInfoConverter.toDTO(oauth2UserInfoForm));
        return R.ok();
    }

    @PostMapping("/user/password")
    @Operation(summary = "修改密码")
    public R<OAuth2UserInfoVO> changePassword(@RequestBody @Validated ChangePasswordForm changePasswordForm) {
        oAuth2UserService.changePassword(changePasswordForm);
        return R.ok();
    }

    @GetMapping("/user/security")
    @Operation(summary = "获取用户安全信息")
    public R<SecurityInfo> getSecurityInfo() {
        return R.build(oAuth2UserService::getSecurityInfo);
    }

    @PostMapping("/user/bind/phone")
    @Operation(summary = "获取用户安全信息")
    public R<?> bindPhone(@RequestBody @Validated BindPhoneForm bindPhoneForm) {
        oAuth2UserService.bindPhone(bindPhoneForm);
        return R.ok();
    }

    @GetMapping("/user/unbind/phone")
    @Operation(summary = "获取用户安全信息")
    public R<?> unbindPhone() {
        oAuth2UserService.unbindPhone();
        return R.ok();
    }

}

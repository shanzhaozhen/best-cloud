package org.shanzhaozhen.authorize.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.service.AccountService;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.converter.UserInfoConverter;
import org.shanzhaozhen.uaa.pojo.form.UserInfoForm;
import org.shanzhaozhen.uaa.pojo.vo.UserInfoVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-26
 * @Description:
 */
@RestController
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/user/base")
    @Operation(summary = "获取用户信息")
    public R<UserInfoVO> getCurrentUserinfo() {
        return R.build(() -> UserInfoConverter.toVO(accountService.getCurrentUserinfo()));
    }


    @PostMapping("/user/base")
    @Operation(summary = "更新用户信息")
    public R<UserInfoVO> updateUserinfo(@RequestBody UserInfoForm userInfoForm) {
        accountService.updateCurrentUserinfo(userInfoForm);
        return R.ok();
    }

}

package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.service.AccountService;
import org.shanzhaozhen.authorize.utils.SecurityUtils;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.feign.UserInfoFeignClient;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.form.UserInfoForm;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-29
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserInfoFeignClient userInfoFeignClient;

    @Override
    public UserInfoDTO getCurrentUserinfo() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Assert.hasText(currentUserId, "当前未有用户登陆或为匿名用户");

        UserInfoDTO userInfo;
        try {
            R<UserInfoDTO> result = userInfoFeignClient.getUserInfoByUserId(currentUserId);
            userInfo = result.getData();
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("当前用户获取异常，请联系管理员!");
        }

        return userInfo;
    }

    @Override
    public R<?> updateCurrentUserinfo(UserInfoForm userInfoForm) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        userInfoForm.setPid(currentUserId);
        return userInfoFeignClient.updateUserInfo(userInfoForm);
    }

}

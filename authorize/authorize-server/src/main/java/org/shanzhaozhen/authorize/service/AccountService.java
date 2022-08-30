package org.shanzhaozhen.authorize.service;

import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.form.ChangePasswordForm;
import org.shanzhaozhen.uaa.pojo.form.UserInfoForm;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-29
 * @Description: 用户信息接口
 */
public interface AccountService {


    UserInfoDTO getCurrentUserinfo();

    R<?> updateCurrentUserinfo(UserInfoForm userInfoForm);

    void changePassword(ChangePasswordForm changePasswordForm);

}

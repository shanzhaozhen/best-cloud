package org.shanzhaozhen.authorize.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.mapper.OAuth2UserMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.authorize.pojo.dto.SecurityInfo;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2UserDO;
import org.shanzhaozhen.authorize.pojo.form.BindPhoneForm;
import org.shanzhaozhen.authorize.service.CaptchaService;
import org.shanzhaozhen.authorize.service.OAuth2UserService;
import org.shanzhaozhen.authorize.utils.SecurityUtils;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.common.core.utils.EncryptUtils;
import org.shanzhaozhen.common.core.utils.PasswordUtils;
import org.shanzhaozhen.authorize.pojo.form.ChangePasswordForm;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class OAuth2UserServiceImpl implements OAuth2UserService {

    private final OAuth2UserMapper oauth2UserMapper;
    private final PasswordEncoder passwordEncoder;
    private final CaptchaService captchaService;

    @Override
    public OAuth2UserDTO getUserById(String userId) {
        OAuth2UserDTO user = oauth2UserMapper.getUserById(userId);
        Assert.notNull(user, "用户不存在");
        return user;
    }

    @Override
    public OAuth2UserDTO getUserByUserId(String userId) {
        return oauth2UserMapper.getUserByUserId(userId);
    }

    @Override
    public OAuth2UserDTO getUserByUsername(String username) {
        return oauth2UserMapper.getUserByUsername(username);
    }

    @Override
    public OAuth2UserDTO getUserByPhone(String phone) {
        return oauth2UserMapper.getUserByPhone(phone);
    }

//    @Override
//    @Cacheable(key = "#root.methodName + ':' + #userId")
//    public JWTUser getJWTUser(String userId) {
//        return oauth2UserMapper.getJWTUserByUserId(userId);
//    }

    @Override
    public OAuth2UserDO getCurrentUser() {
        String userId = SecurityUtils.getCurrentUserId();
        if (!StringUtils.hasText(userId)) {
            return null;
        }

        return oauth2UserMapper.selectById(userId);
    }

    @Override
    @Transactional
    public String register(OAuth2UserDTO oauth2UserDTO) {
        Assert.isNull(oauth2UserMapper.selectUserByUsername(oauth2UserDTO.getUsername()), "注册失败，该用户名已存在！");
        OAuth2UserDO newUser = new OAuth2UserDO();
        BeanUtils.copyProperties(oauth2UserDTO, newUser, "accountNonExpired", "accountNonLocked", "credentialsNonExpired", "enabled");
        newUser.setPassword(PasswordUtils.encryption(oauth2UserDTO.getPassword()));
        oauth2UserMapper.insert(newUser);
        return newUser.getId();
    }

    @Override
    public Boolean isExistUser(String username) {
        OAuth2UserDO userDO = oauth2UserMapper.selectUserByUsername(username);
        return userDO == null;
    }

//    @Override
//    public CurrentUser getUserInfo() {
//        CurrentUser currentUser = new CurrentUser();
//        return currentUser;
//    }

    @Override
    @Transactional
    public Page<OAuth2UserDTO> getUserPage(Page<OAuth2UserDTO> page, String keyword) {
        return oauth2UserMapper.getUserPage(page, keyword);
    }

    @Override
    @Transactional
    public String addUser(OAuth2UserDTO oauth2UserDTO) {
        Assert.isNull(oauth2UserMapper.selectUserByUsername(oauth2UserDTO.getUsername()), "注册失败，该用户名已存在！");
        OAuth2UserDO userDO = new OAuth2UserDO();
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2UserDTO, userDO, "password");
        userDO.setPassword(PasswordUtils.encryption(oauth2UserDTO.getPassword()));
        oauth2UserMapper.insert(userDO);
        return userDO.getId();
    }

    @Override
    @Transactional
    public String updateUser(OAuth2UserDTO oauth2UserDTO) {
        Assert.notNull(oauth2UserDTO.getId(), "用户id不能为空");
        OAuth2UserDO userDO = oauth2UserMapper.selectById(oauth2UserDTO.getId());
        Assert.notNull(userDO, "更新失败：没有找到该用户或已被删除");
        CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2UserDTO, userDO, "password");
        // 密码不为空则更新密码
        if (StringUtils.hasText(oauth2UserDTO.getPassword())) {
            userDO.setPassword(PasswordUtils.encryption(oauth2UserDTO.getPassword()));
        }
        oauth2UserMapper.updateById(userDO);
        return userDO.getId();
    }

    @Override
    @Transactional
    public String deleteUser(String userId) {
        oauth2UserMapper.deleteById(userId);
        return userId;
    }

    @Override
    @Transactional
    public List<String> batchDeleteUser(List<String> userIds) {
        Assert.notEmpty(userIds, "没有需要删除的用户");
        for (String userId : userIds) {
            this.deleteUser(userId);
        }
        return userIds;
    }

    @Override
    public Boolean logout() {
//        String userId = UserDetailsUtils.getUserId();
        return true;
    }

    @Override
    @Transactional
    public void changePassword(ChangePasswordForm changePasswordForm) {
        // 检验用户是否存在
        OAuth2UserDO userDO = this.getCurrentUser();
        Assert.notNull(userDO, "当前登陆状态为匿名用户或用户不存在");

        String encodePassword = userDO.getPassword();
        boolean matches = passwordEncoder.matches(changePasswordForm.getOldPassword(), encodePassword);
        Assert.isTrue(matches, "原密码不正确");

        encodePassword = passwordEncoder.encode(changePasswordForm.getNewPassword());
        userDO.setPassword(encodePassword);
        oauth2UserMapper.updateById(userDO);
    }

    @Override
    public SecurityInfo getSecurityInfo() {
        OAuth2UserDO currentUser = this.getCurrentUser();
        Assert.notNull(currentUser, "当前登陆状态为匿名用户或用户不存在");

        SecurityInfo securityInfo = new SecurityInfo();

        if (StringUtils.hasText(currentUser.getPhone())) {
            securityInfo.setPhone(EncryptUtils.mobileEncrypt(currentUser.getPhone()));
        }

        return securityInfo;
    }

    @Override
    public void bindPhone(BindPhoneForm bindPhoneForm) {
        // 检验用户是否存在
        OAuth2UserDO userDO = this.getCurrentUser();
        Assert.notNull(userDO, "当前登陆状态为匿名用户或用户不存在");

        // 检查该手机号码是否跟当前账号已绑定号码一样
        if (StringUtils.hasText(userDO.getPhone())) {
            Assert.isTrue(bindPhoneForm.getPhone().equals(userDO.getPhone()), "当前号码已绑定该账号");
        }

        // 检查手机号码是否已被其他账号绑定
        OAuth2UserDTO phoneUser = oauth2UserMapper.getUserByPhone(bindPhoneForm.getPhone());
        Assert.isNull(phoneUser, "该手机号已绑定其他账号，请先解绑再进行绑定！");

        // 校验手机验证码
        boolean isVerify = captchaService.verifyCaptcha(bindPhoneForm.getPhone(), bindPhoneForm.getCaptcha());
        Assert.isTrue(isVerify, "验证码错误，请重试！");

        userDO.setPhone(bindPhoneForm.getPhone());
        oauth2UserMapper.updateById(userDO);
    }

    @Override
    public void unbindPhone() {
        // 检验用户是否存在
        OAuth2UserDO userDO = this.getCurrentUser();
        Assert.notNull(userDO, "当前登陆状态为匿名用户或用户不存在");

        userDO.setPhone(null);
        oauth2UserMapper.updateById(userDO);
    }

}

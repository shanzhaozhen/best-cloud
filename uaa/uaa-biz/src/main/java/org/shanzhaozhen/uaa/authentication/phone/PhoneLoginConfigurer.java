package org.shanzhaozhen.uaa.authentication.phone;

import org.shanzhaozhen.uaa.authentication.AbstractLoginFilterConfigurer;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.web.authentication.ui.DefaultLoginPageGeneratingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class PhoneLoginConfigurer<H extends HttpSecurityBuilder<H>> extends
        AbstractLoginFilterConfigurer<H, PhoneLoginConfigurer<H>, PhoneAuthenticationFilter> {

    public PhoneLoginConfigurer() {
        super(new PhoneAuthenticationFilter(), "/login/phone");
        phoneParameter("phone");
        captchaParameter("captcha");
    }

    /**
     * 设置校验手机号的字段
     * @param phoneParameter
     * @return
     */
    public PhoneLoginConfigurer<H> phoneParameter(String phoneParameter) {
        getAuthenticationFilter().setPhoneParameter(phoneParameter);
        return this;
    }

    /**
     * 设置校验验证码的字段
     * @param captchaParameter
     * @return
     */
    public PhoneLoginConfigurer<H> captchaParameter(String captchaParameter) {
        getAuthenticationFilter().setCaptchaParameter(captchaParameter);
        return this;
    }

    /**
     * 初始化手机验证码登陆校验方式的配置
     * @param http
     * @throws Exception
     */
    @Override
    public void init(H http) throws Exception {
        super.init(http);
        initDefaultLoginFilter(http);
    }

    /**
     * 设置手机登陆的验证地址
     * @param loginProcessingUrl creates the {@link RequestMatcher} based upon the
     * loginProcessingUrl
     * @return
     */
    @Override
    protected RequestMatcher createLoginProcessingUrlMatcher(String loginProcessingUrl) {
        return new AntPathRequestMatcher(loginProcessingUrl, "POST");
    }

    /**
     * 获取手机号的字段名称
     * @return
     */
    private String getPhoneParameter() {
        return getAuthenticationFilter().getPhoneParameter();
    }

    /**
     * 获取验证码的字段名称
     * @return
     */
    private String getCaptchaParameter() {
        return getAuthenticationFilter().getCaptchaParameter();
    }

    /**
     * 设置默认配置
     * @param http
     */
    private void initDefaultLoginFilter(H http) {
        DefaultLoginPageGeneratingFilter loginPageGeneratingFilter = http
                .getSharedObject(DefaultLoginPageGeneratingFilter.class);
        if (loginPageGeneratingFilter != null) {
            loginPageGeneratingFilter.setFormLoginEnabled(true);
            loginPageGeneratingFilter.setUsernameParameter(getPhoneParameter());
            loginPageGeneratingFilter.setPasswordParameter(getCaptchaParameter());
            loginPageGeneratingFilter.setAuthenticationUrl(getLoginProcessingUrl());
        }
    }

}
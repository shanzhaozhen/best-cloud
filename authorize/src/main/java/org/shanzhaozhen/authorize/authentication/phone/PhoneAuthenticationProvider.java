package org.shanzhaozhen.authorize.authentication.phone;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.shanzhaozhen.authorize.exception.CaptchaErrorException;
import org.shanzhaozhen.authorize.service.CaptchaService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * 手机号登陆认证
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PhoneAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private final PhoneUserDetailsService phoneUserDetailsService;
    private final CaptchaService captchaService;

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    private final UserDetailsChecker authenticationChecks = new AccountStatusUserDetailsChecker();

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();


    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(phoneUserDetailsService, "captchaUserDetailsService must not be null");
        Assert.notNull(captchaService, "captchaService must not be null");
    }

    @Override
    public void setMessageSource(@NotNull MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(PhoneAuthenticationToken.class, authentication,
                () -> this.messages.getMessage("PhoneAuthenticationProvider.onlySupports",
                        "Only PhoneAuthenticationToken is supported"));

        PhoneAuthenticationToken phoneAuthenticationToken = (PhoneAuthenticationToken) authentication;

        String phone = phoneAuthenticationToken.getName();
        String captcha = (String) phoneAuthenticationToken.getCredentials();

        UserDetails user;
        try {
            user = retrieveUser(phone, captcha, phoneAuthenticationToken);
        }
        catch (PhoneNotFoundException ex) {
            log.debug("Failed to find user by phone '" + phone + "'");
            throw new BadCredentialsException(this.messages
                    .getMessage("PhoneAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");

        // 校验账号状态
        this.authenticationChecks.check(user);
        return createSuccessAuthentication(authentication, user);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (PhoneAuthenticationToken.class.isAssignableFrom(authentication));
    }

    protected final UserDetails retrieveUser(String phone, String captcha, PhoneAuthenticationToken authentication)
            throws AuthenticationException {

        try {
            // 先校验验证码
            captchaService.verifyCaptcha(phone, captcha);

            // 校验通过则查找用户
            UserDetails loadedUser = phoneUserDetailsService.loadUserByPhone(phone);
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException(
                        "PhoneUserDetailsService returned null, which is an interface contract violation");
            }
            return loadedUser;
        } catch (CaptchaErrorException | PhoneNotFoundException | InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    /**
     * 认证成功将非授信凭据转为授信凭据.
     * 封装用户信息 角色信息。
     * @param authentication
     * @param user
     * @return
     */
    protected Authentication createSuccessAuthentication(Authentication authentication,
                                                         UserDetails user) {
        Collection<? extends GrantedAuthority> authorities = authoritiesMapper.mapAuthorities(user.getAuthorities());
        PhoneAuthenticationToken authenticationToken = new PhoneAuthenticationToken(user, null, authorities);
        authenticationToken.setDetails(authentication.getDetails());
        return authenticationToken;
    }

}

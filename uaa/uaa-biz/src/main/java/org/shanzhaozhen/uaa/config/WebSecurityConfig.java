package org.shanzhaozhen.uaa.config;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.authentication.DefaultAuthenticationFailureHandler;
import org.shanzhaozhen.uaa.authentication.DefaultAuthenticationSuccessHandler;
import org.shanzhaozhen.uaa.authentication.account.AccountLoginConfigurer;
import org.shanzhaozhen.uaa.authentication.phone.PhoneAuthenticationProvider;
import org.shanzhaozhen.uaa.authentication.phone.PhoneLoginConfigurer;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

    private final DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

    private final UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().anyRequest().permitAll()
                .and()
//                .headers().frameOptions().disable()
//                .formLogin().disable()
                .apply(new AccountLoginConfigurer<>(http, userDetailsService))
                    .successHandler(defaultAuthenticationSuccessHandler)
                    .failureHandler(defaultAuthenticationFailureHandler)
                .and()
                .apply(new PhoneLoginConfigurer<>())
                    .successHandler(defaultAuthenticationSuccessHandler)
                    .failureHandler(defaultAuthenticationFailureHandler)
        ;
    }
}

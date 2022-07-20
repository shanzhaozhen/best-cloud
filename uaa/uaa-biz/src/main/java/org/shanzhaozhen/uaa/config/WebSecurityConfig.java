package org.shanzhaozhen.uaa.config;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.authentication.phone.PhoneAuthenticationProvider;
import org.shanzhaozhen.uaa.authentication.phone.PhoneLoginConfigurer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PhoneAuthenticationProvider phoneAuthenticationProvider;

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
                .formLogin().disable()
                .apply(new PhoneLoginConfigurer<>());
    }
}

package org.shanzhaozhen.security.biz.config.oauth2;
/*

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    */
/**
     * 需要放行的URL
     *//*

    private static final String[] AUTH_WHITELIST = {
            "/", "/login/**", "/logout/**", "/register/**",
            "/rsa/publicKey", "/oauth/logout", "/oauth/**",
            "/swagger-resources/**", "/v2/api-docs/**", "/swagger2", "/swagger2/**", "/swagger-ui.html",
            "/druid/**",
            "/webjars/**",
            "/upload", "/files/**",
            "/error", "/test"
    };

    private final PasswordEncoder passwordEncoder;

    private final CustomUserDetailsService customUserDetailsService;

    */
/**
     * 定义授权规则
     * @param auth
     * @throws Exception
     *//*

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf().disable()
                .authorizeRequests()
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
                .headers()
                .frameOptions().disable()       //允许iframe
                .and()
        ;
    }

    */
/**
     *  如果不配置SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户，而且也注入不了
     *//*

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}
*/

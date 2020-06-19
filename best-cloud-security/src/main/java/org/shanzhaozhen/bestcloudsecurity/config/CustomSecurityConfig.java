package org.shanzhaozhen.bestcloudsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@EnableWebSecurity
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 需要放行的URL
     */
    private static final String[] AUTH_WHITELIST = {
            "/",
            "/login",
            "/register/**",
            "/swagger-resources/**",
            "/v2/api-docs/**",
            "/swagger2",
            "/swagger2/**",
            "/swagger-ui.html",
            "/druid/**",
            "/webjars/**",
            "/upload",
            "/test",
            "/files/**",
            "/error"
    };

    private final CustomUserDetailsService customUserDetailsService;

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    private final CustomJwtAuthenticationFilter customJwtAuthenticationFilter;

    private final CustomJwtTokenProvider customJwtTokenProvider;

    private final CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource;

    private final CustomAccessDecisionManager myAccessDecisionManager;

    public CustomSecurityConfig(CustomUserDetailsService customUserDetailsService,
                                CustomAuthenticationEntryPoint customAuthenticationEntryPoint,
                                CustomJwtAuthenticationFilter customJwtAuthenticationFilter,
                                CustomJwtTokenProvider customJwtTokenProvider,
                                CustomFilterInvocationSecurityMetadataSource customFilterInvocationSecurityMetadataSource,
                                CustomAccessDecisionManager myAccessDecisionManager) {
        this.customUserDetailsService = customUserDetailsService;
        this.customAuthenticationEntryPoint = customAuthenticationEntryPoint;
        this.customJwtAuthenticationFilter = customJwtAuthenticationFilter;
        this.customJwtTokenProvider = customJwtTokenProvider;
        this.customFilterInvocationSecurityMetadataSource = customFilterInvocationSecurityMetadataSource;
        this.myAccessDecisionManager = myAccessDecisionManager;
    }

    /**
     * 定义认证规则
     * anyRequest().authenticated(): 其他地址的访问均需验证权限
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http);
        http
            .cors()
                .and()
            .csrf()
                .disable()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
            .authorizeRequests()
//                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers(AUTH_WHITELIST).permitAll()
                .anyRequest().authenticated()
                .and()
            .headers()
                .frameOptions().disable()       //允许iframe
                .and()
            .formLogin()
                .disable()
            .logout()
                .disable()
//            .exceptionHandling()
//                .authenticationEntryPoint(customAuthenticationEntryPoint)
//                .and()
            .addFilterBefore(customJwtAuthenticationFilter, BasicAuthenticationFilter.class)
            .addFilterBefore(CustomUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilterAfter(CustomFilterSecurityInterceptor(), FilterSecurityInterceptor.class)
        ;
    }

    /**
     * 定义授权规则
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
    }

    /**
     * 该方式才是最佳的AuthenticationFilter注入方式
     * @return
     * @throws Exception
     */
    @Bean
    public CustomUsernamePasswordAuthenticationFilter CustomUsernamePasswordAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new CustomUsernamePasswordAuthenticationFilter(customJwtTokenProvider);
        customUsernamePasswordAuthenticationFilter.setAuthenticationManager(authenticationManagerBean());
        return customUsernamePasswordAuthenticationFilter;
    }

    /**
     * 注入自定义 FilterSecurityInterceptor
     * @return
     * @throws Exception
     */
    @Bean
    public CustomFilterSecurityInterceptor CustomFilterSecurityInterceptor() {
        CustomFilterSecurityInterceptor customFilterSecurityInterceptor = new CustomFilterSecurityInterceptor(customFilterInvocationSecurityMetadataSource);
        customFilterSecurityInterceptor.setAccessDecisionManager(myAccessDecisionManager);
        return customFilterSecurityInterceptor;
    }

    /**
     * 配置允许跨域
     * @return
     */
    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "HEAD", "OPTION"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}

package org.shanzhaozhen.authorize.config.security;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.authentication.handler.DefaultAuthenticationFailureHandler;
import org.shanzhaozhen.authorize.authentication.handler.DefaultAuthenticationSuccessHandler;
import org.shanzhaozhen.authorize.authentication.account.AccountLoginConfigurer;
import org.shanzhaozhen.authorize.authentication.phone.PhoneLoginConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final static String[] whiteUrl = {"/**/*.ico", "/**/*.css","/**/*.js", "/static/**", "/v3/**",
			"/", "/login", "/front/**"
//			, "/**", "/authorize/rsa/publicKey"
			, "/.well-known/openid-configuration"
	};

	private final DefaultAuthenticationSuccessHandler defaultAuthenticationSuccessHandler;

	private final DefaultAuthenticationFailureHandler defaultAuthenticationFailureHandler;

	private final UserDetailsService userDetailsService;


	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
						.antMatchers(whiteUrl).permitAll()
						.anyRequest().authenticated()
			)
				.csrf().disable()
				.formLogin().disable()
				.exceptionHandling((exceptions) -> exceptions
						.authenticationEntryPoint(
								new LoginUrlAuthenticationEntryPoint("/login"))
				)
				.apply(new AccountLoginConfigurer<>(http, userDetailsService))
//				.successHandler(defaultAuthenticationSuccessHandler)
				.failureHandler(defaultAuthenticationFailureHandler)
				.and()
				.apply(new PhoneLoginConfigurer<>())
//				.successHandler(defaultAuthenticationSuccessHandler)
				.failureHandler(defaultAuthenticationFailureHandler)
		;
		return http.build();
	}

}

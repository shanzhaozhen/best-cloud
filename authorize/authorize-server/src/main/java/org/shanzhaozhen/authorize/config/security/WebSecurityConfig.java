package org.shanzhaozhen.authorize.config.security;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.authentication.federated.FederatedIdentityConfigurer;
import org.shanzhaozhen.authorize.authentication.account.AccountLoginConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final static String[] whiteUrl = {"/**/*.ico", "/**/*.css","/**/*.js", "/static/**", "/v3/**",
			"/login", "/front/**"
//			, "/**", "/authorize/rsa/publicKey"
			, "/.well-known/openid-configuration"
	};

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
//				.formLogin().disable()
//				.exceptionHandling((exceptions) -> exceptions
//						.authenticationEntryPoint(
//								new LoginUrlAuthenticationEntryPoint("/login"))
//				)
				.userDetailsService(userDetailsService)
//				.formLogin()
				.apply(new AccountLoginConfigurer<>())
				.and()
				.apply(new FederatedIdentityConfigurer())
//				.loginPage("/login")
//				.successHandler(defaultAuthenticationSuccessHandler)
//				.failureHandler(defaultAuthenticationFailureHandler)
//				.and()
//				.apply(new PhoneLoginConfigurer<>())
//				.successHandler(defaultAuthenticationSuccessHandler)
//				.failureHandler(defaultAuthenticationFailureHandler)
		;
		return http.build();
	}

}

package org.shanzhaozhen.authorize.config.security;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.authentication.bind.Oauth2BindConfigurer;
import org.shanzhaozhen.authorize.authentication.federated.FederatedIdentityConfigurer;
import org.shanzhaozhen.authorize.authentication.account.AccountLoginConfigurer;
import org.shanzhaozhen.uaa.feign.SocialUserFeignClient;
import org.shanzhaozhen.uaa.feign.UserFeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	public final static String[] whiteUrl = {
			"/v3/**",
			"/login", "/register", "/front/**"
//			, "/**", "/authorize/rsa/publicKey"
			, "/.well-known/openid-configuration"
	};

	private final UserDetailsService userDetailsService;
	private final UserFeignClient userFeignClient;
	private final SocialUserFeignClient socialUserFeignClient;


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
				.apply(new AccountLoginConfigurer<>(socialUserFeignClient))
				.and()
				.apply(new FederatedIdentityConfigurer(socialUserFeignClient))
				.and()
				.apply(new Oauth2BindConfigurer(socialUserFeignClient))
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

	@Bean
	WebSecurityCustomizer webSecurityCustomizer() {
		return web -> web.ignoring().antMatchers(
				"/webjars/**", "/front/**", "/static/**",
				"/**/*.ico", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg"
		);
	}

}

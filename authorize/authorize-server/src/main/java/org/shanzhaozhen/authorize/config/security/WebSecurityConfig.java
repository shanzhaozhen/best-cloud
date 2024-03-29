package org.shanzhaozhen.authorize.config.security;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.authentication.bind.Oauth2BindConfigurer;
import org.shanzhaozhen.authorize.authentication.federated.FederatedIdentityConfigurer;
import org.shanzhaozhen.authorize.authentication.account.AccountLoginConfigurer;
import org.shanzhaozhen.authorize.authentication.phone.PhoneLoginConfigurer;
import org.shanzhaozhen.authorize.service.OAuthUserSocialService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	public final static String[] resourcesPath = {
			"/webjars/**", "/front/**", "/static/**",
			"/favicon.ico", "/*/*.css", "/*/*.js", "/*/*.png", "/*/*.jpg"
	};

	public final static String[] whiteUrl = {
			"/v3/**",
			"/login", "/register", "/front/**", "/captcha/**"
			, "/.well-known/openid-configuration"
	};

	private final UserDetailsService userDetailsService;
	private final OAuthUserSocialService socialUserService;


	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http

//				AuthorizationManagerRequestMatcherRegistry

			.authorizeHttpRequests(authorizeHttpRequests ->
					authorizeHttpRequests
							.requestMatchers(resourcesPath).permitAll()
							.requestMatchers(whiteUrl).permitAll()
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
				.apply(new AccountLoginConfigurer<>(socialUserService))
				.and()
				.apply(new PhoneLoginConfigurer<>())
				.and()
				.apply(new FederatedIdentityConfigurer(socialUserService))
				.and()
				.apply(new Oauth2BindConfigurer(socialUserService))
//				.loginPage("/login")
		;
		return http.build();
	}

//	@Bean
//	WebSecurityCustomizer webSecurityCustomizer() {
//		return web -> web.ignoring().antMatchers(
//				"/webjars/**", "/front/**", "/static/**",
//				"/**/*.ico", "/**/*.css", "/**/*.js", "/**/*.png", "/**/*.jpg"
//		);
//	}

}

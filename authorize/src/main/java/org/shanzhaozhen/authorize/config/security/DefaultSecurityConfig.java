package org.shanzhaozhen.authorize.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
public class DefaultSecurityConfig {

	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeRequests(authorizeRequests ->
				authorizeRequests
						.antMatchers("/v3/**").permitAll()
//						.antMatchers("/**", "/authorize/rsa/publicKey", "/.well-known/openid-configuration").permitAll()
						.anyRequest().authenticated()
			)
			.csrf().disable()
			.formLogin(withDefaults());
		return http.build();
	}

}

package org.shanzhaozhen.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@RequiredArgsConstructor
// 注解需要使用@EnableWebFluxSecurity而非@EnableWebSecurity，因为SpringCloud Gateway基于WebFlux
@EnableWebFluxSecurity
public class ResourceServerConfig {



}

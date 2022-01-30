package org.shanzhaozhen.gateway.filter;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.constant.SecurityConstants;
import org.shanzhaozhen.gateway.config.WhiteListConfig;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-01-28
 * @Description: 白名单路径访问时需要移除JWT请求头
 */
@EnableWebFluxSecurity
@RequiredArgsConstructor
public class WhiteListRemoveJwtFilter implements WebFilter {

    private final WhiteListConfig whiteListConfig;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        URI uri = request.getURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        // 白名单路径移除JWT请求头
        List<String> ignoreUrls = whiteListConfig.getUrls();
        for (String ignoreUrl : ignoreUrls) {
            if (pathMatcher.match(ignoreUrl, uri.getPath())) {
                request = exchange.getRequest().mutate().header(SecurityConstants.AUTHORIZATION_KEY, "").build();
                exchange = exchange.mutate().request(request).build();
                return chain.filter(exchange);
            }
        }
        return chain.filter(exchange);
    }
}

package org.shanzhaozhen.authorize.authentication.federated;

import org.jetbrains.annotations.NotNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class FederatedIdentitySocialBindFilter extends OncePerRequestFilter {

    private static final String BIND_URL = "/social-bind";

    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AntPathRequestMatcher antPathRequestMatcher = new AntPathRequestMatcher(BIND_URL);
        if (authentication instanceof OAuth2AuthenticationToken) {
            // 未绑定用户的情况，只允许在绑定页面
            if (!antPathRequestMatcher.matches(request)) {
                response.sendRedirect(BIND_URL);
                return;
            }
        } else {
            // 非 Oauth2 账号类型直接跳转到个人页
            if (antPathRequestMatcher.matches(request)) {
                response.sendRedirect("/account");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

}

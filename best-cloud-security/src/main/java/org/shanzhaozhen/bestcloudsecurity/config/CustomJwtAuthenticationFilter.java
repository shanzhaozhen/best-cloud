package org.shanzhaozhen.bestcloudsecurity.config;

import org.shanzhaozhen.basiccommon.dto.JWTUser;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {

    private final CustomJwtTokenProvider customJwtTokenProvider;

    public CustomJwtAuthenticationFilter(CustomJwtTokenProvider customJwtTokenProvider) {
        this.customJwtTokenProvider = customJwtTokenProvider;
    }

    /**
     * 1.从每个请求header获取token
     * 2.调用前面写的validateToken方法对token进行合法性验证
     * 3.解析得到username，并从database取出用户相关信息权限
     * 4.把用户信息(role等)放进SecurityContext以备整个请求过程使用。
     * （例如哪里需要判断用户权限是否足够时可以直接从SecurityContext取出去check）
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        String jwtToken = customJwtTokenProvider.getJwtTokenFromRequest(httpServletRequest);

        // 如果请求头中有token而且token校验正确，则进行解析，并且设置认证信息
        if (StringUtils.hasText(jwtToken)) {

            /**
             * token 过期时重新登录
             */
            if (!customJwtTokenProvider.validateToken(httpServletResponse, jwtToken)) {
                return;
            }

            SecurityContextHolder.getContext().setAuthentication(this.createAuthentication(jwtToken));
        }

        /**
         * 不管成不成功都交给下一个过滤器判断
         */
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    /**
     * 这里从token中获取用户信息并新建一个UsernamePasswordAuthenticationToken供给过滤链进行权限过滤
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken createAuthentication(String token) {
        JWTUser jwtUser = customJwtTokenProvider.getJWTUser(token);
        return new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
    }




}

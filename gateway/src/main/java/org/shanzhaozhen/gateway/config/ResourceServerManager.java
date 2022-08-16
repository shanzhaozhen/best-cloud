package org.shanzhaozhen.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.core.constant.GlobalConstants;
import org.shanzhaozhen.common.core.constant.SecurityConstants;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-01-28
 * @Description: 网关自定义鉴权管理器，用于判断是否有资源的访问权限
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class ResourceServerManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();

        // 进行跨域请求的时候，并且请求头中有额外参数，比如token，客户端会先发送一个OPTIONS请求，来探测后续需要发起的跨域POST请求是否安全可接受
        if (HttpMethod.OPTIONS.equals(request.getMethod())) { // 预检请求放行
            return Mono.just(new AuthorizationDecision(true));
        }

        // todo: 放行白名单
        // return Mono.just(new AuthorizationDecision(true));

        PathMatcher pathMatcher = new AntPathMatcher();
        String method = request.getMethodValue();
        String path = request.getURI().getPath();
        String restfulPath = method + ":" + path; // RESTFul接口权限设计 @link https://www.cnblogs.com/haoxianrui/p/14961707.html

        // 如果token以"bearer "为前缀，到此方法里说明JWT有效即已认证
        String token = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_KEY);

        if (!StringUtils.hasText(token) || !StringUtils.startsWithIgnoreCase(token, SecurityConstants.JWT_PREFIX)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        /**
         * 鉴权开始
         *
         * 缓存取 [URL权限-角色集合] 规则数据
         * urlPermRolesRules = [{'key':'GET:/api/v1/users/*','value':['ADMIN','TEST']},...]
         */


        HashOperations<String, String, List<String>> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        Map<String, List<String>> urlMatchRole = stringObjectObjectHashOperations.entries(GlobalConstants.URL_PERM_ROLES_KEY);

        // 根据请求路径获取有访问权限的角色列表
        List<String> authorizedRoles = new ArrayList<>(); // 拥有访问权限的角色
        boolean requireCheck = false; // 是否需要鉴权，默认未设置拦截规则不需鉴权

        for (Map.Entry<String, List<String>> permRoles : urlMatchRole.entrySet()) {
            String permission = permRoles.getKey();
            if (pathMatcher.match(permission, path)) {
                List<String> roles = permRoles.getValue();
                if (!CollectionUtils.isEmpty(roles)) {
                    authorizedRoles.addAll(roles);
                }
                if (!requireCheck) {
                    requireCheck = true;
                }
            }
        }
        // 没有设置拦截规则放行
        if (!requireCheck) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 判断JWT中携带的用户角色是否有权限访问
        return authentication.log()
                .filter(Authentication::isAuthenticated).log()
                .flatMapIterable(Authentication::getAuthorities).log()
                .map(GrantedAuthority::getAuthority)
                .any(authority -> {
                    String roleCode = authority.substring(SecurityConstants.AUTHORITY_PREFIX.length()); // 用户的角色
                    if (GlobalConstants.ROOT_ROLE_CODE.equals(roleCode)) {
                        return true; // 如果是超级管理员则放行
                    }
                    return !CollectionUtils.isEmpty(authorizedRoles) && authorizedRoles.contains(roleCode);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}

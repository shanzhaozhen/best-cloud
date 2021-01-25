package org.shanzhaozhen.gateway.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.common.enums.AuthConstants;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.*;

/**
 * 鉴权管理器
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CustomAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    private final RedisTemplate<String, Object> redisTemplate;

    private final RedisTokenStore redisTokenStore;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        ServerHttpRequest request = authorizationContext.getExchange().getRequest();
        String path = request.getURI().getPath();

        // 1. 对应跨域的预检请求直接放行
        if (Objects.equals(request.getMethod(), HttpMethod.OPTIONS)) {
            return Mono.just(new AuthorizationDecision(true));
        }

        // 2. token为空拒绝访问
        String token = request.getHeaders().getFirst(AuthConstants.JWT_TOKEN_HEADER);
        if (StringUtils.isEmpty(token)) {
            return Mono.just(new AuthorizationDecision(false));
        }

        // 3.缓存取资源权限角色关系列表
        Map<Object, Object> resourceMap = redisTemplate.opsForHash().entries(AuthConstants.RESOURCE_ROLES_KEY);
        Iterator<Object> iterator = resourceMap.keySet().iterator();

        Set<ConfigAttribute> configAttributes = new HashSet<>();
        AntPathMatcher antPathMatcher = new AntPathMatcher();

        while (iterator.hasNext()) {
            String requestUrl = (String) iterator.next();
            if (antPathMatcher.match(requestUrl, path)) {
                configAttributes.addAll((List<ConfigAttribute>) resourceMap.get(requestUrl));
            }
        }


        // 4. 读取redis中token含有的权限
        OAuth2AccessToken oAuth2AccessToken = redisTokenStore.readAccessToken(token);

//        Iterator<Collection<ConfigAttribute>> iterator = resourceMap.keySet().iterator();
//
//        // 请求路径匹配到的资源需要的角色权限集合authorities统计
//        List<ConfigAttribute> authorities = new ArrayList<>();
//        while (iterator.hasNext()) {
//            ConfigAttribute pattern = iterator.next();
//            if (pathMatcher.match(pattern, path)) {
//                authorities.addAll(Convert.toList(String.class, resourceRolesMap.get(pattern)));
//            }
//        }

        return authentication
                .filter(Authentication::isAuthenticated)
                .flatMapIterable(Authentication::getAuthorities)
                .map(GrantedAuthority::getAuthority)
                .any(roleId -> {
                    // 3. roleId是请求用户的角色(格式:ROLE_{roleId})，authorities是请求资源所需要角色的集合
                    log.info("访问路径：{}", path);
                    log.info("用户角色roleId：{}", roleId);
                    log.info("资源需要权限authorities：{}", configAttributes);
                    return configAttributes.contains(roleId);
                })
                .map(AuthorizationDecision::new)
                .defaultIfEmpty(new AuthorizationDecision(false));
    }

}


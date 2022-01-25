package org.shanzhaozhen.gateway.config;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.shanzhaozhen.common.constant.SecurityConstants;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.util.Map;


/**
 * 安全拦截全局过滤器
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityGlobalFilter implements GlobalFilter, Ordered {

    private final RedisTemplate redisTemplate;

    @SneakyThrows
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();

        // 不是正确的的JWT不做解析处理
        String token = request.getHeaders().getFirst(SecurityConstants.AUTHORIZATION_KEY);
        if (!StringUtils.hasText(token) || !StringUtils.startsWithIgnoreCase(token, SecurityConstants.JWT_PREFIX)) {
            return chain.filter(exchange);
        }

        // 解析JWT获取jti，以jti为key判断redis的黑名单列表是否存在，存在则拦截访问
        token = StringUtils.replace(token, SecurityConstants.JWT_PREFIX, Strings.EMPTY);

        Map<String, Object> payload = JWSObject.parse(token).getPayload().toJSONObject();

        String jti = (String) payload.get(SecurityConstants.JWT_JTI);
        Boolean isBlack = redisTemplate.hasKey(SecurityConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (isBlack) {
            return ResponseUtils.writeErrorInfo(response, ResultCode.TOKEN_ACCESS_FORBIDDEN);
        }

        // 存在token且不是黑名单，request写入JWT的载体信息
        request = exchange.getRequest().mutate()
                .header(SecurityConstants.JWT_PAYLOAD_KEY, URLEncoder.encode(payload, "UTF-8"))
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

package org.shanzhaozhen.gateway.filter;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.shanzhaozhen.common.core.constant.SecurityConstants;
import org.shanzhaozhen.common.core.result.ResultCode;
import org.shanzhaozhen.gateway.util.ResponseUtils;
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

import java.util.Map;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-01-28
 * @Description: 安全拦截全局过滤器
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class SecurityGlobalFilter implements GlobalFilter, Ordered {

    private final RedisTemplate<String, Object> redisTemplate;

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
        // todo: 由于oauth2会将用户储存，考虑更改其他方式进行对用户操作，增加下线功能
        token = StringUtils.replace(token, SecurityConstants.JWT_PREFIX, Strings.EMPTY);

        Payload payload = JWSObject.parse(token).getPayload();
        Map<String, Object> payloadMap = payload.toJSONObject();

        String jti = (String) payloadMap.get(SecurityConstants.JWT_JTI);
        Boolean isBlack = redisTemplate.hasKey(SecurityConstants.TOKEN_BLACKLIST_PREFIX + jti);
        if (Boolean.TRUE.equals(isBlack)) {
            return ResponseUtils.writeErrorInfo(response, ResultCode.JWT_FORBIDDEN);
        }

        // 存在token且不是黑名单，request写入JWT的载体信息
        request = exchange.getRequest().mutate()
//                .header(SecurityConstants.JWT_PAYLOAD_KEY, URLEncoder.encode(payload.toString(), "UTF-8"))
                .header(SecurityConstants.JWT_PAYLOAD_KEY, payload.toString())
                .build();
        exchange = exchange.mutate().request(request).build();
        return chain.filter(exchange);

    }

    @Override
    public int getOrder() {
        return 0;
    }

}

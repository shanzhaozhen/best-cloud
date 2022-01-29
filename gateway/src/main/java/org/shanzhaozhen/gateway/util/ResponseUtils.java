package org.shanzhaozhen.gateway.util;

import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.common.core.result.ResultCode;
import org.shanzhaozhen.common.core.utils.JacksonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;


public class ResponseUtils {

    public static Mono<Void> writeErrorInfo(ServerHttpResponse response, ResultCode resultCode) {
        switch (resultCode) {
            case JWT_SIGNATURE:
            case JWT_MALFORMED:
            case JWT_EXPIRED:
            case JWT_UNSUPPORTED:
            case JWT_ILLEGAL_ARGUMENT:
            case JWT_ERROR:
                response.setStatusCode(HttpStatus.UNAUTHORIZED);
                break;
            case JWT_FORBIDDEN:
                response.setStatusCode(HttpStatus.FORBIDDEN);
                break;
            default:
                response.setStatusCode(HttpStatus.BAD_REQUEST);
                break;
        }
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        response.getHeaders().set("Access-Control-Allow-Origin", "*");
        response.getHeaders().set("Cache-Control", "no-cache");
        String body = JacksonUtils.toJSONString(R.build(resultCode));
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }

}

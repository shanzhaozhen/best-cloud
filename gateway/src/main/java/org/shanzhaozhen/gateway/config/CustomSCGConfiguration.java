package org.shanzhaozhen.gateway.config;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Configuration
public class CustomSCGConfiguration {

    @Bean
    public BlockRequestHandler blockRequestHandler() {
        return (exchange, t) -> ServerResponse.status(444).contentType(MediaType.APPLICATION_JSON)
                .body(fromValue("SCS Sentinel block"));
    }

}

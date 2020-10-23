package org.shanzhaozhen.client.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "basic")
public interface TestClient {

    @GetMapping(value = "/test")
    String test();

}

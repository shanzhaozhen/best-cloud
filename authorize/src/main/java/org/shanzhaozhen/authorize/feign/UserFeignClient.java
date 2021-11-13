package org.shanzhaozhen.authorize.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "security")
public interface UserFeignClient {



}

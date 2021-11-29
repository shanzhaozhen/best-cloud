package org.shanzhaozhen.security.feign;

import org.shanzhaozhen.common.entity.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "security")
public interface UserFeignClient {

    @GetMapping("/user/oauth/{username}")
    R<User> loadUserByUsername(@PathVariable String username);

}

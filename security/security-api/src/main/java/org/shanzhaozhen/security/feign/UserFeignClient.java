package org.shanzhaozhen.security.feign;

import org.shanzhaozhen.common.entity.R;
import org.shanzhaozhen.security.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "security")
public interface UserFeignClient {

    @GetMapping("/user/oauth/{username}")
    R<UserDTO> getAuthUserByUsername(@PathVariable String username);

}

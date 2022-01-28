package org.shanzhaozhen.uaa.feign;

import org.shanzhaozhen.common.result.R;
import org.shanzhaozhen.uaa.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "uaa")
public interface UserFeignClient {

    @GetMapping("/user/oauth/{username}")
    R<UserDTO> loadUserByUsername(@PathVariable String username);

}

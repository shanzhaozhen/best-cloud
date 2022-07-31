package org.shanzhaozhen.uaa.feign;

import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "uaa")
public interface UserFeignClient {

    @GetMapping("/user/account/{username}")
    R<UserDTO> loadUserByUsername(@PathVariable("username") String username);

    @GetMapping("/user/phone/{phone}")
    R<UserDTO> loadUserByPhone(@PathVariable("phone") String phone);

}

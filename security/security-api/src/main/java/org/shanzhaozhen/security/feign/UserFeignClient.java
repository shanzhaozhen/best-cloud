package org.shanzhaozhen.security.feign;

import org.shanzhaozhen.common.entity.R;
import org.shanzhaozhen.security.vo.UserVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "security")
public interface UserFeignClient {


    @GetMapping("/user/info/{username}")
    R<UserVO> getUserByUsername(String username);

}

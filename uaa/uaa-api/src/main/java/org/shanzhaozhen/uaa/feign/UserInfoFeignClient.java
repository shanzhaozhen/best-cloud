package org.shanzhaozhen.uaa.feign;

import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.pojo.dto.UserInfoDTO;
import org.shanzhaozhen.uaa.pojo.form.UserInfoForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "uaa", contextId = "user-info")
public interface UserInfoFeignClient {

    @GetMapping("/user-info/{userId}")
    R<UserInfoDTO> getUserInfoByUserId(@PathVariable("userId") String userId);

    @PostMapping("/user-info")
    R<?> updateUserInfo(@RequestBody UserInfoForm userInfoForm);

}

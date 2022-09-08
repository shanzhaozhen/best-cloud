package org.shanzhaozhen.uaa.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.SocialInfo;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;
import org.shanzhaozhen.uaa.pojo.form.SocialUserBindForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "uaa", contextId = "social-user")
public interface SocialUserFeignClient {

    @GetMapping("/social/login/{username}")
    R<UserDTO> loadUserBySocial(@PathVariable("username") String username, @RequestParam("type") String type);
    @GetMapping("/social/info/{userId}")
    R<SocialInfo> getSocialInfo(@PathVariable("userId") String userId);

    @GetMapping("/social/unbind/{userId}")
    R<?> unbindSocial(@PathVariable("userId") String userId, @RequestParam("type") String type);

    @PostMapping("/social/github")
    R<?> updateGithubUser(@RequestBody GithubUser githubUser);

    @PostMapping("/social/github/bind")
    R<?> bindGithubUser(@RequestBody SocialUserBindForm<GithubUser> socialUserBindForm);

}

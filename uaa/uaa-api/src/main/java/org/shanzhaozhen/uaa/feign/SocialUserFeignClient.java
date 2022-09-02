package org.shanzhaozhen.uaa.feign;

import io.swagger.v3.oas.annotations.Operation;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;
import org.shanzhaozhen.uaa.pojo.form.SocialUserBindForm;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "uaa", contextId = "social-user")
public interface SocialUserFeignClient {


    @PostMapping("/social/github")
    R<?> updateGithubUser(@RequestBody GithubUser githubUser);

    @PostMapping("/social/github/bind")
    R<?> bindGithubUser(@RequestBody SocialUserBindForm socialUserBindForm);

}

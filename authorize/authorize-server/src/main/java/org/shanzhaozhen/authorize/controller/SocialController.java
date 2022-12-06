package org.shanzhaozhen.authorize.controller;

import feign.FeignException;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.utils.SecurityUtils;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.common.core.utils.JacksonUtils;
import org.shanzhaozhen.uaa.feign.SocialUserFeignClient;
import org.shanzhaozhen.uaa.pojo.dto.SocialInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-31
 * @Description:
 */
@RestController
@RequiredArgsConstructor
public class SocialController {

    public static final String GET_SOCIAL_INFO = "/social/info";
    public static final String UNBIND_SOCIAL_INFO = "/social/unbind";

    private final SocialUserFeignClient socialUserFeignClient;

    @Operation(summary = "获取用户绑定信息")
    @GetMapping(GET_SOCIAL_INFO)
    public R<SocialInfo> getSocialInfo() {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Assert.hasText(currentUserId, "当前没有登陆用户或为匿名用户");
        return socialUserFeignClient.getSocialInfo(currentUserId);
    }

    @Operation(summary = "解绑用户信息")
    @GetMapping(UNBIND_SOCIAL_INFO)
    public R<?> unbindSocial(@RequestParam("type") String type) {
        String currentUserId = SecurityUtils.getCurrentUserId();
        Assert.hasText(currentUserId, "当前没有登陆用户或为匿名用户");
        try {
            return socialUserFeignClient.unbindSocial(currentUserId, type);
        } catch (FeignException e) {
            e.printStackTrace();
            return JacksonUtils.toPojo(e.contentUTF8(), R.class);
        }
    }

}

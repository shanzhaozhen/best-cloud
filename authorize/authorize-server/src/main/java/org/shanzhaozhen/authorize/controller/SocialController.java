package org.shanzhaozhen.authorize.controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.service.OAuthUserSocialService;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.authorize.pojo.dto.SocialInfo;
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

    private final OAuthUserSocialService socialUserService;

    @Operation(summary = "获取用户绑定信息")
    @GetMapping(GET_SOCIAL_INFO)
    public R<SocialInfo> getSocialInfo() {
        return R.build(socialUserService::getCurrentSocialInfo);
    }

    @Operation(summary = "解绑用户信息")
    @GetMapping(UNBIND_SOCIAL_INFO)
    public R<?> unbindSocial(@RequestParam("type") String type) {
        socialUserService.unbindSocial(type);
        return R.ok();
    }

}

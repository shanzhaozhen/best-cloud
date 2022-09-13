package org.shanzhaozhen.uaa.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.mapper.RoleMapper;
import org.shanzhaozhen.uaa.mapper.UserMapper;
import org.shanzhaozhen.uaa.pojo.dto.SocialInfo;
import org.shanzhaozhen.uaa.pojo.dto.UserDTO;
import org.shanzhaozhen.uaa.pojo.entity.GithubUser;
import org.shanzhaozhen.uaa.pojo.form.SocialUserBindForm;
import org.shanzhaozhen.uaa.service.SocialUserService;
import org.springframework.web.bind.annotation.*;


@Tag(name = "SocialUser", description = "社区用户接口")
@RestController
@RequiredArgsConstructor
public class SocialUserController {

    private final SocialUserService socialUserService;

    public static final String GET_SOCIAL_INFO = "/social/info/{userId}";
    public static final String BIND_SOCIAL_INFO = "/social/bind";
    public static final String UNBIND_SOCIAL_INFO = "/social/unbind/{userId}";
    public static final String UPDATE_GITHUB_USER = "/social/github";
    public static final String BIND_GITHUB_USER = "/social/github/bind";
    public static final String LOGIN_SOCIAL = "/social/login/{username}";

    @Operation(summary = "获取用户绑定信息")
    @GetMapping(GET_SOCIAL_INFO)
    public R<SocialInfo> getSocialInfo(@PathVariable("userId") String userId) {
        return R.build(() -> socialUserService.getSocialInfo(userId));
    }

    @Operation(summary = "第三方账号绑定")
    @GetMapping(BIND_SOCIAL_INFO)
    public R<?> bindSocialUser(@RequestParam("userId") String userId, @RequestParam("socialUsername") String socialUsername, @RequestParam("socialType") String socialType) {
        socialUserService.bindSocialUser(userId, socialUsername, socialType);
        return R.ok();
    }

    @Operation(summary = "解绑第三方账号")
    @GetMapping(UNBIND_SOCIAL_INFO)
    public R<?> unbindSocial(@PathVariable("userId") String userId, @RequestParam("type") String type) {
        socialUserService.unbindSocial(userId, type);
        return R.ok();
    }

    @Operation(summary = "更新github用户信息")
    @PostMapping(UPDATE_GITHUB_USER)
    public R<?> updateGithubUser(@RequestBody GithubUser githubUser) {
        socialUserService.updateGithubUser(githubUser);
        return R.ok();
    }

    @Operation(summary = "通过父级ID获取菜单列表")
    @PostMapping(BIND_GITHUB_USER)
    public R<?> bindGithubUser(@RequestBody SocialUserBindForm<GithubUser> socialUserBindForm) {
        socialUserService.bindGithubUser(socialUserBindForm);
        return R.ok();
    }

    @Operation(summary = "通过父级ID获取菜单列表")
    @GetMapping(LOGIN_SOCIAL)
    R<UserDTO> loadUserBySocial(@PathVariable("username") String username, @RequestParam("type") String type) {
        return R.build(() -> socialUserService.loadUserBySocial(username, type));
    }

}

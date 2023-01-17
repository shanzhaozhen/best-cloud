package org.shanzhaozhen.oauth.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.common.core.entity.BasePageParams;
import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.oauth.converter.OAuth2UserConverter;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2UserForm;
import org.shanzhaozhen.oauth.pojo.vo.OAuth2UserVO;
import org.shanzhaozhen.oauth.service.OAuth2UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "OAuth2UserController", description = "oauth2用户管理接口")
@RestController
@RequiredArgsConstructor
public class OAuth2UserController {

    private static final String GET_USER_PAGE = "/user/page";
    private static final String GET_USER_BY_ID = "/user/{userId}";
    private static final String ADD_USER = "/user";
    private static final String UPDATE_USER = "/user";
    private static final String DELETE_USER = "/user/{userId}";
    private static final String BATCH_DELETE_USER = "/user";

    private final OAuth2UserService oauth2UserService;

    @Operation(summary = "获取用户信息（分页）")
    @PostMapping(GET_USER_PAGE)
    public R<Page<OAuth2UserVO>> getOAuth2UserPage(@RequestBody BasePageParams<OAuth2UserDTO> pageParams) {
        return R.build(() -> OAuth2UserConverter.toVO(oauth2UserService.getOAuth2UserPage(pageParams.getPage(), pageParams.getKeyword())));
    }

    @Operation(summary = "获取用户信息（通过用户id）")
    @GetMapping(GET_USER_BY_ID)
    public R<OAuth2UserVO> getOAuth2UserById(@Parameter(description = "用户id", example = "1") @PathVariable("userId") String userId) {
        return R.build(() -> OAuth2UserConverter.toVO(oauth2UserService.getOAuth2UserById(userId)));
    }

    @Operation(summary = "添加用户接口")
    @PostMapping(ADD_USER)
    public R<String> addOAuth2User(@RequestBody @Validated({Insert.class}) OAuth2UserForm userForm) {
        return R.build(() -> oauth2UserService.addOAuth2User(OAuth2UserConverter.toDTO(userForm)));
    }

    @Operation(summary = "更新用户接口")
    @PutMapping(UPDATE_USER)
    public R<String> updateOAuth2User(@RequestBody @Validated({Update.class}) OAuth2UserForm userForm) {
        return R.build(() -> oauth2UserService.updateOAuth2User(OAuth2UserConverter.toDTO(userForm)));
    }

    @Operation(summary = "删除用户接口")
    @DeleteMapping(DELETE_USER)
    public R<String> deleteOAuth2User(@Parameter(description = "用户id", example = "[1, 2]") @PathVariable("userId") String userId) {
        return R.build(() -> oauth2UserService.deleteOAuth2User(userId));
    }

    @Operation(summary = "批量删除用户接口")
    @DeleteMapping(BATCH_DELETE_USER)
    public R<List<String>> batchDeleteOAuth2User(@Parameter(description = "用户id", example = "[1, 2]") @RequestBody List<String> userIds) {
        return R.build(() -> oauth2UserService.batchDeleteOAuth2User(userIds));
    }

}

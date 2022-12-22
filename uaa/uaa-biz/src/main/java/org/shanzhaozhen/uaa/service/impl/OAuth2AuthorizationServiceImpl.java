package org.shanzhaozhen.uaa.service.impl;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.constant.OAuth2ParameterNames;
import org.shanzhaozhen.uaa.mapper.OAuth2AuthorizationMapper;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationDTO;
import org.shanzhaozhen.uaa.pojo.entity.OAuth2AuthorizationDO;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.service.OAuth2AuthorizationService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@RestController
@Service
@RequiredArgsConstructor
public class OAuth2AuthorizationServiceImpl implements OAuth2AuthorizationService {

    private static final String GET_OAUTH2_AUTHORIZATION_BY_ID = "/ws/oauth2/authorization";
    private static final String GET_OAUTH2_AUTHORIZATION_BY_TOKEN = "/ws/oauth2/authorization";
    private static final String SAVE_OAUTH2_AUTHORIZATION = "/ws/oauth2/authorization";
    private static final String DELETE_OAUTH2_AUTHORIZATION_BY_ID = "/ws/oauth2/authorization/{id}";

    private final OAuth2AuthorizationMapper oauth2AuthorizationMapper;

    @Override
    @Operation(summary = "通过 id 获取用户授权信息")
    @GetMapping(value = GET_OAUTH2_AUTHORIZATION_BY_ID, params = { "id" })
    public OAuth2AuthorizationDTO getOAuth2AuthorizationById(@RequestParam("id") String id) {
        return oauth2AuthorizationMapper.getOAuth2AuthorizationById(id);
    }

    @Override
    @Operation(summary = "通过 token 获取用户授权信息")
    @GetMapping(value = GET_OAUTH2_AUTHORIZATION_BY_TOKEN, params = { "token" })
    public OAuth2AuthorizationDTO getOAuth2AuthorizationByToken(@RequestParam("token") String token, @RequestParam("tokenType") String tokenType) {
        if (tokenType == null) {
            return oauth2AuthorizationMapper.getOAuth2AuthorizationByToken(token);
        } else if (OAuth2ParameterNames.STATE.equals(tokenType)) {
            return oauth2AuthorizationMapper.getOAuth2AuthorizationByState(token);
        } else if (OAuth2ParameterNames.CODE.equals(tokenType)) {
            return oauth2AuthorizationMapper.getOAuth2AuthorizationByAuthorizationCode(token);
        } else if (OAuth2ParameterNames.ACCESS_TOKEN.equals(tokenType)) {
            return oauth2AuthorizationMapper.getOAuth2AuthorizationByAccessToken(token);
        } else if (OAuth2ParameterNames.REFRESH_TOKEN.equals(tokenType)) {
            return oauth2AuthorizationMapper.getOAuth2AuthorizationByRefreshToken(token);
        }

        return null;
    }

    @Operation(summary = "保存用户授权信息")
    @PostMapping(value = SAVE_OAUTH2_AUTHORIZATION)
    public void saveOAuth2Authorization(@RequestBody OAuth2AuthorizationDTO oauth2AuthorizationDTO) {
        OAuth2AuthorizationDO oauth2Authorization = this.oauth2AuthorizationMapper.selectById(oauth2AuthorizationDTO.getId());
        if (oauth2Authorization == null) {
            oauth2Authorization = new OAuth2AuthorizationDO();
            BeanUtils.copyProperties(oauth2AuthorizationDTO, oauth2Authorization);
            this.oauth2AuthorizationMapper.insert(oauth2Authorization);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2AuthorizationDTO, oauth2Authorization);
            this.oauth2AuthorizationMapper.updateById(oauth2Authorization);
        }
    }

    @Operation(summary = "通过 id 删除用户授权信息")
    @DeleteMapping(value = DELETE_OAUTH2_AUTHORIZATION_BY_ID)
    public void deleteOAuth2AuthorizationById(@PathVariable("id") String id) {
        this.oauth2AuthorizationMapper.deleteById(id);
    }

}

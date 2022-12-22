package org.shanzhaozhen.uaa.service.impl;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.mapper.OAuth2AuthorizationConsentMapper;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.shanzhaozhen.uaa.pojo.entity.OAuth2AuthorizationConsentDO;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.service.OAuth2AuthorizationConsentService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: 如果是授权码的流程，可能客户端申请了多个权限，比如：获取用户信息、修改用户信息。
 *               此Service处理的是用户给这个客户端哪些权限，比如只给获取用户信息的权限
 */
@RestController
@Service
@RequiredArgsConstructor
public class OAuth2AuthorizationConsentServiceImpl implements OAuth2AuthorizationConsentService {

    private static final String GET_OAUTH2_AUTHORIZATION_CONSENT = "/ws/oauth2/authorization-consent";
    private static final String SAVE_OAUTH2_AUTHORIZATION_CONSENT = "/ws/oauth2/authorization-consent";
    private static final String DELETE_OAUTH2_AUTHORIZATION_CONSENT = "/ws/oauth2/authorization-consent";


    private final OAuth2AuthorizationConsentMapper oauth2AuthorizationConsentMapper;

    @Override
    @Operation(summary = "获取用户授权范围信息")
    @GetMapping(GET_OAUTH2_AUTHORIZATION_CONSENT)
    public OAuth2AuthorizationConsentDTO getOAuth2AuthorizationConsent(@RequestParam("registeredClientId") String registeredClientId, @RequestParam("principalName") String principalName) {
        OAuth2AuthorizationConsentDO oauth2AuthorizationConsentDO = this.oauth2AuthorizationConsentMapper.findOAuth2AuthorizationConsent(registeredClientId, principalName);
        if (oauth2AuthorizationConsentDO != null) {
            OAuth2AuthorizationConsentDTO oauth2AuthorizationConsentDTO = new OAuth2AuthorizationConsentDTO();
            BeanUtils.copyProperties(oauth2AuthorizationConsentDO, oauth2AuthorizationConsentDTO);
            return oauth2AuthorizationConsentDTO;
        }
        return null;
    }

    @Override
    @Operation(summary = "保存用户授权范围信息")
    @PostMapping(SAVE_OAUTH2_AUTHORIZATION_CONSENT)
    @Transactional
    public void addOrUpdateOAuth2AuthorizationConsent(@RequestBody OAuth2AuthorizationConsentDTO oauth2AuthorizationConsentDTO) {
        Assert.hasText(oauth2AuthorizationConsentDTO.getRegisteredClientId(), "客户端ID不能为空");
        Assert.hasText(oauth2AuthorizationConsentDTO.getPrincipalName(), "授权的用户名不能为空");
        OAuth2AuthorizationConsentDO oauth2AuthorizationConsentDO =
                this.oauth2AuthorizationConsentMapper.findOAuth2AuthorizationConsent(oauth2AuthorizationConsentDTO.getRegisteredClientId(), oauth2AuthorizationConsentDTO.getPrincipalName());
        if (oauth2AuthorizationConsentDO == null) {
            oauth2AuthorizationConsentDO = new OAuth2AuthorizationConsentDO();
            BeanUtils.copyProperties(oauth2AuthorizationConsentDTO, oauth2AuthorizationConsentDO);
            oauth2AuthorizationConsentMapper.insert(oauth2AuthorizationConsentDO);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2AuthorizationConsentDTO, oauth2AuthorizationConsentDO);
            oauth2AuthorizationConsentMapper.updateById(oauth2AuthorizationConsentDO);
        }
    }

    @Override
    @Operation(summary = "删除用户授权范围信息")
    @DeleteMapping(DELETE_OAUTH2_AUTHORIZATION_CONSENT)
    @Transactional
    public void deleteOAuth2AuthorizationConsent(@RequestParam("registeredClientId") String registeredClientId, @RequestParam("principalName") String principalName) {
        oauth2AuthorizationConsentMapper.deleteOAuth2AuthorizationConsent(registeredClientId, principalName);
    }

}

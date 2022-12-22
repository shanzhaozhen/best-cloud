package org.shanzhaozhen.uaa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.mapper.OAuth2RegisteredClientMapper;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.uaa.pojo.entity.OAuth2RegisteredClientDO;
import org.shanzhaozhen.uaa.pojo.form.OAuth2ClientSettingsForm;
import org.shanzhaozhen.uaa.pojo.form.OAuth2RegisteredClientForm;
import org.shanzhaozhen.uaa.pojo.form.OAuth2TokenSettingsForm;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.service.OAuth2ClientSettingsService;
import org.shanzhaozhen.uaa.service.OAuth2RegisteredClientService;
import org.shanzhaozhen.uaa.service.OAuth2TokenSettingsService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@RestController
@Service
@RequiredArgsConstructor
public class OAuth2RegisteredClientServiceImpl implements OAuth2RegisteredClientService {

    private static final String GET_OAUTH2_REGISTERED_CLIENT_BY_ID = "/ws/oauth2/registered-client";
    private static final String GET_OAUTH2_REGISTERED_CLIENT_BY_CLIENT_ID = "/ws/oauth2/registered-client";
    private static final String SAVE_OAUTH2_REGISTERED_CLIENT = "/ws/oauth2/registered-client";

    private final OAuth2RegisteredClientMapper oauth2RegisteredClientMapper;
    private final OAuth2ClientSettingsService oauth2ClientSettingsService;
    private final OAuth2TokenSettingsService oauth2TokenSettingsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientDTO> page, String keyword) {
        return oauth2RegisteredClientMapper.getOAuth2RegisteredClientPage(page, keyword);
    }

    @Override
    @Operation(summary = "通过 id 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_ID, params = { "id" })
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(@RequestParam("id") String id) {
        OAuth2RegisteredClientDO oauth2RegisteredClientDO = oauth2RegisteredClientMapper.selectById(id);

        if (oauth2RegisteredClientDO != null) {
            OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
            BeanUtils.copyProperties(oauth2RegisteredClientDO, oauth2RegisteredClientDTO);
            return this.assembleOAuth2RegisteredClient(oauth2RegisteredClientDTO);
        }
        return null;
    }

    @Override
    @Operation(summary = "通过 clientId 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_CLIENT_ID, params = { "clientId" })
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(@RequestParam("clientId") String clientId) {
        OAuth2RegisteredClientDO oauth2RegisteredClientDO = oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oauth2RegisteredClientDO != null) {
            OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
            BeanUtils.copyProperties(oauth2RegisteredClientDO, oauth2RegisteredClientDTO);
            OAuth2RegisteredClientDTO oauth2RegisteredClientDTO1 = this.assembleOAuth2RegisteredClient(oauth2RegisteredClientDTO);
            return oauth2RegisteredClientDTO1;
        }
        return null;
    }

    @Override
    public OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        Assert.notNull(oauth2RegisteredClientDTO, "客户端信息不能为空");
        Assert.hasText(oauth2RegisteredClientDTO.getId(), "客户端id不能为空");
        Assert.hasText(oauth2RegisteredClientDTO.getClientId(), "客户端id不能为空");

        OAuth2ClientSettingsDTO clientSettings =
                oauth2ClientSettingsService.getOAuth2ClientSettingsByRegisteredClientId(oauth2RegisteredClientDTO.getId());
        OAuth2TokenSettingsDTO tokenSetting =
                oauth2TokenSettingsService.getOAuth2TokenSettingsByRegisteredClientId(oauth2RegisteredClientDTO.getId());

        return oauth2RegisteredClientDTO
                .setClientSettings(clientSettings)
                .setTokenSettings(tokenSetting);
    }

    @Override
    public void addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientForm oauth2RegisteredClientForm) {
        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
        BeanUtils.copyProperties(oauth2RegisteredClientForm, oauth2RegisteredClientDTO);

        OAuth2ClientSettingsForm clientSettings = oauth2RegisteredClientForm.getClientSettings();
        OAuth2TokenSettingsForm tokenSettings = oauth2RegisteredClientForm.getTokenSettings();

        oauth2RegisteredClientDTO.setClientSettings(Optional.ofNullable(clientSettings).map(o -> {
            OAuth2ClientSettingsDTO oauth2ClientSettingsDTO = new OAuth2ClientSettingsDTO();
            BeanUtils.copyProperties(clientSettings, oauth2ClientSettingsDTO);
            return oauth2ClientSettingsDTO;
        }).orElse(null));

        oauth2RegisteredClientDTO.setTokenSettings(Optional.ofNullable(tokenSettings).map(o -> {
            OAuth2TokenSettingsDTO oauth2TokenSettingsDTO = new OAuth2TokenSettingsDTO();
            BeanUtils.copyProperties(tokenSettings, oauth2TokenSettingsDTO);
            return oauth2TokenSettingsDTO;
        }).orElse(null));

        this.addOrUpdateOAuth2RegisteredClient(oauth2RegisteredClientDTO);
    }

    @Override
    @Transactional
    @Operation(summary = "保存 OAuth2 客户端信息")
    @PostMapping( SAVE_OAUTH2_REGISTERED_CLIENT)
    public void addOrUpdateOAuth2RegisteredClient(@RequestBody OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        String clientId = oauth2RegisteredClientDTO.getClientId();
        OAuth2RegisteredClientDO oauth2RegisteredClient;
        if (StringUtils.hasText(clientId)) {
            oauth2RegisteredClient = this.oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        } else {
            clientId = UUID.randomUUID().toString();
            oauth2RegisteredClient = null;
        }

        if (oauth2RegisteredClient == null) {
            oauth2RegisteredClient = new OAuth2RegisteredClientDO();
            BeanUtils.copyProperties(oauth2RegisteredClientDTO, oauth2RegisteredClient);
            oauth2RegisteredClient.setClientId(clientId);
            String encodePassword = passwordEncoder.encode(oauth2RegisteredClientDTO.getClientSecret());
            oauth2RegisteredClient.setClientSecret(encodePassword);
            this.oauth2RegisteredClientMapper.insert(oauth2RegisteredClient);
        } else {
            if (StringUtils.hasText(oauth2RegisteredClientDTO.getClientSecret())) {
                String encodePassword = passwordEncoder.encode(oauth2RegisteredClientDTO.getClientSecret());
                CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2RegisteredClientDTO, oauth2RegisteredClient);
                oauth2RegisteredClient.setClientSecret(encodePassword);
            } else {        // 为空不修改密码
                CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oauth2RegisteredClientDTO, oauth2RegisteredClient);
            }
            this.oauth2RegisteredClientMapper.updateById(oauth2RegisteredClient);
        }
        if (oauth2RegisteredClientDTO.getClientSettings() != null) {
            oauth2ClientSettingsService.addOrUpdateOAuth2ClientSettings(oauth2RegisteredClient.getId(), oauth2RegisteredClientDTO.getClientSettings());
        }

        if (oauth2RegisteredClientDTO.getTokenSettings() != null) {
            oauth2TokenSettingsService.addOrUpdateOAuth2TokenSettings(oauth2RegisteredClient.getId(), oauth2RegisteredClientDTO.getTokenSettings());
        }

    }

    @Override
    @Transactional
    public void deleteOAuth2RegisteredClientById(String id) {
        this.oauth2RegisteredClientMapper.deleteOAuth2RegisteredClientById(id);
        oauth2ClientSettingsService.deleteOAuth2ClientSettingsByRegisteredClientId(id);
        oauth2TokenSettingsService.deleteOAuth2TokenSettingsByRegisteredClientId(id);
    }

    @Override
    @Transactional
    public void deleteOAuth2RegisteredClientByClientId(String clientId) {
        OAuth2RegisteredClientDO oauth2RegisteredClient = this.oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oauth2RegisteredClient != null) {
            this.deleteOAuth2RegisteredClientById(oauth2RegisteredClient.getId());
        }
    }



}

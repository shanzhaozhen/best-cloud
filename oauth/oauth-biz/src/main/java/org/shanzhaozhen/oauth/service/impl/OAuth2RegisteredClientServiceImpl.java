package org.shanzhaozhen.oauth.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.oauth.mapper.OAuth2RegisteredClientMapper;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.oauth.pojo.entity.OAuth2RegisteredClientDO;
import org.shanzhaozhen.oauth.service.OAuth2ClientSettingsService;
import org.shanzhaozhen.oauth.service.OAuth2RegisteredClientService;
import org.shanzhaozhen.oauth.service.OAuth2TokenSettingsService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

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

    private final OAuth2RegisteredClientMapper oauth2RegisteredClientMapper;
    private final OAuth2ClientSettingsService oauth2ClientSettingsService;
    private final OAuth2TokenSettingsService oauth2TokenSettingsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Page<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientDTO> page, String keyword) {
        return oauth2RegisteredClientMapper.getOAuth2RegisteredClientPage(page, keyword);
    }

    @Override
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
    @Transactional
    public void addOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        String clientId = oauth2RegisteredClientDTO.getClientId();
        if (StringUtils.hasText(clientId)) {
            OAuth2RegisteredClientDO oauth2RegisteredClientInDB = oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
            Assert.isNull(oauth2RegisteredClientInDB, "客户端id已被占用，请更改！");
        } else {
            oauth2RegisteredClientDTO.setClientId(UUID.randomUUID().toString());
        }
        OAuth2RegisteredClientDO oauth2RegisteredClient = new OAuth2RegisteredClientDO();
        BeanUtils.copyProperties(oauth2RegisteredClientDTO, oauth2RegisteredClient);
        String encodePassword = passwordEncoder.encode(oauth2RegisteredClientDTO.getClientSecret());
        oauth2RegisteredClient.setClientSecret(encodePassword);
        oauth2RegisteredClientMapper.insert(oauth2RegisteredClient);
        
        if (oauth2RegisteredClientDTO.getClientSettings() != null) {
            oauth2ClientSettingsService.addOrUpdateOAuth2ClientSettings(oauth2RegisteredClient.getId(), oauth2RegisteredClientDTO.getClientSettings());
        }

        if (oauth2RegisteredClientDTO.getTokenSettings() != null) {
            oauth2TokenSettingsService.addOrUpdateOAuth2TokenSettings(oauth2RegisteredClient.getId(), oauth2RegisteredClientDTO.getTokenSettings());
        }
    }

    @Override
    @Transactional
    public void updateOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO) {
        String clientId = oauth2RegisteredClientDTO.getClientId();
        if (StringUtils.hasText(clientId)) {
            OAuth2RegisteredClientDO oauth2RegisteredClientInDB = oauth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
            Assert.isTrue(oauth2RegisteredClientInDB == null || oauth2RegisteredClientInDB.equals(oauth2RegisteredClientInDB.getId()), "客户端id已被占用，请更改！");
        } else {
            oauth2RegisteredClientDTO.setClientId(UUID.randomUUID().toString());
        }

        OAuth2RegisteredClientDO oauth2RegisteredClient = oauth2RegisteredClientMapper.selectById(oauth2RegisteredClientDTO.getId());
        // 为空不修改密码
        if (StringUtils.hasText(oauth2RegisteredClientDTO.getClientSecret())) {
            String encodePassword = passwordEncoder.encode(oauth2RegisteredClientDTO.getClientSecret());
            oauth2RegisteredClientDTO.setClientSecret(encodePassword);
        } else {
            oauth2RegisteredClientDTO.setClientSecret(oauth2RegisteredClient.getClientSecret());
        }
        CustomBeanUtils.copyPropertiesExcludeMeta(oauth2RegisteredClientDTO, oauth2RegisteredClient);
        oauth2RegisteredClientMapper.updateById(oauth2RegisteredClient);

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

}

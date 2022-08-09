package org.shanzhaozhen.authorize.service.impl;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2RegisteredClientConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2RegisteredClientMapper;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RegisteredClientDO;
import org.shanzhaozhen.authorize.pojo.vo.OAuth2RegisteredClientVO;
import org.shanzhaozhen.authorize.service.*;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class CustomOAuth2RegisteredClientServiceImpl implements CustomOAuth2RegisteredClientService {

    private final OAuth2RegisteredClientMapper oAuth2RegisteredClientMapper;
    private final OAuth2ClientSettingsService oAuth2ClientSettingsService;
    private final OAuth2TokenSettingsService oAuth2TokenSettingsService;

    @Override
    public Page<OAuth2RegisteredClientVO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientVO> page, String keyword) {
        return oAuth2RegisteredClientMapper.getOAuth2RegisteredClientPage(page, keyword);
    }

    @Override
    public RegisteredClient getOAuth2RegisteredClientById(String id) {
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = oAuth2RegisteredClientMapper.selectById(id);

        if (oAuth2RegisteredClientDO != null) {
            return this.assembleOAuth2RegisteredClient(oAuth2RegisteredClientDO);
        }
        return null;
    }

    @Override
    public RegisteredClient getOAuth2RegisteredClientByClientId(String clientId) {
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = oAuth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oAuth2RegisteredClientDO != null) {
            return this.assembleOAuth2RegisteredClient(oAuth2RegisteredClientDO);
        }
        return null;
    }

    @Override
    public RegisteredClient assembleOAuth2RegisteredClient(OAuth2RegisteredClientDO oAuth2RegisteredClientDO) {
        Assert.notNull(oAuth2RegisteredClientDO, "客户端信息不能为空");
        Assert.notEmpty(oAuth2RegisteredClientDO.getId(), "客户端id不能为空");
        Assert.notEmpty(oAuth2RegisteredClientDO.getClientId(), "客户端id不能为空");

        ClientSettings clientSettings =
                oAuth2ClientSettingsService.getOAuth2ClientSettingsByRegisteredClientId(oAuth2RegisteredClientDO.getId());
        TokenSettings tokenSetting =
                oAuth2TokenSettingsService.getOAuth2TokenSettingsByRegisteredClientId(oAuth2RegisteredClientDO.getId());

        return OAuth2RegisteredClientConverter.toRegisteredClient(oAuth2RegisteredClientDO, clientSettings, tokenSetting);
    }

    @Override
    @Transactional
    public String addOrUpdateOAuth2RegisteredClient(RegisteredClient registeredClient) {
        String clientId = registeredClient.getClientId();
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = this.oAuth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oAuth2RegisteredClientDO == null) {
            oAuth2RegisteredClientDO = OAuth2RegisteredClientConverter.toDO(registeredClient);
            this.oAuth2RegisteredClientMapper.insert(oAuth2RegisteredClientDO);
        } else {
            OAuth2RegisteredClientDO registeredClientDO = OAuth2RegisteredClientConverter.toDO(registeredClient);
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(registeredClientDO, oAuth2RegisteredClientDO);
            this.oAuth2RegisteredClientMapper.updateById(oAuth2RegisteredClientDO);
        }
        oAuth2ClientSettingsService.addOrUpdateOAuth2ClientSettings(oAuth2RegisteredClientDO.getId(), registeredClient.getClientSettings());
        oAuth2TokenSettingsService.addOrUpdateOAuth2TokenSettings(oAuth2RegisteredClientDO.getId(), registeredClient.getTokenSettings());
        return oAuth2RegisteredClientDO.getId();
    }

    @Override
    @Transactional
    public void deleteOAuth2RegisteredClientById(String id) {
        this.oAuth2RegisteredClientMapper.deleteOAuth2RegisteredClientById(id);
        oAuth2ClientSettingsService.deleteOAuth2ClientSettingsByRegisteredClientId(id);
        oAuth2TokenSettingsService.deleteOAuth2TokenSettingsByRegisteredClientId(id);
    }

    @Override
    public void deleteOAuth2RegisteredClientByClientId(String clientId) {
        OAuth2RegisteredClientDO oAuth2RegisteredClient = this.oAuth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oAuth2RegisteredClient != null) {
            this.deleteOAuth2RegisteredClientById(oAuth2RegisteredClient.getId());
        }
    }

    @Override
    public void save(RegisteredClient registeredClient) {
        this.addOrUpdateOAuth2RegisteredClient(registeredClient);
    }

    @Override
    public RegisteredClient findById(String id) {
        return this.getOAuth2RegisteredClientById(id);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return this.getOAuth2RegisteredClientByClientId(clientId);
    }
}

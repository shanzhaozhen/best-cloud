package org.shanzhaozhen.uaa.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.uaa.mapper.OAuth2RegisteredClientMapper;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2ClientSettingsDTO;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2TokenSettingsDTO;
import org.shanzhaozhen.uaa.pojo.entity.OAuth2RegisteredClientDO;
import org.shanzhaozhen.uaa.pojo.vo.OAuth2RegisteredClientVO;
import org.shanzhaozhen.common.core.utils.CustomBeanUtils;
import org.shanzhaozhen.uaa.service.OAuth2ClientSettingsService;
import org.shanzhaozhen.uaa.service.OAuth2RegisteredClientService;
import org.shanzhaozhen.uaa.service.OAuth2TokenSettingsService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;


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

    private final OAuth2RegisteredClientMapper oAuth2RegisteredClientMapper;
    private final OAuth2ClientSettingsService oAuth2ClientSettingsService;
    private final OAuth2TokenSettingsService oAuth2TokenSettingsService;

    @Override
    public Page<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientDTO> page, String keyword) {
        return oAuth2RegisteredClientMapper.getOAuth2RegisteredClientPage(page, keyword);
    }

    @Override
    @Operation(summary = "通过 id 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_ID, params = { "id" })
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(@RequestParam("id") String id) {
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = oAuth2RegisteredClientMapper.selectById(id);

        if (oAuth2RegisteredClientDO != null) {
            OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
            BeanUtils.copyProperties(oAuth2RegisteredClientDO, oAuth2RegisteredClientDTO);
            return this.assembleOAuth2RegisteredClient(oAuth2RegisteredClientDTO);
        }
        return null;
    }

    @Override
    @Operation(summary = "通过 clientId 获取 OAuth2 客户端信息")
    @GetMapping(value = GET_OAUTH2_REGISTERED_CLIENT_BY_CLIENT_ID, params = { "clientId" })
    public OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(@RequestParam("clientId") String clientId) {
        OAuth2RegisteredClientDO oAuth2RegisteredClientDO = oAuth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oAuth2RegisteredClientDO != null) {
            OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO = new OAuth2RegisteredClientDTO();
            BeanUtils.copyProperties(oAuth2RegisteredClientDO, oAuth2RegisteredClientDTO);
            OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO1 = this.assembleOAuth2RegisteredClient(oAuth2RegisteredClientDTO);
            return oAuth2RegisteredClientDTO1;
        }
        return null;
    }

    @Override
    public OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO) {
        Assert.notNull(oAuth2RegisteredClientDTO, "客户端信息不能为空");
        Assert.hasText(oAuth2RegisteredClientDTO.getId(), "客户端id不能为空");
        Assert.hasText(oAuth2RegisteredClientDTO.getClientId(), "客户端id不能为空");

        OAuth2ClientSettingsDTO clientSettings =
                oAuth2ClientSettingsService.getOAuth2ClientSettingsByRegisteredClientId(oAuth2RegisteredClientDTO.getId());
        OAuth2TokenSettingsDTO tokenSetting =
                oAuth2TokenSettingsService.getOAuth2TokenSettingsByRegisteredClientId(oAuth2RegisteredClientDTO.getId());

        return oAuth2RegisteredClientDTO
                .setClientSettings(clientSettings)
                .setTokenSettings(tokenSetting);
    }

    @Override
    @Transactional
    @Operation(summary = "保存 OAuth2 客户端信息")
    @PostMapping( SAVE_OAUTH2_REGISTERED_CLIENT)
    public void addOrUpdateOAuth2RegisteredClient(@RequestBody OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO) {
        OAuth2RegisteredClientDO oAuth2RegisteredClient = this.oAuth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(oAuth2RegisteredClientDTO.getClientId());
        if (oAuth2RegisteredClient == null) {
            oAuth2RegisteredClient = new OAuth2RegisteredClientDO();
            BeanUtils.copyProperties(oAuth2RegisteredClientDTO, oAuth2RegisteredClient);
            this.oAuth2RegisteredClientMapper.insert(oAuth2RegisteredClient);
        } else {
            CustomBeanUtils.copyPropertiesExcludeMetaAndNull(oAuth2RegisteredClientDTO, oAuth2RegisteredClient);
            this.oAuth2RegisteredClientMapper.updateById(oAuth2RegisteredClient);
        }
        oAuth2ClientSettingsService.addOrUpdateOAuth2ClientSettings(oAuth2RegisteredClient.getId(), oAuth2RegisteredClientDTO.getClientSettings());
        oAuth2TokenSettingsService.addOrUpdateOAuth2TokenSettings(oAuth2RegisteredClient.getId(), oAuth2RegisteredClientDTO.getTokenSettings());
    }

    @Override
    @Transactional
    public void deleteOAuth2RegisteredClientById(String id) {
        this.oAuth2RegisteredClientMapper.deleteOAuth2RegisteredClientById(id);
        oAuth2ClientSettingsService.deleteOAuth2ClientSettingsByRegisteredClientId(id);
        oAuth2TokenSettingsService.deleteOAuth2TokenSettingsByRegisteredClientId(id);
    }

    @Override
    @Transactional
    public void deleteOAuth2RegisteredClientByClientId(String clientId) {
        OAuth2RegisteredClientDO oAuth2RegisteredClient = this.oAuth2RegisteredClientMapper.getOAuth2RegisteredClientByClientId(clientId);
        if (oAuth2RegisteredClient != null) {
            this.deleteOAuth2RegisteredClientById(oAuth2RegisteredClient.getId());
        }
    }



}

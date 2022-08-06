package org.shanzhaozhen.authorize.service.impl;


import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2RedirectUriConverter;
import org.shanzhaozhen.authorize.mapper.OAuth2RedirectUriMapper;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2RedirectUriDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RedirectUriDO;
import org.shanzhaozhen.authorize.service.OAuth2RedirectUriService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description: oauth2客户端重定向uri表 服务实现类
 */
@Service
@RequiredArgsConstructor
public class OAuth2RedirectUriServiceImpl implements OAuth2RedirectUriService {

    private final OAuth2RedirectUriMapper oAuth2RedirectUriMapper;

    @Override
    public List<OAuth2RedirectUriDTO> getOAuth2RedirectUrisByRegisteredClientId(String registeredClientId) {
        return oAuth2RedirectUriMapper.getOAuth2RedirectUrisByRegisteredClientId(registeredClientId);
    }

    @Override
    public void addOAuth2RedirectUris(String clientId, Set<OAuth2RedirectUriDTO> redirectUris) {
        if (CollectionUtils.isEmpty(redirectUris)) return;

        for (OAuth2RedirectUriDTO redirectUri : redirectUris) {
            OAuth2RedirectUriDO oAuth2RedirectUriDO = OAuth2RedirectUriConverter.toDO(redirectUri);
            this.oAuth2RedirectUriMapper.insert(oAuth2RedirectUriDO);
        }
    }

    @Override
    public void updateOAuth2RedirectUris(String registeredClientId, Set<OAuth2RedirectUriDTO> redirectUris) {
        this.deleteOAuth2RedirectUrisByRegisteredClientId(registeredClientId);
        this.addOAuth2RedirectUris(registeredClientId, redirectUris);
    }

    @Override
    public void deleteOAuth2RedirectUrisByRegisteredClientId(String registeredClientId) {
        oAuth2RedirectUriMapper.deleteOAuth2RedirectUrisByRegisteredClientId(registeredClientId);
    }

}

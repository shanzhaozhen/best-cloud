package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2RegisteredClientConverter;
import org.shanzhaozhen.uaa.feign.OAuth2RegisteredClientClient;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2RegisteredClientDTO;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;


/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
@Service
@RequiredArgsConstructor
public class OAuth2RegisteredClientServiceImpl implements RegisteredClientRepository {

    private final OAuth2RegisteredClientClient oAuth2RegisteredClientClient;

    @Override
    public void save(RegisteredClient registeredClient) {
        oAuth2RegisteredClientClient.saveOAuth2RegisteredClient(OAuth2RegisteredClientConverter.toDTO(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
        OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO = oAuth2RegisteredClientClient.getOAuth2RegisteredClientById(id);
        return OAuth2RegisteredClientConverter.toRegisteredClient(oAuth2RegisteredClientDTO);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO = oAuth2RegisteredClientClient.getOAuth2RegisteredClientByClientId(clientId);
        return OAuth2RegisteredClientConverter.toRegisteredClient(oAuth2RegisteredClientDTO);
    }

}

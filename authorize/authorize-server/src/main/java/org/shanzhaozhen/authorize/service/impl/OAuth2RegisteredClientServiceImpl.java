package org.shanzhaozhen.authorize.service.impl;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.converter.OAuth2RegisteredClientConverter;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2RegisteredClientDTO;
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

    @Override
    public void save(RegisteredClient registeredClient) {
//        oauth2RegisteredClientClient.saveOAuth2RegisteredClient(OAuth2RegisteredClientConverter.toDTO(registeredClient));
    }

    @Override
    public RegisteredClient findById(String id) {
//        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = oauth2RegisteredClientClient.getOAuth2RegisteredClientById(id);
        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = null;
        return OAuth2RegisteredClientConverter.toRegisteredClient(oauth2RegisteredClientDTO);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
//        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = oauth2RegisteredClientClient.getOAuth2RegisteredClientByClientId(clientId);
        OAuth2RegisteredClientDTO oauth2RegisteredClientDTO = null;
        return OAuth2RegisteredClientConverter.toRegisteredClient(oauth2RegisteredClientDTO);
    }

}

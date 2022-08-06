package org.shanzhaozhen.authorize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2RegisteredClientDTO;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface CustomOAuth2RegisteredClientService extends RegisteredClientRepository {

    Page<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientDTO> page, String keyword);

    OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(String id);

    OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(String clientId);

    OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO);

    String addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO);

    void deleteOAuth2RegisteredClientById(String id);

    void deleteOAuth2RegisteredClientByClientId(String clientId);

}

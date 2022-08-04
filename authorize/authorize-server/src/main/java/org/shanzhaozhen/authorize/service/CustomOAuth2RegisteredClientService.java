package org.shanzhaozhen.authorize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.authorize.pojo.dto.Oauth2RegisteredClientDTO;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface CustomOAuth2RegisteredClientService extends RegisteredClientRepository {

    Page<Oauth2RegisteredClientDTO> getOauth2RegisteredClientPage(Page<Oauth2RegisteredClientDTO> page, String keyword);

    Oauth2RegisteredClientDTO getOauth2RegisteredClientById(String id);

    Oauth2RegisteredClientDTO getOauth2RegisteredClientByClientId(String clientId);

    Oauth2RegisteredClientDTO assembleOauth2RegisteredClient(Oauth2RegisteredClientDTO oauth2RegisteredClientDTO);

    String addOrUpdateOauth2RegisteredClient(Oauth2RegisteredClientDTO oauth2RegisteredClientDTO);

    String deleteOauth2RegisteredClientById(String id);

    String deleteOauth2RegisteredClientByClientId(String id);

}

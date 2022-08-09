package org.shanzhaozhen.authorize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.authorize.pojo.vo.OAuth2RegisteredClientVO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2RegisteredClientDO;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface CustomOAuth2RegisteredClientService extends RegisteredClientRepository {

    Page<OAuth2RegisteredClientVO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientVO> page, String keyword);

    RegisteredClient getOAuth2RegisteredClientById(String id);

    RegisteredClient getOAuth2RegisteredClientByClientId(String clientId);

    RegisteredClient assembleOAuth2RegisteredClient(OAuth2RegisteredClientDO oAuth2RegisteredClientDO);

    String addOrUpdateOAuth2RegisteredClient(RegisteredClient registeredClient);

    void deleteOAuth2RegisteredClientById(String id);

    void deleteOAuth2RegisteredClientByClientId(String clientId);

}

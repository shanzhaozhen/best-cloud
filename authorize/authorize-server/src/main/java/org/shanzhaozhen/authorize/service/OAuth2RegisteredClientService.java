package org.shanzhaozhen.authorize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2RegisteredClientDTO;
import org.shanzhaozhen.oauth.pojo.form.OAuth2RegisteredClientForm;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public interface OAuth2RegisteredClientService extends RegisteredClientRepository {

    /**
     * 通过 id 获取 oauth2 客户端信息
     * @param id
     * @return
     */
    OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(String id);

    /**
     * 通过客户端 id 获取 oauth2 客户端信息
     * @param clientId
     * @return
     */
    OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(String clientId);

    /**
     * 组装客户端信息（获得 clientSettings 及 tokenSettings）
     * @param oauth2RegisteredClientDTO
     * @return
     */
    OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO);

    /**
     * 添加或更新 OAuth2 客户端信息
     * @param oauth2RegisteredClientDTO
     */
    void addOrUpdateOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO);

    /**
         * 通过id删除客户端信息
         * @param id
         */
    void deleteOAuth2RegisteredClientById(String id);

}

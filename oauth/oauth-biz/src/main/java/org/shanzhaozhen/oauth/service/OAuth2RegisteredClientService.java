package org.shanzhaozhen.oauth.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2RegisteredClientDTO;

/**
 * @Author: shanzhaozhen
 * @Date: 2023-01-14
 * @Description:
 */
public interface OAuth2RegisteredClientService {

    /**
     * 通过关键字查找 oauth2 客户端信息分页结果
     * @param page
     * @param keyword
     * @return
     */
    Page<OAuth2RegisteredClientDTO> getOAuth2RegisteredClientPage(Page<OAuth2RegisteredClientDTO> page, String keyword);

    /**
     * 通过 id 获取 oauth2 客户端信息
     * @param registeredClientId
     * @return
     */
    OAuth2RegisteredClientDTO getOAuth2RegisteredClientByRegisteredClientId(String registeredClientId);

    /**
     * 组装客户端信息（获得 clientSettings 及 tokenSettings）
     * @param oauth2RegisteredClientDTO
     * @return
     */
    OAuth2RegisteredClientDTO assembleOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO);

    /**
     * 添加 OAuth2 客户端信息
     * @param oauth2RegisteredClientDTO
     */
    void addOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO);

    /**
     * 更新 OAuth2 客户端信息
     * @param oauth2RegisteredClientDTO
     */
    void updateOAuth2RegisteredClient(OAuth2RegisteredClientDTO oauth2RegisteredClientDTO);

    /**
     * 通过id删除客户端信息
     * @param registeredClientId
     */
    void deleteOAuth2RegisteredClientByRegisteredClientId(String registeredClientId);

}

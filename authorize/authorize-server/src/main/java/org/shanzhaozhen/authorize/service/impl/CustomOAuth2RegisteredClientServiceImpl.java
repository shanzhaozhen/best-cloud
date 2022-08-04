package org.shanzhaozhen.authorize.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.authorize.service.CustomOAuth2RegisteredClientService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-06-17
 * @Description:
 */
public class CustomOAuth2RegisteredClientServiceImpl implements CustomOAuth2RegisteredClientService {

    @Override
    public Page<RegisteredClient> page(Page<RegisteredClient> page, Long registeredClientId, String keyword) {
        return null;
    }

    @Override
    public void deleteById(String id) {

    }

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        return null;
    }
}

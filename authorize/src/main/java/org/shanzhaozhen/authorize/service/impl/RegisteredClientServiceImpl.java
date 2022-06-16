package org.shanzhaozhen.authorize.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.shanzhaozhen.authorize.service.RegisteredClientService;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

public class RegisteredClientServiceImpl implements RegisteredClientService {


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

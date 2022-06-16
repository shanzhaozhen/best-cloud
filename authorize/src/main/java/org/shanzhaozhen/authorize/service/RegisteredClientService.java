package org.shanzhaozhen.authorize.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public interface RegisteredClientService extends RegisteredClientRepository {

    Page<RegisteredClient> page(Page<RegisteredClient> page, Long registeredClientId, String keyword);

    void deleteById(String id);

}

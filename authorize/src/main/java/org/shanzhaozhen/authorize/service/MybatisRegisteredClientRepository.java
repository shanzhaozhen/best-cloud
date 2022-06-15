package org.shanzhaozhen.authorize.service;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

public interface MybatisRegisteredClientRepository extends RegisteredClientRepository {

        void save(RegisteredClient registeredClient);

        RegisteredClient findById(String id);

        RegisteredClient findByClientId(String clientId);

}

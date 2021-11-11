package org.shanzhaozhen.authorize.config.oauth2;
/*

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.sql.DataSource;

@Configuration
@EnableAuthorizationServer
@RequiredArgsConstructor
public class AuthorizationServerConfig1 extends AuthorizationServerConfigurerAdapter {

    private final DataSource dataSource;

    private final TokenStore tokenStore;

    private final AuthenticationManager authenticationManager;

    private final CustomUserDetailsService customUserDetailsService;


    */
/**
     * 定义令牌端点上的安全约束
     * @param security
     * @throws Exception
     *//*

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .allowFormAuthenticationForClients()        //如果使用表单认证则需要加上
                .checkTokenAccess("isAuthenticated()")      // oauth/check_token    公开(permitAll)/拦截(isAuthenticated)
                .tokenKeyAccess("isAuthenticated()");       // oauth/token_key  公开(permitAll)/拦截(isAuthenticated)
    }

    */
/**
     * 定义客户端详细信息服务的配置程序。可以初始化客户详细信息，或者您可以仅引用现有库。
     * @param clients
     * @throws Exception
     *//*

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        */
/**
         * client模式，没有用户的概念，直接与认证服务器交互，用配置中的客户端信息去申请accessToken，
         * 客户端有自己的client_id,client_secret对应于用户的username,password，而客户端也拥有自己的authorities，
         * 当采取client模式认证时，对应的权限也就是客户端自己的authorities
         *//*


        */
/**
         * password模式，自己本身有一套用户体系，在认证时需要带上自己的用户名和密码，以及客户端的client_id,client_secret
         * 此时，accessToken所包含的权限是用户本身的权限，而不是客户端的权限
         *//*



        clients.jdbc(dataSource)
//                .passwordEncoder(passwordEncoder)
        ;
    }

    */
/**
     * 定义授权和令牌端点以及令牌服务
     * 1. 密码模式下配置认证管理器 AuthenticationManager
     * 2. 设置 AccessToken的存储介质tokenStore， 默认使用内存当做存储介质。
     * 3. userDetailsService注入
     * @param endpoints
     * @throws Exception
     *//*

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .userDetailsService(customUserDetailsService);
    }

}
*/

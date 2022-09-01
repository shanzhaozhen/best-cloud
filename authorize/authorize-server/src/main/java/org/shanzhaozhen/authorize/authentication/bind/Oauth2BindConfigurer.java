package org.shanzhaozhen.authorize.authentication.bind;

import org.shanzhaozhen.authorize.authentication.federated.FederatedIdentityConfigurer;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.util.Assert;


public class Oauth2BindConfigurer extends AbstractHttpConfigurer<FederatedIdentityConfigurer, HttpSecurity> {

    private String loginProcessingUrl = OAuth2BindAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        ClientRegistrationRepository clientRegistrationRepository =
                applicationContext.getBean(ClientRegistrationRepository.class);

        OAuth2AuthorizedClientService oAuth2AuthorizedClientService =
                applicationContext.getBean(OAuth2AuthorizedClientService.class);


        OAuth2BindAuthenticationFilter oAuth2BindAuthenticationFilter = new OAuth2BindAuthenticationFilter(clientRegistrationRepository,
                oAuth2AuthorizedClientService, this.loginProcessingUrl);

        oAuth2BindAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();

        OAuth2BindAuthenticationProvider oAuth2BindAuthenticationProvider = new OAuth2BindAuthenticationProvider(
                accessTokenResponseClient, new DefaultOAuth2UserService());

        GrantedAuthoritiesMapper userAuthoritiesMapper = http.getSharedObject(GrantedAuthoritiesMapper.class);
        if (userAuthoritiesMapper != null) {
            oAuth2BindAuthenticationProvider.setAuthoritiesMapper(userAuthoritiesMapper);
        }

        http
                .addFilterAfter(oAuth2BindAuthenticationFilter, OAuth2LoginAuthenticationFilter.class)
                .authenticationProvider(this.postProcess(oAuth2BindAuthenticationProvider));
    }

    public Oauth2BindConfigurer loginProcessingUrl(String loginProcessingUrl) {
        Assert.hasText(loginProcessingUrl, "loginProcessingUrl cannot be empty");
        this.loginProcessingUrl = loginProcessingUrl;
        return this;
    }

}
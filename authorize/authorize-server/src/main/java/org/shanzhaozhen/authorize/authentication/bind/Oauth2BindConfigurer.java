package org.shanzhaozhen.authorize.authentication.bind;

import org.shanzhaozhen.authorize.authentication.federated.FederatedIdentityConfigurer;
import org.shanzhaozhen.authorize.service.SocialUserService;
import org.springframework.context.ApplicationContext;
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

    private final SocialUserService socialUserService;
    private String loginProcessingUrl = OAuth2BindAuthenticationFilter.DEFAULT_FILTER_PROCESSES_URI;

    public Oauth2BindConfigurer(SocialUserService socialUserService) {
        this.socialUserService = socialUserService;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ApplicationContext applicationContext = http.getSharedObject(ApplicationContext.class);
        ClientRegistrationRepository clientRegistrationRepository =
                applicationContext.getBean(ClientRegistrationRepository.class);

        OAuth2AuthorizedClientService oauth2AuthorizedClientService =
                applicationContext.getBean(OAuth2AuthorizedClientService.class);


        OAuth2BindAuthenticationFilter oauth2BindAuthenticationFilter =
                new OAuth2BindAuthenticationFilter(clientRegistrationRepository, oauth2AuthorizedClientService,
                        socialUserService, this.loginProcessingUrl);

        oauth2BindAuthenticationFilter.setAuthenticationManager(http.getSharedObject(AuthenticationManager.class));

        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient = new DefaultAuthorizationCodeTokenResponseClient();

        OAuth2BindAuthenticationProvider oauth2BindAuthenticationProvider = new OAuth2BindAuthenticationProvider(
                accessTokenResponseClient, new DefaultOAuth2UserService());

        GrantedAuthoritiesMapper userAuthoritiesMapper = http.getSharedObject(GrantedAuthoritiesMapper.class);
        if (userAuthoritiesMapper != null) {
            oauth2BindAuthenticationProvider.setAuthoritiesMapper(userAuthoritiesMapper);
        }

        http
                .addFilterAfter(oauth2BindAuthenticationFilter, OAuth2LoginAuthenticationFilter.class)
                .authenticationProvider(this.postProcess(oauth2BindAuthenticationProvider));
    }

    public Oauth2BindConfigurer loginProcessingUrl(String loginProcessingUrl) {
        Assert.hasText(loginProcessingUrl, "loginProcessingUrl cannot be empty");
        this.loginProcessingUrl = loginProcessingUrl;
        return this;
    }

}
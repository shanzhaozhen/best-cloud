package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.vo.OAuth2AuthorizationConsentVO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationConsentDO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


public class OAuth2AuthorizationConsentConverter {

    public static OAuth2AuthorizationConsent toOAuth2AuthorizationConsent(OAuth2AuthorizationConsentDO oAuth2AuthorizationConsentDO) {
        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent
                .withId(oAuth2AuthorizationConsentDO.getRegisteredClientId(), oAuth2AuthorizationConsentDO.getPrincipalName());
        String authorities = oAuth2AuthorizationConsentDO.getAuthorities();
        if (authorities != null) {
            for (String authority : StringUtils.commaDelimitedListToSet(authorities)) {
                builder.authority(new SimpleGrantedAuthority(authority));
            }
        }
        return builder.build();
    }

    public static OAuth2AuthorizationConsentDO toDO(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        OAuth2AuthorizationConsentDO oAuth2AuthorizationConsentDO = new OAuth2AuthorizationConsentDO();
        BeanUtils.copyProperties(oAuth2AuthorizationConsent, oAuth2AuthorizationConsentDO);
        Set<GrantedAuthority> authorities = oAuth2AuthorizationConsent.getAuthorities();
        if (!CollectionUtils.isEmpty(authorities)) {
            String authoritiesString = StringUtils.collectionToDelimitedString(
                    authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()), ",");
            oAuth2AuthorizationConsentDO.setAuthorities(authoritiesString);
        }
        return oAuth2AuthorizationConsentDO;
    }
}

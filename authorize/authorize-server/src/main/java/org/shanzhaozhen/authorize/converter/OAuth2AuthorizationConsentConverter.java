package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;


public class OAuth2AuthorizationConsentConverter {

    public static OAuth2AuthorizationConsent toOAuth2AuthorizationConsent(OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO) {
        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent
                .withId(oAuth2AuthorizationConsentDTO.getRegisteredClientId(), oAuth2AuthorizationConsentDTO.getPrincipalName());
        String authorities = oAuth2AuthorizationConsentDTO.getAuthorities();
        if (authorities != null) {
            for (String authority : StringUtils.commaDelimitedListToSet(authorities)) {
                builder.authority(new SimpleGrantedAuthority(authority));
            }
        }
        return builder.build();
    }

    public static OAuth2AuthorizationConsentDTO toDTO(OAuth2AuthorizationConsent oAuth2AuthorizationConsent) {
        OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO = new OAuth2AuthorizationConsentDTO();
        BeanUtils.copyProperties(oAuth2AuthorizationConsent, oAuth2AuthorizationConsentDTO);
        Set<GrantedAuthority> authorities = oAuth2AuthorizationConsent.getAuthorities();
        if (!CollectionUtils.isEmpty(authorities)) {
            String authoritiesString = StringUtils.collectionToDelimitedString(
                    authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()), ",");
            oAuth2AuthorizationConsentDTO.setAuthorities(authoritiesString);
        }
        return oAuth2AuthorizationConsentDTO;
    }
}

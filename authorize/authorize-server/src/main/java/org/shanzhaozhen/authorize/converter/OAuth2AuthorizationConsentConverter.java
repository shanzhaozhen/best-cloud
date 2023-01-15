package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.oauth.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;


public class OAuth2AuthorizationConsentConverter {

    public static OAuth2AuthorizationConsent toOAuth2AuthorizationConsent(OAuth2AuthorizationConsentDTO oauth2AuthorizationConsentDTO) {
        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent
                .withId(oauth2AuthorizationConsentDTO.getRegisteredClientId(), oauth2AuthorizationConsentDTO.getPrincipalName());
        String authorities = oauth2AuthorizationConsentDTO.getAuthorities();
        if (authorities != null) {
            for (String authority : StringUtils.commaDelimitedListToSet(authorities)) {
                builder.authority(new SimpleGrantedAuthority(authority));
            }
        }
        return builder.build();
    }

    public static OAuth2AuthorizationConsentDTO toDTO(OAuth2AuthorizationConsent oauth2AuthorizationConsent) {
        OAuth2AuthorizationConsentDTO oauth2AuthorizationConsentDTO = new OAuth2AuthorizationConsentDTO();
        BeanUtils.copyProperties(oauth2AuthorizationConsent, oauth2AuthorizationConsentDTO);
        Set<GrantedAuthority> authorities = oauth2AuthorizationConsent.getAuthorities();
        if (!CollectionUtils.isEmpty(authorities)) {
            String authoritiesString = StringUtils.collectionToDelimitedString(
                    authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()), ",");
            oauth2AuthorizationConsentDTO.setAuthorities(authoritiesString);
        }
        return oauth2AuthorizationConsentDTO;
    }
}

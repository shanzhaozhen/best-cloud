package org.shanzhaozhen.authorize.converter;

import org.shanzhaozhen.authorize.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2AuthorizationConsentDO;
import org.shanzhaozhen.common.core.utils.JacksonUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationConsent;

import java.util.HashSet;
import java.util.Optional;


public class OAuth2AuthorizationConsentConverter {

    public static OAuth2AuthorizationConsent toOAuth2AuthorizationConsent(OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO) {
        if (oAuth2AuthorizationConsentDTO == null) {
            return null;
        }

        OAuth2AuthorizationConsent.Builder builder = OAuth2AuthorizationConsent
                .withId(oAuth2AuthorizationConsentDTO.getRegisteredClientId(), oAuth2AuthorizationConsentDTO.getPrincipalName())
                .authorities(grantedAuthorities -> grantedAuthorities.addAll(
                        Optional.ofNullable(oAuth2AuthorizationConsentDTO.getAuthorities())
                                .orElse(new HashSet<>()))
                );

        return builder.build();
    }

    public static OAuth2AuthorizationConsentDTO toDTO(OAuth2AuthorizationConsent authorizationConsent) {

        OAuth2AuthorizationConsentDTO.OAuth2AuthorizationConsentDTOBuilder builder = OAuth2AuthorizationConsentDTO.builder()
                .registeredClientId(authorizationConsent.getRegisteredClientId())
                .principalName(authorizationConsent.getPrincipalName())
                .authorities(authorizationConsent.getAuthorities());

        return builder.build();
    }

    public static OAuth2AuthorizationConsentDTO toDTO(OAuth2AuthorizationConsentDO oAuth2AuthorizationConsentDO) {
        OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO = new OAuth2AuthorizationConsentDTO();
        BeanUtils.copyProperties(oAuth2AuthorizationConsentDO, oAuth2AuthorizationConsentDTO);
        oAuth2AuthorizationConsentDTO.setAuthorities(
                Optional.ofNullable(oAuth2AuthorizationConsentDO.getAuthorities())
                        .map(o -> JacksonUtils.toPojoSet(o, GrantedAuthority.class))
                        .orElse(null)
        );

        return oAuth2AuthorizationConsentDTO;
    }

    public static OAuth2AuthorizationConsentDO toDO(OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO) {
        OAuth2AuthorizationConsentDO oAuth2AuthorizationConsentDO = new OAuth2AuthorizationConsentDO();
        BeanUtils.copyProperties(oAuth2AuthorizationConsentDTO, oAuth2AuthorizationConsentDO);
        oAuth2AuthorizationConsentDO.setAuthorities(Optional.ofNullable(oAuth2AuthorizationConsentDTO.getAuthorities())
                .map(JacksonUtils::toJSONString)
                .orElse(null)
        );
        return oAuth2AuthorizationConsentDO;
    }
}

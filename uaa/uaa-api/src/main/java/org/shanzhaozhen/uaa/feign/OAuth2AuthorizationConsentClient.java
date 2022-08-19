package org.shanzhaozhen.uaa.feign;

import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "uaa")
public interface OAuth2AuthorizationConsentClient {

    @GetMapping("/oauth2/authorization-consent")
    OAuth2AuthorizationConsentDTO getOAuth2AuthorizationConsent(String registeredClientId, String principalName);

    @PostMapping("/oauth2/authorization-consent")
    R<?> saveOAuth2AuthorizationConsent(OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO);

    @DeleteMapping("/oauth2/authorization-consent")
    R<?> deleteOAuth2AuthorizationConsent(@RequestParam("registeredClientId") String registeredClientId, @RequestParam("registeredClientId") String principalName);


}

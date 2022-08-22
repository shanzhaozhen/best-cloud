package org.shanzhaozhen.uaa.feign;

import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationConsentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "uaa", contextId = "oAuth2AuthorizationConsent")
public interface OAuth2AuthorizationConsentClient {

    @GetMapping("/ws/oauth2/authorization-consent")
    OAuth2AuthorizationConsentDTO getOAuth2AuthorizationConsent(@RequestParam("registeredClientId") String registeredClientId, @RequestParam("principalName") String principalName);

    @PostMapping("/ws/oauth2/authorization-consent")
    R<?> saveOAuth2AuthorizationConsent(@RequestBody OAuth2AuthorizationConsentDTO oAuth2AuthorizationConsentDTO);

    @DeleteMapping("/ws/oauth2/authorization-consent")
    R<?> deleteOAuth2AuthorizationConsent(@RequestParam("registeredClientId") String registeredClientId, @RequestParam("registeredClientId") String principalName);


}

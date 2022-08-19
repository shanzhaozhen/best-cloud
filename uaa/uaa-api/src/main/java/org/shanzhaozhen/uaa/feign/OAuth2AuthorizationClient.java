package org.shanzhaozhen.uaa.feign;

import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2AuthorizationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "uaa")
public interface OAuth2AuthorizationClient {

    @GetMapping(value = "/oauth2/authorization", params = { "id" })
    OAuth2AuthorizationDTO getOAuth2AuthorizationById(@RequestParam("id") String id);

    @GetMapping(value = "/oauth2/authorization", params = { "token" })
    OAuth2AuthorizationDTO getOAuth2AuthorizationByToken(@RequestParam("token") String token, @RequestParam("tokenType") String tokenType);

    @PostMapping("/oauth2/authorization")
    R<?> saveOAuth2Authorization(@RequestBody OAuth2AuthorizationDTO oAuth2AuthorizationDTO);

    @DeleteMapping("/oauth2/authorization/{id}")
    R<?> deleteOAuth2AuthorizationById(@PathVariable("id") String id);

}

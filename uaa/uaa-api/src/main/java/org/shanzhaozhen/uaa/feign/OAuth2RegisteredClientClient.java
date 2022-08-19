package org.shanzhaozhen.uaa.feign;

import org.shanzhaozhen.common.core.result.R;
import org.shanzhaozhen.uaa.pojo.dto.OAuth2RegisteredClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "uaa")
public interface OAuth2RegisteredClientClient {

    @GetMapping(value = "/oauth2/registered-client", params = { "id" })
    OAuth2RegisteredClientDTO getOAuth2RegisteredClientById(@RequestParam("id") String id);

    @GetMapping(value = "/oauth2/registered-client", params = { "clientId" })
    OAuth2RegisteredClientDTO getOAuth2RegisteredClientByClientId(@RequestParam("clientId") String clientId);

    @PostMapping("/oauth2/registered-client")
    R<?> saveOAuth2RegisteredClient(@RequestBody OAuth2RegisteredClientDTO oAuth2RegisteredClientDTO);

}

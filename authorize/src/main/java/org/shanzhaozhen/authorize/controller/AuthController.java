package org.shanzhaozhen.authorize.controller;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.config.jose.Jwks;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPublicKey;
import java.util.Map;


@Api(tags = "认证中心")
@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class AuthController {

    private final Jwks jwks;

    @ApiOperation(value = "获取公钥")
    @GetMapping("/public-key")
    public Map<String, Object> getPublicKey() {
        RSAPublicKey publicKey = jwks.getPublicKey();
        RSAKey key = new RSAKey.Builder(publicKey).build();
        return new JWKSet(key).toJSONObject();
    }

}

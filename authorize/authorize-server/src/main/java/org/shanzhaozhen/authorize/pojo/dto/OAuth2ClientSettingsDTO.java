package org.shanzhaozhen.authorize.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端配置DTO实体")
public class OAuth2ClientSettingsDTO implements Serializable {

    private static final long serialVersionUID = 1339017694529471908L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String clientId;

    @Schema(description = "客户端是否需要证明密钥")
    private Boolean requireProofKey;

    @Schema(description = "客户端是否需要授权确认页面")
    private Boolean requireAuthorizationConsent;

    @Schema(description = "jwkSet url")
    private String jwkSetUrl;

    @Schema(description = "支持的签名算法")
    private String tokenEndpointAuthenticationSigningAlgorithm;

}

package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端配置Form实体")
public class OAuth2ClientSettingsForm {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "客户端是否需要证明密钥")
    private boolean requireProofKey;

    @Schema(description = "客户端是否需要授权确认页面")
    private boolean requireAuthorizationConsent;

    @Schema(description = "jwkSet url")
    private String jwkSetUrl;

    @Schema(description = "支持的签名算法")
    private String tokenEndpointAuthenticationSigningAlgorithm;

}

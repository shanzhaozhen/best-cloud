package org.shanzhaozhen.uaa.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfoVO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端配置VO实体")
public class OAuth2ClientSettingsVO extends BaseInfoVO {

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

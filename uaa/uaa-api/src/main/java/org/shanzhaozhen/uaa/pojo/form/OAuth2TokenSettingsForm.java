package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2 token 配置 DO实体")
public class OAuth2TokenSettingsForm {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "access_token 有效时间")
    private Long accessTokenTimeToLive;

    @Schema(description = "token 格式  jwt、opaque")
    private String accessTokenFormat;

    @Schema(description = "是否重用 refresh_token")
    private boolean reuseRefreshTokens;

    @Schema(description = "refresh_token 有效时间")
    private Long refreshTokenTimeToLive;

    @Schema(description = "oidc id_token 签名算法")
    private String idTokenSignatureAlgorithm;

}

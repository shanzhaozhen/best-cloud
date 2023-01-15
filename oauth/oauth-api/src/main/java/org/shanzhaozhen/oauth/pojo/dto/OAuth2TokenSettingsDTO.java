package org.shanzhaozhen.oauth.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2 token 配置 DTO实体")
public class OAuth2TokenSettingsDTO extends BaseInfo {

    private static final long serialVersionUID = -755091389477016224L;

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

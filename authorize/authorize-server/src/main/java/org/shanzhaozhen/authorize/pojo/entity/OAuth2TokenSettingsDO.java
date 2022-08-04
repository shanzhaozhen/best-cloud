package org.shanzhaozhen.authorize.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_token_settings")
@Schema(description = "oauth2客户端信息DO实体")
public class OAuth2TokenSettingsDO implements Serializable {

    private static final long serialVersionUID = 104913821115105292L;

    @Schema(description = "oauth2客户端id")
    private String clientId;

    @Schema(description = "access_token 有效时间")
    private Duration accessTokenTimeToLive;

    @Schema(description = "token 格式  jwt、opaque")
    private String tokenFormat;

    @Schema(description = "是否重用 refresh_token")
    private boolean reuseRefreshTokens = true;

    @Schema(description = "refresh_token 有效时间")
    private Duration refreshTokenTimeToLive;

    @Schema(description = "oidc id_token 签名算法")
    private String idTokenSignatureAlgorithm;

}

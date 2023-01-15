package org.shanzhaozhen.oauth.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseEntity;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_token_settings")
@Schema(description = "oauth2 token 配置 DO实体")
public class OAuth2TokenSettingsDO extends BaseEntity {

    private static final long serialVersionUID = 104913821115105292L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
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

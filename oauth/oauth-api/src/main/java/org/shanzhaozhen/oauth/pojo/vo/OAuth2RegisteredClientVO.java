package org.shanzhaozhen.oauth.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfoVO;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息VO实体")
public class OAuth2RegisteredClientVO extends BaseInfoVO {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "客户端id")
    private String clientId;

    @Schema(description = "客户端到期时间")
    private Instant clientIdIssuedAt;

    @Schema(description = "客户端密码")
    private String clientSecret;

    @Schema(description = "客户端密码到期时间")
    private Instant clientSecretExpiresAt;

    @Schema(description = "客户端名称")
    private String clientName;

    @Schema(description = "客户端认证方式")
    private String clientAuthenticationMethods;

    @Schema(description = "客户端授权方式")
    private String authorizationGrantTypes;

    @Schema(description = "客户端允许重定向的uri")
    private String redirectUris;

    @Schema(description = "客户端允许的授权范围")
    private String scopes;

    @Schema(description = "客户端配置")
    private OAuth2ClientSettingsVO clientSettings;

    @Schema(description = "token配置")
    private OAuth2TokenSettingsVO tokenSettings;

}

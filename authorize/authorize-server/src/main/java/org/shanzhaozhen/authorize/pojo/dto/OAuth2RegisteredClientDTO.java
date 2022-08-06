package org.shanzhaozhen.authorize.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息DTO实体")
public class OAuth2RegisteredClientDTO extends BaseInfo {

    private static final long serialVersionUID = 1942162986392496847L;

    @Schema(description = "id")
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
    private Set<OAuth2ClientAuthenticationMethodDTO> clientAuthenticationMethods;

    @Schema(description = "客户端授权方式")
    private Set<OAuth2AuthorizationGrantTypeDTO> authorizationGrantTypes;

    @Schema(description = "客户端允许重定向的uri")
    private Set<OAuth2RedirectUriDTO> redirectUris;

    @Schema(description = "客户端允许授权范围")
    private Set<OAuth2ScopeDTO> scopes;

    @Schema(description = "客户端配置")
    private OAuth2ClientSettingsDTO clientSettings;

    @Schema(description = "token配置")
    private OAuth2TokenSettingsDTO tokenSettings;

}

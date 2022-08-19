package org.shanzhaozhen.uaa.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfoVO;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Instant;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息VO实体")
public class OAuth2RegisteredClientVO extends BaseInfoVO {

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
    private Set<ClientAuthenticationMethod> clientAuthenticationMethods;

    @Schema(description = "客户端授权方式")
    private Set<AuthorizationGrantType> authorizationGrantTypes;

    @Schema(description = "客户端允许重定向的uri")
    private Set<String> redirectUris;

    @Schema(description = "客户端允许授权范围")
    private Set<String> scopes;

    @Schema(description = "客户端配置")
    private ClientSettings clientSettings;

    @Schema(description = "token配置")
    private TokenSettings tokenSettings;

}

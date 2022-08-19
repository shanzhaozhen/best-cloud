package org.shanzhaozhen.uaa.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息DTO实体")
public class OAuth2AuthorizationDTO extends BaseInfo {

    private static final long serialVersionUID = -3322039147048874029L;

    @Schema(description = "主键ID")
    private String id;
    
    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "")
    private String principalName;

    @Schema(description = "")
    private String authorizationGrantType;

    @Schema(description = "")
    private String attributes;


    @Schema(description = "")
    private String state;


    @Schema(description = "")
    private String authorizationCodeValue;

    @Schema(description = "")
    private Instant authorizationCodeIssuedAt;

    @Schema(description = "")
    private Instant authorizationCodeExpiresAt;

    @Schema(description = "")
    private String authorizationCodeMetadata;


    @Schema(description = "")
    private String accessTokenValue;

    @Schema(description = "")
    private Instant accessTokenIssuedAt;

    @Schema(description = "")
    private Instant accessTokenExpiresAt;

    @Schema(description = "")
    private String accessTokenMetadata;

    @Schema(description = "")
    private String accessTokenType;

    @Schema(description = "")
    private String accessTokenScopes;


    @Schema(description = "")
    private String refreshTokenValue;

    @Schema(description = "")
    private Instant refreshTokenIssuedAt;

    @Schema(description = "")
    private Instant refreshTokenExpiresAt;

    @Schema(description = "")
    private String refreshTokenMetadata;


    @Schema(description = "")
    private String oidcIdTokenValue;

    @Schema(description = "")
    private Instant oidcIdTokenIssuedAt;

    @Schema(description = "")
    private Instant oidcIdTokenExpiresAt;

    @Schema(description = "")
    private String oidcIdTokenMetadata;

}

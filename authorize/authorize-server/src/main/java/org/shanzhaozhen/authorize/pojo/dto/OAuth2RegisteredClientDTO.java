package org.shanzhaozhen.authorize.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息DTO实体")
public class OAuth2RegisteredClientDTO extends BaseInfo {

    private static final long serialVersionUID = -7336539012480314362L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "客户端id")
    private String clientId;

    @Schema(description = "客户端到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime clientIdIssuedAt;

    @Schema(description = "客户端密码")
    @JsonIgnore()
    private String clientSecret;

    @Schema(description = "客户端密码到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime clientSecretExpiresAt;

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
    private OAuth2ClientSettingsDTO clientSettings;

    @Schema(description = "token配置")
    private OAuth2TokenSettingsDTO tokenSettings;

    @Schema(description = "描述")
    private String description;

}

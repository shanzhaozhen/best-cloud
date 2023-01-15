package org.shanzhaozhen.oauth.pojo.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息Form实体")
public class OAuth2RegisteredClientForm {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "客户端id")
    private String clientId;

    @Schema(description = "客户端到期时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime clientIdIssuedAt;

    @Schema(description = "客户端密码")
    @NotEmpty(groups = {Insert.class}, message = "客户端密码不能为空")
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
    private OAuth2ClientSettingsForm clientSettings;

    @Schema(description = "token配置")
    private OAuth2TokenSettingsForm tokenSettings;

    @Schema(description = "描述")
    private String description;

}

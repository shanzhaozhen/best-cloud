package org.shanzhaozhen.authorize.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2ClientSettingsDO;
import org.shanzhaozhen.authorize.pojo.entity.OAuth2TokenSettingsDO;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息DTO实体")
public class Oauth2RegisteredClientDTO {

    @Schema(description = "id")
    private String id;

    @Schema(description = "客户端id")
    private String clientId;

    @Schema(description = "客户端密码")
    private String clientSecret;

    @Schema(description = "客户端名称")
    private String clientName;

    @Schema(description = "认证方式")
    private Set<String> clientAuthenticationMethods;

    @Schema(description = "授权码")
    private Set<String> authorizationGrantTypes;

    @Schema(description = "作用域")
    private Set<String> scopes;

    @Schema(description = "回调地址")
    private Set<String> redirectUris;

    @Schema(description = "客户端设置")
    private OAuth2ClientSettingsDO clientSettings;

    @Schema(description = "token 设置")
    private OAuth2TokenSettingsDO tokenSettings;

}

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

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_registered_client")
@Schema(description = "oauth2客户端信息DO实体")
public class OAuth2RegisteredClientDO extends BaseEntity {

    private static final long serialVersionUID = 1914056921456851028L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "客户端id")
    private String clientId;

    @Schema(description = "客户端到期时间")
    private LocalDateTime clientIdIssuedAt;

    @Schema(description = "客户端密码")
    private String clientSecret;

    @Schema(description = "客户端密码到期时间")
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

    @Schema(description = "描述")
    private String description;

}

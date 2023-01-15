package org.shanzhaozhen.oauth.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息DTO实体")
public class OAuth2AuthorizationConsentDTO extends BaseInfo {

    private static final long serialVersionUID = -5100739084251045792L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "授权用户名称")
    private String principalName;

    @Schema(description = "授权权限")
    private String authorities;

}

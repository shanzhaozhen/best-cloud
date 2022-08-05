package org.shanzhaozhen.authorize.pojo.dto;

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
@Schema(description = "oauth2客户端认证方式DTO实体")
public class OAuth2ClientAuthenticationMethodDTO extends BaseInfo {

    private static final long serialVersionUID = 7862959602507032396L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String clientId;

    @Schema(description = "客户端认证方式")
    private String clientAuthenticationMethod;

}

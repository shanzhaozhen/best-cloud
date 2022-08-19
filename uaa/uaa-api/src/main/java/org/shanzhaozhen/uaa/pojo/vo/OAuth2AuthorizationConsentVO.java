package org.shanzhaozhen.uaa.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfoVO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端信息VO实体")
public class OAuth2AuthorizationConsentVO extends BaseInfoVO {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "授权用户名称")
    private String principalName;

    @Schema(description = "授权权限")
    private Set<GrantedAuthority> authorities;

}

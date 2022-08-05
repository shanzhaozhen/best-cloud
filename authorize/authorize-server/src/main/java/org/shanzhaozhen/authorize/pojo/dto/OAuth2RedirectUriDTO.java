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
@Schema(description = "oauth2允许重定向的 uri DTO实体")
public class OAuth2RedirectUriDTO extends BaseInfo {

    private static final long serialVersionUID = 4512441721005677882L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String clientId;

    @Schema(description = "客户端允许重定向的uri")
    private String redirectUri;

}

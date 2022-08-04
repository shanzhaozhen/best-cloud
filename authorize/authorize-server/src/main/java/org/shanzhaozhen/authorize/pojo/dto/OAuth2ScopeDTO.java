package org.shanzhaozhen.authorize.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "oauth2客户端授权范围DTO实体")
public class OAuth2ScopeDTO implements Serializable {

    private static final long serialVersionUID = -4403936131034211395L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "oauth2客户端id")
    private String clientId;

    @Schema(description = "客户端允许的授权范围")
    private String scope;

    @Schema(description = "描述")
    private String description;

}

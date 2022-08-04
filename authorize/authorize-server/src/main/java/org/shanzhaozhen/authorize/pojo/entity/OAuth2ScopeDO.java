package org.shanzhaozhen.authorize.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_scope")
@Schema(description = "oauth2客户端信息DO实体")
public class OAuth2ScopeDO implements Serializable {

    private static final long serialVersionUID = -4403936131034211395L;

    @Schema(description = "oauth2客户端id")
    private String clientId;

    @Schema(description = "客户端允许的scope 来自role表")
    private String scope;

    @Schema(description = "描述")
    private String description;

}

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
@TableName("oauth2_client_auth_method")
@Schema(description = "oauth2客户端信息DO实体")
public class OAuth2ClientAuthMethodDO implements Serializable {

    private static final long serialVersionUID = -1096564364823745973L;

    @Schema(description = "oauth2客户端id")
    private String clientId;

    @Schema(description = "客户端认证方式")
    private String clientAuthenticationMethod;

}

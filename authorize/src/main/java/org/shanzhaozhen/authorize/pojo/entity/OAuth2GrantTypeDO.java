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
@TableName("oauth2_registered_client")
@Schema(description = "oauth2客户端授权DO实体")
public class OAuth2GrantTypeDO implements Serializable {

    private static final long serialVersionUID = -8892019207322733934L;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "客户端授权方式")
    private String grantTypeName;

}

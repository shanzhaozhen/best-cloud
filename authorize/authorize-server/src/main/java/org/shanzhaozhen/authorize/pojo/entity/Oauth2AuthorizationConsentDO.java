package org.shanzhaozhen.authorize.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_authorization_consent")
@Schema(description = "oauth2客户端信息DO实体")
public class Oauth2AuthorizationConsentDO implements Serializable {

    private static final long serialVersionUID = -6568842581990261240L;

    @TableField("registered_client_id")
    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @TableField("principal_name")
    private String principalName;

    private String authorities;

}

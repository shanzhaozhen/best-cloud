package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseEntity;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_authorization_consent")
@Schema(description = "oauth2客户端信息DO实体")
public class OAuth2AuthorizationConsentDO extends BaseEntity {

    private static final long serialVersionUID = -6568842581990261240L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "授权用户名称")
    private String principalName;

    @Schema(description = "授权权限")
    private String authorities;

}

package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
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
@TableName("oauth2_client_settings")
@Schema(description = "oauth2客户端配置DO实体")
public class OAuth2ClientSettingsDO extends BaseEntity {

    private static final long serialVersionUID = 1339017694529471908L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "客户端是否需要证明密钥")
    private boolean requireProofKey;

    @Schema(description = "客户端是否需要授权确认页面")
    private boolean requireAuthorizationConsent;

    @Schema(description = "jwkSet url")
    private String jwkSetUrl;

    @Schema(description = "支持的签名算法")
    private String tokenEndpointAuthenticationSigningAlgorithm;

}

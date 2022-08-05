package org.shanzhaozhen.authorize.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseEntity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_redirect_uri")
@Schema(description = "oauth2允许重定向的 uri DO实体")
public class OAuth2RedirectUriDO extends BaseEntity {

    private static final long serialVersionUID = 4512441721005677882L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "oauth2客户端id")
    private String clientId;

    @Schema(description = "客户端允许重定向的uri")
    private String redirectUri;

    @Schema(description = "版本号")
    @Version
    private Integer version;

}

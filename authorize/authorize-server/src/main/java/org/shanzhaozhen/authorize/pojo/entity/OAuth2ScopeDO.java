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
@TableName("oauth2_scope")
@Schema(description = "oauth2客户端授权范围DO实体")
public class OAuth2ScopeDO extends BaseEntity {

    private static final long serialVersionUID = -4403936131034211395L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "oauth2客户端id")
    private String registeredClientId;

    @Schema(description = "客户端允许的授权范围")
    private String scope;

    @Schema(description = "描述")
    private String description;

}

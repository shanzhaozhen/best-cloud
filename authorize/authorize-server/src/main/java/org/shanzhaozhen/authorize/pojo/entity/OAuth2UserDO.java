package org.shanzhaozhen.authorize.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("oauth2_user")
@Schema(description = "用户DO实体")
public class OAuth2UserDO extends BaseEntity {

    private static final long serialVersionUID = 3064727069207896868L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "账户是否过期,过期无法验证")
    private boolean accountNonExpired = true;

    @Schema(description = "指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证")
    private boolean accountNonLocked = true;

    @Schema(description = "指示是否已过期的用户的凭据(密码),过期的凭据防止认证")
    private boolean credentialsNonExpired = true;

    @Schema(description = "是否被禁用,禁用的用户不能身份验证")
    private boolean enabled = true;
    
}

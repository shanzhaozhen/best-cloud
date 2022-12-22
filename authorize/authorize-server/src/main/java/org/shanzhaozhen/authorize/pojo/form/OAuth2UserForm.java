package org.shanzhaozhen.authorize.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户Form实体")
public class OAuth2UserForm {

    @Schema(description = "主键ID")
    @NotNull(groups = {Update.class}, message = "用户id不能为空")
    private String id;

    @Schema(description = "用户名")
    @Length(min = 4, max = 20, message = "用户名长度为4-20位")
    private String username;

    @Schema(description = "密码")
    @Length(min = 6, max = 25, message = "密码长度为6-25位")
    private String password;

    @Schema(description = "账户是否过期,过期无法验证")
    private boolean accountNonExpired;

    @Schema(description = "指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证")
    private boolean accountNonLocked;

    @Schema(description = "指示是否已过期的用户的凭据(密码),过期的凭据防止认证")
    private boolean credentialsNonExpired;

    @Schema(description = "是否被禁用,禁用的用户不能身份验证")
    private boolean enabled;

    @Schema(description = "关联的角色id")
    private List<String> roleIds;

}

package org.shanzhaozhen.uaa.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "用户DTO实体")
public class UserDTO extends BaseInfo {

    private static final long serialVersionUID = -9022947720181781365L;

    @Schema(description = "主键ID")
    private Long id;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "账户是否过期,过期无法验证")
    private boolean accountNonExpired;

    @Schema(description = "指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证")
    private boolean accountNonLocked;

    @Schema(description = "指示是否已过期的用户的凭据(密码),过期的凭据防止认证")
    private boolean credentialsNonExpired;

    @Schema(description = "是否被禁用,禁用的用户不能身份验证")
    private boolean enabled;

    @Schema(description = "记录用户的角色")
    private List<RoleDTO> roles;

    @Schema(description = "关联的角色ID")
    private List<Long> roleIds;

    @Schema(description = "关联的用户信息")
    private UserInfoDTO userInfo;

}

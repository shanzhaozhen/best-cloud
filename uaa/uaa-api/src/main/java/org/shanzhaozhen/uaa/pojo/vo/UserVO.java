package org.shanzhaozhen.uaa.pojo.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfoVO;
import org.shanzhaozhen.common.core.jackson.ToStringListSerialize;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "用户VO对象")
public class UserVO extends BaseInfoVO {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "用户名")
    private String username;

    @TableField(exist = false)
    private Collection<GrantedAuthority> authorities;

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

    @Schema(description = "关联的用户信息")
    private UserInfoVO userInfo;

    @Schema(description = "关联的部门信息")
    private DepartmentVO departmentInfo;

}

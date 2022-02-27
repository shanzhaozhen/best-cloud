package org.shanzhaozhen.uaa.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "权限用户")
public class AuthUser implements UserDetails {

    private static final long serialVersionUID = 172687743338426957L;

    @Schema(description = "用户")
    private Long userId;

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

    @Schema(description = "用户权限")
    private Collection<? extends GrantedAuthority> authorities;

    public AuthUser(UserDTO user) {
        this.userId = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.accountNonExpired = user.isAccountNonExpired();
        this.accountNonLocked = user.isAccountNonExpired();
        this.credentialsNonExpired = user.isCredentialsNonExpired();
        this.enabled = user.isEnabled();

        if (!CollectionUtils.isEmpty(user.getRoles())) {
            this.authorities = user.getRoles().stream().map(
                    r -> new SimpleGrantedAuthority(r.getCode())
            ).collect(Collectors.toSet());
        }

    }
}

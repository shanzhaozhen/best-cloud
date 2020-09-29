package org.shanzhaozhen.basiccommon.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户VO对象")
public class UserVO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @TableField(exist = false)
    private Collection<GrantedAuthority> authorities;

    @ApiModelProperty(value = "账户是否过期,过期无法验证")
    private boolean accountNonExpired;

    @ApiModelProperty(value = "指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证")
    private boolean accountNonLocked;

    @ApiModelProperty(value = "指示是否已过期的用户的凭据(密码),过期的凭据防止认证")
    private boolean credentialsNonExpired;

    @ApiModelProperty(value = "是否被禁用,禁用的用户不能身份验证")
    private boolean enabled;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    private Date birthday;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号码")
    private String phoneNumber;

    @ApiModelProperty(value = "地址编号")
    private String addressCode;

    @ApiModelProperty(value = "详细地址")
    private String detailedAddress;

    @ApiModelProperty(value = "个人介绍")
    private String introduction;

    @ApiModelProperty(value = "关联的角色id")
    private List<Long> roleIds;

}

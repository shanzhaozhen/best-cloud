package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "用户Form实体")
public class UserForm {

    @Schema(title = "主键ID")
    @NotNull(groups = {Update.class}, message = "用户id不能为空")
    private Long id;

    @Schema(title = "用户名")
    @Length(min = 4, max = 20, message = "用户名长度为4-20位")
    private String username;

    @Schema(title = "密码")
    @Length(min = 6, max = 25, message = "密码长度为6-25位")
    private String password;

    @Schema(title = "部门ID")
    private Long depId;

    @Schema(title = "账户是否过期,过期无法验证")
    private boolean accountNonExpired;

    @Schema(title = "指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证")
    private boolean accountNonLocked;

    @Schema(title = "指示是否已过期的用户的凭据(密码),过期的凭据防止认证")
    private boolean credentialsNonExpired;

    @Schema(title = "是否被禁用,禁用的用户不能身份验证")
    private boolean enabled;

    @Schema(title = "姓名")
    private String name;

    @Schema(title = "昵称")
    private String nickname;

    @Schema(title = "性别")
    private Integer sex;

    @Schema(title = "生日")
    private Date birthday;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "手机号码")
    private String phoneNumber;

    @Schema(title = "地址编号")
    private String addressCode;

    @Schema(title = "详细地址")
    private String detailedAddress;

    @Schema(title = "个人介绍")
    private String introduction;

    @Schema(title = "关联的角色id")
    private List<Long> roleIds;

}

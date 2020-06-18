package org.shanzhaozhen.basiccommon.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(description = "用户Form实体")
public class UserForm {

    @ApiModelProperty(value = "主键ID")
    @NotNull(groups = {Update.class}, message = "用户id不能为空")
    private Long id;

    @ApiModelProperty(value = "用户名")
    @Length(min = 4, max = 20, message = "用户名长度为4-20位")
    private String username;

    @ApiModelProperty(value = "密码")
    @Length(min = 6, max = 25, message = "密码长度为6-25位")
    private String password;

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

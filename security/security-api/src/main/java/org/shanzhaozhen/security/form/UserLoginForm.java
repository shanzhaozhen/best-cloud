package org.shanzhaozhen.security.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "用户登陆表单实体")
public class UserLoginForm {

    @ApiModelProperty(value = "用户名")
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4, max = 20, message = "用户名长度为4-20位")
    @Valid
    private String username;

    @ApiModelProperty(value = "密码")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 25, message = "密码长度为6-25位")
    private String password;

    @Schema(title = "登陆方式（account、phone）")
    private String type;

    @Schema(title = "自动登陆")
    private Boolean autoLogin;

}

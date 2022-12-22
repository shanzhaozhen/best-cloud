package org.shanzhaozhen.authorize.pojo.form;

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
@Schema(description = "账号登陆表单实体")
public class AccountLogin {

    @Schema(description = "用户名")
    @NotEmpty(message = "用户名不能为空")
    @Length(min = 4, max = 20, message = "用户名长度为4-20位")
    @Valid
    private String username;

    @Schema(description = "密码")
    @NotEmpty(message = "密码不能为空")
    @Length(min = 6, max = 25, message = "密码长度为6-25位")
    private String password;

    @Schema(description = "自动登陆")
    private Boolean autoLogin;

}

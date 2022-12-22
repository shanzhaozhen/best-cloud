package org.shanzhaozhen.authorize.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "手机登陆表单实体")
public class PhoneLogin {

    @Schema(description = "手机号")
    @NotEmpty(message = "手机号不能为空")
    @Valid
    private String phone;

    @Schema(description = "验证码")
    @NotEmpty(message = "验证码不能为空")
    private String captcha;

    @Schema(description = "自动登陆")
    private Boolean autoLogin;

}

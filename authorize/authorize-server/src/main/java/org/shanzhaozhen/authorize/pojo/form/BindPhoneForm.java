package org.shanzhaozhen.authorize.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "绑定手机Form实体")
public class BindPhoneForm {

    @Schema(description = "手机号")
    @NotEmpty(message = "手机号不能为空")
    private String phone;

    @Schema(description = "验证码")
    @NotEmpty(message = "验证码不能为空")
    private String captcha;

}

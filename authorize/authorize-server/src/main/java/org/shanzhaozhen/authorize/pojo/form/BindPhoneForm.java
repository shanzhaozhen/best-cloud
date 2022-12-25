package org.shanzhaozhen.authorize.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "绑定手机Form实体")
public class BindPhoneForm {

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "验证码")
    private String code;

}

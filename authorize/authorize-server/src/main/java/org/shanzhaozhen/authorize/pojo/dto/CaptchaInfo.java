package org.shanzhaozhen.authorize.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "验证码信息实体")
public class CaptchaInfo {

    @Schema(description = "id")
    private String key;

    @Schema(description = "验证码")
    private String captcha;

}

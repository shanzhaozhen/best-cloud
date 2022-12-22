package org.shanzhaozhen.authorize.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-08-30
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "修改密码Form实体")
public class ChangePasswordForm {

    @Schema(description = "用户id")
    private String userId;

    @Schema(description = "原密码")
    @NotEmpty(message = "原密码不能为空")
    private String oldPassword;

    @Schema(description = "新密码")
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

}

package org.shanzhaozhen.authorize.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "用户安全信息实体")
public class SecurityInfo {

    @Schema(description = "密码安全级别")
    private Integer securityLevel;

    @Schema(description = "绑定手机")
    private String phone;

}
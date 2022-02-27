package org.shanzhaozhen.common.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "参数信息")
public class MethodParamInfo {

    @Schema(description = "参数类型")
    private Class<?> paramType;

    @Schema(description = "参数值")
    private String paramValue;

}

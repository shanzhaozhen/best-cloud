package org.shanzhaozhen.common.core.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "方法信息")
public class MethodInfo {

    @Schema(description = "方法名")
    private String methodName;

    @Schema(description = "方法名（简易）")
    private String methodSimpleName;

    @Schema(description = "方法名（完全）")
    private String methodFullName;

    @Schema(description = "参数类型")
    private Class<?>[] paramTypes;

}

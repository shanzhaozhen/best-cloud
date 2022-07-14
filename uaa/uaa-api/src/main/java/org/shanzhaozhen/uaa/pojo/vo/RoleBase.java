package org.shanzhaozhen.uaa.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "角色主要信息实体")
public class RoleBase {

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "标识名称")
    private String identification;

}

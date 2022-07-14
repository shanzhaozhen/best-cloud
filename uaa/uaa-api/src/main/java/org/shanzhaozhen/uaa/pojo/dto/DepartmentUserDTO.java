package org.shanzhaozhen.uaa.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "部门-用户关系DTO实体")
public class DepartmentUserDTO extends BaseInfo {

    private static final long serialVersionUID = 8762109586891523108L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "部门ID")
    private String departmentId;

    @Schema(description = "用户ID")
    private String userId;

}

package org.shanzhaozhen.uaa.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "部门DTO实体")
public class DepartmentDTO extends BaseInfo {

    private static final long serialVersionUID = -7605706631514164388L;

    @Schema(description = "主键ID")
    private String id;

    @Schema(description = "上级ID")
    private String pid;

    @Schema(description = "部门名称")
    private String name;

    @Schema(description = "部门编码")
    private String code;

    @Schema(description = "排序等级")
    private Integer priority;

    @Schema(description = "部门描述")
    private String description;

    @Schema(description = "下级部门")
    private List<DepartmentDTO> children;

}

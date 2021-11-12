package org.shanzhaozhen.basiccommon.dto;

import org.shanzhaozhen.basiccommon.domain.BaseInfo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "资源DTO实体")
public class ResourceDTO extends BaseInfo {

    private static final long serialVersionUID = -1919471263242925847L;

    @Schema(title = "主键ID")
    private Long id;

    @Schema(title = "资源名称")
    private String name;

    @Schema(title = "资源路由")
    private String path;

    @Schema(title = "资源类型")
    private Integer type;

    @Schema(title = "上级ID")
    private Long pid;

    @Schema(title = "排序等级")
    private Integer priority;

//    @Schema(title = "支持Get请求")
//    private Boolean supportGet;
//
//    @Schema(title = "支持Post请求")
//    private Boolean supportPost;
//
//    @Schema(title = "支持Put请求")
//    private Boolean supportPut;
//
//    @Schema(title = "支持Delete请求")
//    private Boolean supportDelete;
//
//    @Schema(title = "支持Patch请求")
//    private Boolean supportPatch;

    @Schema(title = "资源描述")
    private String description;

    private List<RoleDTO> roles;

    private List<ResourceDTO> children;
}

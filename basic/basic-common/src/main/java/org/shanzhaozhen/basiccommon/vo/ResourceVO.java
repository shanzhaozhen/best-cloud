package org.shanzhaozhen.basiccommon.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "资源VO实体")
public class ResourceVO extends BaseInfoVO {

    @Schema(title = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(title = "资源名称")
    private String name;

    @Schema(title = "资源路由")
    private String path;

    @Schema(title = "资源类型")
    private Integer type;

    @Schema(title = "上级ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    @Schema(title = "排序等级")
    private Integer priority;

    @Schema(title = "支持Get请求")
    private Boolean supportGet;

    @Schema(title = "支持Post请求")
    private Boolean supportPost;

    @Schema(title = "支持Put请求")
    private Boolean supportPut;

    @Schema(title = "支持Delete请求")
    private Boolean supportDelete;

    @Schema(title = "支持Patch请求")
    private Boolean supportPatch;

    @Schema(title = "资源描述")
    private String description;

    private List<org.shanzhaozhen.basiccommon.vo.RoleVO> roles;

    private List<ResourceVO> children;
}

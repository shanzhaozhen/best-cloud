package org.shanzhaozhen.uaa.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfoVO;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "权限VO实体")
public class PermissionVO extends BaseInfoVO {

    @Schema(description = "主键ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限路由")
    private String path;

    @Schema(description = "权限类型")
    private Integer type;

    @Schema(description = "上级ID")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long pid;

    @Schema(description = "排序等级")
    private Integer priority;

    @Schema(description = "支持Get请求")
    private Boolean supportGet;

    @Schema(description = "支持Post请求")
    private Boolean supportPost;

    @Schema(description = "支持Put请求")
    private Boolean supportPut;

    @Schema(description = "支持Delete请求")
    private Boolean supportDelete;

    @Schema(description = "支持Patch请求")
    private Boolean supportPatch;

    @Schema(description = "权限描述")
    private String description;

    private List<RoleVO> roles;

    private List<PermissionVO> children;
}

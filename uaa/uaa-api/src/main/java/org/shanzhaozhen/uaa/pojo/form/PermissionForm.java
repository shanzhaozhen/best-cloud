package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "权限Form实体")
public class PermissionForm {

    @Schema(description = "主键ID")
    @NotNull(groups = {Update.class}, message = "权限id不能为空")
    private Long id;

    @Schema(description = "权限名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "权限名称不能为空")
    private String name;

    @Schema(description = "权限路由")
    private String path;

    @Schema(description = "权限类型")
    private Integer type;

    @Schema(description = "上级ID")
    private Long pid;

    @Schema(description = "排序等级")
    private Integer priority;

    @Schema(description = "支持Get请求")
    private Boolean supportGet;

//    @Schema(description = "支持Post请求")
//    private Boolean supportPost;
//
//    @Schema(description = "支持Put请求")
//    private Boolean supportPut;
//
//    @Schema(description = "支持Delete请求")
//    private Boolean supportDelete;
//
//    @Schema(description = "支持Patch请求")
//    private Boolean supportPatch;

    @Schema(description = "权限描述")
    private String description;

}

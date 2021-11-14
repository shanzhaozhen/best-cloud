package org.shanzhaozhen.security.form;

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
@Schema(description = "资源Form实体")
public class ResourceForm {

    @Schema(title = "主键ID")
    @NotNull(groups = {Update.class}, message = "资源id不能为空")
    private Long id;

    @Schema(title = "权限名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "资源名称不能为空")
    private String name;

    @Schema(title = "资源路由")
    private String path;

    @Schema(title = "资源类型")
    private Integer type;

    @Schema(title = "上级ID")
    private Long pid;

    @Schema(title = "排序等级")
    private Integer priority;

    @Schema(title = "支持Get请求")
    private Boolean supportGet;

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

}

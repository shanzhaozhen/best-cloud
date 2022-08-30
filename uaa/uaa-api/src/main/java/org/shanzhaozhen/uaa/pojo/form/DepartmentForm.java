package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "部门Form实体")
public class DepartmentForm extends BaseInfo {

    @Schema(description = "主键ID")
    @NotNull(groups = {Update.class}, message = "权限id不能为空")
    private String id;

    @Schema(description = "上级ID")
    private String pid;

    @Schema(description = "部门名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "部门名称不能为空")
    private String name;

    @Schema(description = "部门名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "部门编码不能为空")
    private String code;

    @Schema(description = "排序等级")
    private Integer priority;

    @Schema(description = "部门描述")
    private String description;

}

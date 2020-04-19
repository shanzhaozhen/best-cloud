package org.shanzhaozhen.bestcloudcommon.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "角色Form实体")
public class RoleForm {

    @ApiModelProperty(value = "主键ID")
    @NotNull(groups = {Update.class}, message = "角色id不能为空")
    private Long id;

    @ApiModelProperty(value = "名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "角色名称不能为空")
    private String name;

    @ApiModelProperty(value = "标识名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "标识名称不能为空")
    private String identification;

    @ApiModelProperty(value = "描述")
    private String description;

    @ApiModelProperty(value = "关联的路由id")
    private List<Long> routeIds;

    @ApiModelProperty(value = "关联的资源id")
    private List<Long> resourceIds;

}

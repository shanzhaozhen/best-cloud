package org.shanzhaozhen.uaa.pojo.form;


import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "角色Form实体")
public class RoleForm {

    @Schema(description = "主键ID")
    @NotNull(groups = {Update.class}, message = "角色id不能为空")
    private String id;

    @Schema(description = "名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "角色名称不能为空")
    private String name;

    @Schema(description = "角色编码")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "角色编码不能为空")
    private String code;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "关联的菜单id")
    private List<String> menuIds;

    @Schema(description = "关联的资源id")
    private List<String> permissionIds;

}

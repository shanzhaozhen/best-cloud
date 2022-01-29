package org.shanzhaozhen.uaa.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "菜单Form实体")
public class MenuForm {

    @Schema(title = "主键ID")
    @NotNull(groups = {Update.class}, message = "资源id不能为空")
    private Long id;

    @Schema(title = "菜单名称")
    @NotNull(groups = {Insert.class, Update.class}, message = "资源名称不能为空")
    private String name;

    @Schema(title = "菜单名称（本地化）")
    private String locale;

    @Schema(title = "菜单路径")
    private String path;

    @Schema(title = "上级ID")
    private Long pid;

    @Schema(title = "图标")
    private String icon;

    @Schema(title = "排序等级")
    private Integer priority;

    @Schema(title = "菜单是否隐藏")
    private Boolean hideInMenu;

    @Schema(title = "隐藏子节点")
    private Boolean hideChildrenInMenu;

    @Schema(title = "参数")
    private String props;

    @Schema(title = "菜单描述")
    private String description;

}

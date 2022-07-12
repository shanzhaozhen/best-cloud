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

    @Schema(description = "主键ID")
    @NotNull(groups = {Update.class}, message = "资源id不能为空")
    private Long id;

    @Schema(description = "上级ID")
    private Long pid;

    @Schema(description = "菜单名称")
    @NotNull(groups = {Insert.class, Update.class}, message = "资源名称不能为空")
    private String name;

    @Schema(description = "菜单名称（本地化）")
    private String locale;

    @Schema(description = "菜单路径")
    private String path;

    @Schema(description = "图标")
    private String icon;

    @Schema(description = "排序等级")
    private Integer priority;

    @Schema(description = "菜单是否隐藏")
    private Boolean hideInMenu;

    @Schema(description = "隐藏子节点")
    private Boolean hideChildrenInMenu;

    @Schema(description = "参数")
    private String props;

    @Schema(description = "菜单描述")
    private String description;

}

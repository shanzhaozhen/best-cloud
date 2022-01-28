package org.shanzhaozhen.uaa.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.entity.BaseInfo;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
@Schema(description = "菜单DO实体")
public class MenuDO extends BaseInfo {

    private static final long serialVersionUID = 4485640590947953262L;

    @Schema(title = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "菜单名称")
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

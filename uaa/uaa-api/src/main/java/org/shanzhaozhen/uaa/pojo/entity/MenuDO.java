package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_menu")
@Schema(description = "菜单DO实体")
public class MenuDO extends BaseInfo {

    private static final long serialVersionUID = 4485640590947953262L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "上级ID")
    private String pid;

    @Schema(description = "菜单名称")
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

    @Schema(description = "版本号")
    @Version
    private Integer version;

}

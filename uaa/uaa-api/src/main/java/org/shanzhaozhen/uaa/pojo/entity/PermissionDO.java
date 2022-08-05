package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseEntity;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_permission")
@Schema(description = "权限DO实体")
public class PermissionDO extends BaseEntity {

    private static final long serialVersionUID = 4485640590947953262L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "权限名称")
    private String name;

    @Schema(description = "权限路由")
    private String path;

    @Schema(description = "权限类型")
    private Integer type;

    @Schema(description = "上级ID")
    private String pid;

    @Schema(description = "排序等级")
    private Integer priority;

//    @Schema(description = "支持Get请求")
//    private Boolean supportGet;
//
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

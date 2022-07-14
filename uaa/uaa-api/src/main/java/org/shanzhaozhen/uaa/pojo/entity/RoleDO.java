package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;


@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_role")
@Schema(description = "角色DO实体")
public class RoleDO extends BaseInfo {

    private static final long serialVersionUID = 6203528166202612882L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "角色编码")
    private String code;

    @Schema(description = "描述")
    private String description;

}

package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_department_user")
@Schema(description = "部门-用户关系DO实体")
public class DepartmentUserDO extends BaseInfo {

    private static final long serialVersionUID = 7181393744926289326L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "部门ID")
    private String departmentId;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "版本号")
    @Version
    private Integer version;

}

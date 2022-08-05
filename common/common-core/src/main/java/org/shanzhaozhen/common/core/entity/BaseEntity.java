package org.shanzhaozhen.common.core.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "实体基础信息")
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -2522409732119831350L;

    @Schema(description = "版本号")
    @Version
    private Integer version;

    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建人")
    private Long createdBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    @Schema(description = "创建时间")
    private LocalDateTime createdDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改人")
    private Long lastModifiedBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @Schema(description = "修改时间")
    private LocalDateTime lastModifiedDate;

}

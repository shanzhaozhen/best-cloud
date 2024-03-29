package org.shanzhaozhen.common.core.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "基础信息VO实体")
public class BaseInfoVO implements Serializable {

    private static final long serialVersionUID = -4890503939284694535L;

    @Schema(description = "创建人")
    private Long createdBy;

    @Schema(description = "创建人名称")
    private String createdByName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    private LocalDateTime createdDate;

    @Schema(description = "修改人")
    private Long lastModifiedBy;

    @Schema(description = "修改人名称")
    private String lastModifiedByName;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "修改时间")
    private LocalDateTime lastModifiedDate;

}

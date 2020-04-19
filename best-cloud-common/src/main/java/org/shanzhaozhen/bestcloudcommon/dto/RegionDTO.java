package org.shanzhaozhen.bestcloudcommon.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "区域信息DTO实体")
public class RegionDTO {

    @ApiModelProperty(value = "主键ID")
    private Long id;

    @ApiModelProperty(value = "父级ID")
    private Long pid;

    @ApiModelProperty(value = "区域名称")
    private String name;

    @ApiModelProperty(value = "区域编码")
    private String code;

    @ApiModelProperty(value = "层级")
    private Integer level;

    @ApiModelProperty(value = "下级区域")
    private List<RegionDTO> children;

}

package org.shanzhaozhen.bestcloudcommon.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Update;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "区域信息Form实体")
public class RegionForm {

    @ApiModelProperty(value = "主键ID")
    @NotNull(groups = {Update.class}, message = "资源id不能为空")
    private Long id;

    @ApiModelProperty(value = "父级ID")
    private Long pid;

    @ApiModelProperty(value = "区域名称")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "区域名称不能为空")
    private String name;

    @ApiModelProperty(value = "区域编码")
    @NotEmpty(groups = {Insert.class, Update.class}, message = "区域编码不能为空")
    private String code;

    @ApiModelProperty(value = "层级")
    @NotNull(groups = {Insert.class, Update.class}, message = "层级不能为空")
    private Integer level;

}

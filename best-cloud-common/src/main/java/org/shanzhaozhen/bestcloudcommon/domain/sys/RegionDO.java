package org.shanzhaozhen.bestcloudcommon.domain.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.bestcommon.domain.BaseEntity;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_region")
@ApiModel(description = "地区信息DO实体")
public class RegionDO extends BaseEntity {

    private static final long serialVersionUID = 216445339652015543L;

    @ApiModelProperty(value = "主键ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父级ID")
    private Long pid;

    @ApiModelProperty(value = "区域名称")
    private String name;

    @ApiModelProperty(value = "区域编码")
    private String code;

    @ApiModelProperty(value = "层级")
    private Integer level;

}

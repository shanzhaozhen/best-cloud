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
@TableName("sys_dictionary")
@ApiModel(description = "字典DO实体")
public class DictionaryDO extends BaseEntity {

    private static final long serialVersionUID = -4727379501712632270L;

    @ApiModelProperty(value = "主键ID")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "上级ID")
    private Long pid;

}

package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.shanzhaozhen.common.core.entity.BaseEntity;
import org.shanzhaozhen.common.core.entity.BaseInfo;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
@Getter
@Setter
@Schema(description = "社区账号")
public class SocialUser extends BaseEntity {

    private static final long serialVersionUID = 9067272005572561832L;

    @Schema(description = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    @Schema(description = "社区账号名")
    private String username;

    @Schema(description = "关联用户id")
    private String userId;

}

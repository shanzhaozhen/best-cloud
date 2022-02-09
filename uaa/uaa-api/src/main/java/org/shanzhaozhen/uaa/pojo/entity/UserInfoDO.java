package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.entity.BaseInfo;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@Schema(description = "用户DO实体")
public class UserInfoDO extends BaseInfo {

    private static final long serialVersionUID = 239637250684871342L;

    @Schema(title = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "关联用户id")
    private Long pid;

    @Schema(title = "部门ID")
    private Long depId;

    @Schema(title = "姓名")
    private String name;

    @Schema(title = "昵称")
    private String nickname;

    @Schema(title = "性别")
    private Integer sex;

    @Schema(title = "生日")
    private Date birthday;

    @Schema(title = "头像")
    private String avatar;

    @Schema(title = "邮箱")
    private String email;

    @Schema(title = "手机号码")
    private String phoneNumber;

    @Schema(title = "地址编号")
    private String addressCode;

    @Schema(title = "详细地址")
    private String detailedAddress;

    @Schema(title = "个人介绍")
    private String introduction;

}

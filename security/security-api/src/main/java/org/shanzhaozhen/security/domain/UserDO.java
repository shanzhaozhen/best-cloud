package org.shanzhaozhen.security.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.entity.BaseInfo;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user")
@Schema(description = "用户DO实体")
public class UserDO extends BaseInfo {

    private static final long serialVersionUID = 3064727069207896868L;

    @Schema(title = "主键ID")
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;

    @Schema(title = "用户名")
    private String username;

    @Schema(title = "密码")
    private String password;

    @Schema(title = "部门ID")
    private Long depId;

    @Schema(title = "账户是否过期,过期无法验证")
    private boolean accountNonExpired = true;

    @Schema(title = "指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证")
    private boolean accountNonLocked = true;

    @Schema(title = "指示是否已过期的用户的凭据(密码),过期的凭据防止认证")
    private boolean credentialsNonExpired = true;

    @Schema(title = "是否被禁用,禁用的用户不能身份验证")
    private boolean enabled = true;

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

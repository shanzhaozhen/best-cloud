package org.shanzhaozhen.uaa.pojo.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sys_user_github")
@Schema(description = "github用户DO实体")
public class GithubUser extends SocialUser {

    @Schema(description = "登陆名")
    private String login;

    @Schema(description = "github用户ID")
    @TableField(updateStrategy = FieldStrategy.IGNORED)
    private String githubId;

    @Schema(description = "节点ID")
    private String nodeId;

    @Schema(description = "头像")
    private String avatarUrl;

    @Schema(description = "邮箱")
    private String email;

    @Schema(description = "昵称")
    private String name;

    @Schema(description = "其他信息")
    private String other;

}

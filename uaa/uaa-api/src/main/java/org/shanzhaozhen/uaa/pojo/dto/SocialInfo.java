package org.shanzhaozhen.uaa.pojo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-06
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "社区用户信息实体")
public class SocialInfo {

    GithubUserInfo github;

}

package org.shanzhaozhen.authorize.pojo.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.authorize.pojo.vo.OAuth2UserSocialVO;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-06
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "社区用户信息实体")
public class SocialInfo {

    OAuth2UserSocialVO github;

}

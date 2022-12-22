package org.shanzhaozhen.authorize.pojo.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.authorize.pojo.entity.SocialUser;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-02
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "社区用户form绑定实体")
public class SocialUserBindForm<T extends SocialUser> {

    private String userId;

    private T socialUser;

}

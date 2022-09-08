package org.shanzhaozhen.uaa.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-09-01
 * @Description: 
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SocialType {

    GITHUB("github", "github-idp"),
    GITEE("gitee", "gitee"),
    GOOGLE("google", "google"),
    QQ("QQ", "QQ");

    private String name;

    private String registrationId;

}

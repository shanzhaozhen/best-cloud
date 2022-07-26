package org.shanzhaozhen.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum LoginType {

    PHONE("手机登陆"),
    ACCOUNT("账号登陆"),
    QQ("QQ登陆"),
    WECHAT("微信登陆"),
    WEIBO("微博登陆"),

    ;

    private String name;

}

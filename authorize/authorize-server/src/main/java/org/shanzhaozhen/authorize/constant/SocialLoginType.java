package org.shanzhaozhen.authorize.constant;

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
public enum SocialLoginType {

    LOGIN_TYPE("登陆", "login"),
    BIND_TYPE("绑定", "bind");


    private String name;

    private String value;
}

package org.shanzhaozhen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodeConst {

    /**
     * 1001: 无权限访问
     * 1002: access_token过期
     * 1003: unique_token无效
     * ...
     * // 用户相关
     * 2001: 未登录
     * 2002: 用户信息错误
     * 2003: 用户不存在
     * // 业务1
     * 3001: 业务1XXX
     * 3002: 业务1XXX
     */

    LOGIN_SUCCESS(2000, "登陆成功"),

    LOGIN_ERROR(4010, "登陆异常"),

    JWT_SIGNATURE(4011, "token签名异常"),

    JWT_MALFORMED(4012, "token格式不正确"),

    JWT_EXPIRED(4013, "token已过期"),

    JWT_UNSUPPORTED(4014, "不支持该token"),

    JWT_ILLEGALARGUMENT(4015, "token参数异常"),

    JWT_ERROR(4016, "token错误"),




    ;

    private int code;
    private String reason;

}

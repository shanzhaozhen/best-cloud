package org.shanzhaozhen.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.constant.IResultCode;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum CodeConst implements IResultCode {

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

    LOGIN_SUCCESS("200", "登陆成功"),

    LOGIN_ERROR("410", "登陆异常"),

    JWT_SIGNATURE("411", "token签名异常"),

    JWT_MALFORMED("412", "token格式不正确"),

    JWT_EXPIRED("413", "token已过期"),

    JWT_UNSUPPORTED("414", "不支持该token"),

    JWT_ILLEGALARGUMENT("415", "token参数异常"),

    JWT_ERROR("416", "token错误"),

    UNKNOWN_ERROR("500", "未知错误"),


    ;

    private String code;
    private String message;

}

package org.shanzhaozhen.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum JwtErrorConst {

    LOGIN_SUCCESS("2000", "登陆成功"),

    LOGIN_ERROR("4010", "登陆异常"),

    JWT_SIGNATURE("4011", "token签名异常"),

    JWT_MALFORMED("4012", "token格式不正确"),

    JWT_EXPIRED("4013", "token已过期"),

    JWT_UNSUPPORTED("4014", "不支持该token"),

    JWT_ILLEGAL_ARGUMENT("4015", "token参数异常"),

    JWT_ERROR("4016", "token错误");

    private String code;
    private String reason;

}

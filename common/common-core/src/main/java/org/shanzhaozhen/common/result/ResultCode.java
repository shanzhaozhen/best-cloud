package org.shanzhaozhen.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    LOGIN_SUCCESS("2000", "登陆成功"),
    LOGIN_ERROR("4010", "登陆异常"),

    JWT_SIGNATURE("4011", "token签名异常"),
    JWT_MALFORMED("4012", "token格式不正确"),
    JWT_EXPIRED("4013", "token已过期"),
    JWT_UNSUPPORTED("4014", "不支持该token"),
    JWT_ILLEGAL_ARGUMENT("4015", "token参数异常"),
    JWT_ERROR("4016", "token错误"),
    JWT_FORBIDDEN("4030", "token已被禁用"),




    UNKNOWN_ERROR("5000", "未知错误"),


    SYSTEM_EXECUTION_ERROR("B0001", "系统执行出错"),
    SYSTEM_EXECUTION_TIMEOUT("B0100", "系统执行超时"),


    ;

    private String code;
    private String message;

    public static ResultCode getValue(String code){
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // 默认系统执行错误
    }

}

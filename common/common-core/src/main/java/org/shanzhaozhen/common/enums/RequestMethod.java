package org.shanzhaozhen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RequestMethod {

    GET("查看", "GET"),
    POST("新增", "POST"),
    PUT("修改", "PUT"),
    DELETE("删除", "DELETE"),
    PATCH("更新", "PATCH");

    private String name;
    private String value;

}


package org.shanzhaozhen.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RequestMethod {

    GET("查看", "GET"),
    POST("新增", "POST"),
    PUT("修改", "PUT"),
    DELETE("删除", "DELETE"),
    PATCH("更新", "PATCH");

    private String name;

    private String value;

}


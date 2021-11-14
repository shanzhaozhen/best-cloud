package org.shanzhaozhen.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TimeSlot {

    ONEWEEK("近一周"),
    ONEMOON("近一个月"),
    TREEMOON("近三个月"),
    ONEYEAR("近一年"),
    TREEYEAR("近三年"),
    ALL("全部");

    private String name;

}

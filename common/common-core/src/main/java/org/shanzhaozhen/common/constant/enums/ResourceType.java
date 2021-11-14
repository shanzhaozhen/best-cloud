package org.shanzhaozhen.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum ResourceType {

    KID("类型" ,0),
    API("API" ,1),
    BUTTON("按钮" ,2);

    private String name;

    private Integer value;

}

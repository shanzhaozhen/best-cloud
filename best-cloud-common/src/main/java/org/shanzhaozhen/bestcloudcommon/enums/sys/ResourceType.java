package org.shanzhaozhen.bestcloudcommon.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResourceType {

    KID("类型" ,0),
    API("API" ,1),
    BUTTON("按钮" ,2);

    private String name;

    private Integer value;

}

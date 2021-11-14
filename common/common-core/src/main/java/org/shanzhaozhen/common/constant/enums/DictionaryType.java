package org.shanzhaozhen.common.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DictionaryType {

    KID("类型" ,0),
    VALUE("值" ,1);

    private String name;

    private Integer value;

}

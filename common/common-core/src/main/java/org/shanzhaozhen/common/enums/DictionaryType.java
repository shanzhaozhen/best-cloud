package org.shanzhaozhen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.entity.EnumParam;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum DictionaryType {

    KID("类型" ,0),
    VALUE("值" ,1);

    private String name;

    private Integer value;

}

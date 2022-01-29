package org.shanzhaozhen.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum SexType {

    SECRECY("保密" ,0),
    MALE("男" ,1),
    FEMALE("女" ,2);

    private String name;

    private Integer value;

}

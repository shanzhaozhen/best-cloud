package org.shanzhaozhen.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SexType {

    SECRECY("保密" ,0),
    MALE("男" ,1),
    FEMALE("女" ,2);

    private String name;

    private Integer value;

}

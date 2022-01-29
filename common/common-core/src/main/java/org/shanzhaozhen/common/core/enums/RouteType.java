package org.shanzhaozhen.common.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.shanzhaozhen.common.core.constant.IEnumType;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum RouteType implements IEnumType {

    PATH("路径" ,0),
    MENU("菜单" ,1),
    API("API" ,2),
    BUTTON("按钮" ,3);

    private String name;

    private Integer value;

}

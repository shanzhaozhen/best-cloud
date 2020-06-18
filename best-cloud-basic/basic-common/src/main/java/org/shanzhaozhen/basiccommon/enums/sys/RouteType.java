package org.shanzhaozhen.basiccommon.enums.sys;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.shanzhaozhen.basiccommon.param.EnumParam;

import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public enum RouteType {

    PATH("路径" ,0),
    MENU("菜单" ,1),
    API("API" ,2),
    BUTTON("按钮" ,3);

    private String name;

    private Integer value;

    public static List<EnumParam> toList() {
        List<EnumParam> list = new ArrayList<>();
        for (RouteType routeType : RouteType.values()) {
            EnumParam enumParam = new EnumParam(routeType.getName(), routeType.getValue());
            list.add(enumParam);
        }
        return list;
    }
}

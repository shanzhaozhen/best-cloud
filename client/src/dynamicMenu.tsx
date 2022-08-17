import type {MenuDataItem} from "@ant-design/pro-components";
import {iconMap} from "@/components/IconOption";
import React from "react";

const loopMenuItem = (menuData: MenuDataItem[]): MenuDataItem[] =>
  menuData.map(({ icon, children, ...item }) => ({
    ...item,
    icon: icon && iconMap[icon as string] && React.createElement(iconMap[icon as string]),
    access: '',
    children: children && loopMenuItem(children),
  }));

export const menuDataRender = (menuData: MenuDataItem[] | undefined): MenuDataItem[] => (menuData && loopMenuItem(menuData)) || []

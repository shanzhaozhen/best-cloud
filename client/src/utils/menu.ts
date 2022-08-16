import {useEffect, useState} from "react";
import {getMenuTree} from "@/services/uaa/menu";
import type {MenuVO} from "@/services/uaa/type/menu";

const loopMenuData = (menuData: MenuVO[]): any =>
  menuData.map(({ id, name, children }) => ({
    title: name,
    key: id,
    children: children && loopMenuData(children),
  }));

export const useMenuTree = () => {
  const [menuTree, setMenuTree] = useState<[]>();
  useEffect(() => {
    getMenuTree().then(({ data }) => {
      setMenuTree(loopMenuData(data || []))
    });

    return () => setMenuTree([]);
  }, []);
  return menuTree
}

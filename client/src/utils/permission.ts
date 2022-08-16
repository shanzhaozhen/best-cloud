import {useEffect, useState} from "react";
import type {PermissionVO} from "@/services/uaa/type/permission";
import {getPermissionTree} from "@/services/uaa/permission";

const loopPermissionData = (permissionData: PermissionVO[]): any =>
  permissionData.map(({ id, name, children }) => ({
    title: name,
    key: id,
    children: children && loopPermissionData(children),
  }));

export const usePermissionTree = () => {
  const [permissionTree, setPermissionTree] = useState<[]>();
  useEffect(() => {
    getPermissionTree().then(({ data }) => {
      setPermissionTree(loopPermissionData(data || []))
    });

    return () => setPermissionTree([]);
  }, []);
  return permissionTree
}

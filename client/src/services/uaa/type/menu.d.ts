import type {RoleVO} from "@/services/uaa/type/role";

export type MenuForm = {
  /** 主键ID */
  id: number;
  /** 菜单名称 */
  name: string;
  /** 菜单名称（本地化） */
  locale?: string;
  /** 菜单路径 */
  path?: string;
  /** 上级ID */
  pid?: number;
  /** 图标 */
  icon?: string;
  /** 排序等级 */
  priority?: number;
  /** 菜单是否隐藏 */
  hideInMenu?: boolean;
  /** 隐藏子节点 */
  hideChildrenInMenu?: boolean;
  /** 参数 */
  props?: string;
  /** 菜单描述 */
  description?: string;
};



type MenuDTO = {
  /** 主键ID */
  id?: number;
  /** 菜单名称 */
  name?: string;
  /** 菜单名称（本地化） */
  locale?: string;
  /** 菜单路径 */
  path?: string;
  /** 上级ID */
  pid?: number;
  /** 图标 */
  icon?: string;
  /** 排序等级 */
  priority?: number;
  /** 菜单是否隐藏 */
  hideInMenu?: boolean;
  /** 隐藏子节点 */
  hideChildrenInMenu?: boolean;
  /** 参数 */
  props?: string;
  /** 菜单描述 */
  description?: string;
  /** 关联的角色 */
  roles?: RoleDTO[];
  /** 下级菜单 */
  children?: MenuDTO[];
};

export type MenuVO = {
  /** 主键ID */
  id?: number;
  /** 菜单名称 */
  name?: string;
  /** 菜单名称（本地化） */
  locale?: string;
  /** 菜单路径 */
  path?: string;
  /** 上级ID */
  pid?: number;
  /** 图标 */
  icon?: string;
  /** 排序等级 */
  priority?: number;
  /** 菜单是否隐藏 */
  hideInMenu?: boolean;
  /** 隐藏子节点 */
  hideChildrenInMenu?: boolean;
  /** 参数 */
  props?: string;
  /** 菜单描述 */
  description?: string;
  /** 关联的角色 */
  roleVOList?: RoleVO[];
  /** 下级菜单 */
  children?: MenuVO[];
  /** 授权角色 */
  access?: string[];
  /** 创建人 */
  createdBy?: number;
  /** 创建人名称 */
  createdByName?: string;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改人名称 */
  lastModifiedByName?: string;
  /** 修改时间 */
  lastModifiedDate?: string;
};

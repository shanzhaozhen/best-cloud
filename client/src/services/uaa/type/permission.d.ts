import type {RoleDTO, RoleVO} from "@/services/uaa/type/role";

export type PermissionForm = {
  /** 主键ID */
  id: number;
  /** 权限名称 */
  name: string;
  /** 权限路由 */
  path?: string;
  /** 权限类型 */
  type?: number;
  /** 上级ID */
  pid?: string;
  /** 排序等级 */
  priority?: number;
  /** 支持Get请求 */
  supportGet?: boolean;
  /** 权限描述 */
  description?: string;
};

export type PermissionDTO = {
  /** 主键ID */
  id?: string;
  /** 权限名称 */
  name?: string;
  /** 权限路由 */
  path?: string;
  /** 权限类型 */
  type?: number;
  /** 上级ID */
  pid?: string;
  /** 排序等级 */
  priority?: number;
  /** 权限描述 */
  description?: string;
  roles?: RoleDTO[];
  children?: PermissionDTO[];
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
};

export

type PermissionVO = {
  /** 主键ID */
  id?: string;
  /** 权限名称 */
  name?: string;
  /** 权限路由 */
  path?: string;
  /** 权限类型 */
  type?: number;
  /** 上级ID */
  pid?: string;
  /** 排序等级 */
  priority?: number;
  /** 支持Get请求 */
  supportGet?: boolean;
  /** 支持Post请求 */
  supportPost?: boolean;
  /** 支持Put请求 */
  supportPut?: boolean;
  /** 支持Delete请求 */
  supportDelete?: boolean;
  /** 支持Patch请求 */
  supportPatch?: boolean;
  /** 权限描述 */
  description?: string;
  roles?: RoleVO[];
  children?: PermissionVO[];
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

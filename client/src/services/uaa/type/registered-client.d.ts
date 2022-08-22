export type RegisteredClientForm = {
  /** 主键ID */
  id: string;
  /** 名称 */
  name: string;
  /** 角色编码 */
  code: string;
  /** 描述 */
  description?: string;
  /** 关联的菜单id */
  menuIds?: number[];
  /** 关联的资源id */
  permissionIds?: number[];
};

export type RegisteredClientBase = {
  /** 主键ID */
  id?: string;
  /** 名称 */
  name?: string;
  /** 标识名称 */
  identification?: string;
};

export type RegisteredClientVO = {
  /** 主键ID */
  id?: string;
  /** 名称 */
  name?: string;
  /** 角色编码 */
  code?: string;
  /** 描述 */
  description?: string;
  /** 关联的菜单id */
  menuIds?: number[];
  /** 关联的资源id */
  permissionIds?: number[];
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

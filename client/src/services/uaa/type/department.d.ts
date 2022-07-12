export type DepartmentForm = {
  /** 主键ID */
  id?: number;
  /** 上级ID */
  pid?: number;
  /** 部门名称 */
  name?: string;
  /** 排序等级 */
  priority?: number;
  /** 部门描述 */
  description?: string;
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
};

export type DepartmentDTO = {
  /** 主键ID */
  id?: number;
  /** 上级ID */
  pid?: number;
  /** 部门名称 */
  name?: string;
  /** 排序等级 */
  priority?: number;
  /** 部门描述 */
  description?: string;
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
  /** 下级部门 */
  children?: DepartmentDTO[];
};

export type DepartmentVO = {
  /** 主键ID */
  id?: number;
  /** 上级ID */
  pid?: number;
  /** 部门名称 */
  name?: string;
  /** 排序等级 */
  priority?: number;
  /** 部门描述 */
  description?: string;
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
  /** 下级部门 */
  children?: DepartmentVO[];
};

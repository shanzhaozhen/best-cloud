export type BaseInfoVO = {
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

export type BaseInfo = {
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
};

export type GrantedAuthority = {
  authority?: string;
};

export type R<T> = {
  /** 业务状态码 */
  code?: string;
  /** 返回的信息 */
  message?: string;
  /** 返回的数据 */
  data?: T;
  /** 请求完成的时间 */
  timestamp?: number;
};

export type Page<T> = {
  records?: T[];
  total?: number;
  size?: number;
  current?: number;
  orders?: OrderItem[];
  optimizeCountSql?: boolean;
  searchCount?: boolean;
  optimizeJoinOfCountSql?: boolean;
  countId?: string;
  maxLimit?: number;
  pages?: number;
};

export type OrderItem = {
  column?: string;
  asc?: boolean;
};

export type PageParams = {
  /** 关键字 */
  keyword?: string;
  /** 当前的页码 */
  current?: number;
  /** 分页大小 */
  size?: number;
  /** 升序 */
  asc?: string[],
  /** 降序 */
  desc?: string[],
  /** 其他参数 */
  [key: string]: any
};

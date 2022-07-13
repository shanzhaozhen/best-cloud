export type UserInfoForm = {
  /** 主键ID */
  id: number;
  /** 关联用户id */
  pid: number;
  /** 姓名 */
  name?: string;
  /** 昵称 */
  nickname?: string;
  /** 性别 */
  sex?: number;
  /** 生日 */
  birthday?: string;
  /** 头像 */
  avatar?: string;
  /** 邮箱 */
  email?: string;
  /** 手机号码 */
  phoneNumber?: string;
  /** 地址编号 */
  addressCode?: string;
  /** 详细地址 */
  detailedAddress?: string;
  /** 个人介绍 */
  introduction?: string;
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
};

export type UserInfoVO = {
  /** 主键ID */
  id?: number;
  /** 关联用户ID */
  pid?: number;
  /** 姓名 */
  name?: string;
  /** 昵称 */
  nickname?: string;
  /** 性别 */
  sex?: number;
  /** 生日 */
  birthday?: string;
  /** 头像 */
  avatar?: string;
  /** 邮箱 */
  email?: string;
  /** 手机号码 */
  phoneNumber?: string;
  /** 地址编号 */
  addressCode?: string;
  /** 详细地址 */
  detailedAddress?: string;
  /** 个人介绍 */
  introduction?: string;
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

export type UserInfoDTO = {
  /** 主键ID */
  id?: number;
  /** 关联用户id */
  pid?: number;
  /** 姓名 */
  name?: string;
  /** 昵称 */
  nickname?: string;
  /** 性别 */
  sex?: number;
  /** 生日 */
  birthday?: string;
  /** 头像 */
  avatar?: string;
  /** 邮箱 */
  email?: string;
  /** 手机号码 */
  phoneNumber?: string;
  /** 地址编号 */
  addressCode?: string;
  /** 详细地址 */
  detailedAddress?: string;
  /** 个人介绍 */
  introduction?: string;
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
};


import type {RoleDTO} from "@/services/uaa/type/role";
import {GrantedAuthority} from "@/services/common/typings";

export type UserForm = {
  /** 主键ID */
  id?: number;
  /** 用户名 */
  username?: string;
  /** 密码 */
  password?: string;
  /** 账户是否过期,过期无法验证 */
  accountNonExpired?: boolean;
  /** 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证 */
  accountNonLocked?: boolean;
  /** 指示是否已过期的用户的凭据(密码),过期的凭据防止认证 */
  credentialsNonExpired?: boolean;
  /** 是否被禁用,禁用的用户不能身份验证 */
  enabled?: boolean;
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
  /** 关联的角色id */
  roleIds?: number[];
};

export type UserVO = {
  /** 主键ID */
  id?: number;
  /** 用户名 */
  username?: string;
  authorities?: GrantedAuthority[];
  /** 账户是否过期,过期无法验证 */
  accountNonExpired?: boolean;
  /** 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证 */
  accountNonLocked?: boolean;
  /** 指示是否已过期的用户的凭据(密码),过期的凭据防止认证 */
  credentialsNonExpired?: boolean;
  /** 是否被禁用,禁用的用户不能身份验证 */
  enabled?: boolean;
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
  /** 关联的角色id */
  roleIds?: number[];
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

export type UserDTO = {
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
  /** 主键ID */
  id?: number;
  /** 用户名 */
  username?: string;
  /** 密码 */
  password?: string;
  /** 账户是否过期,过期无法验证 */
  accountNonExpired?: boolean;
  /** 指定用户是否被锁定或者解锁,锁定的用户无法进行身份验证 */
  accountNonLocked?: boolean;
  /** 指示是否已过期的用户的凭据(密码),过期的凭据防止认证 */
  credentialsNonExpired?: boolean;
  /** 是否被禁用,禁用的用户不能身份验证 */
  enabled?: boolean;
  /** 记录用户的角色 */
  roles?: RoleDTO[];
  /** 关联的角色ID */
  roleIds?: number[];
  userInfoDTO?: UserInfoDTO;
};

export type UserInfoDTO = {
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
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
};

export type CurrentUser = {
  userInfo?: UserInfoVO;
  /** 角色 */
  roles?: RoleBase[];
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



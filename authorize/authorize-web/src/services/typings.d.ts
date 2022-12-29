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

type FakeCaptcha = {
  code?: number;
  status?: string;
};

type LoginResult = {
  status?: string;
  type?: string;
  currentAuthority?: string;
}

export type AccountLogin = {
  /** 用户名 */
  username?: string;
  /** 密码 */
  password?: string;
  /** 自动登陆 */
  autoLogin?: boolean;
};

export type PhoneLogin = {
  /** 手机号 */
  phone?: string;
  /** 验证码 */
  captcha?: string;
  /** 自动登陆 */
  autoLogin?: boolean;
};

export type UserInfoForm = {
  /** 主键ID */
  id: string;
  /** 关联用户id */
  pid: string;
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

export type UserInfoVO = {
  /** 主键ID */
  id: string;
  /** 关联用户id */
  pid: string;
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

export type ChangePasswordForm = {
  /** 旧密码 */
  oldPassword?: string;
  /** 新密码 */
  newPassword?: string;
};

export type BindPhoneForm = {
  /** 手机号 */
  phone: string;
  /* 验证码 */
  captcha: string
};

export type SocialInfo = {
  /** github 账号 */
  github?: GithubUserInfo;
};

export type GithubUserInfo = {
  /** github 用户名 */
  username?: string;
  /** 头像 */
  avatarUrl?: string;
  /** 绑定时间 */
  bindDate?: string;
};

export type SecurityInfo = {
  /** 密码安全等级 */
  security?: string;
  /** 手机号 */
  phone?: string;
};

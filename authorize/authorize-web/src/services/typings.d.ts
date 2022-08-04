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

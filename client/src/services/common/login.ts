// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import {LoginParams, LoginResult, R} from "@/services/common/typings";


/** 登录接口 POST /api/authorize/oauth2/token */
export async function login(body: LoginParams, options?: Record<string, any>) {
  return request<LoginResult>('/api/authorize/oauth2/token', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': 'Basic YXV0aDoxMjM0NTY=' // 客户端信息Base64明文：auth:123456
    },
    params: {
      grant_type: 'password',
      ...body
    },
    ...(options || {}),
  });
}

/** 登出用户接口 GET /user/logout */
export async function logout(options?: Record<string, any>) {
  return request<R<boolean>>(`/api/uaa/user/logout`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 发送验证码 POST /api/login/captcha */
export async function getFakeCaptcha(
  params: {
    // query
    /** 手机号 */
    phone?: string;
  },
  options?: Record<string, any>,
) {
  return request<API.FakeCaptcha>('/api/login/captcha', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';
import {FakeCaptcha, PhoneLogin, R} from "@/services/typings";
import {CurrentUser} from "@/pages/AccountManage/data";

const apiRoot = process.env.NODE_ENV === 'production' ? '' : '/api'

/** 获取当前用户基础信息 POST /user/base */
export async function getCurrentUserBaseInfo(options?: Record<string, any>) {
  return request<R<CurrentUser>>(`${apiRoot}/user/base`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 通过手机验证码接口 POST /login/phone */
export async function loginByPhone(phoneLogin: PhoneLogin, options?: Record<string, any>) {
  return request<R<boolean>>(`${apiRoot}/login/phone`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: phoneLogin,
    ...(options || {}),
  });
}

/** 发送验证码 POST /login/captcha */
export async function getFakeCaptcha(params: { phone?: string; }, options?: { [key: string]: any }) {
  return request<FakeCaptcha>('${apiRoot}/login/captcha', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';
import {R, SocialInfo} from "@/services/typings";

const apiRoot = process.env.NODE_ENV === 'production' ? '' : '/api'

/** 获取当前用户绑定信息 GET /social/info */
export async function getSocialInfo(options?: Record<string, any>) {
  return request<R<SocialInfo>>(`${apiRoot}/social/info`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 解绑第三方用户 GET /social/unbind */
export async function unbindSocial(type?: string, options?: Record<string, any>) {
  return request<R<SocialInfo>>(`${apiRoot}/social/unbind`, {
    method: 'GET',
    params: {
      type: type
    },
    ...(options || {}),
  });
}

/** 绑定第三方用户 POST /bind/account */
export async function bindAccount(params?: any, options?: Record<string, any>) {
  return request<R<SocialInfo>>(`${apiRoot}/bind/account`, {
    method: 'POST',
    params,
    ...(options || {}),
  });
}

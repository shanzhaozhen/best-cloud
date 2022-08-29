// @ts-ignore
/* eslint-disable */
import { request } from '@umijs/max';
import {R, UserInfoVO, UserInfoForm} from "@/services/typings";

const apiRoot = process.env.NODE_ENV === 'production' ? '' : '/api'

/** 获取当前用户基础信息 POST /user/base */
export async function getCurrentUserInfo(options?: Record<string, any>) {
  return request<R<UserInfoVO>>(`${apiRoot}/user/base`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 跟新当前用户基础信息 POST /user/base */
export async function updateUserInfo(userInfoForm: UserInfoForm, options?: Record<string, any>) {
  return request<R<UserInfoVO>>(`${apiRoot}/user/base`, {
    method: 'POST',
    data: userInfoForm,
    ...(options || {}),
  });
}

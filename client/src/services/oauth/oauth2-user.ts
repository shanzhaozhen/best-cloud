// @ts-ignore
import { request } from '@umijs/max';
import type {Page, PageParams, R} from "@/services/common/typings";
import type {OAuth2UserForm, OAuth2UserVO} from "@/services/oauth/type/oauth2-user";

/** 获取用户信息（分页） GET /user/page */
export async function getOAuth2UserPage(params: PageParams, options?: Record<string, any>) {
  return request<R<Page<OAuth2UserVO>>>(`/api/oauth/user/page`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 添加用户接口 POST /user */
export async function addOAuth2User(oauth2UserForm: OAuth2UserForm, options?: Record<string, any>) {
  return request<R<string>>(`/api/oauth/user`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: oauth2UserForm,
    ...(options || {}),
  });
}

/** 更新用户接口 PUT /user */
export async function updateOAuth2User(oauth2UserForm: OAuth2UserForm, options?: Record<string, any>) {
  return request<R<string>>(`/api/oauth/user`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: oauth2UserForm,
    ...(options || {}),
  });
}

/** 批量删除用户接口 DELETE /user */
export async function batchDeleteOAuth2User(userIds: (string | undefined)[], options?: Record<string, any>) {
  return request<R<string>>(`/api/oauth/user`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: userIds,
    ...(options || {}),
  });
}

/** 获取用户信息（通过用户id） GET /user/${userId} */
export async function getOAuth2UserById(userId: string, options?: Record<string, any>) {
  return request<R<OAuth2UserVO>>(`/api/oauth/user/${userId}`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 删除用户接口 DELETE /user/${userId} */
export async function deleteOAuth2User(userId: string, options?: Record<string, any>) {
  return request<R<string>>(`/api/oauth/user/${userId}`, {
    method: 'DELETE',
    ...(options || {}),
  });
}

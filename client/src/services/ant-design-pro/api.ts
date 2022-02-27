// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取当前的用户 GET /api/uaa/user/current */
export async function currentUser(options?: Record<string, any>) {
  return request<{
    data: API.CurrentUser;
  }>('/api/uaa/user/current', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 退出登录接口 POST /api/login/outLogin */
export async function outLogin(options?: Record<string, any>) {
  return request<Record<string, any>>('/api/login/outLogin', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 登录接口 POST /api/authorize/oauth2/token */
export async function login(body: API.LoginParams, options?: Record<string, any>) {
  return request<API.LoginResult>('/api/authorize/oauth2/token', {
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

/** 此处后端没有提供注释 GET /api/notices */
export async function getNotices(options?: Record<string, any>) {
  return request<API.NoticeIconList>('/api/notices', {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取规则列表 GET /api/rule */
export async function rule(
  params: {
    // query
    /** 当前的页码 */
    current?: number;
    /** 页面的容量 */
    pageSize?: number;
  },
  options?: Record<string, any>,
) {
  return request<API.RuleList>('/api/rule', {
    method: 'GET',
    params: {
      ...params,
    },
    ...(options || {}),
  });
}

/** 新建规则 PUT /api/rule */
export async function updateRule(options?: Record<string, any>) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'PUT',
    ...(options || {}),
  });
}

/** 新建规则 POST /api/rule */
export async function addRule(options?: Record<string, any>) {
  return request<API.RuleListItem>('/api/rule', {
    method: 'POST',
    ...(options || {}),
  });
}

/** 删除规则 DELETE /api/rule */
export async function removeRule(options?: Record<string, any>) {
  return request<Record<string, any>>('/api/rule', {
    method: 'DELETE',
    ...(options || {}),
  });
}

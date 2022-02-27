// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 获取用户角色信息（分页） GET /user-role */
export async function getUserPageByRoleId1(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getUserPageByRoleId1Params,
  options?: Record<string, any>,
) {
  return request<API.RPageUserVO>(`/api/uaa/user-role`, {
    method: 'GET',
    params: {
      ...params,
      page: undefined,
      ...params['page'],
    },
    ...(options || {}),
  });
}

/** 添加用户角色 POST /user-role */
export async function addUserRole(body: API.UserRoleForm, options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/user-role`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除用户角色 DELETE /user-role */
export async function deleteUserRoles(body: API.UserRoleForm, options?: Record<string, any>) {
  return request<API.RInteger>(`/api/uaa/user-role`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

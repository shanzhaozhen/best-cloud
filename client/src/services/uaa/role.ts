// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 更新角色接口 PUT /role */
export async function updateRole(body: API.RoleForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/role`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 添加角色接口 POST /role */
export async function addRole(body: API.RoleForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/role`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 批量删除角色接口 DELETE /role */
export async function batchDeleteRole(body: number[], options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/role`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取角色信息（通过角色id） GET /role/${param0} */
export async function getRoleById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getRoleByIdParams,
  options?: Record<string, any>,
) {
  const { roleId: param0, ...queryParams } = params;
  return request<API.RRoleVO>(`/api/uaa/role/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 删除角色接口 DELETE /role/${param0} */
export async function deleteRole(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteRoleParams,
  options?: Record<string, any>,
) {
  const { roleId: param0, ...queryParams } = params;
  return request<R<number>>(`/api/uaa/role/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取角色信息（分页） GET /role/page */
export async function getRolePage(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getRolePageParams,
  options?: Record<string, any>,
) {
  return request<API.RPageRoleVO>(`/api/uaa/role/page`, {
    method: 'GET',
    params: {
      ...params,
      page: undefined,
      ...params['page'],
    },
    ...(options || {}),
  });
}

/** 获取所有角色 GET /role/all */
export async function getAllRoles(options?: Record<string, any>) {
  return request<API.RListRoleVO>(`/api/uaa/role/all`, {
    method: 'GET',
    ...(options || {}),
  });
}

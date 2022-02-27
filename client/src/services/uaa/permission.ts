// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 权限更新接口 PUT /permission */
export async function updatePermission(body: API.PermissionForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/permission`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 权限添加接口 POST /permission */
export async function addPermission(body: API.PermissionForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/permission`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 批量权限删除接口 DELETE /permission */
export async function batchDeletePermission(body: number[], options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/permission`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取权限（通过权限id） GET /permission/${param0} */
export async function getPermissionById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getPermissionByIdParams,
  options?: Record<string, any>,
) {
  const { permissionId: param0, ...queryParams } = params;
  return request<API.RPermissionVO>(`/api/uaa/permission/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 权限删除接口 DELETE /permission/${param0} */
export async function deletePermission(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deletePermissionParams,
  options?: Record<string, any>,
) {
  const { permissionId: param0, ...queryParams } = params;
  return request<R<number>>(`/api/uaa/permission/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取所有权限（树状结构） GET /permission/tree */
export async function getPermissionTree(options?: Record<string, any>) {
  return request<API.RListPermissionVO>(`/api/uaa/permission/tree`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取所有父节点权限（树状结构） GET /permission/root-tree */
export async function getAllPermissionRootTree(options?: Record<string, any>) {
  return request<API.RListPermissionVO>(`/api/uaa/permission/root-tree`, {
    method: 'GET',
    ...(options || {}),
  });
}

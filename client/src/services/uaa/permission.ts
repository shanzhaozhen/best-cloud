// @ts-ignore
import { request } from '@umijs/max';
import type {PermissionForm, PermissionVO} from './type/permission';
import type {R} from "@/services/common/typings";

/** 权限更新接口 PUT /permission */
export async function updatePermission(permissionForm: PermissionForm, options?: Record<string, any>) {
  return request<R<string>>(`/api/uaa/permission`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: permissionForm,
    ...(options || {}),
  });
}

/** 权限添加接口 POST /permission */
export async function addPermission(permissionForm: PermissionForm, options?: Record<string, any>) {
  return request<R<string>>(`/api/uaa/permission`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: permissionForm,
    ...(options || {}),
  });
}

/** 批量权限删除接口 DELETE /permission */
export async function batchDeletePermission(permissionIds: (string | undefined)[], options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/permission`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: permissionIds,
    ...(options || {}),
  });
}

/** 获取权限（通过权限id） GET /permission/${permissionId} */
export async function getPermissionById(permissionId: string, options?: Record<string, any>) {
  return request<R<PermissionVO>>(`/api/uaa/permission/${permissionId}`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 权限删除接口 DELETE /permission/${permissionId} */
export async function deletePermission(permissionId: string, options?: Record<string, any>) {
  return request<R<string>>(`/api/uaa/permission/${permissionId}`, {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 获取所有权限（树状结构） GET /permission/tree */
export async function getPermissionTree(options?: Record<string, any>) {
  return request<R<PermissionVO[]>>(`/api/uaa/permission/tree`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取所有权限（树状结构） GET /permission/pid */
export async function getPermissionByPid(pid?: string, options?: Record<string, any>) {
  return request<R<PermissionVO[]>>(`/api/uaa/permission/pid`, {
    params: {
      pid
    },
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取所有父节点权限（树状结构） GET /permission/root-tree */
export async function getAllPermissionRootTree(options?: Record<string, any>) {
  return request<R<PermissionVO[]>>(`/api/uaa/permission/root-tree`, {
    method: 'GET',
    ...(options || {}),
  });
}

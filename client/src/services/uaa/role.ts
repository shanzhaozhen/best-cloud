// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import {Page, PageParams, R} from "@/services/common/typings";
import {RoleForm, RoleVO} from "@/services/uaa/type/role";

/** 更新角色接口 PUT /role */
export async function updateRole(body: RoleForm, options?: Record<string, any>) {
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
export async function addRole(body: RoleForm, options?: Record<string, any>) {
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
export async function batchDeleteRole(roleIds: (number | undefined)[], options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/role`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: roleIds,
    ...(options || {}),
  });
}

/** 获取角色信息（通过角色id） GET /role/${param0} */
export async function getRoleById(roleId: number, options?: Record<string, any>) {
  return request<R<RoleVO>>(`/api/uaa/role/${roleId}`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 删除角色接口 DELETE /role/${param0} */
export async function deleteRole(roleId: number, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/role/${roleId}`, {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 获取角色信息（分页） GET /role/page */
export async function getRolePage(params: PageParams, options?: Record<string, any>) {
  return request<R<Page<RoleVO>>>(`/api/uaa/role/page`, {
    method: 'GET',
    params,
    ...(options || {}),
  });
}

/** 获取所有角色 GET /role/all */
export async function getAllRoles(options?: Record<string, any>) {
  return request<R<RoleVO[]>>(`/api/uaa/role/all`, {
    method: 'GET',
    ...(options || {}),
  });
}

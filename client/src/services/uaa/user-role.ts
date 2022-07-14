// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import {UserRoleForm} from "@/services/uaa/type/role";
import {Page, PageParams, R} from "@/services/common/typings";
import {UserVO} from "@/services/uaa/type/user";

/** 获取用户角色信息（分页） GET /user-role */
export async function getUserPageByRoleId(pageParams: PageParams, options?: Record<string, any>) {
  return request<R<Page<UserVO>>>(`/api/uaa/user-role`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: {
      ...pageParams,
    },
    ...(options || {}),
  });
}

/** 添加用户角色 POST /user-role */
export async function addUserRoles(userRoleForm: UserRoleForm, options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/user-role`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: userRoleForm,
    ...(options || {}),
  });
}

/** 删除用户角色 DELETE /user-role */
export async function deleteUserRoles(userRoleForm: UserRoleForm, options?: Record<string, any>) {
  return request<R<string>>(`/api/uaa/user-role`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: userRoleForm,
    ...(options || {}),
  });
}

// @ts-ignore
import { request } from 'umi';
import type {UserRoleForm} from "@/services/uaa/type/role";
import type {R} from "@/services/common/typings";

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

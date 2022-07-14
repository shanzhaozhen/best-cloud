// @ts-ignore
import { request } from 'umi';
import type {DepartmentUserForm} from "@/services/uaa/type/department";
import type {R} from "@/services/common/typings";

/** 添加用户角色 POST /department-user */
export async function addDepartmentUsers(departmentUserForm: DepartmentUserForm, options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/department-user`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: departmentUserForm,
    ...(options || {}),
  });
}

/** 删除用户角色 DELETE /department-user */
export async function deleteDepartmentUsers(departmentUserForm: DepartmentUserForm, options?: Record<string, any>) {
  return request<R<string>>(`/api/uaa/department-user`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: departmentUserForm,
    ...(options || {}),
  });
}

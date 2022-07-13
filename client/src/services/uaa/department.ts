// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import {Page, PageParams, R} from "@/services/common/typings";
import {DepartmentForm, DepartmentVO} from './type/department';
import {RoleVO} from "@/services/uaa/type/role";

/** 获取部门信息（分页） GET /department/page */
export async function getDepartmentPage(params: PageParams, options?: Record<string, any>) {
  return request<R<Page<RoleVO>>>(`/api/uaa/department/page`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
    ...(options || {}),
  });
}

/** 获取部门信息（通过部门id） GET /department/${departmentId} */
export async function getDepartmentById(departmentId: number, options?: Record<string, any>) {
  return request<R<DepartmentVO>>(`/api/uaa/department/${departmentId}`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取所有部门信息（树状结构） GET /department/tree */
export async function getDepartmentTree(options?: Record<string, any>) {
  return request<R<DepartmentVO[]>>(`/api/uaa/department/tree`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取所有部门信息 GET /department/all */
export async function getDepartmentList(keyword?: string, options?: Record<string, any>) {
  return request<R<DepartmentVO[]>>(`/api/uaa/department/list`, {
    params: {
      keyword
    },
    method: 'GET',
    ...(options || {}),
  });
}

/** 通过父级id获取部门列表 GET /department/pid */
export async function getDepartmentByPid(pid?: number, options?: Record<string, any>) {
  return request<R<DepartmentVO[]>>(`/api/uaa/department/pid`, {
    params: {
      pid
    },
    method: 'GET',
    ...(options || {}),
  });
}

/** 添加部门接口 POST /department */
export async function addDepartment(departmentForm: DepartmentForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/department`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: departmentForm,
    ...(options || {}),
  });
}

/** 更新部门接口 PUT /department */
export async function updateDepartment(departmentForm: DepartmentForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/department`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: departmentForm,
    ...(options || {}),
  });
}

/** 删除部门接口 DELETE /department/${param0} */
export async function deleteDepartment(departmentId: number, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/department/${departmentId}`, {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 批量删除部门接口 DELETE /department */
export async function batchDeleteDepartment(departmentIds: (number | undefined)[], options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/department`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: departmentIds,
    ...(options || {}),
  });
}


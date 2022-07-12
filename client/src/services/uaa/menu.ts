// @ts-ignore
/* eslint-disable */
import { request } from 'umi';
import {R} from "@/services/common/typings";
import {MenuForm, MenuVO} from './type/menu';

/** 更新菜单接口 PUT /menu */
export async function updateMenu(menuForm: MenuForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/menu`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: menuForm,
    ...(options || {}),
  });
}

/** 添加菜单接口 POST /menu */
export async function addMenu(menuForm: MenuForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/menu`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: menuForm,
    ...(options || {}),
  });
}

/** 批量删除菜单接口 DELETE /menu */
export async function batchDeleteMenu(menuIds: (number | undefined)[], options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/menu`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: menuIds,
    ...(options || {}),
  });
}

/** 获取菜单信息（通过菜单id） GET /menu/${menuId} */
export async function getMenuById(menuId: number, options?: Record<string, any>) {
  return request<R<MenuVO>>(`/api/uaa/menu/${menuId}`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 删除菜单接口 DELETE /menu/${param0} */
export async function deleteMenu(menuId: number, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/menu/${menuId}`, {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 获取所有菜单信息（树状结构） GET /menu/tree */
export async function getMenuTree(options?: Record<string, any>) {
  return request<R<MenuVO[]>>(`/api/uaa/menu/tree`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取所有菜单信息 GET /menu/all */
export async function getAllMenus(options?: Record<string, any>) {
  return request<R<MenuVO[]>>(`/api/uaa/menu/all`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 通过父级id获取菜单列表 GET /menu/pid */
export async function getMenuByPid(pid?: number, options?: Record<string, any>) {
  return request<R<MenuVO[]>>(`/api/uaa/menu/pid`, {
    params: {
      pid
    },
    method: 'GET',
    ...(options || {}),
  });
}

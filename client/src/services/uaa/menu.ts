// @ts-ignore
/* eslint-disable */
import { request } from 'umi';

/** 更新菜单接口 PUT /menu */
export async function updateMenu(body: API.MenuForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/menu`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 添加菜单接口 POST /menu */
export async function addMenu(body: API.MenuForm, options?: Record<string, any>) {
  return request<R<number>>(`/api/uaa/menu`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 批量删除菜单接口 DELETE /menu */
export async function batchDeleteMenu(body: number[], options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/menu`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 获取菜单信息（通过菜单id） GET /menu/${param0} */
export async function getMenuById(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.getMenuByIdParams,
  options?: Record<string, any>,
) {
  const { menuId: param0, ...queryParams } = params;
  return request<API.RMenuVO>(`/api/uaa/menu/${param0}`, {
    method: 'GET',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 删除菜单接口 DELETE /menu/${param0} */
export async function deleteMenu(
  // 叠加生成的Param类型 (非body参数swagger默认没有生成对象)
  params: API.deleteMenuParams,
  options?: Record<string, any>,
) {
  const { menuId: param0, ...queryParams } = params;
  return request<R<number>>(`/api/uaa/menu/${param0}`, {
    method: 'DELETE',
    params: { ...queryParams },
    ...(options || {}),
  });
}

/** 获取所有菜单信息（树状结构） GET /menu/tree */
export async function getMenuTree(options?: Record<string, any>) {
  return request<API.RListMenuVO>(`/api/uaa/menu/tree`, {
    method: 'GET',
    ...(options || {}),
  });
}

/** 获取所有菜单信息 GET /menu/all */
export async function getAllMenus(options?: Record<string, any>) {
  return request<API.RListMenuVO>(`/api/uaa/menu/all`, {
    method: 'GET',
    ...(options || {}),
  });
}

// @ts-ignore
import { request } from 'umi';
import type {Page, PageParams, R} from "@/services/common/typings";
import type {OAuth2RegisteredClientDTO, OAuth2RegisteredClientForm} from "@/services/uaa/type/registered-client";


/** 获取客户端信息（分页） GET /registered-client/page */
export async function getRegisteredClientPage(params: PageParams, options?: Record<string, any>) {
  return request<R<Page<OAuth2RegisteredClientDTO>>>(`/api/uaa/oauth2/registered-client/page`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: params,
    ...(options || {}),
  });
}

/** 获取客户端信息（通过客户端id） GET /registered-client */
export async function getRegisteredClientById(id: string, options?: Record<string, any>) {
  return request<R<OAuth2RegisteredClientDTO>>(`/api/uaa/oauth2/registered-client`, {
    method: 'GET',
    params: {
      id
    },
    ...(options || {}),
  });
}

/** 获取客户端信息（通过客户端id） GET /registered-client */
export async function getRegisteredClientByClientId(clientId: string, options?: Record<string, any>) {
  return request<R<OAuth2RegisteredClientDTO>>(`/api/uaa/oauth2/registered-client`, {
    method: 'GET',
    params: {
      clientId
    },
    ...(options || {}),
  });
}


/** 添加客户端接口 POST /registered-client */
export async function addRegisteredClient(body: OAuth2RegisteredClientForm, options?: Record<string, any>) {
  return request<R<string>>(`/api/uaa/oauth2/registered-client`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 更新客户端接口 PUT /registered-client */
export async function updateRegisteredClient(body: OAuth2RegisteredClientForm, options?: Record<string, any>) {
  return request<R<string>>(`/api/uaa/oauth2/registered-client`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    data: body,
    ...(options || {}),
  });
}

/** 删除客户端接口 DELETE /registered-client/${registered-clientId} */
export async function deleteRegisteredClient(registeredClientId: string, options?: Record<string, any>) {
  return request<R<string>>(`/api/uaa/oauth2/registered-client/${registeredClientId}`, {
    method: 'DELETE',
    ...(options || {}),
  });
}

/** 批量删除客户端接口 DELETE /registered-client */
export async function batchDeleteRegisteredClient(registeredClientIds: (string | undefined)[], options?: Record<string, any>) {
  return request<R<number[]>>(`/api/uaa/oauth2/registered-client`, {
    method: 'DELETE',
    headers: {
      'Content-Type': 'application/json',
    },
    data: registeredClientIds,
    ...(options || {}),
  });
}





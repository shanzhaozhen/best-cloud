import Footer from '@/components/Footer';
import RightContent from '@/components/RightContent';
import {BookOutlined, ExclamationCircleOutlined, LinkOutlined} from '@ant-design/icons';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import {PageLoading, SettingDrawer} from '@ant-design/pro-components';
import type { RunTimeLayoutConfig } from '@umijs/max';
import { history, Link } from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import type { RequestOptionsInit, ResponseError } from 'umi-request';
import {getCurrentUserInfo} from "@/services/uaa/user";
import type {CurrentUser} from "@/services/uaa/type/user";
import {stringify} from "querystring";
import {Modal, notification} from "antd";
import {getToken} from "@/utils/common";
import type {RequestConfig} from "@@/plugin-request/request";
import {User, UserManager} from "oidc-client-ts";
import OidcConfig from "../config/oidcConfig";



const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/';

const userManager = new UserManager(OidcConfig);

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  user?: User | null;
  currentUser?: CurrentUser;
  loading?: boolean;
  fetchUserInfo?: () => Promise<CurrentUser | undefined>;
  userManager?: UserManager
}> {
  const fetchUserInfo = async () => {
    try {
      const { data } = await getCurrentUserInfo();
      return data;
    } catch (error) {
      history.push(loginPath);
    }
    return undefined;
  };


  // 如果不是登录页面，执行
  if (history.location.pathname !== loginPath) {
    const user = await userManager.getUser()

    // const currentUser = await fetchUserInfo();
    return {
      fetchUserInfo,
      // currentUser,
      user,
      settings: defaultSettings,
      userManager
    };
  }
  return {
    fetchUserInfo,
    settings: defaultSettings,
    userManager
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    waterMarkProps: {
      content: initialState?.currentUser?.userInfo?.name,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      const { location } = history;
      console.log(history);
      // // 如果没有登录，重定向到 login
      // if (!initialState?.currentUser && location.pathname !== loginPath) {
      //   history.push(loginPath);
      // }

      // 如果没有登录，重定向到登陆页
      if (!initialState?.user && location.pathname !== loginPath) {
          history.push(loginPath);
      }

    },
    // todo: 动态菜单
    // menuDataRender: () => initialState?.menuData || [],
    links: isDev
      ? [
          <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
            <LinkOutlined />
            <span>OpenAPI 文档</span>
          </Link>,
          <Link key="doc" to="/~docs">
            <BookOutlined />
            <span>业务组件文档</span>
          </Link>,
        ]
      : [],
    menuHeaderRender: undefined,
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    childrenRender: (children, props) => {
      // if (initialState?.loading) return <PageLoading />;
      return (
        <>
          {children}
          {!props.location?.pathname?.includes('/login') && (
            <SettingDrawer
              disableUrlParams
              enableDarkTheme
              settings={initialState?.settings}
              onSettingChange={(settings) => {
                setInitialState((preInitialState) => ({
                  ...preInitialState,
                  settings,
                }));
              }}
            />
          )}
        </>
      );
    },
    ...initialState?.settings,
  };
};

/*
const codeMessage = {
  200: '服务器成功返回请求的数据。',
  201: '新建或修改数据成功。',
  202: '一个请求已经进入后台排队（异步任务）。',
  204: '删除数据成功。',
  400: '发出的请求有错误，服务器没有进行新建或修改数据的操作。',
  401: '用户没有权限（令牌、用户名、密码错误）。',
  403: '用户得到授权，但是访问是被禁止的。',
  404: '发出的请求针对的是不存在的记录，服务器没有进行操作。',
  405: '请求方法不被允许。',
  406: '请求的格式不可得。',
  410: '请求的资源被永久删除，且不会再得到的。',
  422: '当创建一个对象时，发生一个验证错误。',
  500: '服务器发生错误，请检查服务器。',
  502: '网关错误。',
  503: '服务不可用，服务器暂时过载或维护。',
  504: '网关超时。',
};

let confirmModalVisible = false;

/!** 异常处理程序
 * @see https://pro.ant.design/zh-CN/docs/request
 *!/
const errorHandler = async (error: ResponseError) => {
  // if (error.message === 'Failed to fetch') {
  //   throw error;
  // }

  const { response } = error;

  if (!response) {
    notification.error({
      description: '您的网络发生异常，无法连接服务器',
      message: '网络异常',
    });
  }

  if (response.status) {
    const errorText = codeMessage[response.status] || response.statusText;
    const { status, url } = response;

    const res = await response.clone().json();

    if (status === 401) {
      /!**
       * (4010, "密码账号认证出错")
       * (4011, "token签名异常")
       * (4012, "token格式不正确")
       * (4013, "token已过期")
       * (4014, "不支持该token")
       * (4015, "token参数异常")
       * (4016, "token错误")
       *!/
      if (res.code >= 4011 && res.code <= 4016) {
        if (history.location.pathname !== '/login' && history.location.pathname !== '/') {
          if (!confirmModalVisible) {
            confirmModalVisible = true;
            Modal.confirm({
              title: '登陆超时',
              icon: <ExclamationCircleOutlined />,
              content: '您已被登出，可以取消继续留在该页面，或者重新登录。',
              okText: '重新登陆',
              cancelText: '留在此页',
              onOk() {
                confirmModalVisible = false;
                history.replace({
                  pathname: '/login',
                  search: stringify({
                    redirect: history.location.pathname,
                  }),
                });
              },
              onCancel() {
                confirmModalVisible = false;
              },
            });
          }
        }
      } else {
        notification.error({
          message: '鉴权失败',
          description: res.message || '您的网络发生异常，无法连接服务器',
        });
      }
    } else {
      notification.error({
        message: `请求错误 ${status}: ${url}`,
        description: res.message || errorText,
      });
    }
  }

  throw error;
};

/!**
 * 自动添加 AccessToken 的请求前拦截器
 * 参考 https://github.com/umijs/umi-request/issues/181#issuecomment-730794198
 * @param url
 * @param options
 *!/
const authHeaderInterceptor = (url: string, options: RequestOptionsInit) => {
  const accessToken = getToken();

  if (url !== '/api/authorize/oauth2/token' && accessToken) {
    return {
      url,
      options: {
        ...options,
        interceptors: true,
        headers: {
          // ...options.headers,
          Authorization: accessToken,
        },
      },
    };
  }

  return {url, options};
};

export const request: RequestConfig = {
  errorHandler,
  // 新增自动添加AccessToken的请求前拦截器
  requestInterceptors: [authHeaderInterceptor],
};
*/



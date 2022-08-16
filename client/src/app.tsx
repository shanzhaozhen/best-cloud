import Footer from '@/components/Footer';
import RightContent from '@/components/RightContent';
import {BookOutlined, LinkOutlined} from '@ant-design/icons';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import { SettingDrawer } from '@ant-design/pro-components';
import type { RunTimeLayoutConfig } from '@umijs/max';
import { history, Link } from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import {getCurrentUserInfo} from "@/services/uaa/user";
import type {CurrentUser} from "@/services/uaa/type/user";
import type {User} from "oidc-client-ts";
import {errorConfig} from "@/requestErrorConfig";
import {userManager} from "../config/oidcConfig";


const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/home';


const whiteList = [
  '/home',
]

// 是否白名单
const isWhiteAllow = (): boolean => whiteList.some(i => history.location.pathname.startsWith(i))

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: CurrentUser;
  loading?: boolean;
  refreshToken?: () => Promise<User | undefined>;
  fetchUserInfo?: () => Promise<CurrentUser | undefined>;
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

  // oauth2 返回页则刷新 token
  if (history.location.pathname.startsWith("/oidc")) {
    const user = await userManager.signinRedirectCallback();
    if (user) {
      localStorage.setItem("token_type", user.token_type);
      localStorage.setItem("access_token", user.access_token);
      localStorage.setItem("refresh_token", user.refresh_token || '');
      localStorage.setItem("id_token", user.id_token || '');
    } else {
      localStorage.clear();
      sessionStorage.clear();
      history.push(loginPath);
    }
  }

  // 如果不是白名单，执行
  if (!isWhiteAllow()) {
    const currentUser = await fetchUserInfo();

    return {
      fetchUserInfo,
      currentUser,
      settings: defaultSettings,
    };
  }

  return {
    fetchUserInfo,
    settings: defaultSettings,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    // 水印
    waterMarkProps: {
      content: initialState?.currentUser?.userInfo?.name,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      // 如果没有登录，重定向到登陆页
      if (!isWhiteAllow() && !initialState?.currentUser) {
        history.push(loginPath);
      }
    },
    // todo: 动态菜单
    // menuDataRender: () => initialState?.menuData || [],
    layoutBgImgList: [
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/D2LWSqNny4sAAAAAAAAAAAAAFl94AQBr',
        left: 85,
        bottom: 100,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/C2TWRpJpiC0AAAAAAAAAAAAAFl94AQBr',
        bottom: -68,
        right: -45,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/F6vSTbj8KpYAAAAAAAAAAAAAFl94AQBr',
        bottom: 0,
        left: 0,
        width: '331px',
      },
    ],
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
          {!props.location?.pathname?.includes('/oidc') && (
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

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request = {
  ...errorConfig,
};

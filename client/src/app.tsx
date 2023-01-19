import Footer from '@/components/Footer';
import RightContent from '@/components/RightContent';
import {LinkOutlined} from '@ant-design/icons';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import {SettingDrawer} from '@ant-design/pro-components';
import type { RunTimeLayoutConfig } from '@umijs/max';
import { history, Link } from '@umijs/max';
import defaultSettings from '../config/defaultSettings';
import type {CurrentUser} from "@/services/uaa/type/user";
import {errorConfig} from "@/requestErrorConfig";
import {menuDataRender} from "@/dynamicMenu";
import {refreshToken} from "../config/oidcConfig";
import {idTokenDecode} from "@/utils/oauth";
import {getMenusByCurrentUser} from "@/services/uaa/menu";
import {MenuVO} from "@/services/uaa/type/menu";
import {Info} from "@/services/uaa/type/user";


const isDev = process.env.NODE_ENV === 'development';
const loginPath = '/welcome';

const whiteList = [
  loginPath,
  '/flow'
]

// 是否白名单
const isWhiteAllow = (): boolean => whiteList.some(i => history.location.pathname.startsWith(i))

/**
 * @see  https://umijs.org/zh-CN/plugins/plugin-initial-state
 * */
export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  currentUser?: CurrentUser;
  menus?: MenuVO[];
  loading?: boolean;
  initializeInfo?: () => Promise<Info | undefined>;
}> {
  const initializeInfo = async () => {
    // 解析 id token
    let userinfo;
    let menus: MenuVO[] = [];
    try {
      const oidcUser = idTokenDecode();
      if (oidcUser && Object.keys(oidcUser).length > 0) {
        userinfo = {
          userId: oidcUser.userId,
          username: oidcUser.username,
          nickname: oidcUser.nickname,
          avatar: oidcUser.avatar,
        }
      }
      const { data } = await getMenusByCurrentUser();
      menus = data && data.length > 0 ? data : [];

      return {
        userinfo,
        menus
      };
    } catch (error) {
      history.push(loginPath);
    }

    return undefined;
  };

  // oauth2 返回页则刷新 token
  if (history.location.pathname.startsWith("/oidc")) {
    await refreshToken(loginPath);
  }

  // 如果不是白名单，执行
  if (!isWhiteAllow()) {
    const info = await initializeInfo();

    return {
      initializeInfo,
      currentUser: info?.userinfo,
      menus: info?.menus,
      settings: defaultSettings as Partial<LayoutSettings>,
    };
  }

  return {
    initializeInfo,
    settings: defaultSettings as Partial<LayoutSettings>,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({ initialState, setInitialState }) => {
  return {
    rightContentRender: () => <RightContent />,
    // 水印
    waterMarkProps: {
      content: initialState?.currentUser?.nickname,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      // 如果没有登录，重定向到登陆页
      if (!isWhiteAllow() && initialState?.currentUser === undefined) {
        history.push(loginPath);
      }
    },
    // 动态菜单
    menuDataRender: () => menuDataRender(initialState?.menus),
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

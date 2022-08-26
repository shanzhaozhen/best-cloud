import type { RunTimeLayoutConfig } from '@umijs/max';
import type { Settings as LayoutSettings } from '@ant-design/pro-components';
import defaultSettings from '../config/defaultSettings';
import RightContent from "@/components/RightContent";

export async function getInitialState(): Promise<{
  settings?: Partial<LayoutSettings>;
  // currentUser?: API.CurrentUser;
  loading?: boolean;
  // fetchUserInfo?: () => Promise<API.CurrentUser | undefined>;
}> {
  // const fetchUserInfo = async () => {
    // try {
    //   const msg = await queryCurrentUser();
    //   return msg.data;
    // } catch (error) {
    //   history.push(loginPath);
    // }
    // return undefined;
  // };
  // 如果不是登录页面，执行
  // if (history.location.pathname !== loginPath) {
  //   const currentUser = await fetchUserInfo();
  //   return {
  //     fetchUserInfo,
  //     currentUser,
  //     settings: defaultSettings,
  //   };
  // }
  return {
    // fetchUserInfo,
    settings: defaultSettings,
  };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = () => {
  return {
    rightContentRender: () => <RightContent />,
    disableContentMargin: false,
    ...defaultSettings,
  };
};

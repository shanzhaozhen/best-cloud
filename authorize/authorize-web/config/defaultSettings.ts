import { ProLayoutProps } from '@ant-design/pro-components';
import {resourcesPath} from "./constants";

/**
 * @name
 */
const Settings: ProLayoutProps & {
  pwa?: boolean;
  logo?: string;
} = {
  navTheme: 'light',
  // 拂晓蓝
  colorPrimary: '#1890ff',
  layout: 'mix',
  contentWidth: 'Fluid',
  fixedHeader: false,
  fixSiderbar: true,
  colorWeak: false,
  title: 'Best Oauth2',
  pwa: true,
  logo: `${resourcesPath}logo.svg`,
  iconfontUrl: '',
  // 不展示顶栏
  // headerRender: false,
  // 不展示页脚
  // footerRender: false,
  // 不展示菜单
  menuRender: false,
  // 不展示菜单顶栏
  menuHeaderRender: false,
  token: {
    // 参见ts声明，demo 见文档，通过token 修改样式
    //https://procomponents.ant.design/components/layout#%E9%80%9A%E8%BF%87-token-%E4%BF%AE%E6%94%B9%E6%A0%B7%E5%BC%8F
  },
};

export default Settings;

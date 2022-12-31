import { Settings as LayoutSettings } from '@ant-design/pro-components';
import {resourcesPath} from "./constants";

/**
 * @name
 */
const Settings: LayoutSettings & {
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
  pwa: false,
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
};

export default Settings;

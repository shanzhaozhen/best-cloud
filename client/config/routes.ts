/**
 * @name umi 的路由配置
 * @description 只支持 path,component,routes,redirect,wrappers,name,icon 的配置
 * @param path  path 只支持两种占位符配置，第一种是动态参数 :id 的形式，第二种是 * 通配符，通配符只能出现路由字符串的最后。
 * @param component 配置 location 和 path 匹配后用于渲染的 React 组件路径。可以是绝对路径，也可以是相对路径，如果是相对路径，会从 src/pages 开始找起。
 * @param routes 配置子路由，通常在需要为多个路径增加 layout 组件时使用。
 * @param redirect 配置路由跳转
 * @param wrappers 配置路由组件的包装组件，通过包装组件可以为当前的路由组件组合进更多的功能。 比如，可以用于路由级别的权限校验
 * @param name 配置路由的标题，默认读取国际化文件 menu.ts 中 menu.xxxx 的值，如配置 name 为 login，则读取 menu.ts 中 menu.login 的取值作为标题
 * @param icon 配置路由的图标，取值参考 https://ant.design/components/icon-cn， 注意去除风格后缀和大小写，如想要配置图标为 <StepBackwardOutlined /> 则取值应为 stepBackward 或 StepBackward，如想要配置图标为 <UserOutlined /> 则取值应为 user 或者 User
 * @doc https://umijs.org/docs/guides/routes
 */
export default [
  {
    path: '/welcome',
    name: 'welcome',
    layout: false,
    component: './Common/Welcome',
  },
  {
    path: '/oidc',
    name: 'oidc',
    layout: false,
    component: './Common/Oidc',
  },
  {
    path: '/home',
    name: 'home',
    icon: 'smile',
    component: './Common/Home',
  },
  {
    path: '/system',
    name: 'system',
    icon: 'crown',
    routes: [
      {
        name: 'user',
        path: '/system/user',
        component: './System/UserList',
      },
      {
        name: 'role',
        path: '/system/role',
        component: './System/RoleList',
      },
      {
        name: 'menu',
        path: '/system/menu',
        component: './System/MenuList',
      },
      {
        name: 'permission',
        path: '/system/permission',
        component: './System/PermissionList',
      },
      {
        name: 'department',
        path: '/system/department',
        component: './System/DepartmentList',
      },
      {
        name: 'organization',
        path: '/system/organization',
        component: './System/OrganizationList',
      },
    ],
  },
  {
    path: '/oauth',
    name: 'oauth',
    icon: 'crown',
    routes: [
      {
        name: 'registeredClient',
        path: '/oauth/registered-client',
        component: './OAuth/RegisteredClientList',
      },
    ],
  },
  {
    path: '/review',
    name: 'review',
    icon: 'crown',
    routes: [
      {
        name: 'flow',
        path: '/review/flow',
        component: './Review/FlowList',
      },
    ],
  },
  {
    name: 'flow',
    path: '/flow',
    layout: false,
    component: './Review/FlowList',
  },
  // {
  //   path: '/admin',
  //   name: 'admin',
  //   icon: 'crown',
  //   access: 'canAdmin',
  //   component: './Admin',
  //   routes: [
  //     {
  //       path: '/admin/sub-page',
  //       name: 'sub-page',
  //       icon: 'smile',
  //       component: './Welcome',
  //     },
  //     {
  //       component: './404',
  //     },
  //   ],
  // },
  // {
  //   name: 'list.table-list',
  //   icon: 'table',
  //   path: '/list',
  //   component: './TableList',
  // },
  // {
  //   path: '/system',
  //   name: 'system',
  //   icon: 'crown',
  //   routes: [
  // {
  //   path: '/system/user',
  //   name: 'user',
  //   icon: 'smile',
  //   component: './System/UserList',
  // },
  // {
  //   path: '/system/menu',
  //   name: 'menu',
  //   icon: 'smile',
  //   component: './System/MenuList',
  // },
  // {
  //   path: '/system/role',
  //   name: 'role',
  //   icon: 'smile',
  //   component: './System/RoleList',
  // },
  // {
  //   path: '/system/resource',
  //   name: 'resource',
  //   icon: 'smile',
  //   component: './System/ResourceList',
  // },
  // {
  //   path: '/system/department',
  //   name: 'department',
  //   icon: 'smile',
  //   component: './System/DepartmentList',
  // },
  // {
  //   path: '/system/task',
  //   name: 'task',
  //   icon: 'smile',
  //   component: './System/TaskList',
  // },
  // {
  //   path: '/system/dictionary',
  //   name: 'dictionary',
  //   icon: 'smile',
  //   component: './System/DictionaryList',
  // },
  // {
  //   path: '/system/region',
  //   name: 'region',
  //   icon: 'smile',
  //   component: './System/RegionList',
  // },
  // {
  //   path: '/system/file',
  //   name: 'file',
  //   icon: 'smile',
  //   component: './System/FileList',
  // },
  // ],
  // },
  {
    path: '/',
    redirect: '/home',
  },
  // {
  //   name: '个人设置',
  //   icon: 'smile',
  //   path: '/accountsettings',
  //   component: './AccountSettings',
  // },
  {
    path: '/403',
    layout: false,
    component: './Common/ErrorPage/403',
  },
  {
    path: '/404',
    layout: false,
    component: './Common/ErrorPage/404',
  },
  {
    path: '/500',
    layout: false,
    component: './Common/ErrorPage/500',
  },
  {
    path: '*',
    redirect: '/404',
  },
];

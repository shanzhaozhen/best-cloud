export default [
  {
    path: '/login',
    layout: false,
    component: './common/Login',
  },
  {
    path: '/welcome',
    name: 'welcome',
    icon: 'smile',
    component: './Welcome',
  },
  {
    path: '/system',
    name: 'system',
    icon: 'crown',
    routes: [
      {
        name: 'user',
        path: '/system/user',
        component: './system/userList',
      },
      {
        name: 'role',
        path: '/system/role',
        component: './system/roleList',
      },
      {
        name: 'menu',
        path: '/system/menu',
        component: './system/menuList',
      },
      {
        name: 'permission',
        path: '/system/permission',
        component: './system/permissionList',
      },
    ],
  }, // {
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
    redirect: '/welcome',
  },
  // {
  //   name: '个人设置',
  //   icon: 'smile',
  //   path: '/accountsettings',
  //   component: './AccountSettings',
  // },
  {
    component: './common/errorPage/404',
  },
];

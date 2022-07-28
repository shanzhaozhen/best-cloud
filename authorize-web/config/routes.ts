export default [
  {
    path: '/',
    redirect: '/login',
  },
  {
    name: 'login',
    path: '/login',
    layout: false,
    component: './Login',
  },
  {
    component: './404',
  },
];

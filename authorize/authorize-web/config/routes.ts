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
    name: 'consent',
    path: '/oauth2/consent',
    layout: false,
    component: './Consent',
  },
  {
    path: '/403',
    layout: false,
    component: './ErrorPage/403',
  },
  {
    path: '/404',
    layout: false,
    component: './ErrorPage/404',
  },
  {
    path: '/500',
    layout: false,
    component: './ErrorPage/500',
  },
  {
    path: '/error',
    layout: false,
    component: './ErrorPage/500',
  },
  {
    path: '*',
    redirect: '/404',
  },
];

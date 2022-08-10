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
    component: './404',
  },
];

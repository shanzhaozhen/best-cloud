import {UserManager, UserManagerSettings} from "oidc-client-ts";
import {history} from "@@/core/history";

const url = window.location.origin;

export const OidcConfig: UserManagerSettings = {
  authority: "http://localhost:9000",         // 要对应后台的 issue 的设置
  client_id: "efd7527b-39d0-468c-9bd6-ff945a696982",
  redirect_uri: url + "/oidc",
  scope: 'openid message.read message.write', // 'openid profile ' + your scopes
  post_logout_redirect_uri: url + "/welcome",
  response_type: "code",
  // response_mode: "fragment",
  // silentRenew: true,
  // silentRenewUrl: window.location.origin + '/silent-renew.html',
  // renewTimeBeforeTokenExpiresInSeconds: 10,
  automaticSilentRenew: false,
  // loadUserInfo: true,
};

export const userManager = new UserManager(OidcConfig);

export const oauth2Login = async () => {
  clearToken();
  await userManager.signinRedirect();
}

export const refreshToken = async (loginPath: string) => {
  const user = await userManager.signinRedirectCallback();
  if (user) {
    localStorage.setItem('token_type', user.token_type);
    localStorage.setItem('access_token', user.access_token);
    localStorage.setItem('refresh_token', user.refresh_token || '');
    localStorage.setItem('id_token', user.id_token || '');
  } else {
    clearToken();
    history.push(loginPath);
  }
}

export const clearToken = () => {
  const redirect = localStorage.getItem('redirect');
  localStorage.clear();
  if (redirect) {
    localStorage.setItem('redirect', redirect)
  }
  sessionStorage.clear();
}


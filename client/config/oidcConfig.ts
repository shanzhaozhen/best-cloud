import {UserManager, UserManagerSettings} from "oidc-client-ts";

export const OidcConfig: UserManagerSettings = {
  authority: "http://localhost:9000",
  client_id: "efd7527b-39d0-468c-9bd6-ff945a696982",
  redirect_uri: 'http://127.0.0.1:8000/oidc',
  scope: 'openid message.read message.write', // 'openid profile ' + your scopes
  // responseType: 'code',
  // silentRenew: true,
  // silentRenewUrl: window.location.origin + '/silent-renew.html',
  // renewTimeBeforeTokenExpiresInSeconds: 10,
  // autoUserInfo: false
};

// export default OidcConfig;

export const userManager = new UserManager(OidcConfig);


import {UserManagerSettings} from "oidc-client-ts";

const OidcConfig: UserManagerSettings = {
  authority: "http://127.0.0.1:9000",
  client_id: "auth",
  redirect_uri: window.location.origin + '/oidc'
};

export default OidcConfig;

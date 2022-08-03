import {UserManagerSettings} from "oidc-client-ts";

const OidcConfig: UserManagerSettings = {
  authority: "http://localhost:9000",
  client_id: "auth",
  redirect_uri: 'http://127.0.0.1:8000/oidc'
};

export default OidcConfig;

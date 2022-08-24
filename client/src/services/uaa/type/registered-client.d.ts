export type OAuth2RegisteredClientForm = {
  /** 主键ID */
  id?: string;
  /** 客户端id */
  clientId?: string;
  /** 客户端到期时间 */
  clientIdIssuedAt?: string;
  /** 客户端密码 */
  clientSecret?: string;
  /** 客户端密码到期时间 */
  clientSecretExpiresAt?: string;
  /** 客户端名称 */
  clientName?: string;
  /** 客户端认证方式 */
  clientAuthenticationMethods?: string;
  /** 客户端授权方式 */
  authorizationGrantTypes?: string;
  /** 客户端允许重定向的uri */
  redirectUris?: string;
  /** 客户端允许的授权范围 */
  scopes?: string;
  clientSettings?: OAuth2ClientSettingsDTO;
  tokenSettings?: OAuth2TokenSettingsDTO;
  /** 客户端认证方式 */
  clientAuthenticationMethodList?: string[];
  /** 客户端授权方式 */
  authorizationGrantTypeList?: string[];
  /** 客户端允许重定向的uri */
  redirectUriList?: any[];
  /** 客户端允许的授权范围 */
  scopeList?: string[];
};

export type OAuth2RegisteredClientDTO = {
  /** 主键ID */
  id?: string;
  /** 客户端id */
  clientId?: string;
  /** 客户端到期时间 */
  clientIdIssuedAt?: string;
  /** 客户端密码 */
  clientSecret?: string;
  /** 客户端密码到期时间 */
  clientSecretExpiresAt?: string;
  /** 客户端名称 */
  clientName?: string;
  /** 客户端认证方式 */
  clientAuthenticationMethods?: string;
  /** 客户端授权方式 */
  authorizationGrantTypes?: string;
  /** 客户端允许重定向的uri */
  redirectUris?: string;
  /** 客户端允许的授权范围 */
  scopes?: string;
  clientSettings?: OAuth2ClientSettingsDTO;
  tokenSettings?: OAuth2TokenSettingsDTO;
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
  /** 客户端认证方式 */
  clientAuthenticationMethodList?: string[];
  /** 客户端授权方式 */
  authorizationGrantTypeList?: string[];
  /** 客户端允许重定向的uri */
  redirectUriList?: any[];
  /** 客户端允许的授权范围 */
  scopeList?: string[];
};

export type OAuth2ClientSettingsDTO = {
  /** 主键ID */
  id?: string;
  /** oauth2客户端id */
  registeredClientId?: string;
  /** 客户端是否需要证明密钥 */
  requireProofKey?: boolean;
  /** 客户端是否需要授权确认页面 */
  requireAuthorizationConsent?: boolean;
  /** jwkSet url */
  jwkSetUrl?: string;
  /** 支持的签名算法 */
  tokenEndpointAuthenticationSigningAlgorithm?: string;
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
};

export type OAuth2TokenSettingsDTO = {
  /** 主键ID */
  id?: string;
  /** oauth2客户端id */
  registeredClientId?: string;
  /** access_token 有效时间 */
  accessTokenTimeToLive?: number;
  /** token 格式  jwt、opaque */
  accessTokenFormat?: string;
  /** 是否重用 refresh_token */
  reuseRefreshTokens?: boolean;
  /** refresh_token 有效时间 */
  refreshTokenTimeToLive?: number;
  /** oidc id_token 签名算法 */
  idTokenSignatureAlgorithm?: string;
  /** 创建人 */
  createdBy?: number;
  /** 创建时间 */
  createdDate?: string;
  /** 修改人 */
  lastModifiedBy?: number;
  /** 修改时间 */
  lastModifiedDate?: string;
};

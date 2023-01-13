/**
 * 获取当前登陆状态的请求需要携带的 token
 */
export const getToken = (): string => {
  const tokenType = localStorage.getItem('token_type') || '';
  const accessToken = localStorage.getItem('access_token') || '';

  return `${tokenType} ${accessToken}`;
}

/**
 * 获取当前登陆中的 access token
 */
export const getAccessToken = (): string => {
  return localStorage.getItem('access_token') || '';
}

/**
 * 获取当前登陆中的 id token
 */
export const getIdToken = (): string => {
  return localStorage.getItem('id_token') || '';
}

/**
 * 解析 JWT
 * @param jwt
 */
export const jwtDecode = (jwt: string): any => {
  if (!jwt) {
    return {};
  }

  const split = jwt.split('.');
  if (split.length !== 3) {
    return {};
  }

  let output = split[1].replace(/-/g, "+").replace(/_/g, "/");
  switch (output.length % 4) {
    case 0:
      break;
    case 2:
      output += "==";
      break;
    case 3:
      output += "=";
      break;
    default:
      return {};
  }

  output = decodeURIComponent(
    atob(output).replace(/(.)/g, function(m, p) {
      let code = p.charCodeAt(0).toString(16).toUpperCase();
      if (code.length < 2) {
        code = "0" + code;
      }
      return "%" + code;
    })
  );

  console.log(output)

  const claims = JSON.parse(output);
  console.log(claims)

  return claims;
}


export const accessTokenDecode = () => {
  return jwtDecode(getAccessToken());
}

export const idTokenDecode = (): any => {
  return jwtDecode(getIdToken());
}

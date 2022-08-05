# authorize

### 授权服务器

### OAuth2流程

流程可以参考该博客：https://www.cnblogs.com/huan1993/p/15416077.html

1. 授权码模式

* 用户首先通过/oauth2/authorization/{registrationId}端点向oauth2-client发起请求

```http request
GET /oauth2/authorization/xxx HTTP/1.1
Host: localhost:9000
```

* 被OAuth2AuthorizationRequestRedirectFilter拦截后组装成下面的请求链接向授权服务器oauth2-server发起授权码授权：

```http request
# http://localhost:9000/authentication/authorize?response_type=code&client_id=auth&scope=message.read&redirect_uri=http://www.baidu.com
GET /oauth2/authorize?response_type=code&client_id=auth&scope=message.read&redirect_uri=http://www.baidu.com HTTP/1.1
Host: localhost:9000
```

* 授权服务器oauth2-server拦截到该请求后，会先检查发起该请求的当前用户是否认证。如果没有认证就抛出401，跳到授权服务器的登录页面，然后用户执行了登录：

```http request
POST /login HTTP/1.1
Host: localhost:9000
Content-Type: application/x-www-form-urlencoded

username=admin&password=123456&_csrf=xxx
```

* 成功登录后进行了302跳转，继续执行/oauth2/authorize授权请求。这时会判断授权请求是否需要用户授权确认，在本DEMO中用户授权是需要二次确认的，会跳转到确认页面

* 同意授权后，授权服务器会调用redirect_uri并携带一个code和state向oauth2-client发起请求

```http request
GET /?code=xxxx HTTP/1.1 
Host: www.baidu.com
```

* oauth2-client的OAuth2AuthorizationCodeGrantFilter拦截到redirect_uri后向授权服务器发起/oauth2/token请求：

```http request
POST /oauth2/token?grant_type=authorization_code&code=xxxx&redirect_uri=https://127.0.0.1:8080/foo/bar HTTP/1.1
Host: localhost:9000
Authorization: auth 123456
```

2. 刷新token

```http request
# http://localhost:9000/authentication/token?grant_type=refresh_token&refresh_token=xxx

POST /oauth2/token?grant_type=refresh_token&refresh_token=xxx
Host: localhost:9000
```

3. 客户端模式

```http request
POST /oauth2/token HTTP/1.1
Host: localhost:9000
Content-Type: application/x-www-form-urlencoded
Authorization: auth 123456

grant_type=client_credentials&scope=all
```

客户端模式不会返回refresh_token

4. 密码模式

oauth2.1 已弃用密码模式，已拓展支持

```http request
POST /oauth2/token?username=admin&password=123456&scope=all&grant_type=password HTTP/1.1
Host: localhost:9000
Authorization: auth 123456
```


5. 撤销令牌

```http request
POST /oauth2/revoke?token=xxx HTTP/1.1
Host: localhost:9000
Authorization: auth 123456
```

6. 查看token信息

```http request
POST /oauth2/introspect?token=XXX HTTP/1.1
Host: localhost:9000
Authorization: Basic xxx
```

7. 查看JWK信息(获取公钥信息)

```http request
GET /oauth2/jwks HTTP/1.1
Host: localhost:9000
```


8. 用户端点

```http request
GET /userinfo HTTP/1.1
Host: localhost:9000
```


### OIDC

/connect/register

/.well-known/openid-configuration

/userinfo


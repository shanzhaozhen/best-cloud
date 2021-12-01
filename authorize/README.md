# authorize

### 授权服务器

### Oauth2流程
1. 授权码模式

* 用户首先通过/oauth2/authorization/{registrationId}端点向oauth2-client发起请求
```http request
GET /oauth2/authorization/xxx HTTP/1.1
Host: localhost:9000
```

* 被OAuth2AuthorizationRequestRedirectFilter拦截后组装成下面的请求链接向授权服务器oauth2-server发起授权码授权：
```http request
# http://localhost:9000/oauth2/authorize?response_type=code&client_id=auth&scope=message.read&redirect_uri=http://www.baidu.com
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
* 
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

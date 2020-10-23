package org.shanzhaozhen.authorize.config.oauth2;

import io.jsonwebtoken.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.authorize.service.UserService;
import org.shanzhaozhen.basiccommon.dto.JWTUser;
import org.shanzhaozhen.basiccommon.enums.sys.JwtErrorConst;
import org.shanzhaozhen.basiccommon.utils.HttpServletResponseUtils;
import org.shanzhaozhen.basiccommon.vo.ResultObject;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@ConfigurationProperties(prefix = "jwt")
@Getter
@Setter
@Component
@Slf4j
@RequiredArgsConstructor
public class CustomJwtTokenProvider {

    public static final String tokenHead = "Bearer ";

    private String issuer;

    private String secret;

    private String header;

    private long expiration;

    private long rememberExpiration;

    private final UserService userService;

    /**
     * 创建token
     *
     * iss：发行人
     * exp：到期时间
     * sub：主题
     * aud：用户
     * nbf：在此之前不可用
     * iat：发布时间
     * jti：JWT ID用于标识该JWT
     */
    public String buildToken(Long id, String username) {
        /**
         * 按照jwt的规定，最后请求的时候应该是 `Bearer token`
         */
        return tokenHead + Jwts.builder()
                /**
                 * 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值
                 * 一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
                 */
                .setId(String.valueOf(id))
                .setSubject(username)
                .setIssuer(issuer)          //iss
                .setIssuedAt(new Date())            //iat: jwt的签发时间
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))        //设置过期时间
                .signWith(SignatureAlgorithm.HS512, secret)         //设置签名使用的签名算法和签名使用的秘钥
                .compact();
    }

    /**
     * 更新token
     * @param token
     * @return
     */
    public String updateToken(String token) {
        Assert.hasText(token, "token不能为空");
        Claims claims = this.getJWTBody(token);
        return this.buildToken(Long.valueOf(claims.getId()), claims.getSubject());
    }

    /**
     * 校验token的签名
     * @param httpServletResponse
     * @param authToken
     * @return
     */
    public boolean validateToken(HttpServletResponse httpServletResponse, String authToken) throws IOException {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
            /**
             * 验证通过同时更新token（延长过期时间）
             * 但是该方法不可取，每次更新旧的Token后，新旧的Token同时能使用问题就大了
             */
            return true;
        } catch (SignatureException ex) {
            log.error("Invalid JWT signature");
            HttpServletResponseUtils.resultJson(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED,
                    new ResultObject<>(JwtErrorConst.JWT_SIGNATURE));
        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token");
            HttpServletResponseUtils.resultJson(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED,
                    new ResultObject<>(JwtErrorConst.JWT_MALFORMED));
        } catch (ExpiredJwtException ex) {
            log.error("Expired JWT token");
            HttpServletResponseUtils.resultJson(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED,
                    new ResultObject<>(JwtErrorConst.JWT_EXPIRED));
        } catch (UnsupportedJwtException ex) {
            log.error("Unsupported JWT token");
            HttpServletResponseUtils.resultJson(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED,
                    new ResultObject<>(JwtErrorConst.JWT_UNSUPPORTED));
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty.");
            HttpServletResponseUtils.resultJson(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED,
                    new ResultObject<>(JwtErrorConst.JWT_ILLEGAL_ARGUMENT));
        } catch (JwtException ex) {
            log.error("JWT error.");
            HttpServletResponseUtils.resultJson(httpServletResponse, HttpServletResponse.SC_UNAUTHORIZED,
                    new ResultObject<>(JwtErrorConst.JWT_ERROR));
        }
        return false;
    }

    /**
     * 从httpServletRequest获取token
     * @param httpServletRequest
     * @return
     */
    public String getJwtTokenFromRequest(HttpServletRequest httpServletRequest) {
        String jwtToken = httpServletRequest.getHeader(header);
        if (StringUtils.hasText(jwtToken) && jwtToken.startsWith(tokenHead)) {
            return jwtToken.substring((tokenHead).length());
        }
        return null;
    }

    /**
     * 从token中获取用户名
     * @param token
     * @return
     */
    public String getUsername(String token) {
        return getJWTBody(token).getSubject();
    }

    /**
     * 获取用户id
     * @param token
     * @return
     */
    public Long getUserId(String token) {
        return Long.valueOf(getJWTBody(token).getId());
    }

    /**
     * 获取jwt用户体信息
     * @param token
     * @return
     */
    public JWTUser getJWTUser(String token) {
        Claims claims = getJWTBody(token);
        Long useId = Long.valueOf(claims.getId());
        Assert.notNull(useId, "没有相关的用户ID信息");
        JWTUser jwtUser = userService.getJWTUser(useId);
        Assert.notNull(jwtUser, "没有找到当前用户信息");
        return jwtUser;
    }

    /**
     * 判断 token 是否已过期
     * @param token
     * @return
     */
    public boolean isExpiration(String token){
        return getJWTBody(token).getExpiration().before(new Date());
    }

    private Claims getJWTBody(String token) {
        token = token.replace(tokenHead, "");

        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

}

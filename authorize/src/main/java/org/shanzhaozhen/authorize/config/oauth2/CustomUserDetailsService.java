package org.shanzhaozhen.authorize.config.oauth2;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.common.entity.R;
import org.shanzhaozhen.security.converter.UserConverter;
import org.shanzhaozhen.security.feign.UserFeignClient;
import org.shanzhaozhen.security.vo.UserVO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


/**
 * 启动服务器时，通过FilterInvocationSecurityMetadataSource获得用户的所有角色及权限信息
 * 当用户登陆时，通过CustomUserDetailsServiceImpl中的loadUserByUsername获得该登陆用户所有的权限
 * 发出请求时，通过FilterInvocationSecurityMetadataSource的getAttributes(Object url)获得需要的权限名
 * 最后在AccessDecisionManager中decide方法进行对比，如果用户拥有的权限名称和该url需要的权限名相同，那么放行，否则认证失败
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        R<UserVO> data = userFeignClient.getUserByUsername(username);

        UserVO userVO = data.getData();

        return userVO == null ? null : UserConverter.toDTO(userVO);
    }

}

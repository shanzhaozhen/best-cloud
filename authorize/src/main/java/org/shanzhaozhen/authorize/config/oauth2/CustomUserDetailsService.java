package org.shanzhaozhen.authorize.config.oauth2;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.service.RoleService;
import org.shanzhaozhen.authorize.service.UserService;
import org.shanzhaozhen.basiccommon.dto.RoleDTO;
import org.shanzhaozhen.basiccommon.dto.UserDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 启动服务器时，通过FilterInvocationSecurityMetadataSource获得用户的所有角色及权限信息
 * 当用户登陆时，通过CustomUserDetailsServiceImpl中的loadUserByUsername获得该登陆用户所有的权限
 * 发出请求时，通过FilterInvocationSecurityMetadataSource的getAttributes(Object url)获得需要的权限名
 * 最后在AccessDecisionManager中decide方法进行对比，如果用户拥有的权限名称和该url需要的权限名相同，那么放行，否则认证失败
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserService userService;

    private final RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.getUserByUsername(username);

        if (null == userDTO) {
            /**
             * 在这里会继续捕获到UsernameNotFoundException异常。
             * 由于hideUserNotFoundExceptions的值为true，所以这里会new一个新的BadCredentialsException异常抛出来，那么最后捕获到并放入session中的就是这个BadCredentialsException异常。
             * 所以我们在页面始终无法捕获我们自定义的异常信息。
             */
            throw new UsernameNotFoundException("用户不存在!");
        } else {
            //将数据库保存的权限存至登陆的账号里面
            List<RoleDTO> roleDTOList = roleService.getRolesByUserId(userDTO.getId());
            if (roleDTOList != null && roleDTOList.size() > 0) {
                Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
                for (RoleDTO roleDTO : roleDTOList) {
                    grantedAuthorities.add(new SimpleGrantedAuthority(roleDTO.getIdentification()));
                }
                userDTO.setAuthorities(grantedAuthorities);
            }
        }

        return userDTO;
    }

}

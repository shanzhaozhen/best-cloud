package org.shanzhaozhen.security.config.security;


import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.security.dto.UserDTO;
import org.shanzhaozhen.security.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
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

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO userDTO = userService.getUserByUsername(username);

        if (userDTO == null) {
            /**
             * 在这里会继续捕获到UsernameNotFoundException异常。
             * 由于hideUserNotFoundExceptions的值为true，所以这里会new一个新的BadCredentialsException异常抛出来，那么最后捕获到并放入session中的就是这个BadCredentialsException异常。
             * 所以我们在页面始终无法捕获我们自定义的异常信息。
             */
            throw new BadCredentialsException("用户不存在!");
        } else {
            //将数据库保存的权限存至登陆的账号里面
//            List<RoleDTO> roles = userDTO.getRoles();
//            List<? extends GrantedAuthority> authorities = new ArrayList<>();
//            if (!CollectionUtils.isEmpty(roles)) {
//                authorities = roles.stream().map(roleDTO -> new SimpleGrantedAuthority(roleDTO.getCode())).collect(Collectors.toList());
//            }
//
//            return User.builder()
//                    .username(username)
//                    .password(userDTO.getPassword())
//                    .accountExpired(userDTO.isAccountNonExpired())
//                    .accountLocked(userDTO.isAccountNonLocked())
//                    .credentialsExpired(userDTO.isCredentialsNonExpired())
//                    .disabled(userDTO.isEnabled())
//                    .authorities(authorities)
//                    .build();

            return null;
        }
    }

}

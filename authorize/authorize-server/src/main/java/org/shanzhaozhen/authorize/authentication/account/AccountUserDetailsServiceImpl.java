package org.shanzhaozhen.authorize.authentication.account;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.pojo.dto.AuthUser;
import org.shanzhaozhen.authorize.pojo.dto.OAuth2UserDTO;
import org.shanzhaozhen.authorize.service.OAuth2UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountUserDetailsServiceImpl implements UserDetailsService {

    private final OAuth2UserService oauth2UserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        OAuth2UserDTO user = oauth2UserService.getUserByUsername(username);
        if (user == null) {
            throw new BadCredentialsException("用户不存在!");
        }
        return new AuthUser(user);
    }

}

package org.shanzhaozhen.authorize.authentication.phone;

import lombok.RequiredArgsConstructor;
import org.shanzhaozhen.authorize.pojo.dto.AuthUser;
import org.shanzhaozhen.authorize.service.OAuth2UserService;
import org.shanzhaozhen.oauth.pojo.dto.OAuth2UserDTO;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PhoneUserDetailsServiceImpl implements PhoneUserDetailsService {

    private final OAuth2UserService oauth2UserService;

    @Override
    public UserDetails loadUserByPhone(String phone) throws PhoneNotFoundException {
        OAuth2UserDTO user = oauth2UserService.getUserByPhone(phone);
        if (user == null) {
            throw new BadCredentialsException("用户不存在!");
        }
        return new AuthUser(user);
    }

}

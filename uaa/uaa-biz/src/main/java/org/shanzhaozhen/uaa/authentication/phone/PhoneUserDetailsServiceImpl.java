package org.shanzhaozhen.uaa.authentication.phone;

import org.springframework.security.core.userdetails.UserDetails;

public class PhoneUserDetailsServiceImpl implements PhoneUserDetailsService {

    @Override
    public UserDetails loadUserByPhone(String phone) throws PhoneNotFoundException {
        return null;
    }

}

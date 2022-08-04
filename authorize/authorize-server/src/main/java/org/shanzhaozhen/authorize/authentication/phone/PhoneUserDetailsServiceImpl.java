package org.shanzhaozhen.authorize.authentication.phone;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PhoneUserDetailsServiceImpl implements PhoneUserDetailsService {

    @Override
    public UserDetails loadUserByPhone(String phone) throws PhoneNotFoundException {
        return null;
    }

}

package org.shanzhaozhen.uaa.authentication.phone;

import org.springframework.security.core.userdetails.UserDetails;

public interface PhoneUserDetailsService {

    /**
     * 通过手机号登陆
     * @param phone
     * @return
     * @throws PhoneNotFoundException
     */
    UserDetails loadUserByPhone(String phone) throws PhoneNotFoundException;

}

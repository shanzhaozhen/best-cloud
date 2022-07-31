package org.shanzhaozhen.authorize.authentication.phone;

import org.springframework.security.core.AuthenticationException;

public class PhoneNotFoundException extends AuthenticationException {

    public PhoneNotFoundException(String msg) {
        super(msg);
    }

    public PhoneNotFoundException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

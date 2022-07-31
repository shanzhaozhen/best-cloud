package org.shanzhaozhen.authorize.exception;

import org.springframework.security.core.AuthenticationException;

public class CaptchaErrorException extends AuthenticationException {

    public CaptchaErrorException(String msg) {
        super(msg);
    }

    public CaptchaErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }

}

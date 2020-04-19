package org.shanzhaozhen.bestcloudcommon.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtils {

    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String encryption(String rawPassword) {
        return encoder.encode(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encoder.matches(rawPassword, encodedPassword);
    }

}

package org.shanzhaozhen.uaa.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.shanzhaozhen.uaa.service.UserService;
import org.springframework.stereotype.Component;

/**
 * @Author: shanzhaozhen
 * @Date: 2022-07-25
 * @Description:
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;

    private final UserService userService;

}

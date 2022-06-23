package com.lorin.handler;

import org.springframework.security.core.AuthenticationException;

/**
 * TODO
 *
 * @author lorin
 * @date 2022/6/23 0:04
 */


public class CaptchaException extends AuthenticationException {

    public CaptchaException(String msg) {
        super(msg);
    }
}

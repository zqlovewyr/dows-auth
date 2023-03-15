package org.dows.auth;

import org.springframework.security.core.AuthenticationException;

public class DowsAuthenticationException extends AuthenticationException {

    public DowsAuthenticationException(String msg) {
        super(msg);
    }
}
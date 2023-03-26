package org.dows.auth.api.exception;

import org.springframework.security.core.AuthenticationException;

public class DowsAuthenticationException extends AuthenticationException {

    public DowsAuthenticationException(String msg) {
        super(msg);
    }
}
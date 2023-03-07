package org.dows.auth.exception;

import org.dows.framework.api.StatusCode;
import org.dows.framework.api.exceptions.BaseException;

/**
 * @author runsix
 */

public class AuthException extends BaseException {
    public AuthException(String msg) {
        super(msg);
    }

    public AuthException(Integer code, String msg) {
        super(code, msg);
    }

    public AuthException(Throwable throwable) {
        super(throwable);
    }

    public AuthException(StatusCode statusCode) {
        super(statusCode);
    }

    public AuthException(StatusCode statusCode, Exception exception) {
        super(statusCode, exception);
    }

    public AuthException(StatusCode statusCode, String msg) {
        super(statusCode, msg);
    }

    public AuthException(StatusCode statusCode, Object[] args, String message) {
        super(statusCode, args, message);
    }

    public AuthException(StatusCode statusCode, Object[] args, String message, Throwable cause) {
        super(statusCode, args, message, cause);
    }
}

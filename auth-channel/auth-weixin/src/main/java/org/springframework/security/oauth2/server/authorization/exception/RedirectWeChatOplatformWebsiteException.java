package org.springframework.security.oauth2.server.authorization.exception;


import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * 重定向 异常
 */
public class RedirectWeChatOplatformWebsiteException extends WeChatOplatformWebsiteException {

    public RedirectWeChatOplatformWebsiteException(String errorCode) {
        super(errorCode);
    }

    public RedirectWeChatOplatformWebsiteException(OAuth2Error error) {
        super(error);
    }

    public RedirectWeChatOplatformWebsiteException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }

    public RedirectWeChatOplatformWebsiteException(OAuth2Error error, String message) {
        super(error, message);
    }

    public RedirectWeChatOplatformWebsiteException(OAuth2Error error, String message, Throwable cause) {
        super(error, message, cause);
    }

}

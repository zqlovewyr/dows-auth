package org.springframework.security.oauth2.server.authorization.exception;


import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * 微信开放平台父异常
 */
public class WeChatOplatformWebsiteException extends OAuth2AuthenticationException {

    public WeChatOplatformWebsiteException(String errorCode) {
        super(errorCode);
    }

    public WeChatOplatformWebsiteException(OAuth2Error error) {
        super(error);
    }

    public WeChatOplatformWebsiteException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }

    public WeChatOplatformWebsiteException(OAuth2Error error, String message) {
        super(error, message);
    }

    public WeChatOplatformWebsiteException(OAuth2Error error, String message, Throwable cause) {
        super(error, message, cause);
    }

    @Override
    public OAuth2Error getError() {
        return super.getError();
    }

}

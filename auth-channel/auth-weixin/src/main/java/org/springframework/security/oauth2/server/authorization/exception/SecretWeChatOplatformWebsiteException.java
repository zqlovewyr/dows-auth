package org.springframework.security.oauth2.server.authorization.exception;


import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * 微信开放平台 Secret 异常
 */
public class SecretWeChatOplatformWebsiteException extends WeChatOplatformWebsiteException {

    public SecretWeChatOplatformWebsiteException(String errorCode) {
        super(errorCode);
    }

    public SecretWeChatOplatformWebsiteException(OAuth2Error error) {
        super(error);
    }

    public SecretWeChatOplatformWebsiteException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }

    public SecretWeChatOplatformWebsiteException(OAuth2Error error, String message) {
        super(error, message);
    }

    public SecretWeChatOplatformWebsiteException(OAuth2Error error, String message, Throwable cause) {
        super(error, message, cause);
    }

}

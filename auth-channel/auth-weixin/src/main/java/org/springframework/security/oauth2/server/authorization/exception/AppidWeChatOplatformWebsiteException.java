package org.springframework.security.oauth2.server.authorization.exception;


import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * 微信开放平台 AppID 异常
 */
public class AppidWeChatOplatformWebsiteException extends WeChatOplatformWebsiteException {

    public AppidWeChatOplatformWebsiteException(String errorCode) {
        super(errorCode);
    }

    public AppidWeChatOplatformWebsiteException(OAuth2Error error) {
        super(error);
    }

    public AppidWeChatOplatformWebsiteException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }

    public AppidWeChatOplatformWebsiteException(OAuth2Error error, String message) {
        super(error, message);
    }

    public AppidWeChatOplatformWebsiteException(OAuth2Error error, String message, Throwable cause) {
        super(error, message, cause);
    }

}

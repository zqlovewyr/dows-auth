package org.springframework.security.oauth2.server.authorization.exception;


import org.springframework.security.oauth2.core.OAuth2Error;

/**
 * 微信开放平台 redirectUri 异常
 */
public class RedirectUriWeChatOplatformWebsiteException extends WeChatOplatformWebsiteException {

    public RedirectUriWeChatOplatformWebsiteException(String errorCode) {
        super(errorCode);
    }

    public RedirectUriWeChatOplatformWebsiteException(OAuth2Error error) {
        super(error);
    }

    public RedirectUriWeChatOplatformWebsiteException(OAuth2Error error, Throwable cause) {
        super(error, cause);
    }

    public RedirectUriWeChatOplatformWebsiteException(OAuth2Error error, String message) {
        super(error, message);
    }

    public RedirectUriWeChatOplatformWebsiteException(OAuth2Error error, String message, Throwable cause) {
        super(error, message, cause);
    }

}

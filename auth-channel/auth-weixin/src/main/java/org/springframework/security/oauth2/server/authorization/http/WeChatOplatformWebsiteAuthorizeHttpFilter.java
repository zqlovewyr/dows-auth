package org.springframework.security.oauth2.server.authorization.http;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.core.endpoint.OAuth2WeChatOplatformWebsiteParameterNames;
import org.springframework.security.oauth2.server.authorization.client.WeChatOplatformWebsiteService;
import org.springframework.security.oauth2.server.authorization.properties.WeChatOplatformWebsiteProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 微信开放平台 网站应用 跳转到微信授权页面
 *
 * @see <a href=
 * "https://developers.weixin.qq.com/doc/oplatform/Website_App/WeChat_Login/Wechat_Login.html">微信登录功能/网站应用微信登录开发指南</a>
 */
@Slf4j
@Data
@EqualsAndHashCode(callSuper = true)
@Component
public class WeChatOplatformWebsiteAuthorizeHttpFilter extends HttpFilter {

    public static final String PREFIX_URL = "/wechat-oplatform/website/authorize";

    public static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";

    public static final String SNSAPI_LOGIN = "snsapi_login";

    private WeChatOplatformWebsiteProperties weChatOplatformWebsiteProperties;

    private WeChatOplatformWebsiteService weChatOplatformWebsiteService;

    /**
     * 微信开放平台 网站应用 授权前缀
     */
    private String prefixUrl = PREFIX_URL;

    @Autowired
    public void setWeChatOplatformWebsiteProperties(WeChatOplatformWebsiteProperties weChatOplatformWebsiteProperties) {
        this.weChatOplatformWebsiteProperties = weChatOplatformWebsiteProperties;
    }

    @Autowired
    public void setWeChatOplatformWebsiteService(WeChatOplatformWebsiteService weChatOplatformWebsiteService) {
        this.weChatOplatformWebsiteService = weChatOplatformWebsiteService;
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String requestUri = request.getRequestURI();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        boolean match = antPathMatcher.match(prefixUrl + "/*", requestUri);
        if (match) {
            log.info("requestUri：{}", requestUri);

            String appid = requestUri.replace(prefixUrl + "/", "");

            String redirectUri = weChatOplatformWebsiteService.getRedirectUriByAppid(appid);

            String binding = request.getParameter(OAuth2WeChatOplatformWebsiteParameterNames.BINDING);
            String scope = request.getParameter(OAuth2ParameterNames.SCOPE);
            if (!SNSAPI_LOGIN.equals(scope)) {
                scope = SNSAPI_LOGIN;
            }

            String state = weChatOplatformWebsiteService.stateGenerate(request, response, appid);
            weChatOplatformWebsiteService.storeBinding(request, response, appid, state, binding);
            weChatOplatformWebsiteService.storeUsers(request, response, appid, state, binding);

            String url = String.format(AUTHORIZE_URL, appid, redirectUri, scope, state);

            log.info("redirectUrl：{}", url);

            response.sendRedirect(url);
            return;
        }

        super.doFilter(request, response, chain);
    }

}

package org.dows.auth.security.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.lang.Nullable;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.security.oauth2.server.authorization.*;
import org.springframework.util.Assert;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class RedisOAuth2AuthorizationService implements OAuth2AuthorizationService {

    //@Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final static Long TIMEOUT = 10L;

    private static final String AUTHORIZATION = "token";

    @Autowired
    public void setRedisTemplate(RedisTemplate redisTemplate) {
        RedisSerializer serializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(serializer);
        redisTemplate.setValueSerializer(RedisSerializer.java());
        redisTemplate.setHashKeySerializer(serializer);
        redisTemplate.setHashValueSerializer(RedisSerializer.java());
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");
        /*
         * 清除同一用户历史登录信息
         * **/
        if (redisTemplate.keys("*") != null) {
            Set<String> keys = redisTemplate.keys("*");
            for (String k : keys) {
                try {
                    OAuth2Authorization oAuth2Authorization = (OAuth2Authorization) redisTemplate.opsForValue().get(k);
                    if (oAuth2Authorization.getPrincipalName().equals(authorization.getPrincipalName())) {
                        redisTemplate.delete(k);
                    }
                } catch (Exception e) {
                    continue;
                }
            }
        }
        String access = "";
        String refresh = "";
        long outtime = 0;
        if (isState(authorization)) {
            String token = authorization.getAttribute("state");
            redisTemplate.opsForValue().
                    //redisUtil.
                            set(buildKey(OAuth2ParameterNames.STATE, token), authorization, TIMEOUT, TimeUnit.MINUTES);
        }

        if (isCode(authorization)) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                    .getToken(OAuth2AuthorizationCode.class);
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            long between = ChronoUnit.MINUTES.between(authorizationCodeToken.getIssuedAt(),
                    authorizationCodeToken.getExpiresAt());
            redisTemplate.opsForValue().
                    //redisUtil.
                            set(buildKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()),
                            authorization, between, TimeUnit.MINUTES);
            outtime = between;
            access = authorizationCodeToken.getTokenValue();
        }

        if (isRefreshToken(authorization)) {
            OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
            long between = ChronoUnit.SECONDS.between(refreshToken.getIssuedAt(), refreshToken.getExpiresAt());
            redisTemplate.opsForValue().
                    //redisUtil.
                            set(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()),
                            authorization, between, TimeUnit.SECONDS);
            outtime = between;
            refresh = refreshToken.getTokenValue();
        }

        if (isAccessToken(authorization)) {
            OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
            long between = ChronoUnit.SECONDS.between(accessToken.getIssuedAt(), accessToken.getExpiresAt());
            redisTemplate.opsForValue().
                    //redisUtil.
                            set(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()),
                            authorization, between, TimeUnit.SECONDS);
            access = accessToken.getTokenValue();
            /**
             * redis 存放 token和refreshtoken
             * **/
            log.info("########################");
            log.info("########################");
            log.info("########################");
            log.info("access_token={}", access);
            log.info("########################");
            log.info("########################");
            log.info("########################");
            redisTemplate.opsForValue().set(access, refresh, outtime, TimeUnit.SECONDS);
        }
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        Assert.notNull(authorization, "authorization cannot be null");

        List<String> keys = new ArrayList<>();
        if (isState(authorization)) {
            String token = authorization.getAttribute("state");
            keys.add(buildKey(OAuth2ParameterNames.STATE, token));
        }

        if (isCode(authorization)) {
            OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                    .getToken(OAuth2AuthorizationCode.class);
            OAuth2AuthorizationCode authorizationCodeToken = authorizationCode.getToken();
            keys.add(buildKey(OAuth2ParameterNames.CODE, authorizationCodeToken.getTokenValue()));
        }

        if (isRefreshToken(authorization)) {
            OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();
            keys.add(buildKey(OAuth2ParameterNames.REFRESH_TOKEN, refreshToken.getTokenValue()));
        }

        if (isAccessToken(authorization)) {
            OAuth2AccessToken accessToken = authorization.getAccessToken().getToken();
            keys.add(buildKey(OAuth2ParameterNames.ACCESS_TOKEN, accessToken.getTokenValue()));
        }
        redisTemplate.delete
                //redisUtil.del
                        (keys);
    }

    @Override
    @Nullable
    public OAuth2Authorization findById(String id) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Nullable
    public OAuth2Authorization findByToken(String token, @Nullable OAuth2TokenType tokenType) {
        Assert.hasText(token, "token cannot be empty");
        Assert.notNull(tokenType, "tokenType cannot be empty");
        return (OAuth2Authorization)
                redisTemplate.opsForValue().
                        //redisUtil.
                                get(buildKey(tokenType.getValue(), token));
    }

    private String buildKey(String type, String id) {
        return String.format("%s::%s::%s", AUTHORIZATION, type, id);
    }

    private static boolean isState(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getAttribute("state"));
    }

    private static boolean isCode(OAuth2Authorization authorization) {
        OAuth2Authorization.Token<OAuth2AuthorizationCode> authorizationCode = authorization
                .getToken(OAuth2AuthorizationCode.class);
        return Objects.nonNull(authorizationCode);
    }

    private static boolean isRefreshToken(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getRefreshToken());
    }

    private static boolean isAccessToken(OAuth2Authorization authorization) {
        return Objects.nonNull(authorization.getAccessToken());
    }

}

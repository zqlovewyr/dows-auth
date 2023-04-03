package org.dows.auth.biz.redis;


import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.api.constant.CacheConstants;
import org.dows.auth.api.constant.SecurityConstants;
import org.dows.auth.api.utils.IdUtils;
import org.dows.auth.api.utils.JwtUtils;
import org.dows.auth.api.utils.ServletUtils;
import org.dows.auth.api.utils.StringUtils;
import org.dows.auth.biz.context.SecurityUtils;
import org.dows.auth.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * token验证处理
 *
 * @author vctgo
 */
@Slf4j
@Component
public class TokenServiceBiz
{
//    @Autowired
//    private RedisService redisService;

    protected static final long MILLIS_SECOND = 1000;

    protected static final long MILLIS_MINUTE = 60 * MILLIS_SECOND;

    private final static long expireTime = CacheConstants.EXPIRATION;

    private final static String ACCESS_TOKEN = CacheConstants.LOGIN_TOKEN_KEY;

    private final static Long MILLIS_MINUTE_TEN = CacheConstants.REFRESH_TIME * MILLIS_MINUTE;

    @Autowired
    private RedisServiceBiz redisService;

    /**
     * 创建令牌
     */
    public Map<String, Object> createToken(LoginUserVo loginUser, Integer accountType)
    {
        String token = IdUtils.fastUUID();
        Long userId = loginUser.getId();
        String userName = loginUser.getAccountName();
        String tenantId = loginUser.getTenantId();
        loginUser.setToken(token);

        //添加地址信息
        refreshToken(loginUser);

        // Jwt存储信息
        Map<String, Object> claimsMap = new HashMap<String, Object>();
        claimsMap.put(SecurityConstants.USER_KEY, token);
        claimsMap.put(SecurityConstants.DETAILS_USER_ID, userId);
        claimsMap.put(SecurityConstants.DETAILS_USERNAME, userName);
        //租户id
        claimsMap.put(SecurityConstants.DETAILS_TENANT_ID, tenantId);
        claimsMap.put(SecurityConstants.ACCOUNT_ID, loginUser.getAccountId());
        //部门id
        // claimsMap.put(SecurityConstants.DETAILS_DEPT_ID, loginUser);
        // 接口返回信息
        Map<String, Object> rspMap = new HashMap<String, Object>();
        rspMap.put("access_token", JwtUtils.createToken(claimsMap));
        rspMap.put("expires_in", expireTime);
        rspMap.put("tenant_id", tenantId);
        rspMap.put("accountId", loginUser.getAccountId());
        if (accountType == 4) {
            Optional.ofNullable(loginUser.getOpenid()).ifPresent(value -> rspMap.put("openid", value));
            Optional.ofNullable(loginUser.getOpenid()).ifPresent(value -> rspMap.put("userId", value));
        }
        return rspMap;
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserVo getLoginUser()
    {
        return getLoginUser(ServletUtils.getRequest());
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserVo getLoginUser(HttpServletRequest request)
    {
        // 获取请求携带的令牌
        String token = SecurityUtils.getToken(request);
        return getLoginUser(token);
    }

    /**
     * 获取用户身份信息
     *
     * @return 用户信息
     */
    public LoginUserVo getLoginUser(String token)
    {
        LoginUserVo user = null;
        try
        {
            if (StringUtils.isNotEmpty(token))
            {
                String userkey = JwtUtils.getUserKey(token);
                JSONObject object = redisService.getCacheObject(getTokenKey(userkey));
                user = JSONObject.parseObject(JSONObject.toJSONString(object),LoginUserVo.class);
                return user;
            }
        }
        catch (Exception e)
        {
            log.error("登录获取用户信息失败：",e);
        }
        return user;
    }

    /**
     * 设置用户身份信息
     */
    public void setLoginUser(LoginUserVo loginUser)
    {
        if (StringUtils.isNotNull(loginUser) && StringUtils.isNotEmpty(loginUser.getToken()))
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 删除用户缓存信息
     */
    public void delLoginUser(String token)
    {
        if (StringUtils.isNotEmpty(token))
        {
            String userkey = JwtUtils.getUserKey(token);
            redisService.deleteObject(getTokenKey(userkey));
        }
    }

    /**
     * 验证令牌有效期，相差不足120分钟，自动刷新缓存
     *
     * @param loginUser
     */
    public void verifyToken(LoginUserVo loginUser)
    {
        long expireTime = loginUser.getExpireTime();
        long currentTime = System.currentTimeMillis();
        if (expireTime - currentTime <= MILLIS_MINUTE_TEN)
        {
            refreshToken(loginUser);
        }
    }

    /**
     * 刷新令牌有效期
     *
     * @param loginUser 登录信息
     */
    public void refreshToken(LoginUserVo loginUser)
    {
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(loginUser.getLoginTime() + expireTime * MILLIS_MINUTE);
        // 根据uuid将loginUser缓存
        String userKey = getTokenKey(loginUser.getToken());
        redisService.setCacheObject(userKey, loginUser, expireTime, TimeUnit.MINUTES);
    }

    private String getTokenKey(String token)
    {
        return ACCESS_TOKEN + token;
    }

}

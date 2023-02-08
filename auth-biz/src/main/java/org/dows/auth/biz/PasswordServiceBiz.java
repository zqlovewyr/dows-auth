package org.dows.auth.biz;

import org.dows.auth.biz.exception.AuthException;
import org.dows.auth.biz.utils.SecurityUtils;
import org.dows.auth.entity.User;
import org.dows.auth.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 登录密码方法
 *
 * @author vctgo
 */
@Component
public class PasswordServiceBiz
{
    @Autowired
    private RedisServiceBiz redisService;

    private int maxRetryCount = 5;

    private Long lockTime = 10l;


    /**
     * 登录账户密码错误次数缓存键名
     *
     * @param username 用户名
     * @return 缓存键key
     */
    private String getCacheKey(String username)
    {
        return "pwd_err_cnt:" + username;
    }

    public void validate(LoginUserVo user, String password)
    {
        String username = user.getAccountName();

        Integer retryCount = redisService.getCacheObject(getCacheKey(username));

        if (retryCount == null)
        {
            retryCount = 0;
        }

        if (retryCount >= Integer.valueOf(maxRetryCount).intValue())
        {
            String errMsg = String.format("密码输入错误%s次，帐户锁定%s分钟", maxRetryCount, lockTime);
            throw new AuthException(errMsg);
        }

        if (!matches(user, password))
        {
            retryCount = retryCount + 1;
            redisService.setCacheObject(getCacheKey(username), retryCount, lockTime, TimeUnit.MINUTES);
            throw new AuthException("用户不存在/密码错误");
        }
        else
        {
            clearLoginRecordCache(username);
        }
    }

    public boolean matches(LoginUserVo user, String rawPassword)
    {
        return SecurityUtils.matchesPassword(rawPassword, user.getPassword());
    }

    public void clearLoginRecordCache(String loginName)
    {
        if (redisService.hasKey(getCacheKey(loginName)))
        {
            redisService.deleteObject(getCacheKey(loginName));
        }
    }
}

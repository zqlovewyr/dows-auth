package org.dows.auth.handler;

import cn.hutool.core.util.ObjectUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class DowsLogoutSuccessHandler implements LogoutHandler {

    @Autowired
    private RedisTemplate redisTemplate;

    @SneakyThrows
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader(Constant.TOKEN);
        Object userInfo = redisTemplate.opsForValue().get(Constant.AUTHENTICATION_TOKEN + token);
        if (ObjectUtil.isNotNull(userInfo)) {
            String user[] = userInfo.toString().split(",");
            if (user != null && user.length == 2) {
//                LoginLog loginLog = new LoginLog();
//                loginLog.setOptionIp(IPUtils.getIpAddr(request));
//                loginLog.setOptionName("用户退出成功");
//                loginLog.setOptionTerminal(request.getHeader("User-Agent"));
//                loginLog.setUsername(user[1]);
//                loginLog.setOptionTime(new Date());
//                SpringUtil.publishEvent(new LoginLogEvent(loginLog));
            }
        }
        redisTemplate.delete(Constant.AUTHENTICATION_TOKEN + token);
    }
}
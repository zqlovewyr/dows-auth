package org.dows.auth.filter;

import cn.hutool.core.util.StrUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.Constant;
import org.dows.auth.DowsAuthenticationException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class ValidateCodeFilter extends OncePerRequestFilter {

    private AntPathMatcher pathMatcher = new AntPathMatcher();
    private RedisTemplate redisTemplate;
    private AuthenticationFailureHandler authenticationFailureHandler;

    public ValidateCodeFilter(RedisTemplate redisTemplate, AuthenticationFailureHandler authenticationFailureHandler) {
        this.redisTemplate = redisTemplate;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        String url = request.getRequestURI();
        if (pathMatcher.match(Constant.TOKEN_ENTRY_POINT_URL, url)) {
            String captcha = request.getParameter("captcha");
            String randomStr = request.getParameter("randomStr");

            if (StrUtil.isBlank(captcha) || StrUtil.isBlank(randomStr)) {
                DowsAuthenticationException exception = new DowsAuthenticationException("验证码为空");
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }

            String code_key = (String) redisTemplate.opsForValue().get(Constant.NUMBER_CODE_KEY + randomStr);
            if (StrUtil.isEmpty(code_key)) {
                DowsAuthenticationException exception = new DowsAuthenticationException("验证码过期");
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }

            if (!captcha.equalsIgnoreCase(code_key)) {
                DowsAuthenticationException exception = new DowsAuthenticationException("验证码不正确");
                authenticationFailureHandler.onAuthenticationFailure(request, response, exception);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
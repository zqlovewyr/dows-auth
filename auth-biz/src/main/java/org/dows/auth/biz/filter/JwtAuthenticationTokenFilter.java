package org.dows.auth.biz.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author:      gfl
 * Mail:        fuleiit@163.com
 * Date:        2023/2/7 22:15
 * Version:     1.0
 * Description: 
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取token
//        String token = request.getHeader("Authorization");
//        if (!StringUtils.hasText(token)) {
//            //放行
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        SecurityContextHolder.setTenantId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_TENANT_ID));
//        SecurityContextHolder.setDeptId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_DEPT_ID));
//        SecurityContextHolder.setUserId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USER_ID));
//        SecurityContextHolder.setUserName(ServletUtils.getHeader(request, SecurityConstants.DETAILS_USERNAME));
//        SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));
//        SecurityContextHolder.setAccountId(ServletUtils.getHeader(request, SecurityConstants.ACCOUNT_ID));
//
//       // String token = SecurityUtils.getToken();
//        if (StringUtils.isNotEmpty(token))
//        {
//            LoginUserVo loginUser = AuthUtil.getLoginUser(token);
//            if (StringUtils.isNotNull(loginUser))
//            {
//                AuthUtil.verifyLoginUserExpire(loginUser);
//                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
//            }
//        }
        //放行
        filterChain.doFilter(request, response);
    }
}

//package org.dows.auth.interceptor;
//
//
//import com.alibaba.fastjson.JSONObject;
//import io.jsonwebtoken.Claims;
//import lombok.extern.slf4j.Slf4j;
//import org.dows.auth.api.constant.SecurityConstants;
//import org.dows.auth.api.utils.ServletUtils;
//import org.dows.auth.api.utils.StringUtils;
//import org.dows.auth.api.utils.JwtUtils;
//import org.dows.auth.context.AuthUtil;
//import org.dows.auth.context.SecurityContextHolder;
//import org.dows.auth.vo.LoginUserVo;
//import org.dows.framework.api.Response;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.AsyncHandlerInterceptor;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.OutputStream;
//
///**
// * 自定义请求头拦截器，将Header数据封装到线程变量中方便获取
// * 注意：此拦截器会同时验证当前用户有效期自动刷新有效期
// *
// * @author vctgo
// */
//@Slf4j
//public class HeaderInterceptor implements AsyncHandlerInterceptor {
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        if (!(handler instanceof HandlerMethod)) {
//            return true;
//        }
//        //获取token
//        String token = request.getHeader("Authorization");
//        String userid = "";
//        try {
//            Claims claims = JwtUtils.parseToken(token);
//            userid = claims.getSubject();
//        } catch (Exception e) {
//            e.printStackTrace();
//            outputMsg(Response.fail("token非法"), response);
//            return false;
//        }
//        // String token = SecurityUtils.getToken();
//        if (StringUtils.isNotEmpty(token)) {
//            LoginUserVo loginUser = AuthUtil.getLoginUser(token);
//            if (StringUtils.isNotNull(loginUser)) {
//                SecurityContextHolder.setTenantId(loginUser.getTenantId());
//                // SecurityContextHolder.setDeptId(ServletUtils.getHeader(request, SecurityConstants.DETAILS_DEPT_ID));
//                SecurityContextHolder.setUserName(loginUser.getAccountName());
//                SecurityContextHolder.setUserKey(ServletUtils.getHeader(request, SecurityConstants.USER_KEY));
//                SecurityContextHolder.setAccountId(loginUser.getAccountId());
//                AuthUtil.verifyLoginUserExpire(loginUser);
//                SecurityContextHolder.set(SecurityConstants.LOGIN_USER, loginUser);
//            } else {
//                outputMsg(new Response(401, "用户未登录"), response);
//                return false;
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//            throws Exception {
//        SecurityContextHolder.remove();
//    }
//
//    /**
//     * 输出JSON格式消息
//     *
//     * @param result
//     * @param response
//     */
//    private <T> void outputMsg(Response result, HttpServletResponse response) {
//        response.setContentType("application/json");
//        OutputStream os = null;
//        try {
//            os = response.getOutputStream();
//            os.write(JSONObject.toJSONString(result).getBytes());
//        } catch (Exception e) {
//            log.error("LoginInterceptor has exception!", e);
//        } finally {
//            if (os != null) {
//                try {
//                    os.close();
//                } catch (Exception e) {
//                    log.error("", e);
//                }
//            }
//        }
//    }
//}

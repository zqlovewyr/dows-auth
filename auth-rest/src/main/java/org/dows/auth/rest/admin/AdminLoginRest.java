package org.dows.auth.rest.admin;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.api.utils.StringUtils;
import org.dows.auth.biz.UserDetailsServiceBiz;
import org.dows.auth.biz.context.AuthUtil;
import org.dows.auth.biz.context.SecurityUtils;
import org.dows.auth.biz.redis.TokenServiceBiz;
import org.dows.auth.form.LoginBodyForm;
import org.dows.auth.vo.LoginUserVo;
import org.dows.framework.api.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户(Users)表控制层
 *
 * @author lait.zhang
 * @since 2022-12-23 22:05:09
 */
@Api(tags = "总部端登录")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("admin/account")
public class AdminLoginRest {

    private final TokenServiceBiz tokenServiceBiz;

    private final UserDetailsServiceBiz userDetailsServiceBiz;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Response login(@RequestBody LoginBodyForm form)
    {
        try{
            // 用户登录
            LoginUserVo vo =userDetailsServiceBiz.login(form.getUserName(),form.getPassword(),2);
            return Response.ok(tokenServiceBiz.createToken(vo));
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }

    @PostMapping("/logout")
    @ApiOperation(value = "登出")
    public Response logout(HttpServletRequest request)
    {
        try{

            String account = SecurityUtils.getAccountId();

            String token = SecurityUtils.getToken(request);
            if (StringUtils.isNotEmpty(token))
            {
                // 删除用户缓存记录
                AuthUtil.logoutByToken(token);
                // 记录用户退出日志
            }
            return Response.ok();
        }catch (Exception e){
            return Response.fail("退出失败,系统错误");
        }
    }
}


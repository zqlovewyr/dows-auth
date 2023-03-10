package org.dows.auth.rest.user;


import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.api.utils.StringUtils;
import org.dows.auth.biz.UserDetailsServiceBiz;
import org.dows.auth.biz.configure.WxMaConfiguration;
import org.dows.auth.biz.configure.WxMaProperties;
import org.dows.auth.biz.context.AuthUtil;
import org.dows.auth.biz.context.SecurityUtils;
import org.dows.auth.biz.redis.TokenServiceBiz;
import org.dows.auth.form.LoginBodyForm;
import org.dows.auth.vo.LoginUserVo;
import org.dows.framework.api.Response;
import org.springframework.beans.factory.annotation.Autowired;
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
@Api(tags = "APP门店端登录")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user/account")
public class UserLoginRest {

    private final TokenServiceBiz tokenServiceBiz;

    private final UserDetailsServiceBiz userDetailsServiceBiz;

    @Autowired
    private WxMaProperties wxMaProperties;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Response login(@RequestBody LoginBodyForm form)
    {
        try{
            WxMaProperties.Config config = wxMaProperties.getConfigs().get(0);
            final WxMaService wxService = WxMaConfiguration.getMaService(config.getAppid());
            WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(form.getCode());
            if(StringUtils.isNull(session.getOpenid())){
                log.error("用户登录失败：openid获取失败");
                return Response.fail("网络异常");
            }
            // 用户登录
            LoginUserVo vo =userDetailsServiceBiz.login(session.getOpenid());
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


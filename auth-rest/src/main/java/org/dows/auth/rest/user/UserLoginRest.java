package org.dows.auth.rest.user;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.biz.TokenServiceBiz;
import org.dows.auth.biz.UserDetailsServiceBiz;
import org.dows.auth.form.LoginBodyForm;
import org.dows.framework.api.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户(Users)表控制层
 * @author lait.zhang
 * @since 2022-12-23 22:05:09
 */
@Api(tags = "登录")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
public class UserLoginRest {

    private final TokenServiceBiz tokenServiceBiz;

    private final UserDetailsServiceBiz userDetailsServiceBiz;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Response login(@RequestBody LoginBodyForm form)
    {
        // 用户登录
        return userDetailsServiceBiz.login(form.getUserName(), form.getPassword());
    }
}


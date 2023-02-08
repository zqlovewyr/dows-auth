package org.dows.auth.biz;

import cn.hutool.core.convert.Convert;
import org.apache.commons.lang3.StringUtils;
import org.dows.account.api.AccountUserApi;
import org.dows.account.biz.AccountBiz;
import org.dows.account.vo.AccountVo;
import org.dows.auth.biz.exception.AuthException;
import org.dows.auth.service.LoginService;
import org.dows.auth.utils.JwtUtil;
import org.dows.auth.biz.utils.SecurityUtils;
import org.dows.auth.constant.UserConstants;
import org.dows.auth.entity.LoginUser;
import org.dows.auth.entity.User;
import org.dows.auth.vo.LoginUserVo;
import org.dows.framework.api.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.HashMap;
import java.util.Objects;

@Configuration
public class UserDetailsServiceBiz implements UserDetailsService{


    @Autowired(required = false)
    private AccountBiz accountBiz;
    @Autowired
    private PasswordServiceBiz passwordServiceBiz;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private LoginService  loginService;
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据用户名查询用户信息
        AccountVo accountVo = accountBiz.queryAccountVoByAccountName(username,2);
        User userInfo = User.builder()
                .accountId(accountVo.getAccountId())
                .tenantId(accountVo.getTenantId())
                .appId(accountVo.getAppId())
                .accountName(accountVo.getAccountName())
                .password(accountVo.getPassword())
                .avatar(accountVo.getAvatar())
                .phone(accountVo.getPhone())
                .accountClientNo(accountVo.getAccountClientNo())
                .sex(accountVo.getSex())
                .source(accountVo.getSource())
                .job(accountVo.getJob())
                .birthday(accountVo.getBirthday())
                .education(accountVo.getEducation())
                .shengXiao(accountVo.getShengXiao())
                .constellation(accountVo.getConstellation())
                .createTime(accountVo.getCreateTime())
                .build();
        //如果查询不到数据就通过抛出异常来给出提示
        if(Objects.isNull(userInfo)){
            throw new RuntimeException("用户名或密码错误");
        }
        //TODO 根据用户查询权限信息 添加到LoginUser中

        //封装成UserDetails对象返回
        return new LoginUser(userInfo);
    }

    /**
     * 登录
     */
    public Response login(String username, String password)
    {
        return loginService.login(username,password);
    }

//
//    /**
//     * 登录
//     */
//    public LoginUserVo login(String username, String password)
//    {
//        // 用户名或密码为空 错误
//        if (StringUtils.isAnyBlank(username, password))
//        {
//            throw new AuthException("用户/密码必须填写");
//        }
//        // 密码如果不在指定范围内 错误
//        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
//                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
//        {
//            throw new AuthException("用户密码不在指定范围");
//        }
//        // 用户名不在指定范围内 错误
//        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
//                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
//        {
//            throw new AuthException("用户名不在指定范围");
//        }
//
//        // 查询用户信息
//        AccountVo accountVo = accountBiz.queryAccountVoByAccountName(username,2);
//        LoginUserVo userInfo = LoginUserVo.builder()
//                .accountId(accountVo.getAccountId())
//                .tenantId(accountVo.getTenantId())
//                .appId(accountVo.getAppId())
//                .accountName(accountVo.getAccountName())
//                .password(accountVo.getPassword())
//                .avatar(accountVo.getAvatar())
//                .phone(accountVo.getPhone())
//                .accountClientNo(accountVo.getAccountClientNo())
//                .sex(accountVo.getSex())
//                .source(accountVo.getSource())
//                .job(accountVo.getJob())
//                .birthday(accountVo.getBirthday())
//                .education(accountVo.getEducation())
//                .shengXiao(accountVo.getShengXiao())
//                .constellation(accountVo.getConstellation())
//                .createTime(accountVo.getCreateTime())
//                .build();
//        //线程塞入租户ID
//        SecurityUtils.setTenantId(Convert.toStr(userInfo.getTenantId()));
//        //先查询是否被停用了租户
////        if (userInfo.getTenantStatus() != null && UserStatus.DISABLE.getCode().equals(userInfo.getTenantStatus().toString()))
////        {
////            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "当前租户已经被停用，请联系管理员");
////            throw new ServiceException("当前租户已经被停用");
////        }
////        if (userInfo.getTenantEndDate() != null && userInfo.getTenantEndDate().compareTo(new Date()) < 0)
////        {
////            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "当前租户已超过租赁日期，请联系管理员");
////            throw new ServiceException("当前租户已超过租赁日期");
////        }
//
//        passwordServiceBiz.validate(userInfo, password);
//        return userInfo;
//    }

}

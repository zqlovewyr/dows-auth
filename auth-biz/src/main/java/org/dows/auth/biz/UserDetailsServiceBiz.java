package org.dows.auth.biz;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import org.dows.auth.biz.exception.AuthException;
import org.dows.auth.biz.utils.SecurityUtils;
import org.dows.auth.biz.utils.StringUtils;
import org.dows.auth.bo.LoginBodyBo;
import org.dows.auth.constant.UserConstants;
import org.dows.auth.service.LoginService;
import org.dows.auth.vo.AccountVo;
import org.dows.auth.vo.LoginUserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.Random;

@Configuration
public class UserDetailsServiceBiz{

    @Autowired
    private LoginService loginService;

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }


//    /**
//     * 登录
//     */
//    public Response login(String username, String password) {
//        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
//        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
//        if(Objects.isNull(authenticate)){
//            throw new RuntimeException("用户名或密码错误");
//        }
//        //使用userid生成token
//        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
//        String userId = loginUser.getUser().getId().toString();
//        String jwt = JwtUtil.createJWT(userId);
//        //authenticate存入redis
//        redisCache.setCacheObject("login:"+userId,loginUser);
//        //把token响应给前端
//        HashMap<String,String> map = new HashMap<>();
//        map.put("token",jwt);
//        return Response.ok(map,"登陆成功");
//    }


    /**
     * 登录
     * @param username
     * @param password
     * @param accountType  账号区分：1、总控端2、总部端、3、门店端APP4、小程序流量用户
     * @return
     */
    public LoginUserVo login(String username, String password,Integer accountType)
    {
        // 用户名或密码为空 错误
        if (StringUtils.isAnyBlank(username, password))
        {
            throw new AuthException("用户/密码必须填写");
        }
        // 密码如果不在指定范围内 错误
        if (password.length() < UserConstants.PASSWORD_MIN_LENGTH
                || password.length() > UserConstants.PASSWORD_MAX_LENGTH)
        {
            throw new AuthException("用户密码不在指定范围");
        }
        // 用户名不在指定范围内 错误
        if (username.length() < UserConstants.USERNAME_MIN_LENGTH
                || username.length() > UserConstants.USERNAME_MAX_LENGTH)
        {
            throw new AuthException("用户名不在指定范围");
        }

        // 查询用户信息
        LoginBodyBo bodyBo = new LoginBodyBo();
        bodyBo.setAccountType(accountType);
        bodyBo.setUserName(username);
        List<AccountVo> accountVos = loginService.selectAccountPage(bodyBo);
        if(accountVos.size() == 0){
            throw new AuthException("没有查询到此用户！请联系管理员");
        }
        AccountVo accountVo = accountVos.get(0);
        // String pass = new BCryptPasswordEncoder().encode(password);
        if(!SecurityUtils.matchesPassword(password, accountVo.getPassword())){
            throw new AuthException("密码错误！请重新输入");
        }

        LoginUserVo userInfo = new LoginUserVo();
        userInfo.setAccountId(accountVo.getAccountId());
        userInfo.setTenantId(accountVo.getTenantId());
        userInfo.setAppId(accountVo.getAppId());
        userInfo.setAccountName(accountVo.getAccountName());
        userInfo.setPassword(accountVo.getPassword());
        userInfo.setAvatar(accountVo.getAvatar());
        userInfo.setPhone(accountVo.getPhone());
        userInfo.setAccountClientNo(accountVo.getAccountClientNo());
        userInfo.setSex(accountVo.getSex());
        userInfo.setSource(accountVo.getSource());
        userInfo.setJob(accountVo.getJob());
        userInfo.setBirthday(accountVo.getBirthday());
        userInfo.setEducation(accountVo.getEducation());
        userInfo.setShengXiao(accountVo.getShengXiao());
        userInfo.setConstellation(accountVo.getConstellation());
        userInfo.setCreateTime(accountVo.getCreateTime());
        //线程塞入租户ID
        SecurityUtils.setTenantId(userInfo.getTenantId());
        // 用户AccountId
        SecurityUtils.setAccountId(userInfo.getAccountId());
        //先查询是否被停用了租户
//        if (userInfo.getTenantStatus() != null && UserStatus.DISABLE.getCode().equals(userInfo.getTenantStatus().toString()))
//        {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "当前租户已经被停用，请联系管理员");
//            throw new ServiceException("当前租户已经被停用");
//        }
//        if (userInfo.getTenantEndDate() != null && userInfo.getTenantEndDate().compareTo(new Date()) < 0)
//        {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "当前租户已超过租赁日期，请联系管理员");
//            throw new ServiceException("当前租户已超过租赁日期");
//        }

       //  passwordServiceBiz.validate(userInfo, password);
        return userInfo;
    }

    /**
     * 小程序端登录使用
     * @param openid
     * @return
     */
    public LoginUserVo login(String openid)
    {
        // 查询用户信息
        LoginBodyBo bodyBo = new LoginBodyBo();
        bodyBo.setAccountType(4);
        bodyBo.setOpenid(openid);
        AccountVo accountVo = null;
        List<AccountVo> accountVos = loginService.selectAccountPage(bodyBo);

        if (accountVos.size() == 0)
        {
            String wxUserNo = genRandomNum();
            AccountVo wxUser = new AccountVo();
            wxUser.setOpenid(openid);
            wxUser.setAccountName(wxUserNo);
            wxUser.setAccountId(IdWorker.getIdStr());
            wxUser.setSource("wx");
            wxUser.setOpenid(openid);
            wxUser.setAccountType(4); // 流量用户
            loginService.saveWxAccount(wxUser);
            accountVo = wxUser;
        }
        else {
            accountVo = accountVos.get(0);
        }
        LoginUserVo userInfo = new LoginUserVo();
        userInfo.setAccountId(accountVo.getAccountId());
        userInfo.setTenantId(accountVo.getTenantId());
        userInfo.setAppId(accountVo.getAppId());
        userInfo.setAccountName(accountVo.getAccountName());
        userInfo.setPassword(accountVo.getPassword());
        userInfo.setAvatar(accountVo.getAvatar());
        userInfo.setPhone(accountVo.getPhone());
        userInfo.setAccountClientNo(accountVo.getAccountClientNo());
        userInfo.setSex(accountVo.getSex());
        userInfo.setSource(accountVo.getSource());
        userInfo.setJob(accountVo.getJob());
        userInfo.setBirthday(accountVo.getBirthday());
        userInfo.setEducation(accountVo.getEducation());
        userInfo.setShengXiao(accountVo.getShengXiao());
        userInfo.setConstellation(accountVo.getConstellation());
        userInfo.setCreateTime(accountVo.getCreateTime());
        //线程塞入租户ID
        SecurityUtils.setTenantId(userInfo.getTenantId());
        // 用户AccountId
        SecurityUtils.setAccountId(userInfo.getAccountId());
        //先查询是否被停用了租户
//        if (userInfo.getTenantStatus() != null && UserStatus.DISABLE.getCode().equals(userInfo.getTenantStatus().toString()))
//        {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "当前租户已经被停用，请联系管理员");
//            throw new ServiceException("当前租户已经被停用");
//        }
//        if (userInfo.getTenantEndDate() != null && userInfo.getTenantEndDate().compareTo(new Date()) < 0)
//        {
//            recordLogService.recordLogininfor(username, Constants.LOGIN_FAIL, "当前租户已超过租赁日期，请联系管理员");
//            throw new ServiceException("当前租户已超过租赁日期");
//        }

        //  passwordServiceBiz.validate(userInfo, password);
        return userInfo;
    }
    // 获取8为随机编码
    public String genRandomNum()
    { int  maxNum = 36; int i; int count = 0; char[] str = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' }; StringBuffer pwd = new StringBuffer(""); Random r = new Random(); while(count < 8){ i = Math.abs(r.nextInt(maxNum)); if (i >= 0 && i < str.length) { pwd.append(str[i]); count ++; } } return pwd.toString();
    }
}

package org.dows.auth.biz;

import org.dows.account.biz.AccountBiz;
import org.dows.account.vo.AccountVo;
import org.dows.auth.entity.LoginUser;
import org.dows.auth.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Component
public class UserDetailsServiceImpl implements UserDetailsService{


    @Autowired
    private AccountBiz accountBiz;

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

}

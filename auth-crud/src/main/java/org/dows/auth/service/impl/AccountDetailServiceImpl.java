package org.dows.auth.service.impl;

import lombok.RequiredArgsConstructor;
import org.dows.account.api.dto.AccountInfo;
import org.dows.account.biz.AccountBiz;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@RequiredArgsConstructor
public class AccountDetailServiceImpl implements UserDetailsService {


    private final AccountApi accountApi;
    @Override
    public UserDetails loadUserByUsername(String accountName) throws UsernameNotFoundException {
        // 查询账号信息
        AccountInfo accountInfo = accountApi.findAccountByAccountname(accountName);
        // 查询权限

        return User.builder()
                .username(accountInfo.getIdentifier())
                .password(accountInfo.getAccountPwd())
                .build();
    }
}

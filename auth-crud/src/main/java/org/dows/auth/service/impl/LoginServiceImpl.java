package org.dows.auth.service.impl;

import org.dows.auth.bo.LoginBodyBo;
import org.dows.auth.mapper.LoginMapper;
import org.dows.auth.service.LoginService;
import org.dows.auth.vo.AccountVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author:      gfl
 * Mail:        fuleiit@163.com
 * Date:        2023/2/7 22:33
 * Version:     1.0
 * Description: 
 */
@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private LoginMapper loginMapper;
    @Override
    public List<AccountVo> selectAccountPage(LoginBodyBo loginBodyBo) {
        return loginMapper.selectAccountPage(loginBodyBo);
    }

    @Override
    public void saveWxAccount(AccountVo accountVo) {

    }

}

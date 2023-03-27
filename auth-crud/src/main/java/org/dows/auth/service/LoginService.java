package org.dows.auth.service;

import org.dows.auth.bo.LoginBodyBo;
import org.dows.auth.vo.AccountVo;
import org.dows.auth.vo.AppInfoVo;

import java.util.List;

/**
 * Author:      gfl
 * Mail:        fuleiit@163.com
 * Date:        2023/2/7
 * Version:     1.0
 * Description:
 */
public interface LoginService {

    // public Response login(String username, String password);
    List<AccountVo> selectAccountPage(LoginBodyBo loginBodyBo);

    void saveWxAccount(AccountVo accountVo);

    List<AppInfoVo> selectAppInfo(LoginBodyBo loginBodyBo);
}

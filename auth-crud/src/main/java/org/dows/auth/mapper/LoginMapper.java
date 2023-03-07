package org.dows.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.dows.auth.bo.LoginBodyBo;
import org.dows.auth.entity.User;
import org.dows.auth.vo.AccountVo;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;

import java.util.List;

/**
 * Author:      gfl
 * Mail:        fuleiit@163.com
 * Date:        2023/2/7
 * Version:     1.0
 * Description:
 */
@Mapper
public interface LoginMapper extends MybatisCrudMapper<User> {

    // public Response login(String username, String password);

    List<AccountVo> selectAccountPage(@Param("loginBodyBo")LoginBodyBo loginBodyBo);

    void saveWxAccount(@Param("accountVo")AccountVo accountVo);
}

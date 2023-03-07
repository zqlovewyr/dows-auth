package org.dows.auth.bo;

import lombok.Data;

/**
 * 用户登录对象
 *
 * @author vctgo
 */
@Data
public class LoginBodyBo
{
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户类型
     */
    private Integer accountType;

    /**
     * openid
     */
    private String openid;

}

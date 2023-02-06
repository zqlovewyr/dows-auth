package org.dows.auth.form;

import lombok.Data;

/**
 * 用户登录对象
 *
 * @author vctgo
 */
@Data
public class LoginBodyForm
{
    /**
     * 用户名
     */
    private String userName;

    /**
     * 用户密码
     */
    private String password;

}

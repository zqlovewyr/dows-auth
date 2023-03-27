package org.dows.auth.form;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(value = "用户名")
    private String userName;

    /**
     * 用户密码
     */
    @ApiModelProperty(value = "用户密码")
    private String password;

    /**
     * 小程序code码
     */
    @ApiModelProperty(value = "小程序code码 登录")
    private String code;

    /**
     * 小程序appId
     */
    @ApiModelProperty(value = "小程序appId 登录")
    private String appId;

}

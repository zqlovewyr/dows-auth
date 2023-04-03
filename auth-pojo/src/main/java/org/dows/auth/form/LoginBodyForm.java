package org.dows.auth.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.dows.auth.enums.MiniAppType;

/**
 * 用户登录对象
 *
 * @author vctgo
 */
@Data
@ApiModel(description = "用户登录对象")
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

}

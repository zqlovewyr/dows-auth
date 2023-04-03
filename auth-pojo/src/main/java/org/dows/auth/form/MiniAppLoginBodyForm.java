package org.dows.auth.form;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.dows.auth.enums.MiniAppType;

/**
 * 小程序用户登录对象
 *
 * @since 2023-04-02
 */
@Data
@ApiModel(description = "小程序用户登录对象")
public class MiniAppLoginBodyForm{
    /**
     * 小程序code码
     */
    @ApiModelProperty(value = "小程序code码")
    private String code;

    /**
     * 小程序appId
     */
    @ApiModelProperty(value = "小程序appId")
    private String appId;

    /**
     * 小程序类型
     */
    @ApiModelProperty(value = "小程序类型")
    private MiniAppType miniAppType;

    /**
     * 门店id
     */
    @ApiModelProperty(value = "门店id")
    private String storeId;

    /**
     * 商户账号id
     */
    @ApiModelProperty(value = "商户账号id")
    private String merchantAccountId;
}

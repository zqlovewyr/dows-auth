package org.dows.auth.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "app信息", description = "app信息")
public class AppInfoVo {

    @ApiModelProperty(value = "appId")
    private String appId;
    @ApiModelProperty(value = "租户ID")
    private String tenantId;
    @ApiModelProperty(value = "租户名称")
    private String tenantName;
    @ApiModelProperty(value = "账户ID")
    private String accountId;

    @ApiModelProperty(value = "appkey")
    private String appKey;


    @ApiModelProperty(value = "secret")
    private String secretKey;
}

package org.dows.auth.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "客户信息", description = "客户信息")
public class AccountVo {
    @ApiModelProperty(value = "主键")
    //主键ID
    //@JsonSerialize(using= ToStringSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Long id;

    @ApiModelProperty(value = "账号名")
    private String accountName;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String phone;
    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "appId")
    private String appId;

    @ApiModelProperty(value = "客户账号id")
    private String accountId;

    @ApiModelProperty(value = "客户编号")
    private String accountClientNo;

    @ApiModelProperty(value = "性别 1 男 ；2 女")
    private Integer sex;

    @ApiModelProperty(value = "消费总金额")
    private BigDecimal totalMoney;

    @ApiModelProperty(value = "活跃度")
    private String activation;

    @ApiModelProperty(value = "最近下单时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date lastOrderTime;

    @ApiModelProperty(value = "总订单量")
    private Integer totalOrderNum;

    @ApiModelProperty(value = "注册渠道")
    private String source;

    @ApiModelProperty(value = "省")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "区域")
    private String district;

    @ApiModelProperty(value = "职业")
    private String job;

    @ApiModelProperty(value = "生日")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "生肖")
    private String shengXiao;

    @ApiModelProperty(value = "星座")
    private String constellation;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "openid")
    private String openid;

    @ApiModelProperty(value = "账号区分：1、总控端2、总部端、3、门店端APP4、小程序流量用户")
    private Integer accountType;

    @ApiModelProperty(value = "支付宝userId")
    private String userId;

    @ApiModelProperty(value = "商户id")
    private String merchantAccountId;

    @ApiModelProperty(value = "门店id")
    private String storeId;

    @ApiModelProperty(value = "客户编号")
    private Integer userNo;

}

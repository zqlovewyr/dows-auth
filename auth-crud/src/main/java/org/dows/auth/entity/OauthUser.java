package org.dows.auth.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.userdetails.User;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * 用户信息
 *
 * @author vctgo
 */
@Data
@Builder
public class OauthUser extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 用户唯一标识
     */
    private String token;
    /**
     * 用户名id
     */
    private Long id;

    @ApiModelProperty(value = "租户ID")
    private String tenantId;

    @ApiModelProperty(value = "应用ID")
    private String appId;

    @ApiModelProperty(value = "账号名")
    private String accountName;
    @ApiModelProperty(value = "密码")
    private String password;
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ApiModelProperty(value = "手机号")
    private String phone;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate lastOrderTime;

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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDate birthday;

    @ApiModelProperty(value = "学历")
    private String education;

    @ApiModelProperty(value = "生肖")
    private String shengXiao;

    @ApiModelProperty(value = "星座")
    private String constellation;

    @ApiModelProperty(value = "注册时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    /**
     * 登录时间
     */
    private Long loginTime;

    /**
     * 过期时间
     */
    private Long expireTime;


    /**
     * 权限列表
     */
    private Set<String> permissions;

    /**
     * 角色列表
     */
    private Set<String> roles;


}

package org.dows.auth.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.dows.framework.crud.mybatis.CrudEntity;

import java.util.Date;

/**
 * (OauthAuthorization)实体类
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "OauthAuthorization对象", description = "")
public class OauthAuthorization implements CrudEntity {
    private static final long serialVersionUID = -80993788131245267L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("")
    private Long id;

    @ApiModelProperty("")
    private String registeredClientId;

    @ApiModelProperty("")
    private String principalName;

    @ApiModelProperty("")
    private String authorizationGrantType;

    @ApiModelProperty("")
    private String attributes;

    @ApiModelProperty("")
    private String state;

    @ApiModelProperty("")
    private String authorizationCodeValue;

    @ApiModelProperty("")
    private Date authorizationCodeIssuedAt;

    @ApiModelProperty("")
    private Date authorizationCodeExpiresAt;

    @ApiModelProperty("")
    private String authorizationCodeMetadata;

    @ApiModelProperty("")
    private String accessTokenValue;

    @ApiModelProperty("")
    private Date accessTokenIssuedAt;

    @ApiModelProperty("")
    private Date accessTokenExpiresAt;

    @ApiModelProperty("")
    private String accessTokenMetadata;

    @ApiModelProperty("")
    private String accessTokenType;

    @ApiModelProperty("")
    private String accessTokenScopes;

    @ApiModelProperty("")
    private String oidcIdTokenValue;

    @ApiModelProperty("")
    private Date oidcIdTokenIssuedAt;

    @ApiModelProperty("")
    private Date oidcIdTokenExpiresAt;

    @ApiModelProperty("")
    private String oidcIdTokenMetadata;

    @ApiModelProperty("")
    private String refreshTokenValue;

    @ApiModelProperty("")
    private Date refreshTokenIssuedAt;

    @ApiModelProperty("")
    private Date refreshTokenExpiresAt;

    @ApiModelProperty("")
    private String refreshTokenMetadata;

    @ApiModelProperty("")
    private String oidcIdTokenClaims;

    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

}


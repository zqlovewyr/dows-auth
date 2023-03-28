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
 * oauth2客户端基础信息表(OauthClient)实体类
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
@ApiModel(value = "OauthClient对象", description = "oauth2客户端基础信息表")
public class OauthClient implements CrudEntity {
    private static final long serialVersionUID = 599368718365762506L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("oauth2客户端id")
    private String clientId;

    @ApiModelProperty("客户端名称")
    private String clientName;

    @ApiModelProperty("客户端密码")
    private String clientSecret;

    @ApiModelProperty("名称首字母")
    private String nameLetters;

    @ApiModelProperty("客户端密码过期时间")
    private Date clientSecretExpiresAt;

    @ApiModelProperty("客户端创建时间")
    private Date clientIdIssuedAt;

    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

}


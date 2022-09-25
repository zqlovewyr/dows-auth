package org.dows.auth.crud.entity;

import java.util.Date;

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

/**
 * oauth2客户端重定向uri表(OauthRedirectUri)实体类
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
@ApiModel(value = "OauthRedirectUri对象", description = "oauth2客户端重定向uri表")
public class OauthRedirectUri implements CrudEntity {
    private static final long serialVersionUID = 131231410008417558L;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("数据库自增主键")
    private Long id;

    @ApiModelProperty("oauth2客户端id")
    private String clientId;

    @ApiModelProperty("客户端允许重定向的uri")
    private String redirectUri;

    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

}


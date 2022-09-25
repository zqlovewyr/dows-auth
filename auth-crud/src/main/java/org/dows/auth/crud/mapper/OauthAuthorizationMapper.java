package org.dows.auth.crud.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;
import org.dows.auth.crud.entity.OauthAuthorization;

/**
 * (OauthAuthorization)表数据库访问层
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Mapper
public interface OauthAuthorizationMapper extends MybatisCrudMapper<OauthAuthorization> {

}


package org.dows.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.auth.crud.entity.OauthAuthorization;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;

/**
 * (OauthAuthorization)表数据库访问层
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Mapper
public interface OauthAuthorizationMapper extends MybatisCrudMapper<OauthAuthorization> {

}


package org.dows.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.auth.crud.entity.OauthClientSettings;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;

/**
 * oauth2客户端配置(OauthClientSettings)表数据库访问层
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Mapper
public interface OauthClientSettingsMapper extends MybatisCrudMapper<OauthClientSettings> {

}


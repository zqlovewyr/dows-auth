package org.dows.auth.service.impl;

import org.dows.auth.crud.entity.OauthClientSettings;
import org.dows.auth.crud.mapper.OauthClientSettingsMapper;
import org.dows.auth.crud.service.OauthClientSettingsService;
import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.springframework.stereotype.Service;


/**
 * oauth2客户端配置(OauthClientSettings)表服务实现类
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Service("oauthClientSettingsService")
public class OauthClientSettingsServiceImpl extends MybatisCrudServiceImpl<OauthClientSettingsMapper, OauthClientSettings> implements OauthClientSettingsService {

}


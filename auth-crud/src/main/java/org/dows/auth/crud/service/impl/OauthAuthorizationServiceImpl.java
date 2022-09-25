package org.dows.auth.crud.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.auth.crud.mapper.OauthAuthorizationMapper;
import org.dows.auth.crud.entity.OauthAuthorization;
import org.dows.auth.crud.service.OauthAuthorizationService;
import org.springframework.stereotype.Service;


/**
 * (OauthAuthorization)表服务实现类
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Service("oauthAuthorizationService")
public class OauthAuthorizationServiceImpl extends MybatisCrudServiceImpl<OauthAuthorizationMapper, OauthAuthorization> implements OauthAuthorizationService {

}


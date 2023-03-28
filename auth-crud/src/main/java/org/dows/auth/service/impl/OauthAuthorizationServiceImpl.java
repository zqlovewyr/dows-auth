package org.dows.auth.service.impl;

import org.dows.auth.entity.OauthAuthorization;
import org.dows.auth.mapper.OauthAuthorizationMapper;
import org.dows.auth.service.OauthAuthorizationService;
import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
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


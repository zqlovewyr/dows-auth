package org.dows.auth.crud.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.auth.crud.mapper.OauthRedirectUriMapper;
import org.dows.auth.crud.entity.OauthRedirectUri;
import org.dows.auth.crud.service.OauthRedirectUriService;
import org.springframework.stereotype.Service;


/**
 * oauth2客户端重定向uri表(OauthRedirectUri)表服务实现类
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Service("oauthRedirectUriService")
public class OauthRedirectUriServiceImpl extends MybatisCrudServiceImpl<OauthRedirectUriMapper, OauthRedirectUri> implements OauthRedirectUriService {

}


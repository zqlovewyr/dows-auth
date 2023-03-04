package org.dows.auth.service.impl;

import org.dows.auth.crud.entity.OauthGrantType;
import org.dows.auth.crud.mapper.OauthGrantTypeMapper;
import org.dows.auth.crud.service.OauthGrantTypeService;
import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.springframework.stereotype.Service;


/**
 * oauth2客户端授权方式表(OauthGrantType)表服务实现类
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Service("oauthGrantTypeService")
public class OauthGrantTypeServiceImpl extends MybatisCrudServiceImpl<OauthGrantTypeMapper, OauthGrantType> implements OauthGrantTypeService {

}


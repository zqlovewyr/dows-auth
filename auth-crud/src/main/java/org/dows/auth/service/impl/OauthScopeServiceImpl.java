package org.dows.auth.service.impl;

import org.dows.auth.crud.entity.OauthScope;
import org.dows.auth.crud.mapper.OauthScopeMapper;
import org.dows.auth.crud.service.OauthScopeService;
import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.springframework.stereotype.Service;


/**
 * 授权-作用域(OauthScope)表服务实现类
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Service("oauthScopeService")
public class OauthScopeServiceImpl extends MybatisCrudServiceImpl<OauthScopeMapper, OauthScope> implements OauthScopeService {

}


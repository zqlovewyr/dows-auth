package org.dows.auth.crud.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.auth.crud.mapper.OauthClientMapper;
import org.dows.auth.crud.entity.OauthClient;
import org.dows.auth.crud.service.OauthClientService;
import org.springframework.stereotype.Service;


/**
 * oauth2客户端基础信息表(OauthClient)表服务实现类
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Service("oauthClientService")
public class OauthClientServiceImpl extends MybatisCrudServiceImpl<OauthClientMapper, OauthClient> implements OauthClientService {

}


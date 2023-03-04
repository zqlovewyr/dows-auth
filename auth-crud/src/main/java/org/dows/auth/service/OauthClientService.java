package org.dows.auth.service;

import org.dows.auth.crud.entity.OauthClient;
import org.dows.framework.crud.mybatis.MybatisCrudService;


/**
 * oauth2客户端基础信息表(OauthClient)表服务接口
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
public interface OauthClientService extends MybatisCrudService<OauthClient> {

}


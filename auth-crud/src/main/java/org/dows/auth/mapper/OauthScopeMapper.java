package org.dows.auth.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.dows.auth.crud.entity.OauthScope;
import org.dows.framework.crud.mybatis.MybatisCrudMapper;

/**
 * 授权-作用域(OauthScope)表数据库访问层
 *
 * @author lait.zhang@gmail
 * @since 2022-08-20 23:15:47
 */
@Mapper
public interface OauthScopeMapper extends MybatisCrudMapper<OauthScope> {

}


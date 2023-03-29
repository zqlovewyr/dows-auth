package org.dows.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.dows.auth.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {

}

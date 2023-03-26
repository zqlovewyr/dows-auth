package org.dows.auth.security.service;

import org.dows.auth.api.model.DowsUser;
import org.springframework.core.Ordered;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;

public interface DowsUserDetailsService extends UserDetailsService, Ordered {
    /**
     * 是否支持此客户端校验
     * @param clientId 目标客户端
     * @return true/false
     */
    default boolean support(String clientId, String grantType) {
        return true;
    }

    /**
     * 排序值 默认取最大的
     * @return 排序值
     */
    default int getOrder() {
        return 0;
    }

//    /**
//     * 构建userdetails
//     * @param sysUser 用户信息
//     * @return UserDetails
//     */
//    default UserDetails getUserDetails(SysUser sysUser) {
//        ArrayList<SimpleGrantedAuthority> authorities = new ArrayList<>();
//        if (!CollectionUtils.isEmpty(sysUser.getPermsSet())) {
//            sysUser.getPermsSet().forEach(auth -> authorities.add(new SimpleGrantedAuthority(auth)));
//        }
//        // 构造security用户
//        return new DowsUser(sysUser.getUserId(),sysUser.getMobile()
//                ,sysUser.getUserName(),
//                sysUser.getUserPass(),
//                true,
//                true,
//                true,
//                true,
//                authorities);
//    }

    /**
     * 通过用户实体查询
     * @param user
     * @return
     */
    default UserDetails loadUserByUser(DowsUser user) {
        return this.loadUserByUsername(user.getUsername());
    }
}

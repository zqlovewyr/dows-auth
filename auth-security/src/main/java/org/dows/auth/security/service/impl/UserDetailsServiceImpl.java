package org.dows.auth.security.service.impl;

import cn.hutool.json.JSON;
import lombok.RequiredArgsConstructor;
import org.dows.auth.security.service.DowsUserDetailsService;
import org.dows.framework.api.Response;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

/**
 * 我们自定义用户名和密码登录和数据库结合
 **/
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements DowsUserDetailsService {

    //后台用户数据库结合
    //private final RemoteUserService remoteUserService;

    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */

    //和数据库结合的真实登录
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //后台
//        Response result = remoteUserService.info(username);
//        //封装权限
//        SysUser user = null;
//        if (!ObjectUtils.isEmpty(result)) {
//            user = JSONObject.toJavaObject((JSON) JSONObject.toJSON(result.get("emp")), SysUser.class);
///*            Set<String> permsSet = new HashSet<>();
//            List<SysMenu> menus = new ArrayList<>();
//            //用户权限列表
//            if (sysUser.getUserId().equals("06afa981-46e9-11e9-b7af-3c2c30a6698a")) {
//                menus = menuService.findMenuForAll(1);
//                permsSet = menuService.getAllPermissions(menus);
//            } else {
//                permsSet = menuService.getUserPermissions(sysUser.getUserId());
//            }
//            sysUser.setPermsSet(permsSet);*/
//        }
//        if (user.getState() == 1) {
//            return getUserDetails(user);
//        } else {
//            return null;
//
//        }
        return null;
    }

    @Override
    public int getOrder() {
        return Integer.MIN_VALUE;
    }
}

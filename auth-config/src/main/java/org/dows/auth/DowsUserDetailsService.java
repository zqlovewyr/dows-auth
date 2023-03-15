package org.dows.auth;

import cn.hutool.core.collection.CollUtil;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class DowsUserDetailsService implements UserDetailsService {


//    @Autowired
//    private PermissionsService permissionsService;


    //private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*SysUser sysUser = userService.getOne(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, username));
        if (ObjectUtil.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return getDetail(sysUser);*/
        return null;
    }

    public UserDetails loadUserByUserId(String userId) throws UsernameNotFoundException {
       /* SysUser sysUser = userService.getById(userId);
        if (ObjectUtil.isNull(sysUser)) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return getDetail(sysUser);*/
        return null;
    }

    private UserDetails getDetail(SysUser sysUser) {
//        Set<String> permissions = permissionsService.getUserPermissions(sysUser.getUserId());
        Set<String> permissions = new HashSet<>();
        String[] roles = new String[0];
        if (CollUtil.isNotEmpty(permissions)) {
            roles = permissions.stream().map(role -> "ROLE_" + role).toArray(String[]::new);
        }
        Collection<? extends GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(roles);
        DowsUserDetailsUser dowsUserDetailsUser = new DowsUserDetailsUser(sysUser.getUserId(), sysUser.getUsername(), sysUser.getPassword(), authorities);
        return dowsUserDetailsUser;
    }
}
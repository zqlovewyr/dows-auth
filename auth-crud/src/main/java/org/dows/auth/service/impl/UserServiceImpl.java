package org.dows.auth.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.dows.auth.entity.User;
import org.dows.auth.mapper.UserMapper;
import org.dows.auth.service.UserService;
import org.springframework.stereotype.Service;


/**
 * 系统用户
 */
@Service
@AllArgsConstructor
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Override
    public int updatePassword(String userId, String password, String newPassword) {
        return 0;
    }

    @Override
    public void saveUserRole(User user) {

    }

    @Override
    public void updateUserRole(User user) {

    }


/*    private final UserRoleMapper sysUserRoleMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void saveUserRole(User user) {
        user.setCreateTime(new Date());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        sysUserMapper.insert(user);
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, user.getUserId()));
        //保存用户与角色关系
        saveUserRoleList(user);
    }

    @Override
    @Transactional
    public void updateUserRole(User user) {
        if (StrUtil.isBlank(user.getPassword())) {
            user.setPassword(null);
        } else {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        baseMapper.updateById(user);
        sysUserRoleMapper.delete(Wrappers.<SysUserRole>update().lambda().eq(SysUserRole::getUserId, user.getUserId()));
        //保存用户与角色关系
        saveUserRoleList(user);
    }


    @Override
    public int updatePassword(String userId, String password, String newPassword) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setPassword(newPassword);
        return sysUserMapper.updateById(sysUser);
    }

    public void saveUserRoleList(User user) {
        if (CollUtil.isNotEmpty(user.getRoleIdList())) {
            user.getRoleIdList().forEach(roleId -> {
                SysUserRole userRole = new SysUserRole();
                userRole.setUserId(user.getUserId());
                userRole.setRoleId(roleId);
                sysUserRoleMapper.insert(userRole);
            });
        }
    }*/
}
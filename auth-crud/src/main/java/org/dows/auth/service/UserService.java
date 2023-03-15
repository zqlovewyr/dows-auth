package org.dows.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.dows.auth.entity.User;


/**
 * 系统用户
 */
public interface UserService extends IService<User> {

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    int updatePassword(String userId, String password, String newPassword);

    void saveUserRole(User user);

    void updateUserRole(User user);

}
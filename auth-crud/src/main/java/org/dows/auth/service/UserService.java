package org.dows.auth.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.dows.auth.entity.OauthUser;


/**
 * 系统用户
 */
public interface UserService extends IService<OauthUser> {

    /**
     * 修改密码
     *
     * @param userId      用户ID
     * @param password    原密码
     * @param newPassword 新密码
     */
    int updatePassword(String userId, String password, String newPassword);

    void saveUserRole(OauthUser oauthUser);

    void updateUserRole(OauthUser oauthUser);

}
package org.dows.auth.service;

import org.dows.framework.api.Response;

/**
 * Author:      gfl
 * Mail:        fuleiit@163.com
 * Date:        2023/2/7
 * Version:     1.0
 * Description:
 */
public interface LoginService {

    public Response login(String username, String password);
}

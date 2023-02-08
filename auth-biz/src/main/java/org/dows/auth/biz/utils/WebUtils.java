package org.dows.auth.biz.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Author:      gfl
 * Mail:        fuleiit@163.com
 * Date:        2023/2/7 21:53
 * Version:     1.0
 * Description: 
 */
public class WebUtils {
    /**
     * 将字符串渲染到客户端
     *
     * @param response 渲染对象
     * @param string 待渲染的字符串
     * @return null
     */
    public static String renderString(HttpServletResponse response, String string) {
        try
        {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(string);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}

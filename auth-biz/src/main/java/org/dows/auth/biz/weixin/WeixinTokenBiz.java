package org.dows.auth.biz.weixin;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.api.weixin.WeixinTokenApi;
import org.dows.auth.biz.utils.HttpClientResult;
import org.dows.auth.biz.utils.HttpClientUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeixinTokenBiz implements WeixinTokenApi {

    private final RedisTemplate redisTemplate;



    //获取微信accesstoken url
    private final String WEIXIN_ACCESS_TOKEN_URL = "https://api.weixin.qq.com/cgi-bin/component/api_component_token";
    //微信公众号token获取 url
    private final String WEIXIN_OFFICIAL_NUMBER_URL = "https://api.weixin.qq.com/cgi-bin/token";
    //获取预授权码 url
    private final String WEIXIN_PREAUTHCODE_URL = "https://api.weixin.qq.com/cgi-bin/component/api_create_preauthcode";

    //票证推送 url
    private final String WEIXIN_PUSH_TICKET_URL = "https://api.weixin.qq.com/cgi-bin/component/api_start_push_ticket";

    



    private final String COMPONENT_VERIFY_TICKET = "component_verify_ticket";








    /**
     * 获取微信access_token
     * @return
     * @throws Exception
     */
    @Override
    public String getWeiXinAccessToken() {
        Map<String,String> param = new HashMap<>();
        param.put("component_appid","wxdb8634feb22a5ab9");
        param.put("component_appsecret","712e104d3d2586fcb8952cd8d272c9a6");
        param.put("component_verify_ticket",(String) redisTemplate.opsForValue().get(COMPONENT_VERIFY_TICKET));
        try {
            HttpClientResult result = HttpClientUtils.doPost(WEIXIN_ACCESS_TOKEN_URL, param,1);
            //直接返回token
            return JSONObject.parseObject(result.getContent()).get("component_access_token").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //获取预授权码
    @Override
    public String getPreauthcode() {

        Map<String,String> param = new HashMap<>();
        String accessToken = getWeiXinAccessToken();
        return null;
    }


    public static void main(String[] args) throws Exception {

        Map<String,String> param = new HashMap<>();
        param.put("grant_type","client_credential");
        param.put("appid","wx41ffec0ebdaf4bc1");
        param.put("secret","e0e48246ee1045e2f5cf3516b5771bb3");
        HttpClientResult result = HttpClientUtils.doGet("https://api.weixin.qq.com/cgi-bin/token",param);
        System.out.println(result.getContent());


    }

}

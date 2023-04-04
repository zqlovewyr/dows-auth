package org.dows.auth.biz.weixin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.api.weixin.WeixinTokenApi;
import org.dows.auth.biz.utils.HttpClientResult;
import org.dows.auth.biz.utils.HttpClientUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeixinTokenBiz implements WeixinTokenApi {

    private final String WEIXIN_REQUEST_URL_PREFIX = "https://api.weixin.qq.com/cgi-bin/component";

    private final String COMPONENT_VERIFY_TICKET = "component_verify_ticket";

    private RedisTemplate redisTemplate;


    /**
     * 获取微信access_token
     * @return
     * @throws Exception
     */
    @Override
    public String getWeiXinAccessToken() throws Exception {
        Map<String,String> param = new HashMap<>();
        param.put("component_appid","wxdb8634feb22a5ab9");
        param.put("component_appsecret","712e104d3d2586fcb8952cd8d272c9a6");
        param.put("component_verify_ticket",(String) redisTemplate.opsForValue().get(COMPONENT_VERIFY_TICKET));
        HttpClientResult result = HttpClientUtil.doPost(WEIXIN_REQUEST_URL_PREFIX+"api_component_token", param);
        return null;
    }











}

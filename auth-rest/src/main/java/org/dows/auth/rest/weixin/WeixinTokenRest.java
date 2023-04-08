package org.dows.auth.rest.weixin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.api.weixin.WeixinTokenApi;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/weixinToken")
@RequiredArgsConstructor
@Slf4j
public class WeixinTokenRest {

    private final WeixinTokenApi weixinTokenApi;

    @GetMapping("/getWeiXinAccessToken")
    public String getWeiXinAccessToken(){
        return weixinTokenApi.getWeiXinAccessToken();
    }










}

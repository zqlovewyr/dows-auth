package org.dows.auth.rest.weixin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.api.weixin.AuthEventReceptionApi;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/authEventReception")
@RequiredArgsConstructor
@Slf4j
public class AuthEventReceptionRest {


    private final AuthEventReceptionApi authEventReceptionApi;

    @PostMapping("/wechat/auth_event")
    public String wxAuthUrlNotify(HttpServletRequest request, String signature, String timestamp,
                           String nonce, String encrypt_type, String msg_signature,
                           String auth_code, String AuthorizerAppid, String AuthorizationCode){
        log.info("start =================");
        return authEventReceptionApi.wxAuthUrlNotify(request,signature,timestamp,nonce,encrypt_type,msg_signature,auth_code,AuthorizerAppid,AuthorizationCode);
    }
}

package org.dows.auth.api.weixin;


import javax.servlet.http.HttpServletRequest;

public interface AuthEventReceptionApi {

    String wxAuthUrlNotify(HttpServletRequest request, String signature, String timestamp,
                                  String nonce, String encrypt_type, String msg_signature,
                                  String auth_code, String AuthorizerAppid, String AuthorizationCode);

}

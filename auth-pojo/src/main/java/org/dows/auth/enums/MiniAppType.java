package org.dows.auth.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum MiniAppType {

    WEIXIN_MINI_APP("微信小程序"),
    ALIPAY_MINI_APP("支付宝小程序");

    private String desc;
}

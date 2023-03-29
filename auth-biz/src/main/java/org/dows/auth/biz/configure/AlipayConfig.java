package org.dows.auth.biz.configure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "alipay.miniapp")
public class AlipayConfig {

    private List<Config> configs;

    @Data
    public static class Config {
        /**
         * 商户生成签名字符串所使用的签名算法类型，目前支持 RSA2 和 RSA
         */
        private String signType;
        /**
         * 字符编码格式
         */
        private String charset;
        /**
         * 参数返回格式
         */
        private String format;
        /**
         * 支付宝网关url
         */
        private String serverUrl;
    }
}

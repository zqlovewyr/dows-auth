package org.dows.auth.api.client;

import lombok.Data;

import javax.net.ssl.SSLContext;
import javax.validation.constraints.NotBlank;

/**
 * 通道配置 在yml/db/配置中心/数据库中配置
 */
@Data
public class PayClientProperties {

    // 环境（沙箱环境xbox/正式环境prd）
    private String env;
    private String channelCode = "alipay";
    @NotBlank(message = "appId 值不能为空")
    //支付宝小程序ID+公众号小程序ID.
    private String appId;
    @NotBlank(message = "privateKey 值不能为空")
    private String privateKey;
    private String payPublicKey;
    private String serviceUrl = "https://openapi.alipay.com/gateway.do";
    private String charset = "UTF-8";
    private String signType = "RSA2";
    private String format = "json";
    private int certModel;
    // 应用公钥证书路径
    private String appCertPath;
    //支付宝公钥证书文件路径
    private String payCertPath;
    //支付宝CA根证书文件路径
    private String payRootCertPath;

    /**************************************微信支付参数配置START**************************/
    /**
     * 服务商模式下的子商户公众账号ID.
     */
    private String subAppId;
    /**
     * 商户号.
     */
    private String mchId;
    /**
     * 商户密钥.
     */
    private String mchKey;
    /**
     * 企业支付密钥.
     */
    private String entPayKey;
    /**
     * 服务商模式下的子商户号.
     */
    private String subMchId;
    /**
     * 微信支付异步回掉地址，通知url必须为直接可访问的url，不能携带参数.
     */
    private String notifyUrl;
    /**
     * 交易类型.
     * <pre>
     * JSAPI--公众号支付
     * NATIVE--原生扫码支付
     * APP--app支付
     * </pre>
     */
    private String tradeType;

    private SSLContext sslContext;
    /**
     * p12证书base64编码
     */
    private String keyString;
    /**
     * p12证书文件的绝对路径或者以classpath:开头的类路径.
     */
    private String keyPath;

    /**
     * apiclient_key.pem证书base64编码
     */
    private String privateKeyString;
    /**
     * apiclient_key.pem证书文件的绝对路径或者以classpath:开头的类路径.
     */
    private String privateKeyPath;

    /**
     * apiclient_cert.pem证书base64编码
     */
    private String privateCertString;
    /**
     * apiclient_cert.pem证书文件的绝对路径或者以classpath:开头的类路径.
     */
    private String privateCertPath;

    /**
     * apiclient_key.pem证书文件内容的字节数组.
     */
    private byte[] privateKeyContent;

    /**
     * apiclient_cert.pem证书文件内容的字节数组.
     */
    private byte[] privateCertContent;

    /**
     * apiV3 秘钥值.
     */
    private String apiV3Key;

    /**
     * apiV3 证书序列号值
     */
    private String certSerialNo;
    /**
     * 微信支付分serviceId
     */
    private String serviceId;

    /**
     * 微信支付分回调地址
     */
    private String payScoreNotifyUrl;


    /**
     * 微信支付分授权回调地址
     */
    private String payScorePermissionNotifyUrl;


    /**
     * 证书自动更新时间差(分钟)，默认一分钟
     */
    private int certAutoUpdateTime = 60;

    /**
     * p12证书文件内容的字节数组.
     */
    private byte[] keyContent;
    /**
     * 微信支付接口请求地址域名部分.
     */
    private String payBaseUrl = "https://api.mch.weixin.qq.com";

    /*********************************微信支付参数配置END*********************************/

    /*********************************微信小程序参数配置START*********************************/

    public static final String PREFIX = "wx.open";

    /**
     * 设置微信开放平台的app secret.
     */
    private String appSecret;

    /**
     * 设置微信开放平台的token.
     */
    private String token;

    /**
     * 设置微信开放平台的EncodingAESKey.
     */
    private String aesKey;

    /*********************************微信小程序参数配置END*********************************/

    public String getPayId() {
        return appId + "@" + channelCode;
    }
}

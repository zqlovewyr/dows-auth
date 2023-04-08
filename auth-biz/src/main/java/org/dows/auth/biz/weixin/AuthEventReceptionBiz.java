package org.dows.auth.biz.weixin;

import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.open.api.WxOpenConfigStorage;
import me.chanjar.weixin.open.api.WxOpenService;
import me.chanjar.weixin.open.api.impl.WxOpenInMemoryConfigStorage;
import me.chanjar.weixin.open.api.impl.WxOpenServiceImpl;
import me.chanjar.weixin.open.bean.message.WxOpenXmlMessage;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dows.auth.api.client.PayClientConfig;
import org.dows.auth.api.client.PayClientProperties;
import org.dows.auth.api.weixin.AuthEventReceptionApi;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthEventReceptionBiz implements AuthEventReceptionApi {

    private final PayClientConfig payClientConfig;

    private final RedisTemplate redisTemplate;

    private final String COMPONENT_VERIFY_TICKET = "component_verify_ticket";

    public String wxAuthUrlNotify(HttpServletRequest request, String signature, String timestamp,
                                  String nonce, String encrypt_type, String msg_signature,
                                  String auth_code, String AuthorizerAppid, String AuthorizationCode) {


        log.info("开始时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        try (InputStream inputStream = request.getInputStream()){
            String xml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            if (!StringUtils.isEmpty(auth_code)) {
                log.info("接收票据信息 预授权码独立信息");
                return "success";
            }
            log.info("接收票据事件密文：" + xml);
            WxOpenService wxOpenClient = null;
            WxOpenXmlMessage wxMessage = WxOpenXmlMessage.fromXml(xml);


            //手动mock
            PayClientProperties mockPayClientProperties = new PayClientProperties();
            mockPayClientProperties.setEnv("test");
            mockPayClientProperties.setChannelCode("weixin");
            mockPayClientProperties.setPrivateKey("MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDS/txxpRy9c0858vOs6aJMLyjixIi5l4glkLqCbkwzSa+jrdPA97zY0QkQEkwjGJn33lxv0/WWREWcTlqFDyM1jl9PsdF9F32WMYC1V7q0QWMHSc9DFDGYxEbgAtrHSwvjmWkCPVEKT8ay5ur9gnzzrvNnR4fGyS90QWpHQhau5sbIlwVwyDuzGBNcOYElto5X5P8htjpxESX9fnNYLDl3D4ecNYXBqBQ/BCJNYHJ7KW8F87HJXqn+I+Z2YY2MVq2VWXTGfZLiSZXP+UklLbtFbGJZvCBjRmC7xYIWIfP8uWcwn0xhdnMAW4oeQTcTSUJxPU8zeaiBh1JFa5ob787TAgMBAAECggEBAILjZT7+jUCdGoW5SB545JxcGQHrp2FyNhwPlxO4dn7OdC2WdKjdRgI+Ul/s6dxPs5vHDhK1K0EnYwNeu9qVFGNxyYB3r5vpCZg1Z0GXhZOyiEsKJ4/WJBo/kDQAsVU2Ic7z6OdhwDckW/+5LgdLflXwrr/f6MORJGX6+oHv5oBiOWkh8S5O3hAHLhCD8LHbaPy0aLDPMSbDZ5OOijFE6DXdvicXKRSjwlDLTF7pnqsYCr234Z09GrO1AuUq+hdaEL9UgSsjp9hHXyA+JHi08W2M56YKDH5Zdp9CFKfT22FPJIT6wYd8366Y5+/WJBkPREKyBCNabzU7Q6AHTOq27vECgYEA/hRBYtidhGkZjLhQd82Drxxg/8HFB3VVliSPLhS9jS8q0Xpkob5ha95ouuwq5VbPi2eRgZy7I44yBp9d0YbZbcb4kbVeMJrMeEOKu3aGwa/K9gNP+IVM6eNy8jQd29csDfFqr3JEQV0pJFCgOUl/QU4TrlZXFh6IqzKROy8R+ykCgYEA1Jc4xkb8sI7uWUEUooSCXwbwvglxTAhau4XaT07ivy3hmcWfCx4juQ3plb+pnkPZmO0vaHFO4F7hzqX00mEu/areZLc8UXXu52kMRf48awuGbVuoN+i3nI8PYY6nkQiGyDQfCwCfkd0S/ETdw1bZhJkFDQEWMEiFB3VRY32LdZsCgYBE326+AaPpMagoWgoN/5qTjCjLC1aCaA70LSLWxe5/5+o+cGP46Cd2WwqeqMgT9M07p65sqPSddb0fyHFhC6HCvS3CKZzHph6I9x4TJNTwiduFhm5WGqQ3vlz5RKFXZ2NcwDVAK8ROx5cQbf6QqNii9iwllOf0agB4D7QIjADeyQKBgQDF+9dqAK5QF6yt/sgi2lk3+pS1xsF218j4Hx5DAep1tVsHBF6r0fPe4bAKQCbNN88sdqfSEsQsfPC6rR+l1dAXecwH7AYGr1hAtzow7SaDYoZcziGJf/ePnEPszRgNH+22AaRvcLMq+sI1okUNKJMGxCNKCxsI+GC7o2yaxU7dxQKBgCdN0CzAT9YYBPbZ4cJel/pCjWPD+0NaqIYpme55oNPWvVsZKsbSnVeCZCUChTwk3O31wWnkJ6TCd3QV1g1xwaqp81ih7E+Uvl0dQAFLLdbuDBJcySi1W71Ui6X7KBEuEQIi2THsclYSPB7DhSQV/ZJFOO4fEn3soVs52Ep/nq+K");
            mockPayClientProperties.setPayPublicKey("MIID6TCCAtGgAwIBAgIUKG4XlAqgjYAg7sHGC0z5suvK9QwwDQYJKoZIhvcNAQELBQAwXjELMAkGA1UEBhMCQ04xEzARBgNVBAoTClRlbnBheS5jb20xHTAbBgNVBAsTFFRlbnBheS5jb20gQ0EgQ2VudGVyMRswGQYDVQQDExJUZW5wYXkuY29tIFJvb3QgQ0EwHhcNMjExMjEwMTAwMTIyWhcNMjYxMjA5MTAwMTIyWjB7MRMwEQYDVQQDDAoxNjA0NDA0MzkyMRswGQYDVQQKDBLlvq7kv6HllYbmiLfns7vnu58xJzAlBgNVBAsMHuS4iua1t+acieaYn+enkeaKgOaciemZkOWFrOWPuDELMAkGA1UEBgwCQ04xETAPBgNVBAcMCFNoZW5aaGVuMIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0v7ccaUcvXNPOfLzrOmiTC8o4sSIuZeIJZC6gm5MM0mvo63TwPe82NEJEBJMIxiZ995cb9P1lkRFnE5ahQ8jNY5fT7HRfRd9ljGAtVe6tEFjB0nPQxQxmMRG4ALax0sL45lpAj1RCk/Gsubq/YJ8867zZ0eHxskvdEFqR0IWrubGyJcFcMg7sxgTXDmBJbaOV+T/IbY6cREl/X5zWCw5dw+HnDWFwagUPwQiTWByeylvBfOxyV6p/iPmdmGNjFatlVl0xn2S4kmVz/lJJS27RWxiWbwgY0Zgu8WCFiHz/LlnMJ9MYXZzAFuKHkE3E0lCcT1PM3mogYdSRWuaG+/O0wIDAQABo4GBMH8wCQYDVR0TBAIwADALBgNVHQ8EBAMCBPAwZQYDVR0fBF4wXDBaoFigVoZUaHR0cDovL2V2Y2EuaXRydXMuY29tLmNuL3B1YmxpYy9pdHJ1c2NybD9DQT0xQkQ0MjIwRTUwREJDMDRCMDZBRDM5NzU0OTg0NkMwMUMzRThFQkQyMA0GCSqGSIb3DQEBCwUAA4IBAQAOnIF7gVz5Hh4Ihs/ukie2dmOrxAw5rksnBryLa8YZlRlu2dRRvohW4dxevlUWS7Op0MV9h/sijMu2gXeTzRkjEch2p0SQWcqwNmqLRs3uQkAnZVcV5e475WzSFwnj7HBm/k7aO44DKTtwiH5sp94s3AN0YP4COF7tkLPNKyMa1xuqQmyfG+lRVz5Mh7VWADteKwtUJ9UflqOVnpeSi1DASLMjLNfrOOCF+ZamJnzB+eOf423BE59xpgHg7WDPKGQA9N9ucfxGcwcaMmHjSTubBG7YkXJQztjx8sl4ncaszq3XqsaYm9gv/rBRZlOX4vRDzCasFCslSbMHLZOAjL0D");
            mockPayClientProperties.setAppId("wxdb8634feb22a5ab9");
            mockPayClientProperties.setAppSecret("712e104d3d2586fcb8952cd8d272c9a6");
            mockPayClientProperties.setMchId("1604404392");
            mockPayClientProperties.setApiV3Key("ba40df0c6afb01e5bae35d7337372cde");
            mockPayClientProperties.setServiceUrl("https://api.weixin.qq.com");
            mockPayClientProperties.setCharset("UTF-8");
            mockPayClientProperties.setSignType("RSA2");
            mockPayClientProperties.setFormat("json");
            mockPayClientProperties.setCertModel(1);
            mockPayClientProperties.setPrivateKeyPath("classpath:weixin/wxapiclient_key.pem");
            mockPayClientProperties.setPayCertPath("classpath:weixin/wxapiclient_cert.pem");
            mockPayClientProperties.setToken("8f5c48103b5c11ebb0460a80ff2603de");
            mockPayClientProperties.setAesKey("8f5c48103b5c11ebb0460a80ff2603dc99999988888");
            Map<String, PayClientProperties> clientMap = List.of(mockPayClientProperties).stream()
                    .filter(pc -> StrUtil.isNotBlank(pc.getAppId()))
                    .collect(Collectors.toMap(PayClientProperties::getPayId, Function.identity()));

//
//            Map<String, PayClientProperties> clientMap = payClientConfig.getClientConfigs().stream()
//                    .filter(pc -> StrUtil.isNotBlank(pc.getAppId()))
//                    .collect(Collectors.toMap(PayClientProperties::getPayId, Function.identity()));
            PayClientProperties payClientProperties = clientMap.get(wxMessage.getAppId() + "@weixin");
            if (payClientProperties.getCertModel() == 1) {
                wxOpenClient = buildWxOpenClient(payClientProperties);
            }

            wxMessage = WxOpenXmlMessage.fromEncryptedXml(xml, wxOpenClient.getWxOpenConfigStorage(), timestamp, nonce, msg_signature);
            log.info("收到授权事件推送通知: {} ", wxMessage.toString());

            //更新数据库以及变量
            if (wxMessage.getInfoType() == null) {
                log.info("未知事件类型，过滤");
                return "success";
            }
            String InfoType = wxMessage.getInfoType();
            if (InfoType.equals("component_verify_ticket")) {
                String componentVerifyTicket = wxMessage.getComponentVerifyTicket();
                Long createTime =  wxMessage.getCreateTime();
                log.info("收到授权事件：验证票据通知 ComponentVerifyTicket=" + componentVerifyTicket);

                if (!StringUtils.isEmpty(componentVerifyTicket)) {
                    //暂时存入redis
                    redisTemplate.opsForValue().set(COMPONENT_VERIFY_TICKET,componentVerifyTicket);
                }
                log.info("test redis componentVerifyTicket: {}", redisTemplate.opsForValue().get(COMPONENT_VERIFY_TICKET));
            } else if (InfoType.equals("authorized") || InfoType.equals("updateauthorized")) {//获取授权AuthorizationCode

                AuthorizerAppid = wxMessage.getAuthorizerAppid();
                AuthorizationCode = wxMessage.getAuthorizationCode();
                //				String AuthorizationCodeExpiredTime = xmlMap.get("AuthorizationCodeExpiredTime") + "";
                //				String PreAuthCode = xmlMap.get("PreAuthCode")+"";
                //todo 根据appid获取信息，获取后进行更新AuthorizationCode信息
                log.info("收到授权事件：回调填写授权码信息！appid={}", AuthorizerAppid);
            } else if (InfoType.equals("notify_third_fasteregister")) {
                log.info("收到授权事件：注册审核事件推送通知");
                //				String AppId = xmlMap.get("AppId")+"";//三方平台appid
                Long createTime = wxMessage.getCreateTime();
                String appid = wxMessage.getAuthorizerAppid();//创建成功的小程序id
                String get_auth_code = wxMessage.getAuthCode();//第三方授权码
                String infoCode = wxMessage.getInfo().getCode();//企业代码
                int infoCodeType = wxMessage.getInfo().getCodeType();//企业代码
                //				String infoComponentPhone = xmlMap.get("info.component_phone")+"";//第三方联系电话
                //企业
                String infoLegalPersonaName = wxMessage.getInfo().getLegalPersonaName();//法人姓名
                String infoLegalPersonaWechat = wxMessage.getInfo().getLegalPersonaWechat();//法人微信号
                String infoName = wxMessage.getInfo().getName();//企业名称
                //个人
                String infoWxuser = wxMessage.getInfo().getWxuser();//个人微信号
                String infoIdname = wxMessage.getInfo().getIdname();//个人姓名

                String msg = wxMessage.getMsg();//推送返回结果内容
                int status = wxMessage.getStatus();//推送事件结果状态

                String taskid =  wxMessage.getInfo().getUniqueId();//推送事件id

                //修改完成信息
                //分为个人与企业
                if (!StringUtils.isEmpty(taskid)) {//个人申请
                    log.info("收到授权事件：个人回调修改注册信息！taskid={}", taskid);
                    //todo 个人回调修改注册信息
                } else {
                    log.info("收到授权事件：企业回调修改注册信息！code={},legalPersonaName={},legalPersonaWechat={}", infoCode, infoLegalPersonaName, infoLegalPersonaWechat);
                    //todo 企业回调修改注册信息
                }
            }
        } catch (Exception e) {
            log.error("接收票据事件异常" + e.getMessage(), e);
        }
        return "success";
    }

    public static WxOpenService buildWxOpenClient(PayClientProperties config) {
        WxOpenConfigStorage wxOpenConfigStorage = new WxOpenInMemoryConfigStorage();
        wxOpenConfigStorage.setComponentAppId(config.getAppId());
        wxOpenConfigStorage.setComponentAesKey(config.getAesKey());
        wxOpenConfigStorage.setComponentAppSecret(config.getAppSecret());
        wxOpenConfigStorage.setComponentToken(config.getToken());
        WxOpenService wxOpenService  = new WxOpenServiceImpl();
        wxOpenService.setWxOpenConfigStorage(wxOpenConfigStorage);
        return wxOpenService;
    }




}

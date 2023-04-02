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
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthEventReceptionBiz implements AuthEventReceptionApi {

    private final PayClientConfig payClientConfig;

    public String wxAuthUrlNotify(HttpServletRequest request, String signature, String timestamp,
                                  String nonce, String encrypt_type, String msg_signature,
                                  String auth_code, String AuthorizerAppid, String AuthorizationCode) {

        try (InputStream inputStream = request.getInputStream()){
            String xml = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            if (!StringUtils.isEmpty(auth_code)) {
                log.info("接收票据信息 预授权码独立信息");
                return "success";
            }
            log.info("接收票据事件密文：" + xml);
            WxOpenService wxOpenClient = null;
            WxOpenXmlMessage wxMessage = WxOpenXmlMessage.fromXml(xml);
            Map<String, PayClientProperties> clientMap = payClientConfig.getClientConfigs().stream()
                    .filter(pc -> StrUtil.isNotBlank(pc.getAppId()))
                    .collect(Collectors.toMap(PayClientProperties::getPayId, Function.identity()));
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
                    //todo实现数据库存储

                }
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

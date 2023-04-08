package org.dows.auth.rest.user;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.auth.api.utils.StringUtils;
import org.dows.auth.biz.UserDetailsServiceBiz;
import org.dows.auth.biz.configure.AlipayConfig;
import org.dows.auth.biz.context.AuthUtil;
import org.dows.auth.biz.context.SecurityUtils;
import org.dows.auth.biz.redis.TokenServiceBiz;
import org.dows.auth.enums.MiniAppType;
import org.dows.auth.form.LoginBodyForm;
import org.dows.auth.form.MiniAppLoginBodyForm;
import org.dows.auth.utils.GetOpenIdUtil;
import org.dows.auth.vo.AppInfoVo;
import org.dows.auth.vo.LoginUserVo;
import org.dows.framework.api.Response;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户(Users)表控制层
 *
 * @author lait.zhang
 * @since 2022-12-23 22:05:09
 */
@Api(tags = "小程序登录")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user/account")
public class UserLoginRest {

    private final TokenServiceBiz tokenServiceBiz;

    private final UserDetailsServiceBiz userDetailsServiceBiz;

    private AlipayConfig alipayConfig;

//    @Autowired
//    private WxMaProperties wxMaProperties;

    @PostMapping("/login")
    @ApiOperation(value = "登录")
    public Response login(@RequestBody MiniAppLoginBodyForm form)
    {
        try{

            AppInfoVo appInfoVo = userDetailsServiceBiz.selectAppInfo(form.getAppId());
            if(appInfoVo == null){
                return Response.fail("此商户未配置");
            }
            if (MiniAppType.WEIXIN_MINI_APP == form.getMiniAppType()) {
//                WxMaProperties.Config config = wxMaProperties.getConfigs().get(0);
//                final WxMaService wxService = WxMaConfiguration.getMaService(config.getAppid());
//                WxMaJscode2SessionResult session = wxService.getUserService().getSessionInfo(form.getCode());
                GetOpenIdUtil getOpenIdUtil=new GetOpenIdUtil();
                String jsonId = getOpenIdUtil.getOpenid(appInfoVo.getAppId(),form.getCode(),appInfoVo.getSecretKey());
                JSONObject jsonObject = JSONObject.parseObject(jsonId);
                String openid = jsonObject.get("openid").toString();
                if(StringUtils.isNull(openid)){
                    log.error("用户登录失败：openid获取失败");
                    return Response.fail("网络异常");
                }
                // 用户登录
                LoginUserVo vo =userDetailsServiceBiz.loginWxMiniApp(openid, form);
                return Response.ok(tokenServiceBiz.createToken(vo, 4));
            } else if (MiniAppType.ALIPAY_MINI_APP == form.getMiniAppType()) {

                // todo 根据appid查询公钥和私钥
                String appPrivateKey = "";
                String alipayPublicKey = "";
                AlipayConfig.Config config = alipayConfig.getConfigs().get(0);
                AlipayClient alipayClient = new DefaultAlipayClient(config.getServerUrl(),
                        appInfoVo.getAppId(), appPrivateKey, config.getFormat(),
                        config.getCharset(), alipayPublicKey, config.getSignType());
                AlipaySystemOauthTokenRequest request = new AlipaySystemOauthTokenRequest();
                request.setCode(form.getCode());
                request.setGrantType("authorization_code");
                AlipaySystemOauthTokenResponse response = alipayClient.execute(request);
                if (StringUtils.isEmpty(response.getUserId())) {
                    log.error("alipay-通过code换取 access_token 和 userId失败 code:{} msg:{}",
                            response.getCode(), response.getMsg());
                }
                // 用户登录
                LoginUserVo vo = userDetailsServiceBiz.loginAliMiniApp(response.getUserId(), form);
                return Response.ok(tokenServiceBiz.createToken(vo, 4));
            }
            return Response.fail("小程序类型参数错误");
        }catch (Exception e){
            return Response.fail(e.getMessage());
        }
    }
    @PostMapping("/logout")
    @ApiOperation(value = "登出")
    public Response logout(HttpServletRequest request)
    {
        try{
            String token = SecurityUtils.getToken(request);
            if (StringUtils.isNotEmpty(token))
            {
                // 删除用户缓存记录
                AuthUtil.logoutByToken(token);
                // 记录用户退出日志
            }
            return Response.ok();
        }catch (Exception e){
            return Response.fail("退出失败,系统错误");
        }
    }

    @GetMapping("/getMerchantAccountIdByAppId")
    @ApiOperation(value = "根据appId获取商户账号id")
    public Response<String> getMerchantAccountIdByAppId(@RequestParam String appId){
        return Response.ok(userDetailsServiceBiz.getMerchantAccountIdByAppId(appId));
    }

}


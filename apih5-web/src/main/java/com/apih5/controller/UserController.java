package com.apih5.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.entity.SysUserWebEntity;
import com.apih5.framework.annotation.RequirePermissions;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.ComConst;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechat.entity.json.response.UMUserInfoResp;
import com.apih5.framework.api.wechat.service.WeChatDbService;
import com.apih5.framework.api.wechat.service.WeChatService;
import com.apih5.framework.api.wechatenterprise.entity.json.request.oauth.OpenIdInfoReq;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseDbService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.wechatutils.JsonUtil;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.cache.RedisCacheService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.PGIdConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.SmsEntity;
import com.apih5.framework.entity.TokenEntity;
import com.apih5.framework.utils.AESUtil;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.SmsUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.mybatis.pojo.SysLog;
import com.apih5.service.LogService;
import com.apih5.service.SysCaptchaService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.extra.emoji.EmojiUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * ??????Controller
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private Environment env;

    @Autowired
    private ResponseEntity repEntity;

    @ApolloConfig
    private Config config;

    @Autowired(required = true)
    private RedisCacheService sysRedisCacheService;

    @Autowired
    private WeChatDbService weChatDbService;

    @Autowired
    private WeChatService weChatService;

    @Autowired
    private WeChatEnterpriseDbService weChatEnterpriseDbService;

    @Autowired
    private WeChatEnterpriseService wechatEnterpriseService;

    @Autowired
    private UserService userService;

//    @Autowired
//    private RoleService roleService;

    @Autowired
    private LogService logService;

    @Autowired
    private SysCaptchaService sysCaptchaService;

//    @Autowired
//    private UserTokenService userTokenService;

    @Autowired
    private Apih5Properties apih5Properties;

    @Autowired
    private RequestHolderConfiguration requestHolderConfiguration;

//    /**
//     * ????????????
//     */
//    @ApiOperation(value = "????????????", notes = "????????????")
//    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Validated({ SysUser.Second.class }) SysUser sysUser) {
//        // ??????ID?????????ID????????????????????????
//        // if(StrUtil.isEmpty(sysUser.getCorpId())
//        // || StrUtil.isEmpty(sysUser.getAppId())){
//        // return repEntity.error("sys.exception");
//        // }
//        if (StrUtil.isEmpty(sysUser.getAccountId())) {
//            return repEntity.error("sys.exception");
//        }
//        // ???????????????????????????ID??????????????????
//        if (StrUtil.equals("1", sysUser.getLoginType())) {
//            // ??????ID???????????????????????????????????????
//            if (StrUtil.isEmpty(sysUser.getUserId()) || StrUtil.isEmpty(sysUser.getUserPwd())) {
//                return repEntity.error("sys.user.empty");
//            }
//
//            // ?????????????????????????????????
//            boolean useCaptcha = apih5Properties.isRegisterUseCaptcha();
//            // ???????????????????????????
//            // boolean captchaAccess = false;
//            // ???????????????????????????
//            // boolean smsCaptchaAccess = false;
//            // ??????????????????????????????????????????
//            if (useCaptcha) {
//                // String captcha = sysUser.getCaptcha();
//                // // ?????????????????????????????????????????????
//                // if (StringUtils.isEmpty(captcha)) {
//                // captcha = "";
//                // }
//                // // ?????????????????????
//                // captchaAccess =
//                // StringUtils.isEmpty(rpcApi.checkCaptchaExists(captcha)) ?
//                // false :
//                // true;
//                // ???????????????????????????
//                // if (!captchaAccess) {
//                // return repEntity.error("sys.user.captcha");
//                // }
//            }
//        }
//        // ??????????????????????????????
//        else if (StrUtil.equals("2", sysUser.getLoginType())) {
//            if (StrUtil.isEmpty(sysUser.getMobile())) {
//                return repEntity.error("sys.user.mobile.error");
//            }
//            // ???????????????????????????
//            boolean useSmsCaptcha = apih5Properties.isUseSmsCaptcha();
//            // ????????????????????????????????????
//            if (useSmsCaptcha) {
//                // userId???????????????
//                String code = SmsUtils.checkCaptchaEhCache(sysUser.getUserId(), sysUser.getCaptcha());
//                if (!StrUtil.equals("ok", code)) {
//                    return repEntity.error(code);
//                }
//            }
//            // ????????????????????????????????????????????????????????????
//            if (StrUtil.isEmpty(sysUser.getUserId())) {
//                sysUser.setUserId(sysUser.getMobile());
//            }
//            // ????????????????????????,??????????????????????????????
//            if (StrUtil.isEmpty(sysUser.getUserPwd())) {
//                sysUser.setUserPwd(sysUser.getCaptcha());
//                sysUser.setExt1(sysUser.getCaptcha());
//            }
//        }
//        return userService.addUser(sysUser);
//    }

    /**
     * ????????????
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request,
            @RequestBody @Validated({ SysUser.Second.class }) SysUser sysUser) {
        // ????????????
        String webAesFlag = request.getParameter("WEBFlag");
        if (StrUtil.equals("Ab25DB", webAesFlag)) {
            try {
                String userId = AESUtil.desEncrypt(sysUser.getUserId());
                String userPwd = AESUtil.desEncrypt(sysUser.getUserPwd());
                sysUser.setUserId(userId);
                sysUser.setUserPwd(userPwd);
            } catch (Exception e) {
                LoggerUtils.printDebugLogger(logger, "???????????????" + e.getMessage());
                return repEntity.error("sys.user.pwd");
            }
        }

        // ????????????ID
        if (StrUtil.isEmpty(sysUser.getAccountId()) && !StrUtil.equals("6", sysUser.getLoginType())) {// 6??????????????????
            return repEntity.error("sys.exception");
        }
        // ???????????????????????????ID??????????????????
        if (StrUtil.equals("1", sysUser.getLoginType())) {
//            int loginErrorCount = sysRedisCacheService.getLoginLockRedis(sysUser.getUserId());
//            if(loginErrorCount>5) {
//                return repEntity.layerMessage("no", "???????????????????????????????????????");
//            }
            // ???????????????????????????
            boolean useCaptcha = apih5Properties.isLoginUseCaptcha();
            // ????????????????????????????????????
            if (useCaptcha) {
                ResponseEntity check = sysCaptchaService.checkSysCaptchaCode(sysUser.getCaptchaId(), sysUser.getCaptchaCode());
                if (!check.isSuccess()) {
                    return repEntity.layerMessage("no", check.getMessage());
                }
            }
            
            // ??????ID???????????????????????????????????????
            if (StrUtil.isEmpty(sysUser.getUserId()) || StrUtil.isEmpty(sysUser.getUserPwd())) {
                return repEntity.error("sys.user.pwd");
            }
            LoggerUtils.printDebugLogger(logger, "login fwh=" + sysUser.getUserId());
        }
        // ??????????????????????????????
        else if (StrUtil.equals("2", sysUser.getLoginType())) {
            if (StrUtil.isEmpty(sysUser.getMobile())) {
                return repEntity.error("sys.user.mobile.error");
            }
            String code = SmsUtils.checkCaptchaEhCache(sysUser.getMobile(), sysUser.getCaptcha());
            if (!StrUtil.equals("ok", code)) {
//                return repEntity.error(code);
            }
            // ?????????????????????set??????
            SysUser dbSysUser = userService.getSysUserByloginAccount(sysUser);
            if(dbSysUser == null) {
                return repEntity.layerMessage("no", "????????????????????????????????????????????????");
            }
            sysUser.setUserId(dbSysUser.getUserId());
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        }
        // ????????????????????????????????????
        else if (StrUtil.equals("21", sysUser.getLoginType())) {
            if (StrUtil.isEmpty(sysUser.getMobile())) {
                return repEntity.error("sys.user.mobile.error");
            }
            // ?????????????????????????????????
            if (StrUtil.isEmpty(sysUser.getUserPwd())) {
                return repEntity.error("sys.user.empty");
            }

            // ?????????????????????set??????
            SysUser sysUserSelect = new SysUser();
            sysUserSelect.setMobile(sysUser.getMobile());
            String userPwd = SecureUtil.md5(sysUser.getUserPwd() + apih5Properties.getMd5Salt());
            sysUserSelect.setUserPwd(userPwd);
            SysUser dbSysUser = userService.getSysUserByloginAccount(sysUserSelect);
            if (dbSysUser == null) {
                return repEntity.error("sys.user.pwd");
            }
            sysUser.setUserId(dbSysUser.getUserId());
//            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setLoginType("1");
        }
        // ???????????????
        else if (StrUtil.equals("3", sysUser.getLoginType()) || StrUtil.equals("5", sysUser.getLoginType())) {
            // ????????????accessToken
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(sysUser.getAccountId());
            String accessToken = accessTokenMap.get("accessToken");
            // 2?????????????????????code??????????????????
            String url = env.getProperty("get.get_user_info_auth").replace("ACCESS_TOKEN", accessToken).replace("CODE",
                    sysUser.getCode());
            // ????????????????????????
            String result = HttpUtil.sendGet(url);
            JSONObject jsonObject = new JSONObject(result);
            String userId = jsonObject.getStr("UserId");
            sysUser.setDeviceId(jsonObject.getStr("DeviceId"));
            sysUser.setUserId(userId);
//            sysUser.setUserPwd(apih5Properties.getDefaultPassword());
            LoggerUtils.printLogger(logger, "login qyh" + sysUser.getUserId());
            if (StrUtil.isEmpty(sysUser.getUserId())) {
                return repEntity.error("sys.user.wechat");
            }
            SysUser sysUserSelect = new SysUser();
            sysUserSelect.setUserId(userId);
            SysUser dbSysUser = userService.getSysUserByloginAccount(sysUserSelect);
            if (dbSysUser == null) {
                return repEntity.error("sys.user.pwd");
            }
            // ??????
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        }
        // ?????????????????????????????????=openid?????????
        else if (StrUtil.equals("31", sysUser.getLoginType())) {
            // ????????????accessToken
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(sysUser.getAccountId());
            String accessToken = accessTokenMap.get("accessToken");
            // 2?????????????????????code??????????????????
            String url = env.getProperty("get.get_user_info_auth").replace("ACCESS_TOKEN", accessToken).replace("CODE",
                    sysUser.getCode());
            // ????????????????????????
            String result = HttpUtil.sendGet(url);
            JSONObject jsonObject = new JSONObject(result);
            String userId = jsonObject.getStr("UserId");
            // openid??????????????????????????????web??????????????????
            if (StrUtil.isNotEmpty(jsonObject.getStr("OpenId"))) {
                String openId = jsonObject.getStr("OpenId");
                Map<String, String> getParamMap = Maps.newHashMap();
                getParamMap.put("access_token", accessToken);
                // post??????
                OpenIdInfoReq OpenInfoReq = new OpenIdInfoReq();
                OpenInfoReq.setOpenid(openId);
                JSONObject useridJson = wechatEnterpriseService.coreServiceByOauth(4, getParamMap, OpenInfoReq);
                // ?????????????????????????????????????????????
                if (StrUtil.isNotEmpty(useridJson.getStr("userid"))) {
                    userId = useridJson.getStr("userid");
                } else {
                    userId = openId;
                    sysUser.setUserId(userId);
                    sysUser.setOpenid(userId);
                    sysUser.setExt1("2");
                    sysUser.setAccountAppType("2");
                    SysUser dbSysUser = userService.getSysUserByUserId(sysUser.getUserId());
                    if (dbSysUser == null) {
                        userService.addUser(sysUser);
                        dbSysUser = userService.getSysUserByUserId(sysUser.getUserId());
                    }

                    Map<String, Object> infoMap = Maps.newHashMap();
                    // ???web?????????userInfo
                    SysUserWebEntity sysUserWebEntity = new SysUserWebEntity();
                    sysUserWebEntity.setUserKey(dbSysUser.getUserKey());
                    sysUserWebEntity.setUserId(dbSysUser.getUserId());
                    sysUserWebEntity.setRealName(dbSysUser.getRealName());
                    sysUserWebEntity.setMobile(dbSysUser.getMobile());
                    sysUserWebEntity.setImageUrl(dbSysUser.getImageUrl());
                    sysUserWebEntity.setGender(dbSysUser.getGender());
                    sysUserWebEntity.setExt1(dbSysUser.getExt1());

//                    // ??????key
//                    String ehCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
                    // ??????ip??????
                    String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
                    // ????????????token
                    String token = TokenUtils.createUserToken(sysUser.getAccountId(), dbSysUser.getUserKey(), 0,
                            apih5Properties.getUserTokenKey(), ip);

                    // ????????????
                    TokenEntity tokenEntity = new TokenEntity();
                    tokenEntity.setToken(token);
                    tokenEntity.setUserId(dbSysUser.getUserId());
                    tokenEntity.setOpenId(dbSysUser.getOpenid());
                    tokenEntity.setAccountId(sysUser.getAccountId());
                    tokenEntity.setRealName(dbSysUser.getRealName());
                    tokenEntity.setExt1(dbSysUser.getExt1());
                    tokenEntity.setExt2(dbSysUser.getExt2());
                    tokenEntity.setExt3(dbSysUser.getExt3());
                    tokenEntity.setExt4(dbSysUser.getExt4());
                    tokenEntity.setExt5(dbSysUser.getExt5());
                    tokenEntity.setExt6(dbSysUser.getExt6());
                    tokenEntity.setExt7(dbSysUser.getExt7());
                    tokenEntity.setExt8(dbSysUser.getExt8());
                    tokenEntity.setExt9(dbSysUser.getExt9());
                    tokenEntity.setExt10(dbSysUser.getExt10());// ????????????
                    tokenEntity.setMobile(dbSysUser.getMobile());
//                    tokenEntity.setWeChatUsereId(weChatUserId);        

                    // ??????web
                    infoMap.put("token", token);
                    infoMap.put("userInfo", sysUserWebEntity);

                    // ????????????????????????token,token???key???userToken_??????key
                    String userEhCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
//                    EhCacheCacheHandler.putUserTokenEhCache(userEhCacheKey, tokenEntity);
                    sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);

                    return repEntity.ok(infoMap);
                }
            }
//            else {
//                    sysUser.setUserId(userId);
//                    sysUser.setOpenid(userId);
//                    sysUser.setExt1("2");
//                    sysUser.setAccountAppType("2");
//                    SysUser dbSysUser = userService.getSysUserByUserId(sysUser.getUserId());
//                    if(dbSysUser == null) {
//                        userService.addUser(sysUser);
//                        dbSysUser = userService.getSysUserByUserId(sysUser.getUserId());
//                    }
//                    
//                    Map<String, Object> infoMap = Maps.newHashMap();
//                    // ???web?????????userInfo
//                    SysUserWebEntity sysUserWebEntity = new SysUserWebEntity();
//                    sysUserWebEntity.setUserKey(dbSysUser.getUserKey());
//                    sysUserWebEntity.setUserId(dbSysUser.getUserId());
//                    sysUserWebEntity.setRealName(dbSysUser.getRealName());
//                    sysUserWebEntity.setMobile(dbSysUser.getMobile());
//                    sysUserWebEntity.setImageUrl(dbSysUser.getImageUrl());
//                    sysUserWebEntity.setGender(dbSysUser.getGender());
//                    sysUserWebEntity.setExt1(dbSysUser.getExt1());
//                    
//                    // ??????key
//                    String ehCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
//                    // ??????ip??????
//                    String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
//                    // ????????????token
//                    String token = TokenUtils.createUserToken(sysUser.getAccountId(), dbSysUser.getUserKey(), 0,
//                            apih5Properties.getUserTokenKey(), ip);
//                    
//                    // ????????????
//                    TokenEntity tokenEntity = new TokenEntity();
//                    tokenEntity.setToken(token);
//                    tokenEntity.setUserId(dbSysUser.getUserId());
//                    tokenEntity.setOpenId(dbSysUser.getOpenid());
//                    tokenEntity.setAccountId(sysUser.getAccountId());
//                    tokenEntity.setRealName(dbSysUser.getRealName());
//                    tokenEntity.setExt1(dbSysUser.getExt1());
//                    tokenEntity.setExt2(dbSysUser.getExt2());
//                    tokenEntity.setExt3(dbSysUser.getExt3());
//                    tokenEntity.setExt4(dbSysUser.getExt4());
//                    tokenEntity.setExt5(dbSysUser.getExt5());
//                    tokenEntity.setExt6(dbSysUser.getExt6());
//                    tokenEntity.setExt7(dbSysUser.getExt7());
//                    tokenEntity.setExt8(dbSysUser.getExt8());
//                    tokenEntity.setExt9(dbSysUser.getExt9());
//                    tokenEntity.setExt10(dbSysUser.getExt10());//????????????
//                    tokenEntity.setMobile(dbSysUser.getMobile());
////                    tokenEntity.setWeChatUsereId(weChatUserId);        
//                    
//                    // ??????web
//                    infoMap.put("token", token);
//                    infoMap.put("userInfo", sysUserWebEntity);
//                    
//                    // ????????????????????????token,token???key???userToken_??????key
//                    String userEhCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
//                    EhCacheCacheHandler.putUserTokenEhCache(userEhCacheKey, tokenEntity);
//                    
//                    return repEntity.ok(infoMap);
//            }

            sysUser.setUserId(userId);
//            sysUser.setUserPwd(apih5Properties.getDefaultPassword());
            LoggerUtils.printLogger(logger, "login qyh" + sysUser.getUserId());
            if (StrUtil.isEmpty(sysUser.getUserId())) {
                return repEntity.error("sys.user.wechat");
            }
            SysUser sysUserSelect = new SysUser();
            sysUserSelect.setUserId(userId);
            SysUser dbSysUser = userService.getSysUserByloginAccount(sysUserSelect);
            if (dbSysUser == null) {
                return repEntity.error("sys.user.pwd");
            }
            // ??????
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        }
        // ???????????????
        else if (StrUtil.equals("32", sysUser.getLoginType())) {
            // ????????????accessToken
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(sysUser.getAccountId());
            String accessToken = accessTokenMap.get("accessToken");
            // 2?????????????????????code??????????????????
            String url = env.getProperty("get.get_user_info_auth").replace("ACCESS_TOKEN", accessToken).replace("CODE",
                    sysUser.getCode());
            // ????????????????????????
            String result = HttpUtil.sendGet(url);
            JSONObject jsonObject = new JSONObject(result);
            String userId = jsonObject.getStr("UserId");
//          Map<String, String> getParamMap = new HashMap<String, String>();
//          getParamMap.put("access_token", accessToken);
//          getParamMap.put("userid", userId);
//          // ??????????????????
//          JSONObject jsonObject10 = wechatEnterpriseService.coreServiceByResurceAddressbook(10, getParamMap, null);
//          String mobile = jsonObject10.getStr("mobile");
//          if(StrUtil.isNotEmpty(mobile)) {
//              SysUser sysUserSelect = new SysUser();
//              sysUserSelect.setMobile(mobile);
//              sysUserSelect.setAccountAppType("1");
//              List<SysUser> sysUserList = userService.listAll(sysUserSelect);
//              sysUserSelect = sysUserList.get(0);
//              userId = sysUserSelect.getUserId();
//          }
            // ??????4a??????userId
            JSONObject param = new JSONObject();
            param.set("empcode", userId);
            String result4A = HttpUtil.sendPostJson(Apih5Properties.getWebUrl() + "/getZjWoaQfFourAUserDetailsByUserId",
                    param.toString());
            JSONObject jsonObjectResult4A = new JSONObject(result4A);
            // ????????????????????????????????????????????????????????????
            if (jsonObjectResult4A.getBool("success")) {
                JSONObject jsonObject4A = jsonObjectResult4A.getJSONObject("data");
                if (jsonObject4A != null) {
                    String loginId = jsonObject4A.getStr("loginId");
                    userId = loginId;
                } else {
                    return repEntity.layerMessage("no", "????????????");
                }
            }
            sysUser.setDeviceId(jsonObject.getStr("DeviceId"));
            sysUser.setUserId(userId);
//          sysUser.setUserPwd(apih5Properties.getDefaultPassword());
            LoggerUtils.printLogger(logger, "login qyh" + sysUser.getUserId());
            if (StrUtil.isEmpty(sysUser.getUserId())) {
                return repEntity.error("sys.user.wechat");
            }
            SysUser sysUserSelect = new SysUser();
            sysUserSelect.setUserId(userId);
            SysUser dbSysUser = userService.getSysUserByloginAccount(sysUserSelect);
            if (dbSysUser == null) {
                return repEntity.error("sys.user.pwd");
            }
            // ??????
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        }
        // ???????????????
        else if (StrUtil.equals("4", sysUser.getLoginType())) {
            // ????????????accessToken
            Map<String, String> accessTokenMap = weChatDbService.getWeChatAccessToken(sysUser.getAccountId());
            // String accessToken = accessTokenMap.get("accessToken");
            String appId = accessTokenMap.get("appId");
            String appSecret = accessTokenMap.get("secret");
            // code????????????
            String url = ComConst.AUTHENTIZATION_WEB_ACCESSTOKEN_URL.replace("CODE", sysUser.getCode())
                    .replace("APPID", appId).replace("SECRET", appSecret);

            // ????????????????????????
            String result = HttpUtil.sendGet(url);
            JSONObject jsonObject = new JSONObject(result);
            String openid = jsonObject.getStr("openid");
            String accessToken = jsonObject.getStr("access_token");
            LoggerUtils.printLogger(logger, "login accessToken" + jsonObject.toString());

            // ?????????????????????
            Map<String, String> getParamMap = Maps.newHashMap();
            getParamMap.put("access_token", accessToken);
            getParamMap.put("openid", openid);
            getParamMap.put("lang", "zh_CN");
            JSONObject jsObj = (JSONObject) weChatService.coreServiceByUserManage(12, getParamMap, null);
            LoggerUtils.printDebugLogger(logger, accessToken + "    accessToken  " + jsObj.toString());
            // ??? JSONObject jsObj = (JSONObject)
            // weChatService.coreServiceByUserManage(9, getParamMap, null);

            UMUserInfoResp resp = (UMUserInfoResp) JsonUtil.fromJSON(jsObj.toString(), UMUserInfoResp.class);

            // ??????db??????
            sysUser.setAccountCorpId(accessTokenMap.get("accountCorpId"));
            sysUser.setAccountAppType(accessTokenMap.get("accountAppType"));
            // sysUser.setUserId(openid);
            // sysUser.setOpenid(openid);
            // sysUser.setRealName(resp.getNickname());
            // sysUser.setGender(String.valueOf(resp.getSex()));
            // sysUser.setImageUrl(resp.getHeadimgurl());
            // sysUser.setUserPwd(apih5Properties.getDefaultPassword());

//            sysUser.setUserId(openid);
            // sysUser.setAccountId(accountId);
//            sysUser.setExt(resp.getNickname());
            sysUser.setUserId(openid);
            // ????????????
            sysUser.setRealName(EmojiUtil.toHtml(resp.getNickname()));
            sysUser.setNickname(EmojiUtil.toHtml(resp.getNickname()));
            sysUser.setGender(String.valueOf(resp.getSex()));
            sysUser.setImageUrl(resp.getHeadimgurl());
            sysUser.setOpenid(openid);
            sysUser.setUserPwd(apih5Properties.getDefaultPassword());
            sysUser.setUserType("1");
            sysUser.setUserStatus("1");
            // ?????????
            sysUser.setExt3("0");
            // 0:?????? 1:?????????
            sysUser.setExt5("0");
            // ???????????????????????????
            SysUser userExists = userService.checkUserIdExists(sysUser);
            // ???????????????????????????
            if (userExists == null) {
                // sysUser.setUserStatus("1");
                userService.addUser(sysUser);
                sysUser.setDefaultUserPwdFlag("1");
            } else if (StrUtil.isEmpty(userExists.getImageUrl())
                    || (userExists.getImageUrl() != null && userExists.getImageUrl().indexOf("qlogo.cn") > 0)) {
                // sysUser.setUserKey(userExists.getUserKey());
//                userExists.setRealName(EmojiUtil.toHtml(resp.getNickname()));
                userExists.setNickname(EmojiUtil.toHtml(resp.getNickname()));
                userExists.setGender(String.valueOf(resp.getSex()));
                userExists.setImageUrl(resp.getHeadimgurl());
                userService.updateUserCommon(userExists);
            }
            
            // sysUser.setUserPwd(apih5Properties.getDefaultPassword());
            // ??????db???????????????????????????
            if (StrUtil.isEmpty(sysUser.getUserId())) {
                return repEntity.error("sys.user.wechat");
            }
        } else if (StrUtil.equals("6", sysUser.getLoginType())) {
            // APP?????????token??????
            // ?????????????????????
            Map<String, Object> infoMap = Maps.newHashMap();
            infoMap.put("token", sysUser.getCode());
            infoMap.put("userInfo", TokenUtils.getSysUserWebObject(sysUser.getCode()));
            return repEntity.ok(infoMap);
            
            // ??????
//            if (StrUtil.isEmpty(sysUser.getCode())) {
//                return this.repEntity.error("sys.error.token");
//            }
//
//            String token = sysUser.getCode();
//            String tokenDecrypt = "";
//            try {
//                tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
//            } catch (Exception e) {
//                return this.repEntity.error("sys.error.token");
//            }
//            JSONObject jsonObjectToken = new JSONObject(tokenDecrypt);
//            String accountId = jsonObjectToken.getStr("accountId");
//            String userKey = jsonObjectToken.getStr("userKey");
//
//            SysUser dbSysUser = this.userService.getSysUserByUserKey(userKey);
//            if (dbSysUser == null) {
//                return this.repEntity.layerMessage("no", "???????????????");
//            }
//
//            sysUser.setAccountId(accountId);
//            sysUser.setUserId(dbSysUser.getUserId());
//            sysUser.setUserPwd(dbSysUser.getUserPwd());
//            sysUser.setDefaultUserPwdFlag("1");
        } else if (StrUtil.equals("7", sysUser.getLoginType())) {
            // ?????????????????????????????????
            if (StrUtil.isEmpty(sysUser.getUserId())) {
                return repEntity.error("sys.user.wechat");
            }
            SysUser sysUserSelect = new SysUser();
            sysUserSelect.setUserId(sysUser.getUserId());
            SysUser dbSysUser = userService.getSysUserByloginAccount(sysUserSelect);
            if (dbSysUser == null) {
                return repEntity.layerMessage("no", "???????????????");
            }
            // ??????
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        } else if (StrUtil.equals("8", sysUser.getLoginType())) {
            // ????????????Redis????????????????????????????????????
            String token = sysUser.getCode();
            String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
            JSONObject jsonObjectToken = new JSONObject(tokenDecrypt);
            SysUser dbSysUser = userService.getSysUserByUserKey(jsonObjectToken.getStr("userKey"));

            // ????????????????????????????????????????????????
            String urlApi = config.getProperty("main.cache.url", "") + "user/login";
            JSONObject jsonObject = new JSONObject(sysUser);
            jsonObject.set("accountId", jsonObjectToken.getStr("accountId"));
            jsonObject.set("loginType", "1");
            jsonObject.set("userId", dbSysUser.getUserId());
            jsonObject.set("userPwd", apih5Properties.getDefaultPassword());
            String result = HttpUtil.sendPostJson(urlApi, jsonObject.toString());
            JSONObject jsonObjectResult = new JSONObject(result);
            // ????????????????????????????????????????????????????????????
            if (jsonObjectResult.getBool("success")) {
                JSONObject jsonObjectData = jsonObjectResult.getJSONObject("data");
                JSONObject jsObjectUserInfo = jsonObjectData.getJSONObject("userInfo");
                // ????????????
                Map<String, Object> infoMap = Maps.newHashMap();
//                infoMap.put("token", jsonObjectData.getStr("token"));
                infoMap.put("token", token);
                infoMap.put("userInfo", jsObjectUserInfo);

//                String userEhCacheKey = jsObjectUserInfo.getStr("userKey") + "-2" + sysUser.getAccountId();
//                TokenEntity tokenEntity = jsObjectUserInfo.toBean(TokenEntity.class);// jsonObjectResult.getBean("userInfo", TokenEntity.class);
//                sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
                // ??????put
                boolean isPutCacheOk = userService.copyCacheLogin(sysUser.getAccountId(),
                        jsObjectUserInfo.getStr("userKey"), jsonObjectData);
                if (isPutCacheOk) {
                    return repEntity.ok(infoMap);
                } else {
                    return repEntity.layerMessage("no", "?????????????????????");
                }
            } else {
                return repEntity.layerMessage("no", "????????????");
            }
        } else {
            return repEntity.layerMessage("no", "???????????????");
        }
        String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
        sysUser.setIp(ip);

//        // ???????????????session
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        // ??????session????????????sessionCreated??????
//        HttpSession session = request.getSession();
//        // session.setAttribute(sysUser.getUserKey(), sysUser.getRealName());
//        // ?????????????????????1??????(???????????????????????????????????????????????????)
//        // ??????????????????????????????????????????????????????????????????session????????????
//        session.setMaxInactiveInterval(60);

        // ???????????????
        return userService.canLogin(sysUser);
    }

    /**
     * ????????????
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @PostMapping(path = "/getCacheUserInfo/{userEhCacheKey}")
    @RequireToken
    public ResponseEntity getCacheUserInfo(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("userEhCacheKey") String userEhCacheKey) {
        return userService.getCacheUserInfo(userEhCacheKey);
    }

    /**
     * ????????????
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @PostMapping(path = "/logout")
    @RequireToken
    public ResponseEntity logout(HttpServletRequest request) {
        // String token = TokenUtils.getToken(request);
        String userEhCacheKey = TokenUtils.getUserEhCacheKey(request);
        String userkey = TokenUtils.getUserKey(request);
        // SysUser sysUserByUserId = userService.getSysUserByUserId(userkey);
        // // ?????????log????????????????????????
        SysLog sysLog = new SysLog();
        sysLog.setLoginAccount(userkey);
        sysLog.setTypeId(2);
        // // ??????ip??????
        String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
        sysLog.setIp(ip);
        logService.logAdd(sysLog);
        // // ?????????token?????????????????????
        // userTokenService.deleteByToken(token);
//        EhCacheCacheHandler.removeUserTokenEhCache(userEhCacheKey);
        sysRedisCacheService.removeUserTokenRedis(userEhCacheKey);

//        
//        // ????????????????????????session,false???????????????Session
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            // session.removeAttribute(userkey);
//            session.invalidate();
//        }

        return repEntity.ok("sys.exit", "");
    }

    /**
     * ????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @RequireToken
    @PostMapping(value = "/changeCompany")
    public ResponseEntity changeCompany(@RequestBody(required = false) SysUser sysUser) {
        return userService.changeCompany(sysUser);
    }

    /**
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @RequireToken
    @PostMapping(value = "/updateUserPwd")
    public ResponseEntity updateUserPassword(@RequestBody(required = false) SysUser sysUser) {
        return userService.updateUserPwd(sysUser);
    }

    /**
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @RequireToken
    @PostMapping(value = "/updateUserDefaultPwd")
    public ResponseEntity updateUserDefaultPwd(@RequestBody(required = false) SysUser sysUser) {
        return userService.updateUserDefaultPwd(sysUser);
    }

    /**
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @RequireToken
    @PostMapping(value = "/resetUserPwd")
    public ResponseEntity resetUserPwd(@RequestBody(required = false) List<SysUser> sysUserList) {
        return userService.resetUserPwd(sysUserList);
    }
    
    /**
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @RequireToken
    @PostMapping(value = "/unLock")
    public ResponseEntity unLock(@RequestBody(required = false) List<SysUser> sysUserList) {
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser : sysUserList) {
                SysUser dbSysUser = userService.getSysUserByUserKey(sysUser.getUserKey());
                sysRedisCacheService.removeLoginLockRedis(dbSysUser.getUserId());
            }
        }
        return repEntity.ok("??????????????????");
    }

    /**
     * ????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @PostMapping(value = "/refreshAccessToken")
    public ResponseEntity refreshAccessToken(@RequestBody(required = false) TokenEntity tokenEntity) {
        return userService.refreshAccessToken(tokenEntity);
    }

    /**
     * ??????????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @PostMapping(value = "/refreshTokenByQf")
    public ResponseEntity refreshTokenByQf(@RequestBody(required = false) TokenEntity tokenEntity) {
        return userService.refreshAccessToken(tokenEntity);
    }

    /**
     * ????????????????????????????????????????????????????????????
     * 
     * @return ????????????
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysRole", value = "??????entity", dataType = "SysRole")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/getSysUserListByBg")
    public ResponseEntity getSysUserListByBg(@RequestBody(required = false) SysUser sysUser) {
        return userService.getSysUserListByBg(sysUser);
    }
    
    /**
     * ????????????????????????????????????????????????????????????
     * 
     * @return ????????????
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysRole", value = "??????entity", dataType = "SysRole")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/getSysUserListByBgXMJX")
    public ResponseEntity getSysUserListByBgXMJX(@RequestBody(required = false) SysUser sysUser) {
        return userService.getSysUserListByBgXMJX(sysUser);
    }

    /**
     * ????????????-???????????????????????????????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "??????entity", dataType = "SysUser")
//    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/getSysUserDetails")
    public ResponseEntity getSysUserDetails(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.getSysUserDetails(sysUser);
    }

    /**
     * ????????????-???????????????????????????????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "??????entity", dataType = "SysUser")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/addSysUserInfoByBg")
    public ResponseEntity addSysUserInfoByBg(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.addSysUserInfoByBg(sysUser);
    }

    /**
     * ????????????-????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "??????entity", dataType = "SysUser")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/updateSysUserInfoByBg")
    public ResponseEntity updateSysUserInfoByBg(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.updateSysUserInfoByBg(sysUser);
    }

    /**
     * ????????????-???????????????????????????????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "??????entity", dataType = "SysUser")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/deleteSysUserInfoByBg")
    public ResponseEntity deleteSysUserInfoByBg(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.deleteSysUserInfoByBg(sysUser);
    }

    /**
     * ??????????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "??????entity", dataType = "SysUser")
    @RequireToken
    @PostMapping("/syncAddSysUser")
    public ResponseEntity syncAddSysUser(@RequestBody(required = false) SysUser sysUser) throws Exception {
        int flag = userService.addUserCommon(sysUser);
        return repEntity.ok(flag);
    }

    /**
     * ??????????????????
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysUser", value = "??????entity", dataType = "SysUser")
    @RequireToken
    @PostMapping("/syncUpdateUserCommon")
    public ResponseEntity syncUpdateUserCommon(@RequestBody(required = false) SysUser sysUser) throws Exception {
        int flag = userService.updateUserCommon(sysUser);
        return repEntity.ok(flag);
    }

    /**
     * ????????????????????????????????????????????????????????????
     * 
     * @return ????????????
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysRole", value = "??????entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/getSysUserList")
    public ResponseEntity getSysUserList(@RequestBody(required = false) OADepartment oaDepartment) {
        return userService.getSysUserList(oaDepartment);
    }

    /**
     * ????????????????????????????????????????????????????????????
     * 
     * @return ????????????
     */
    @ApiOperation(value = "????????????", notes = "????????????")
    @ApiImplicitParam(name = "sysRole", value = "??????entity", dataType = "SysRole")
    @GetMapping("/apiSysUserList/{orgId}")
    public ResponseEntity apiSysUserList(@RequestBody(required = false) @PathVariable("orgId") String orgId) {
        OADepartment oaDepartment = new OADepartment();
        oaDepartment.setOrgId(orgId);
        return userService.apiSysUserList(oaDepartment);
    }

    /**
     * ????????????
     */
    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    @PostMapping("/queryAll")
    // @RequirePermissions(value = USER_MGMT)
    @RequireToken
    public ResponseEntity queryAll(SysUser sysUser, HttpServletRequest request) {
        // HttpServletRequest request =
        // requestHolderConfiguration.getHttpServletRequest();
//        String ss = TokenUtils.getUserId(request);
//        JSONObject js = wechatEnterpriseService.sendMsgText(request, "wangya", "t");
//        String s = TokenUtils.getAccountId(request);
//        // ????????????
//        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        // List<SysUser> sysUsers = userService.listAll(sysUser);
        // sysUsers.size();
        // // ?????????????????????
        // PageInfo<SysUser> p = new PageInfo<>(sysUsers);

        // return repEntity.okList(sysUsers, p.getTotal());
        return repEntity.ok("");
    }

    /**
     * ??????/??????/?????? ???????????????????????????
     * 
     * @param smsEntity
     * @return
     */
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    @ApiImplicitParam(name = "smsEntity", value = "??????entity", dataType = "SmsEntity")
    // @RequireToken
    @PostMapping("/getMobileSmsCaptcha")
    public ResponseEntity getMobileSmsCaptcha(@RequestBody(required = false) SmsEntity smsEntity) {
        return userService.getMobileSmsCaptcha(smsEntity);
    }

    /**
     * ???????????????????????????????????????????????????????????????????????????
     * 
     * @param sysUser
     * @return sysUserList
     */
    @ApiOperation(value = "?????????????????????", notes = "?????????????????????")
    @ApiImplicitParam(name = "smsEntity", value = "??????entity", dataType = "SmsEntity")
    @RequireToken
    @PostMapping("/getSysUserListByUser")
    public ResponseEntity getUserListByUser(@RequestBody(required = false) SysUser sysUser) {
        return userService.getSysUserListByUser(sysUser);
    }

    @ApiOperation(value = "??????????????????", notes = "??????????????????")
    @ApiImplicitParam(name = "sysUser", value = "????????????entity", dataType = "SysUser")
    // @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/moveUpdateSysUser")
    public ResponseEntity moveUpdateSysUser(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.moveUpdateSysUser(sysUser);
    }

    @ApiOperation(value = "???????????????????????????", notes = "???????????????????????????")
    @ApiImplicitParam(name = "sysUser", value = "??????entity", dataType = "SysUser")
    // @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/updateSysUserPhoneNumber")
    public ResponseEntity updateSysUserPhoneNumber(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.updateSysUserPhoneNumber(sysUser);
    }
    
    @ApiOperation(value = "??????????????????????????????ext3", notes = "??????????????????????????????ext3")
    @ApiImplicitParam(name = "sysUser", value = "??????entity", dataType = "SysUser")
    // @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/updateSysUserScoringLeader")
    public ResponseEntity updateSysUserScoringLeader(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.updateSysUserScoringLeader(sysUser);
    }

//    /**
//     * ??????????????????
//     */
//    @RequestMapping("/online")
//    public Object online() {
//        return "?????????????????????" + MySessionListener.online + "???";
//    }
}

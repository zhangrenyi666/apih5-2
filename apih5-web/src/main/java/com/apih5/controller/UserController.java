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
 * 用户Controller
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
//     * 用户注册
//     */
//    @ApiOperation(value = "用户注册", notes = "用户注册")
//    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
//    @PostMapping("/register")
//    public ResponseEntity register(@RequestBody @Validated({ SysUser.Second.class }) SysUser sysUser) {
//        // 企业ID或应用ID没有返回系统异常
//        // if(StrUtil.isEmpty(sysUser.getCorpId())
//        // || StrUtil.isEmpty(sysUser.getAppId())){
//        // return repEntity.error("sys.exception");
//        // }
//        if (StrUtil.isEmpty(sysUser.getAccountId())) {
//            return repEntity.error("sys.exception");
//        }
//        // 普通登录模式（用户ID、密码验证）
//        if (StrUtil.equals("1", sysUser.getLoginType())) {
//            // 用户ID、密码为空时，返回错误提示
//            if (StrUtil.isEmpty(sysUser.getUserId()) || StrUtil.isEmpty(sysUser.getUserPwd())) {
//                return repEntity.error("sys.user.empty");
//            }
//
//            // 注册是否使用图片验证码
//            boolean useCaptcha = apih5Properties.isRegisterUseCaptcha();
//            // 图片验证码是否通过
//            // boolean captchaAccess = false;
//            // 短信验证码是否通过
//            // boolean smsCaptchaAccess = false;
//            // 如果配置了需要图片验证码注册
//            if (useCaptcha) {
//                // String captcha = sysUser.getCaptcha();
//                // // 处理验证码前台传过来是空的情况
//                // if (StringUtils.isEmpty(captcha)) {
//                // captcha = "";
//                // }
//                // // 如果验证码正确
//                // captchaAccess =
//                // StringUtils.isEmpty(rpcApi.checkCaptchaExists(captcha)) ?
//                // false :
//                // true;
//                // 如果验证码没有通过
//                // if (!captchaAccess) {
//                // return repEntity.error("sys.user.captcha");
//                // }
//            }
//        }
//        // 手机验证码快捷登录时
//        else if (StrUtil.equals("2", sysUser.getLoginType())) {
//            if (StrUtil.isEmpty(sysUser.getMobile())) {
//                return repEntity.error("sys.user.mobile.error");
//            }
//            // 是否使用短信验证码
//            boolean useSmsCaptcha = apih5Properties.isUseSmsCaptcha();
//            // 配置了需要短信验证码注册
//            if (useSmsCaptcha) {
//                // userId为手机号码
//                String code = SmsUtils.checkCaptchaEhCache(sysUser.getUserId(), sysUser.getCaptcha());
//                if (!StrUtil.equals("ok", code)) {
//                    return repEntity.error(code);
//                }
//            }
//            // 当注册没有用户名时，将手机号默认为用户名
//            if (StrUtil.isEmpty(sysUser.getUserId())) {
//                sysUser.setUserId(sysUser.getMobile());
//            }
//            // 当注册没有密码时,将机短信验证码为密码
//            if (StrUtil.isEmpty(sysUser.getUserPwd())) {
//                sysUser.setUserPwd(sysUser.getCaptcha());
//                sysUser.setExt1(sysUser.getCaptcha());
//            }
//        }
//        return userService.addUser(sysUser);
//    }

    /**
     * 用户登录
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
    @PostMapping("/login")
    public ResponseEntity login(HttpServletRequest request,
            @RequestBody @Validated({ SysUser.Second.class }) SysUser sysUser) {
        // 前端解密
        String webAesFlag = request.getParameter("WEBFlag");
        if (StrUtil.equals("Ab25DB", webAesFlag)) {
            try {
                String userId = AESUtil.desEncrypt(sysUser.getUserId());
                String userPwd = AESUtil.desEncrypt(sysUser.getUserPwd());
                sysUser.setUserId(userId);
                sysUser.setUserPwd(userPwd);
            } catch (Exception e) {
                LoggerUtils.printDebugLogger(logger, "解密失败：" + e.getMessage());
                return repEntity.error("sys.user.pwd");
            }
        }

        // 企业账号ID
        if (StrUtil.isEmpty(sysUser.getAccountId()) && !StrUtil.equals("6", sysUser.getLoginType())) {// 6单点登录模式
            return repEntity.error("sys.exception");
        }
        // 普通登录模式（用户ID、密码验证）
        if (StrUtil.equals("1", sysUser.getLoginType())) {
//            int loginErrorCount = sysRedisCacheService.getLoginLockRedis(sysUser.getUserId());
//            if(loginErrorCount>5) {
//                return repEntity.layerMessage("no", "账号被锁定，请联系管理员！");
//            }
            // 是否有图形验证方式
            boolean useCaptcha = apih5Properties.isLoginUseCaptcha();
            // 如果配置了需要验证码登录
            if (useCaptcha) {
                ResponseEntity check = sysCaptchaService.checkSysCaptchaCode(sysUser.getCaptchaId(), sysUser.getCaptchaCode());
                if (!check.isSuccess()) {
                    return repEntity.layerMessage("no", check.getMessage());
                }
            }
            
            // 用户ID、密码为空时，返回错误提示
            if (StrUtil.isEmpty(sysUser.getUserId()) || StrUtil.isEmpty(sysUser.getUserPwd())) {
                return repEntity.error("sys.user.pwd");
            }
            LoggerUtils.printDebugLogger(logger, "login fwh=" + sysUser.getUserId());
        }
        // 手机验证码快捷登录时
        else if (StrUtil.equals("2", sysUser.getLoginType())) {
            if (StrUtil.isEmpty(sysUser.getMobile())) {
                return repEntity.error("sys.user.mobile.error");
            }
            String code = SmsUtils.checkCaptchaEhCache(sysUser.getMobile(), sysUser.getCaptcha());
            if (!StrUtil.equals("ok", code)) {
//                return repEntity.error(code);
            }
            // 短信验证时默认set密码
            SysUser dbSysUser = userService.getSysUserByloginAccount(sysUser);
            if(dbSysUser == null) {
                return repEntity.layerMessage("no", "登陆错误，请检查手机号或验证码！");
            }
            sysUser.setUserId(dbSysUser.getUserId());
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        }
        // 手机验证（用户名、密码）
        else if (StrUtil.equals("21", sysUser.getLoginType())) {
            if (StrUtil.isEmpty(sysUser.getMobile())) {
                return repEntity.error("sys.user.mobile.error");
            }
            // 码为空时，返回错误提示
            if (StrUtil.isEmpty(sysUser.getUserPwd())) {
                return repEntity.error("sys.user.empty");
            }

            // 短信验证时默认set密码
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
        // 企业号类型
        else if (StrUtil.equals("3", sysUser.getLoginType()) || StrUtil.equals("5", sysUser.getLoginType())) {
            // 获取微信accessToken
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(sysUser.getAccountId());
            String accessToken = accessTokenMap.get("accessToken");
            // 2、通过网页授权code获取用户信息
            String url = env.getProperty("get.get_user_info_auth").replace("ACCESS_TOKEN", accessToken).replace("CODE",
                    sysUser.getCode());
            // 请求接口后，结果
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
            // 密码
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        }
        // 企业号类型（非静默授权=openid方式）
        else if (StrUtil.equals("31", sysUser.getLoginType())) {
            // 获取微信accessToken
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(sysUser.getAccountId());
            String accessToken = accessTokenMap.get("accessToken");
            // 2、通过网页授权code获取用户信息
            String url = env.getProperty("get.get_user_info_auth").replace("ACCESS_TOKEN", accessToken).replace("CODE",
                    sysUser.getCode());
            // 请求接口后，结果
            String result = HttpUtil.sendGet(url);
            JSONObject jsonObject = new JSONObject(result);
            String userId = jsonObject.getStr("UserId");
            // openid存在时，是临时用户，web端做特殊处理
            if (StrUtil.isNotEmpty(jsonObject.getStr("OpenId"))) {
                String openId = jsonObject.getStr("OpenId");
                Map<String, String> getParamMap = Maps.newHashMap();
                getParamMap.put("access_token", accessToken);
                // post参数
                OpenIdInfoReq OpenInfoReq = new OpenIdInfoReq();
                OpenInfoReq.setOpenid(openId);
                JSONObject useridJson = wechatEnterpriseService.coreServiceByOauth(4, getParamMap, OpenInfoReq);
                // 转换成功，说明已经是企业号人员
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
                    // 为web提供的userInfo
                    SysUserWebEntity sysUserWebEntity = new SysUserWebEntity();
                    sysUserWebEntity.setUserKey(dbSysUser.getUserKey());
                    sysUserWebEntity.setUserId(dbSysUser.getUserId());
                    sysUserWebEntity.setRealName(dbSysUser.getRealName());
                    sysUserWebEntity.setMobile(dbSysUser.getMobile());
                    sysUserWebEntity.setImageUrl(dbSysUser.getImageUrl());
                    sysUserWebEntity.setGender(dbSysUser.getGender());
                    sysUserWebEntity.setExt1(dbSysUser.getExt1());

//                    // 缓存key
//                    String ehCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
                    // 获取ip地址
                    String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
                    // 生成用户token
                    String token = TokenUtils.createUserToken(sysUser.getAccountId(), dbSysUser.getUserKey(), 0,
                            apih5Properties.getUserTokenKey(), ip);

                    // 缓存设置
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
                    tokenEntity.setExt10(dbSysUser.getExt10());// 权限放置
                    tokenEntity.setMobile(dbSysUser.getMobile());
//                    tokenEntity.setWeChatUsereId(weChatUserId);        

                    // 传给web
                    infoMap.put("token", token);
                    infoMap.put("userInfo", sysUserWebEntity);

                    // 向缓存中插入用户token,token的key为userToken_用户key
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
//                    // 为web提供的userInfo
//                    SysUserWebEntity sysUserWebEntity = new SysUserWebEntity();
//                    sysUserWebEntity.setUserKey(dbSysUser.getUserKey());
//                    sysUserWebEntity.setUserId(dbSysUser.getUserId());
//                    sysUserWebEntity.setRealName(dbSysUser.getRealName());
//                    sysUserWebEntity.setMobile(dbSysUser.getMobile());
//                    sysUserWebEntity.setImageUrl(dbSysUser.getImageUrl());
//                    sysUserWebEntity.setGender(dbSysUser.getGender());
//                    sysUserWebEntity.setExt1(dbSysUser.getExt1());
//                    
//                    // 缓存key
//                    String ehCacheKey = dbSysUser.getUserKey() + "-" + sysUser.getAccountId();
//                    // 获取ip地址
//                    String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
//                    // 生成用户token
//                    String token = TokenUtils.createUserToken(sysUser.getAccountId(), dbSysUser.getUserKey(), 0,
//                            apih5Properties.getUserTokenKey(), ip);
//                    
//                    // 缓存设置
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
//                    tokenEntity.setExt10(dbSysUser.getExt10());//权限放置
//                    tokenEntity.setMobile(dbSysUser.getMobile());
////                    tokenEntity.setWeChatUsereId(weChatUserId);        
//                    
//                    // 传给web
//                    infoMap.put("token", token);
//                    infoMap.put("userInfo", sysUserWebEntity);
//                    
//                    // 向缓存中插入用户token,token的key为userToken_用户key
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
            // 密码
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        }
        // 交建通类型
        else if (StrUtil.equals("32", sysUser.getLoginType())) {
            // 获取微信accessToken
            Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(sysUser.getAccountId());
            String accessToken = accessTokenMap.get("accessToken");
            // 2、通过网页授权code获取用户信息
            String url = env.getProperty("get.get_user_info_auth").replace("ACCESS_TOKEN", accessToken).replace("CODE",
                    sysUser.getCode());
            // 请求接口后，结果
            String result = HttpUtil.sendGet(url);
            JSONObject jsonObject = new JSONObject(result);
            String userId = jsonObject.getStr("UserId");
//          Map<String, String> getParamMap = new HashMap<String, String>();
//          getParamMap.put("access_token", accessToken);
//          getParamMap.put("userid", userId);
//          // 读取某个成员
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
            // 通过4a获取userId
            JSONObject param = new JSONObject();
            param.set("empcode", userId);
            String result4A = HttpUtil.sendPostJson(Apih5Properties.getWebUrl() + "/getZjWoaQfFourAUserDetailsByUserId",
                    param.toString());
            JSONObject jsonObjectResult4A = new JSONObject(result4A);
            // 如果成功，则将主服务器缓存同步给当前缓存
            if (jsonObjectResult4A.getBool("success")) {
                JSONObject jsonObject4A = jsonObjectResult4A.getJSONObject("data");
                if (jsonObject4A != null) {
                    String loginId = jsonObject4A.getStr("loginId");
                    userId = loginId;
                } else {
                    return repEntity.layerMessage("no", "登录失败");
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
            // 密码
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        }
        // 服务号类型
        else if (StrUtil.equals("4", sysUser.getLoginType())) {
            // 获取微信accessToken
            Map<String, String> accessTokenMap = weChatDbService.getWeChatAccessToken(sysUser.getAccountId());
            // String accessToken = accessTokenMap.get("accessToken");
            String appId = accessTokenMap.get("appId");
            String appSecret = accessTokenMap.get("secret");
            // code换区地址
            String url = ComConst.AUTHENTIZATION_WEB_ACCESSTOKEN_URL.replace("CODE", sysUser.getCode())
                    .replace("APPID", appId).replace("SECRET", appSecret);

            // 请求接口后，结果
            String result = HttpUtil.sendGet(url);
            JSONObject jsonObject = new JSONObject(result);
            String openid = jsonObject.getStr("openid");
            String accessToken = jsonObject.getStr("access_token");
            LoggerUtils.printLogger(logger, "login accessToken" + jsonObject.toString());

            // 从腾讯获取数据
            Map<String, String> getParamMap = Maps.newHashMap();
            getParamMap.put("access_token", accessToken);
            getParamMap.put("openid", openid);
            getParamMap.put("lang", "zh_CN");
            JSONObject jsObj = (JSONObject) weChatService.coreServiceByUserManage(12, getParamMap, null);
            LoggerUtils.printDebugLogger(logger, accessToken + "    accessToken  " + jsObj.toString());
            // 、 JSONObject jsObj = (JSONObject)
            // weChatService.coreServiceByUserManage(9, getParamMap, null);

            UMUserInfoResp resp = (UMUserInfoResp) JsonUtil.fromJSON(jsObj.toString(), UMUserInfoResp.class);

            // 修改db数据
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
            // 微信昵称
            sysUser.setRealName(EmojiUtil.toHtml(resp.getNickname()));
            sysUser.setNickname(EmojiUtil.toHtml(resp.getNickname()));
            sysUser.setGender(String.valueOf(resp.getSex()));
            sysUser.setImageUrl(resp.getHeadimgurl());
            sysUser.setOpenid(openid);
            sysUser.setUserPwd(apih5Properties.getDefaultPassword());
            sysUser.setUserType("1");
            sysUser.setUserStatus("1");
            // 已关注
            sysUser.setExt3("0");
            // 0:用户 1:管理员
            sysUser.setExt5("0");
            // 如果验证码都已通过
            SysUser userExists = userService.checkUserIdExists(sysUser);
            // 如果注册账号不存在
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
            // 考虑db中没有时的关注情况
            if (StrUtil.isEmpty(sysUser.getUserId())) {
                return repEntity.error("sys.user.wechat");
            }
        } else if (StrUtil.equals("6", sysUser.getLoginType())) {
            // APP登录，token模式
            // 最终返回的数据
            Map<String, Object> infoMap = Maps.newHashMap();
            infoMap.put("token", sysUser.getCode());
            infoMap.put("userInfo", TokenUtils.getSysUserWebObject(sysUser.getCode()));
            return repEntity.ok(infoMap);
            
            // 四局
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
//                return this.repEntity.layerMessage("no", "用户不存在");
//            }
//
//            sysUser.setAccountId(accountId);
//            sysUser.setUserId(dbSysUser.getUserId());
//            sysUser.setUserPwd(dbSysUser.getUserPwd());
//            sysUser.setDefaultUserPwdFlag("1");
        } else if (StrUtil.equals("7", sysUser.getLoginType())) {
            // 第三方登陆，无密码形式
            if (StrUtil.isEmpty(sysUser.getUserId())) {
                return repEntity.error("sys.user.wechat");
            }
            SysUser sysUserSelect = new SysUser();
            sysUserSelect.setUserId(sysUser.getUserId());
            SysUser dbSysUser = userService.getSysUserByloginAccount(sysUserSelect);
            if (dbSysUser == null) {
                return repEntity.layerMessage("no", "用户不存在");
            }
            // 密码
            sysUser.setUserPwd(dbSysUser.getUserPwd());
            sysUser.setDefaultUserPwdFlag("1");
        } else if (StrUtil.equals("8", sysUser.getLoginType())) {
            // 因为使用Redis和微服务则不需要缓存获取
            String token = sysUser.getCode();
            String tokenDecrypt = SecureUtil.des("apih5_key".getBytes()).decryptStr(token);
            JSONObject jsonObjectToken = new JSONObject(tokenDecrypt);
            SysUser dbSysUser = userService.getSysUserByUserKey(jsonObjectToken.getStr("userKey"));

            // 缓存登录模式，从主服务器拿回缓存
            String urlApi = config.getProperty("main.cache.url", "") + "user/login";
            JSONObject jsonObject = new JSONObject(sysUser);
            jsonObject.set("accountId", jsonObjectToken.getStr("accountId"));
            jsonObject.set("loginType", "1");
            jsonObject.set("userId", dbSysUser.getUserId());
            jsonObject.set("userPwd", apih5Properties.getDefaultPassword());
            String result = HttpUtil.sendPostJson(urlApi, jsonObject.toString());
            JSONObject jsonObjectResult = new JSONObject(result);
            // 如果成功，则将主服务器缓存同步给当前缓存
            if (jsonObjectResult.getBool("success")) {
                JSONObject jsonObjectData = jsonObjectResult.getJSONObject("data");
                JSONObject jsObjectUserInfo = jsonObjectData.getJSONObject("userInfo");
                // 返回参数
                Map<String, Object> infoMap = Maps.newHashMap();
//                infoMap.put("token", jsonObjectData.getStr("token"));
                infoMap.put("token", token);
                infoMap.put("userInfo", jsObjectUserInfo);

//                String userEhCacheKey = jsObjectUserInfo.getStr("userKey") + "-2" + sysUser.getAccountId();
//                TokenEntity tokenEntity = jsObjectUserInfo.toBean(TokenEntity.class);// jsonObjectResult.getBean("userInfo", TokenEntity.class);
//                sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
                // 缓存put
                boolean isPutCacheOk = userService.copyCacheLogin(sysUser.getAccountId(),
                        jsObjectUserInfo.getStr("userKey"), jsonObjectData);
                if (isPutCacheOk) {
                    return repEntity.ok(infoMap);
                } else {
                    return repEntity.layerMessage("no", "服务器缓存异常");
                }
            } else {
                return repEntity.layerMessage("no", "登录失败");
            }
        } else {
            return repEntity.layerMessage("no", "无权限登陆");
        }
        String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
        sysUser.setIp(ip);

//        // 登录时创建session
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        // 没有session自动调用sessionCreated方法
//        HttpSession session = request.getSession();
//        // session.setAttribute(sysUser.getUserKey(), sysUser.getRealName());
//        // 设置过期时间为1分钟(只要客户浏览器一直请求该时间会重置)
//        // 关闭浏览器后超过设置时间或超过设置时间未请求session自动销毁
//        session.setMaxInactiveInterval(60);

        // 用户名登录
        return userService.canLogin(sysUser);
    }

    /**
     * 用户退出
     */
    @ApiOperation(value = "用户缓存获取", notes = "用户缓存获取")
    @PostMapping(path = "/getCacheUserInfo/{userEhCacheKey}")
    @RequireToken
    public ResponseEntity getCacheUserInfo(HttpServletRequest request, HttpServletResponse response,
            @PathVariable("userEhCacheKey") String userEhCacheKey) {
        return userService.getCacheUserInfo(userEhCacheKey);
    }

    /**
     * 用户退出
     */
    @ApiOperation(value = "用户退出", notes = "用户退出")
    @PostMapping(path = "/logout")
    @RequireToken
    public ResponseEntity logout(HttpServletRequest request) {
        // String token = TokenUtils.getToken(request);
        String userEhCacheKey = TokenUtils.getUserEhCacheKey(request);
        String userkey = TokenUtils.getUserKey(request);
        // SysUser sysUserByUserId = userService.getSysUserByUserId(userkey);
        // // 往系统log表中添加一条记录
        SysLog sysLog = new SysLog();
        sysLog.setLoginAccount(userkey);
        sysLog.setTypeId(2);
        // // 获取ip地址
        String ip = requestHolderConfiguration.getHttpServletRequest().getRemoteAddr();
        sysLog.setIp(ip);
        logService.logAdd(sysLog);
        // // 将用户token表中的数据删除
        // userTokenService.deleteByToken(token);
//        EhCacheCacheHandler.removeUserTokenEhCache(userEhCacheKey);
        sysRedisCacheService.removeUserTokenRedis(userEhCacheKey);

//        
//        // 用户退出登录销毁session,false是防止创建Session
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            // session.removeAttribute(userkey);
//            session.invalidate();
//        }

        return repEntity.ok("sys.exit", "");
    }

    /**
     * 切换公司
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "切换公司", notes = "切换公司")
    @ApiImplicitParam(name = "sysUser", value = "系统人员entity", dataType = "SysUser")
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
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParam(name = "sysUser", value = "系统人员entity", dataType = "SysUser")
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
    @ApiOperation(value = "修改默认密码", notes = "修改默认密码")
    @ApiImplicitParam(name = "sysUser", value = "系统人员entity", dataType = "SysUser")
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
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParam(name = "sysUser", value = "系统人员entity", dataType = "SysUser")
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
    @ApiOperation(value = "修改密码", notes = "修改密码")
    @ApiImplicitParam(name = "sysUser", value = "系统人员entity", dataType = "SysUser")
    @RequireToken
    @PostMapping(value = "/unLock")
    public ResponseEntity unLock(@RequestBody(required = false) List<SysUser> sysUserList) {
        if (sysUserList != null && sysUserList.size() > 0) {
            for (SysUser sysUser : sysUserList) {
                SysUser dbSysUser = userService.getSysUserByUserKey(sysUser.getUserKey());
                sysRedisCacheService.removeLoginLockRedis(dbSysUser.getUserId());
            }
        }
        return repEntity.ok("已解除锁定！");
    }

    /**
     * 缓存更新
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "缓存更新", notes = "缓存更新")
    @ApiImplicitParam(name = "sysUser", value = "系统人员entity", dataType = "SysUser")
    @PostMapping(value = "/refreshAccessToken")
    public ResponseEntity refreshAccessToken(@RequestBody(required = false) TokenEntity tokenEntity) {
        return userService.refreshAccessToken(tokenEntity);
    }

    /**
     * 千方缓存更新
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "缓存更新", notes = "缓存更新")
    @ApiImplicitParam(name = "sysUser", value = "系统人员entity", dataType = "SysUser")
    @PostMapping(value = "/refreshTokenByQf")
    public ResponseEntity refreshTokenByQf(@RequestBody(required = false) TokenEntity tokenEntity) {
        return userService.refreshAccessToken(tokenEntity);
    }

    /**
     * 根据部门关系表获取用户列表（旧框架使用）
     * 
     * @return 用户列表
     */
    @ApiOperation(value = "查询角色", notes = "查询角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/getSysUserListByBg")
    public ResponseEntity getSysUserListByBg(@RequestBody(required = false) SysUser sysUser) {
        return userService.getSysUserListByBg(sysUser);
    }
    
    /**
     * 根据部门关系表获取用户列表（旧框架使用）
     * 
     * @return 用户列表
     */
    @ApiOperation(value = "查询角色", notes = "查询角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/getSysUserListByBgXMJX")
    public ResponseEntity getSysUserListByBgXMJX(@RequestBody(required = false) SysUser sysUser) {
        return userService.getSysUserListByBgXMJX(sysUser);
    }

    /**
     * 后台管理-新增用户、用户部门关系数据
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParam(name = "sysUser", value = "用户entity", dataType = "SysUser")
//    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/getSysUserDetails")
    public ResponseEntity getSysUserDetails(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.getSysUserDetails(sysUser);
    }

    /**
     * 后台管理-新增用户、用户部门关系数据
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParam(name = "sysUser", value = "用户entity", dataType = "SysUser")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/addSysUserInfoByBg")
    public ResponseEntity addSysUserInfoByBg(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.addSysUserInfoByBg(sysUser);
    }

    /**
     * 后台管理-修改用户
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "修改用户", notes = "修改用户")
    @ApiImplicitParam(name = "sysUser", value = "用户entity", dataType = "SysUser")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/updateSysUserInfoByBg")
    public ResponseEntity updateSysUserInfoByBg(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.updateSysUserInfoByBg(sysUser);
    }

    /**
     * 后台管理-删除用户、用户部门关系数据
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "删除用户", notes = "删除用户")
    @ApiImplicitParam(name = "sysUser", value = "用户entity", dataType = "SysUser")
    @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/deleteSysUserInfoByBg")
    public ResponseEntity deleteSysUserInfoByBg(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.deleteSysUserInfoByBg(sysUser);
    }

    /**
     * 同步新增用户
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParam(name = "sysUser", value = "用户entity", dataType = "SysUser")
    @RequireToken
    @PostMapping("/syncAddSysUser")
    public ResponseEntity syncAddSysUser(@RequestBody(required = false) SysUser sysUser) throws Exception {
        int flag = userService.addUserCommon(sysUser);
        return repEntity.ok(flag);
    }

    /**
     * 同步修改用户
     * 
     * @param sysUser
     * @return
     */
    @ApiOperation(value = "新增用户", notes = "新增用户")
    @ApiImplicitParam(name = "sysUser", value = "用户entity", dataType = "SysUser")
    @RequireToken
    @PostMapping("/syncUpdateUserCommon")
    public ResponseEntity syncUpdateUserCommon(@RequestBody(required = false) SysUser sysUser) throws Exception {
        int flag = userService.updateUserCommon(sysUser);
        return repEntity.ok(flag);
    }

    /**
     * 根据部门关系表获取用户列表（旧框架使用）
     * 
     * @return 用户列表
     */
    @ApiOperation(value = "查询角色", notes = "查询角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/getSysUserList")
    public ResponseEntity getSysUserList(@RequestBody(required = false) OADepartment oaDepartment) {
        return userService.getSysUserList(oaDepartment);
    }

    /**
     * 根据部门关系表获取用户列表（旧框架使用）
     * 
     * @return 用户列表
     */
    @ApiOperation(value = "查询角色", notes = "查询角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @GetMapping("/apiSysUserList/{orgId}")
    public ResponseEntity apiSysUserList(@RequestBody(required = false) @PathVariable("orgId") String orgId) {
        OADepartment oaDepartment = new OADepartment();
        oaDepartment.setOrgId(orgId);
        return userService.apiSysUserList(oaDepartment);
    }

    /**
     * 用户查询
     */
    @ApiOperation(value = "查询所有用户", notes = "查询所有用户")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
    @PostMapping("/queryAll")
    // @RequirePermissions(value = USER_MGMT)
    @RequireToken
    public ResponseEntity queryAll(SysUser sysUser, HttpServletRequest request) {
        // HttpServletRequest request =
        // requestHolderConfiguration.getHttpServletRequest();
//        String ss = TokenUtils.getUserId(request);
//        JSONObject js = wechatEnterpriseService.sendMsgText(request, "wangya", "t");
//        String s = TokenUtils.getAccountId(request);
//        // 分页查询
//        PageHelper.startPage(sysUser.getPage(), sysUser.getLimit());
        // List<SysUser> sysUsers = userService.listAll(sysUser);
        // sysUsers.size();
        // // 得到分页后信息
        // PageInfo<SysUser> p = new PageInfo<>(sysUsers);

        // return repEntity.okList(sysUsers, p.getTotal());
        return repEntity.ok("");
    }

    /**
     * 注册/登录/改密 获取手机短信验证码
     * 
     * @param smsEntity
     * @return
     */
    @ApiOperation(value = "获取手机验证码", notes = "获取手机验证码")
    @ApiImplicitParam(name = "smsEntity", value = "短信entity", dataType = "SmsEntity")
    // @RequireToken
    @PostMapping("/getMobileSmsCaptcha")
    public ResponseEntity getMobileSmsCaptcha(@RequestBody(required = false) SmsEntity smsEntity) {
        return userService.getMobileSmsCaptcha(smsEntity);
    }

    /**
     * 获取所有用户信息（只是自己的数据，不关联部门信息）
     * 
     * @param sysUser
     * @return sysUserList
     */
    @ApiOperation(value = "获取手机验证码", notes = "获取手机验证码")
    @ApiImplicitParam(name = "smsEntity", value = "短信entity", dataType = "SmsEntity")
    @RequireToken
    @PostMapping("/getSysUserListByUser")
    public ResponseEntity getUserListByUser(@RequestBody(required = false) SysUser sysUser) {
        return userService.getSysUserListByUser(sysUser);
    }

    @ApiOperation(value = "移动人员排序", notes = "移动菜单排序")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
    // @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/moveUpdateSysUser")
    public ResponseEntity moveUpdateSysUser(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.moveUpdateSysUser(sysUser);
    }

    @ApiOperation(value = "修改用户姓名手机号", notes = "修改用户姓名手机号")
    @ApiImplicitParam(name = "sysUser", value = "用户entity", dataType = "SysUser")
    // @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/updateSysUserPhoneNumber")
    public ResponseEntity updateSysUserPhoneNumber(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.updateSysUserPhoneNumber(sysUser);
    }
    
    @ApiOperation(value = "批量修改用户评分领导ext3", notes = "批量修改用户评分领导ext3")
    @ApiImplicitParam(name = "sysUser", value = "用户entity", dataType = "SysUser")
    // @RequirePermissions(value = PGIdConst.SYS_USER_MGMT)
    @RequireToken
    @PostMapping("/updateSysUserScoringLeader")
    public ResponseEntity updateSysUserScoringLeader(@RequestBody(required = false) SysUser sysUser) throws Exception {
        return userService.updateSysUserScoringLeader(sysUser);
    }

//    /**
//     * 查询在线人数
//     */
//    @RequestMapping("/online")
//    public Object online() {
//        return "当前在线人数：" + MySessionListener.online + "人";
//    }
}

package com.apih5.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.entity.SysUserOtherEntity;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.mybatis.dao.BaseAccountMapper;
import com.apih5.mybatis.pojo.BaseAccount;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * 系统对外提供的接口token-Controller
 */
@Api(value="用户controller",tags={"用户操作接口"})
@RestController
public class ApiTokenController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ResponseEntity repEntity;
	@ApolloConfig(ConfigConst.ZJ)
	private Config zjConfig;
	@Autowired
	private UserService userService;
	@Autowired
	private BaseAccountMapper baseAccountMapper;

	/**
	 * 用户登录
	 */
	@ApiOperation(value = "用户登录", notes = "用户登录")
	@ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
	@PostMapping("/getAccessToken")
	public ResponseEntity getAccessToken(@RequestBody @Validated({ SysUser.Second.class }) SysUser sysUser) {
		// 对外服务
		if (StrUtil.isEmpty(sysUser.getLoginType())) {
			// 企业ID或企业秘钥错误（企业ID）
			if(StrUtil.isEmpty(sysUser.getOtherCorpId())){
				return repEntity.error("sys.other.token");
			}
			// 企业ID或企业秘钥错误（企业秘钥）
			if(StrUtil.isEmpty(sysUser.getOtherCorpSecret())){
				return repEntity.error("sys.other.token");
			}
			// 为了通用性，将模拟用户id、密码登录
			sysUser.setUserId(sysUser.getOtherCorpId());
			sysUser.setUserPwd(sysUser.getOtherCorpSecret());
		}

		// 用户名登录
		return userService.canLogin(sysUser);
	}

//	/**
//	 * 用户登录
//	 */
//	@GetMapping("/getJobForZjSysInfo")
//	public ResponseEntity getJobForZjSysInfo() {
//		tempSyncZjUserInfoService.SyncZjSysInfo();
//		return null;
//	}
	
	/**
	 * 中交·千方·用户登录
	 */
	@ApiOperation(value = "用户登录", notes = "用户登录")
	@ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
	@PostMapping("/getZjQfAccessToken")
	public JSONObject getZjQfAccessToken(@RequestBody(required = false) SysUserOtherEntity sysUserOtherEntity) {
		
	    SysUser sysUser = new SysUser();
		BeanUtil.copyProperties(sysUserOtherEntity, sysUser);
		JSONObject log = new JSONObject(sysUserOtherEntity);
		LoggerUtils.printLogger(logger, "getZjQfAccessToken-sysUserOtherEntity==" + log.toString());
		sysUser.setUserId(sysUserOtherEntity.getLoginId());
		// 隧道局的时候，获取empcode当成loginId
		if(StrUtil.equals("2", sysUserOtherEntity.getUserType())) {
		    JSONObject jsonObject = new JSONObject();
		    jsonObject.set("loginId", sysUserOtherEntity.getLoginId());
		    String empCode = HttpUtil.sendPostJson(Apih5Properties.getWebUrl() + "getZjWoaQfFourAUserByEmpCode", jsonObject.toString());
		    sysUser.setUserId(empCode);
		}

		JSONObject returnJSONObject = new JSONObject();
		// 企业ID或企业秘钥错误（企业ID）
		if(StrUtil.isEmpty(sysUser.getOtherCorpId())){
			LoggerUtils.printLogger(logger, "getZjQfAccessToken===企业ID不能为空");
			returnJSONObject.put("success", false);
			returnJSONObject.put("desc", "企业ID不能为空");
			return returnJSONObject;
		}
		// 企业ID或企业秘钥错误（企业秘钥）
		if(StrUtil.isEmpty(sysUser.getOtherCorpSecret())){
			LoggerUtils.printLogger(logger, "getZjQfAccessToken===企业秘钥不能为空");
			returnJSONObject.put("success", false);
			returnJSONObject.put("desc", "企业秘钥不能为空");
			return returnJSONObject;
		}
		// Account表验证
		BaseAccount baseAccount = new BaseAccount();
		baseAccount.setCorpId(sysUser.getOtherCorpId());
		baseAccount.setSecret(sysUser.getOtherCorpSecret());
		List<BaseAccount> dbBaseAccountList = baseAccountMapper.selectByBaseAccountList(baseAccount);
		if(dbBaseAccountList == null || dbBaseAccountList.size()==0) {
			LoggerUtils.printLogger(logger, "getZjQfAccessToken===企业秘钥错误");
			returnJSONObject.put("success", false);
			returnJSONObject.put("desc", "企业秘钥错误");
			return returnJSONObject;
		}
		baseAccount = dbBaseAccountList.get(0);
		sysUser.setAccountId(baseAccount.getAccountId());
		sysUser.setLoginType("7");
		sysUser.setDefaultUserPwdFlag("1");
		
		// 千方接口验证
//		try {
//			if(!StrUtil.equals("qianfangSyncUser", sysUser.getUserId())) {
//				String qfUrl = zjConfig.getProperty("zj.qf.url", "") 
//						+ "Portal/irest/base/loginHandler/checkUser?loginId="
//						+ sysUser.getUserId() + "&token="+sysUserOtherEntity.getToken();
//				String result = cn.hutool.http.HttpUtil.post(qfUrl, "", 2000); 
//				JSONObject resultJsonObject = new JSONObject(result);
//				if(!StrUtil.equals("true", resultJsonObject.getStr("success"), true)) {
//					LoggerUtils.printLogger(logger, "getZjQfAccessToken===千方系统token验证错误1");
//					returnJSONObject.put("success", false);
//					returnJSONObject.put("desc", "千方系统token验证错误" + resultJsonObject.getStr("errorMessage"));
//					return returnJSONObject;
//				}
//			}
//		} catch (Exception e) {
//			LoggerUtils.printLogger(logger, "getZjQfAccessToken===千方系统token验证错误");
//			returnJSONObject.put("success", false);
//			returnJSONObject.put("desc", "千方系统token验证错误2");
//			return returnJSONObject;
//		}
		
		// 暂时固定写死，不然影响单点登录
		sysUser.setAccountId(baseAccount.getOtherAccountId());
		ResponseEntity returnCanLogin = userService.canLogin(sysUser);
		if(returnCanLogin.isSuccess()) {
			Object object = returnCanLogin.getData();
			JSONObject jsonObject = new JSONObject(object);
			returnJSONObject.put("success", true);
			String uri = zjConfig.getProperty("qf.to.woa.home", "").replace("CODE", jsonObject.getStr("token"));
			returnJSONObject.put("ipAddress", uri);
			returnJSONObject.put("woaToken", jsonObject.getStr("token"));
			returnJSONObject.put("desc", "ok");
			return returnJSONObject;
		} else {
	    	LoggerUtils.printLogger(logger, "getZjQfAccessToken===" + returnCanLogin.getMessage());
			returnJSONObject.put("success", false);
			returnJSONObject.put("desc", returnCanLogin.getMessage());
			return returnJSONObject;
		}
	}
	
	/**
     * 第三放获取token凭证
     */
    @ApiOperation(value = "用户登录", notes = "用户登录")
    @ApiImplicitParam(name = "sysUser", value = "系统用户entity", dataType = "SysUser")
    @PostMapping("/getOtherWebAccessToken")
    public ResponseEntity getOtherWebAccessToken(HttpServletRequest request,
            @RequestBody(required = false) SysUserOtherEntity sysUserOtherEntity) {
        String ip = getIpAddress(request);
//        if(!StrUtil.equals("39.107.235.98", ip)
//                && !StrUtil.equals("39.106.104.41", ip)
//                && !StrUtil.equals("0:0:0:0:0:0:0:1", ip)) {
//            return repEntity.layerMessage("no", "疑似攻击，禁止访问！"); 
//        }
        
        SysUser sysUser = new SysUser();
        BeanUtil.copyProperties(sysUserOtherEntity, sysUser);
        sysUser.setUserId(sysUserOtherEntity.getLoginId());

        // 企业ID或企业秘钥错误（企业ID）
        if(StrUtil.isEmpty(sysUser.getOtherCorpId())){
            return repEntity.layerMessage("no", "企业ID不能为空");
        }
        // 企业ID或企业秘钥错误（企业秘钥）
        if(StrUtil.isEmpty(sysUser.getOtherCorpSecret())){
            return repEntity.layerMessage("no", "企业秘钥不能为空");
        }
        // Account表验证
        BaseAccount baseAccount = new BaseAccount();
        baseAccount.setCorpId(sysUser.getOtherCorpId());
        baseAccount.setSecret(sysUser.getOtherCorpSecret());
        List<BaseAccount> dbBaseAccountList = baseAccountMapper.selectByBaseAccountList(baseAccount);
        if(dbBaseAccountList == null || dbBaseAccountList.size()==0) {
            return repEntity.layerMessage("no", "企业秘钥错误");
        }
        baseAccount = dbBaseAccountList.get(0);
        sysUser.setAccountId(baseAccount.getAccountId());
        sysUser.setLoginType("7");
        sysUser.setDefaultUserPwdFlag("1");

        // 暂时固定写死，不然影响单点登录
//        sysUser.setAccountId(baseAccount.getOtherAccountId());
        ResponseEntity returnCanLogin = userService.canLogin(sysUser);
        
        if(returnCanLogin.isSuccess()) {
            Object object = returnCanLogin.getData();
            JSONObject jsonObject = new JSONObject(object);
            JSONObject jsonObjectReturn = new JSONObject();
            jsonObjectReturn.put("webCode", jsonObject.getStr("token"));
            jsonObjectReturn.put("expiresIn", 1200);
            return repEntity.ok(jsonObjectReturn);
        } else {
            return repEntity.layerMessage("no", "授权失败");
        }
    }
    
    /** 
     * 获取用户真实IP地址，不使用request.getRemoteAddr();的原因是有可能用户使用了代理软件方式避免真实IP地址, 
     *  
     * 可是，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串IP值，究竟哪个才是真正的用户端的真实IP呢？ 
     * 答案是取X-Forwarded-For中第一个非unknown的有效IP字符串。 
     *  
     * 如：X-Forwarded-For：192.168.1.110, 192.168.1.120, 192.168.1.130, 
     * 192.168.1.100 
     *  
     * 用户真实IP为： 192.168.1.110 
     *  
     * @param request 
     * @return 
     */  
    public static String getIpAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
        return ip;  
    }  
    
    /**
     *获取端口
     */
     public void getPost(HttpServletRequest request, HttpServletResponse response)
         throws ServletException, IOException {
         String uri = request.getRequestURI();//返回请求行中的资源名称
         String url = request.getRequestURL().toString();//获得客户端发送请求的完整url
         String ip = request.getRemoteAddr();//返回发出请求的IP地址
         String params = request.getQueryString();//返回请求行中的参数部分
         String host=request.getRemoteHost();//返回发出请求的客户机的主机名
         int port =request.getRemotePort();//返回发出请求的客户机的端口号。
         System.out.println(ip);
         System.out.println(url);
         System.out.println(uri);
         System.out.println(params);
         System.out.println(host);
         System.out.println(port);
     }
}

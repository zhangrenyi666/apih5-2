package com.apih5.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.wechat.service.WeChatDbService;
import com.apih5.framework.api.wechat.service.WeChatService;
import com.apih5.framework.api.wechatenterprise.entity.JSSdkEntity;
import com.apih5.framework.api.wechatenterprise.entity.WeChatAccessTokenEntity;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.wechatutils.DateUtil;
import com.apih5.framework.api.wechatutils.MapUtil;
import com.apih5.framework.api.zjoa.common.CachData;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseAccountMapper;
import com.apih5.mybatis.pojo.BaseAccount;
import com.apih5.service.BaseAccountService;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONObject;

//import net.sf.json.JSONObject;

/**
 * 中交微信oa系统中转服务
 * 
 * @return www.apih5.com
 */      
@Service("weChatDbService")
public class WeChatDbServiceImpl implements WeChatDbService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private Environment env;  

	@Autowired
	private BaseAccountService accountService;
	
	@Autowired
	private WeChatEnterpriseService wechatEnterpriseService;
	
	@Autowired
	private WeChatService weChatService;

    @Autowired(required = true)
    private BaseAccountMapper baseAccountMapper;
    
	
//	/**
//	 * 根据用户id获取分公司IP地址
//	 * 
//	 * @return 公司IP地址
//	 */
//	public String getZjWeChatOaCompanyIpAddressByUserId(String oaUserId){
//		logger.debug("根据用户id获取分公司IP地址 userid："+oaUserId);
//		// 接口地址
//		String apiUrl = getWeChatOaApiUrl("getIpAddressByUserId.do");
//		// 参数
//		Map<String, String> getParamMap = new HashMap<String, String>();
//		getParamMap.put("userId", oaUserId);
//		// get请求
//		String resultCompanyUrl = HttpUtil.sendGet(apiUrl, MapUtil.mapToString(getParamMap));
//		logger.debug("根据用户id获取分公司IP地址 resultCompanyUrl："+resultCompanyUrl);
//		// 成功结果
//		JSONObject jsonCompanyUrl = new JSONObject(resultCompanyUrl);
//		String companyUrl = (String) jsonCompanyUrl.get("ipAddress");
//		return companyUrl;
//	}
//	
//	
//	/**
//	 * 根据用户id获取部门id
//	 * 
//	 * @return 部门id（=orgId）
//	 */
//	@Override
//	public String getZjWeChatOaDepartmentIdByUserId(String oaUserId) {
//		logger.debug("getZjWeChatOaDepartmentIdByUserId----"+oaUserId);
//		// 3、通过微信OA系统获取部门id也就是orgId
//		String weChatOaApiUrl = getWeChatOaApiUrl("getDepartmentListByUserId.do");
//		// 参数
//		Map<String, String> getParamMap = new HashMap<String, String>();
//		getParamMap.put("userId", oaUserId);
//		// get请求
//		String resultWeChatOa = HttpUtil.sendGet(weChatOaApiUrl, MapUtil.mapToString(getParamMap));
//		// 成功结果
//		JSONObject jsonObjectWeChatOa = new JSONObject(resultWeChatOa);//JSONObject.fromObject(resultWeChatOa);
//		String departmentId = (String) jsonObjectWeChatOa.get("departmentId");
//		logger.debug("getZjWeChatOaDepartmentIdByUserId---departmentId=-"+oaUserId);
//		return departmentId;
//	}
//	
//	
//	/**
//	 * 获取ACCESS_TOKEN
//	 * 
//	 * @return 微信ACCESS_TOKEN
//	 */
//	@Override
//	public String getZjWeChatAccessToken() {
//		String accessTokenUrl = getWeChatOaApiUrl("getWeChatAccessToken.do");
//		
//		//1、获取ACCESS_TOKEN
//		String result = HttpUtil.sendGet(accessTokenUrl);
//		JSONObject resultJson = new JSONObject(result);//JSONObject.fromObject(result);
//		String accessToken = (String) resultJson.get("access_token");
//		
//		return accessToken;
//	}
//	
//	/**
//	 * 获取微oa的apiURl地址
//	 * 
//	 * @param oaUserId
//	 * @return
//	 */
//	private String getWeChatOaApiUrl(String apiName){
//		// 微oa地址
//		String wechatOaurl =  env.getProperty("wechat.oaurl");
//		// 接口地址
//		String apiUrl = wechatOaurl + apiName;
//		return apiUrl;
//	}


	/**
	 * 获取微信服务号token信息
	 * 
	 * mapKeya  accessToken、corpId
	 */
	@Override
	public Map<String, String> getWeChatAccessToken(String accountId) {
		LoggerUtils.printDebugLogger(logger, "getWeChatAccessToken  ");
		BaseAccount baseAccount = accountService.getAccountInfo(accountId);
		Map<String, String> map = Maps.newHashMap();
		LoggerUtils.printDebugLogger(logger, "getWeChatAccessToken  "+baseAccount.getSecret());
		map.put("accessToken", baseAccount.getAccessToken());
		map.put("appId", baseAccount.getCorpId());
		map.put("secret", baseAccount.getSecret());
		map.put("token", baseAccount.getToken());
		map.put("accountCorpId", baseAccount.getAccountCorpId());
		map.put("accountAppType", baseAccount.getAccountAppType());
		return map;
	}
//
//	/**
//	 * 获取微信服务号token信息
//	 * 
//	 * mapKeya  accessToken、corpId
//	 */
//	@Override
//	public Map<String, String> getJsSdkWeChatAccessToken(String accountId) {
//		BaseAccount baseAccount = accountService.getAccountInfo(accountId);
//		Map<String, String> map = Maps.newHashMap();
//		map.put("accessToken", baseAccount.getAccessToken());
//		map.put("appId", baseAccount.getCorpId());
//		map.put("appSecret", baseAccount.getSecret());
//		map.put("token", baseAccount.getToken());
//		return map;
//	}
	
//	/**
//	 * 获取所有基础账号表数据
//	 * 
//	 * @param accountAppType 1:企业号；2：服务号；
//	 * @return account_id的list
//	 */
//	public JSONArray getWeChatTokenList(String accountAppType) {
//		BaseAccount baseAccount = new BaseAccount();
//		baseAccount.setAccountAppType(accountAppType);
//		// 获取数据
//        List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountList(baseAccount);
//        JSONArray jsonArray = JSONUtil.parseArray(baseAccountList);
//        return jsonArray;
//	}
	
	/**
	 * 获取微信jssdk签名
	 * 
	 * @param accountId 企业账号ID
	 * @param url 前台授权地址
	 * @return JSSdkEntity
	 */
	@Override
	public JSSdkEntity getWeChatJSSdkConfig(String accountId, String url) {
		String noncestr = UuidUtil.generate();
		int timestamp = com.apih5.framework.api.wechatutils.DateUtil.getCurrentTimestamp();
		Map<String, String> accessTokenMap = this.getWeChatAccessToken(accountId);
		String accessToken = accessTokenMap.get("accessToken");
		String corpId = accessTokenMap.get("appId");
		Map<String, String> jsapi_ticket = getApiTicket(accessToken, "jsapi_ticket_fwh");
//		LoggerUtils.printDebugLogger(logger, "getWeChatJSSdkConfig corpId"+corpId);
		
		Map<String, String> signMap = Maps.newHashMap();
		signMap.put("jsapi_ticket", jsapi_ticket.get("ticket"));
		LoggerUtils.printDebugLogger(logger, "fwh     jsapi_ticket===" + jsapi_ticket.get("ticket"));
		signMap.put("noncestr", noncestr);
		signMap.put("timestamp", String.valueOf(timestamp));
		signMap.put("url", url);
//		logger.debug("-----------前台url="+url);
		String content = MapUtil.getSignString(signMap);
//		logger.debug("-----------前台url签名="+content);
//		String signature = CodecUtil.encryptSHA(content);
		String signature = SecureUtil.sha1(content);

		// jsonStr = "{\"appid\":\"" + baseAccount.getCorpId() + "\",
		// \"timestamp\":\"" + timestamp
		// + "\", \"noncestr\":\"" + noncestr + "\", \"signature\":\"" +
		// signature + "\"}";
		JSSdkEntity jsSdkInfo = new JSSdkEntity();
		jsSdkInfo.setAppid(corpId);
		jsSdkInfo.setTimestamp(String.valueOf(timestamp));
		jsSdkInfo.setNoncestr(noncestr);
		jsSdkInfo.setSignature(signature);
		return jsSdkInfo;
	}

	private Map<String, String> getApiTicket(String accessToken, String mapKey) {
		Map<String, String> api_ticket = new HashMap<String, String>();
		WeChatAccessTokenEntity token = CachData.wechatAccessMap.get(mapKey);
		if (token == null || StrUtil.isEmpty(token.getToken())
				|| DateUtil.checkDateTimeout(token.getUpdateTime(), 100)) {
			// 获取jsapi_ticket
			Date newData = new Date();
			Map<String, String> getParamMap = new HashMap<String, String>();
			getParamMap.put("access_token", accessToken);
			if ("jsapi_ticket_fwh".equals(mapKey)) {
				JSONObject ticket = weChatService.getJsSdkTicket(1, getParamMap);
				if ("0".equals(String.valueOf(ticket.get("errcode")))) {
					api_ticket.put("ticket", ticket.getStr("ticket"));
					WeChatAccessTokenEntity newToken = new WeChatAccessTokenEntity();
					newToken.setToken(api_ticket.get("ticket"));
					newToken.setUpdateTime(newData);
					CachData.wechatAccessMap.put("jsapi_ticket_fwh", newToken);
				} else {
					api_ticket.put("ticket", "");
					WeChatAccessTokenEntity newToken = new WeChatAccessTokenEntity();
					CachData.wechatAccessMap.put("jsapi_ticket_fwh", newToken);
				}
			} else {
				JSONObject ticket = weChatService.getJsSdkTicket(2, getParamMap);

				if ("0".equals(String.valueOf(ticket.get("errcode")))) {
					api_ticket.put("ticket", ticket.getStr("ticket"));
					api_ticket.put("group_id", ticket.getStr("group_id"));
					WeChatAccessTokenEntity newToken = new WeChatAccessTokenEntity();
					newToken.setToken(api_ticket.get("ticket"));
					newToken.setUpdateTime(newData);
					CachData.wechatAccessMap.put("jsapi_ticket_fwh", newToken);
				} else {
					api_ticket.put("ticket", "");
					api_ticket.put("group_id", "");
					WeChatAccessTokenEntity newToken = new WeChatAccessTokenEntity();
					CachData.wechatAccessMap.put("jsapi_ticket_fwh", newToken);
				}
			}
		} else {
			api_ticket.put("ticket", token.getToken());
			api_ticket.put("group_id", token.getGroupId());
		}
		return api_ticket;
	}
	
}

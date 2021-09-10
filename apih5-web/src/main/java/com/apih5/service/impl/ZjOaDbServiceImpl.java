package com.apih5.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.wechatutils.MapUtil;
import com.apih5.framework.api.zjoa.service.ZjOaDbService;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.dao.BaseAccountMapper;
import com.apih5.service.BaseAccountService;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;

import cn.hutool.json.JSONObject;

//import net.sf.json.JSONObject;

/**
 * 中交微办公接口
 * 
 * @return www.apih5.com
 */
@Service("zjOaDbService")
public class ZjOaDbServiceImpl implements ZjOaDbService {
	
//	@Autowired
//	private Environment env;  
	@ApolloConfig(ConfigConst.ZJ)
	private Config zjConfig;

	@Autowired
	private BaseAccountService accountService;
	
	@Autowired
	private WeChatEnterpriseService wechatEnterpriseService;

    @Autowired(required = true)
    private BaseAccountMapper baseAccountMapper;
    
	private static final Logger logger = LoggerFactory.getLogger(ZjOaDbServiceImpl.class);
	
	/**
	 * 根据用户id获取分公司IP地址
	 * 
	 * @return 公司IP地址
	 */
	public String getZjWeChatOaCompanyIpAddressByUserId(String oaUserId){
		logger.debug("根据用户id获取分公司IP地址 userid："+oaUserId);
		// 接口地址
		String apiUrl = getWeChatOaApiUrl("getIpAddressByUserId.do");
		// 参数
		Map<String, String> getParamMap = new HashMap<String, String>();
		getParamMap.put("userId", oaUserId);
		// get请求
		String resultCompanyUrl = HttpUtil.sendGet(apiUrl, MapUtil.mapToString(getParamMap));
		logger.debug("根据用户id获取分公司IP地址 resultCompanyUrl："+resultCompanyUrl);
		// 成功结果
		JSONObject jsonCompanyUrl = new JSONObject(resultCompanyUrl);
		String companyUrl = (String) jsonCompanyUrl.get("ipAddress");
		return companyUrl;
	}
	
	
	/**
	 * 根据用户id获取部门id
	 * 
	 * @return 部门id（=orgId）
	 */
	@Override
	public String getZjWeChatOaDepartmentIdByUserId(String oaUserId) {
		logger.debug("getZjWeChatOaDepartmentIdByUserId----"+oaUserId);
		// 3、通过微信OA系统获取部门id也就是orgId
		String weChatOaApiUrl = getWeChatOaApiUrl("getDepartmentListByUserId.do");
		// 参数
		Map<String, String> getParamMap = new HashMap<String, String>();
		getParamMap.put("userId", oaUserId);
		// get请求
		String resultWeChatOa = HttpUtil.sendGet(weChatOaApiUrl, MapUtil.mapToString(getParamMap));
		// 成功结果
		JSONObject jsonObjectWeChatOa = new JSONObject(resultWeChatOa);//JSONObject.fromObject(resultWeChatOa);
		String departmentId = (String) jsonObjectWeChatOa.get("departmentId");
		logger.debug("getZjWeChatOaDepartmentIdByUserId---departmentId=-"+oaUserId);
		return departmentId;
	}
	
	
	/**
	 * 获取ACCESS_TOKEN
	 * 
	 * @return 微信ACCESS_TOKEN
	 */
	@Override
	public String getZjWeChatAccessToken() {
		String accessTokenUrl = getWeChatOaApiUrl("getWeChatAccessToken.do");
		
		//1、获取ACCESS_TOKEN
		String result = HttpUtil.sendGet(accessTokenUrl);
		JSONObject resultJson = new JSONObject(result);//JSONObject.fromObject(result);
		String accessToken = (String) resultJson.get("access_token");
		
		return accessToken;
	}
	
	/**
	 * 获取微oa的apiURl地址
	 * 
	 * @param oaUserId
	 * @return
	 */
	private String getWeChatOaApiUrl(String apiName){
		// 微oa地址
		String wechatOaurl = zjConfig.getProperty("wechat.oaurl", "");// env.getProperty("wechat.oaurl");
		// 接口地址
		String apiUrl = wechatOaurl + apiName;
		return apiUrl;
	}
}

package com.apih5.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.entity.BaseAccountWebEntity;
import com.apih5.framework.api.ComConst;
import com.apih5.framework.api.wechat.service.WeChatDbService;
import com.apih5.framework.api.wechat.service.WeChatService;
import com.apih5.framework.api.wechatenterprise.entity.JSSdkEntity;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseDbService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.cache.RedisCacheHandler;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.BaseAccountMapper;
import com.apih5.mybatis.pojo.BaseAccount;
import com.apih5.service.BaseAccountService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;

@Service("baseAccountService")
public class BaseAccountServiceImpl implements BaseAccountService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RedisCacheHandler redisCacheHandler;
	
	private static String URL = "http://weixin.fheb.cn:99/apiWoaSyncOld/";
	
	public static Map<String, BaseAccount> corpInfoMap = new HashMap<String, BaseAccount>();
    
	@Autowired(required = true)
    private ResponseEntity repEntity;

    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private BaseAccountMapper baseAccountMapper;
    
	@Autowired(required = true)
	private WeChatService weChatService;
	
	@Autowired(required = true)
	private WeChatEnterpriseService wechatEnterpriseService;

	@Autowired(required = true)
	private WeChatEnterpriseDbService weChatEnterpriseDbService;
	
	@Autowired(required = true)
	private WeChatDbService weChatDbService;
   
	@Override
    public ResponseEntity getCorpInfo(BaseAccount baseAccount) {
        if (baseAccount == null) {
        	repEntity.error("sys.exception");
        }
        // 获取数据
//        BaseAccount dbBaseAccount = null;
//        if(corpInfoMap != null && corpInfoMap.get("baseAccount") != null) {
//        	dbBaseAccount = corpInfoMap.get("baseAccount");
//        } else {
//        	dbBaseAccount = baseAccountMapper.selectByPrimaryKey(baseAccount.getAccountId());
//        }
        BaseAccount dbBaseAccount = this.getBaseAccount(baseAccount.getAccountId());
        LoggerUtils.printDebugLogger(logger, "-getCorpInfo1"+dbBaseAccount.getCorpId()+"  "+dbBaseAccount.getAgentId());
        
        BaseAccountWebEntity webEntity = new BaseAccountWebEntity();
        webEntity.setCorpId(dbBaseAccount.getCorpId());
        webEntity.setAgentId(dbBaseAccount.getAgentId());
        // uri设置
		String uri = ComConst.AUTHENTIZATION_WEB_REDIRACT_URL
				.replace("APPID", dbBaseAccount.getCorpId())
				.replace("REDIRECT_URI", baseAccount.getDomianUrl())
				.replace("SCOPE", dbBaseAccount.getScope())
				.replace("STATE", baseAccount.getState());
		webEntity.setUri(uri);
//		LoggerUtils.printDebugLogger(logger, "-uri"+uri);
		// 
		if(StrUtil.isNotEmpty(baseAccount.getJssdkUrl())){
			JSSdkEntity jssdkEntity = null;
			if(StrUtil.equals("1", dbBaseAccount.getAccountAppType())){
				jssdkEntity = weChatEnterpriseDbService.getWeChatJSSdkConfig(baseAccount.getAccountId(), baseAccount.getJssdkUrl());
			}
			else if(StrUtil.equals("2", dbBaseAccount.getAccountAppType())){
//				LoggerUtils.printLogger(logger, "-AccountAppTyp"+dbBaseAccount.getAccountAppType());
				jssdkEntity = weChatDbService.getWeChatJSSdkConfig(baseAccount.getAccountId(), baseAccount.getJssdkUrl());
//				LoggerUtils.printLogger(logger, "jsjsdk"+jssdkEntity.getAppid());
			}
			webEntity.setJssdkEntity(jssdkEntity);
		}
		else {
			webEntity.setJssdkEntity(new JSSdkEntity());
		}
        return repEntity.ok(webEntity);
    }
    
    @Override
    public ResponseEntity getBaseAccountListByCondition(BaseAccount baseAccount) {
        if (baseAccount == null) {
            baseAccount = new BaseAccount();
        }
        // 分页查询
        PageHelper.startPage(baseAccount.getPage(),baseAccount.getLimit());
        // 获取数据
        List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountList(baseAccount);
        // 得到分页信息
        PageInfo<BaseAccount> p = new PageInfo<>(baseAccountList);

        return repEntity.okList(baseAccountList, p.getTotal());
    }

    @Override
    public ResponseEntity saveBaseAccount(BaseAccount baseAccount) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        baseAccount.setAccountId(UuidUtil.generate());
        baseAccount.setDelFlag("0");
        baseAccount.setCreateTime(new Date());
        baseAccount.setCreateUser(userKey);
        baseAccount.setModifyTime(new Date());
        baseAccount.setModifyUser(userKey);
        int flag = baseAccountMapper.insert(baseAccount);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", baseAccount);
        }
    }

    @Override
    public ResponseEntity updateBaseAccount(BaseAccount baseAccount) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        int flag = 0;
        BaseAccount dbbaseAccount = baseAccountMapper.selectByPrimaryKey(baseAccount.getAccountId());
        if (dbbaseAccount != null && StrUtil.isNotEmpty(dbbaseAccount.getAccountId())) {
           // 共通
           dbbaseAccount.setModifyTime(new Date());
           dbbaseAccount.setModifyUser(userKey);
           flag = baseAccountMapper.updateByPrimaryKey(dbbaseAccount);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",baseAccount);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateBaseAccount(List<BaseAccount> baseAccountList) {
        int flag = 0;
        if (baseAccountList != null && baseAccountList.size() > 0) {
           flag = baseAccountMapper.batchDeleteUpdateBaseAccount(baseAccountList);
        }
        // 失败
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",baseAccountList);
        }
   }
    
//	/**
//	 * 获取accessToken
//	 * @return accessToken
//	 */
//	public BaseAccount getAccountInfo(String accountId) {
//		BaseAccount accountInfo = baseAccountMapper.selectByPrimaryKey(accountId);
//		String accessToken = "";
//		// 判断accessToken是否存在或者超时
//		if (StrUtil.isEmpty(accountInfo.getAccessToken())
//				|| DateUtil.checkDateTimeout(accountInfo.getModifyTime(), 120)) {
//			// 企业号已经超时
//			if(StrUtil.equals("1", accountInfo.getAccountAppType())){
//				Map<String, String> getParamMap = Maps.newHashMap();
//				getParamMap.put("corpid", accountInfo.getCorpId());
//				getParamMap.put("corpsecret", accountInfo.getSecret());
//				JSONObject token = wechatEnterpriseService.getToken(getParamMap);			
//				accessToken = token.getStr("access_token");
//			}
//			// 服务号已经超时
//			else if(StrUtil.equals("2", accountInfo.getAccountAppType())){
////				if(false) {
//					Map<String, String> getParamMap = Maps.newHashMap();
//					getParamMap.put("appid", accountInfo.getCorpId());
//					getParamMap.put("secret", accountInfo.getSecret());
//					accessToken = weChatService.getToken(getParamMap);
////				} else {
////					// web、应用服务器分离的情况下，调用web服务器地址（腾讯规定IP白名单）
////					JSONObject jsonObject = new JSONObject();
////					jsonObject.put("corpId", accountInfo.getCorpId());
////					jsonObject.put("secret", accountInfo.getSecret());
////					String url = "http://jb.ccfourth.com/otherApi/" + "getWeChatAccessTokenByFwh";
////					accessToken = HttpUtil.sendPostJson(url, jsonObject.toString());
////				}
//			}
//			// 保存token到DB
//			accountInfo.setAccessToken(accessToken);
//			// update
//			accountInfo.setDelFlag("0");
//			accountInfo.setModifyTime(new Date());
//			accountInfo.setModifyUser("");
//			baseAccountMapper.updateByPrimaryKeySelective(accountInfo);
//		} else {
//			accessToken = accountInfo.getAccessToken();
//		}
//		accountInfo.setAccessToken(accessToken);
//		return accountInfo;
//	}

    /**
     * 获取getBaseAccount
     * 
     * @return baseAccount
     */
    @Override
    public BaseAccount getBaseAccount(String accountId) {
        BaseAccount baseAccount = this.getBaseAccountRedis(accountId);
        // 如是空则从数据库获取，然后把当前获取的token值放入redis中
        if(baseAccount == null) {
            baseAccount = baseAccountMapper.selectByPrimaryKey(accountId);
            this.putBaseAccountRedis(accountId, baseAccount);
        } 
        return baseAccount;
    }

    /**
	 * 获取accessToken（必须微信调用才可以使用，因为判断了AccessToken是否存在
	 * 
	 * @return accessToken
	 */
    @Override
	public BaseAccount getAccountInfo(String accountId) {
	    BaseAccount baseAccount = this.getAccessTokenRedis(accountId);
	    // 如是空则从数据库获取，然后把当前获取的token值放入redis中
	    if(baseAccount == null || StrUtil.isEmpty(baseAccount.getAccessToken())) {
	        baseAccount = this.getBaseAccount(accountId);
	        String accessToken = "";
	        // 企业号时
            if(StrUtil.equals("1", baseAccount.getAccountAppType())){
                Map<String, String> getParamMap = Maps.newHashMap();
                getParamMap.put("corpid", baseAccount.getCorpId());
                getParamMap.put("corpsecret", baseAccount.getSecret());
                JSONObject token = wechatEnterpriseService.getToken(getParamMap);           
                accessToken = token.getStr("access_token");
            }
            // 服务号时
            else if(StrUtil.equals("2", baseAccount.getAccountAppType())){
                Map<String, String> getParamMap = Maps.newHashMap();
                getParamMap.put("appid", baseAccount.getCorpId());
                getParamMap.put("secret", baseAccount.getSecret());
                accessToken = weChatService.getToken(getParamMap);
            }
            baseAccount.setAccessToken(accessToken);
            // 同时更新baseAccount表及对应的缓存
            baseAccount.setModifyTime(new Date());
            baseAccountMapper.updateByPrimaryKey(baseAccount);
            this.putBaseAccountRedis(accountId, baseAccount);
            // accessToken更新
            this.putAccessTokenRedis(accountId, baseAccount);
	    }
	    return baseAccount;
	}
    
	@Override
	public ResponseEntity syncWeChatAccessTokenByFrom(BaseAccount baseAccount) {
		List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountList(baseAccount);
		JSONObject jsonObject = new JSONObject();
		for(BaseAccount dbBaseAccount:baseAccountList) {
			String accountId = dbBaseAccount.getAccountId();
			jsonObject.put(accountId, dbBaseAccount.getAccessToken());
		}
		return repEntity.ok(jsonObject);
	}
	
	@Override
	public ResponseEntity syncWeChatAccessToken(BaseAccount baseAccount) {
		String url = URL + "syncWeChatAccessTokenByFrom";
        String result = HttpUtil.sendPostJson(url, "");
        JSONObject resultJsonObject = new JSONObject(result);
        JSONObject jsonObject = resultJsonObject.getJSONObject("data");
		
        List<BaseAccount> baseAccountList = baseAccountMapper.selectByBaseAccountList(baseAccount);
		for(BaseAccount dbBaseAccount:baseAccountList) {
			String accountId = dbBaseAccount.getAccountId();
			String accessToken = jsonObject.getStr(accountId);
			if(!StrUtil.equals(dbBaseAccount.getAccessToken(), accessToken)) {
				dbBaseAccount.setAccessToken(accessToken);
				dbBaseAccount.setModifyTime(new Date());
				dbBaseAccount.setModifyUser("sync");
			    // accessToken更新
	            this.putAccessTokenRedis(accountId, dbBaseAccount);
		        baseAccountMapper.updateByPrimaryKey(dbBaseAccount);
			}
		}
		return repEntity.ok("ok");
	}

    @Override
    public void putAccessTokenRedis(String accountId, BaseAccount baseAccount) {
        JSONObject jsonObject = new JSONObject(baseAccount);
        redisCacheHandler.setCacheWithTimeout("accessToken:" + accountId, jsonObject.toString(), 100L, TimeUnit.MINUTES);
    }

    @Override
    public BaseAccount getAccessTokenRedis(String accountId) {
        Object object = redisCacheHandler.getCache("accessToken:" + accountId);
        if(object == null) {
            return null;
        }
        String token = (String)object;
        if(StrUtil.isEmpty(token)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(token);
        if(StrUtil.isEmpty(jsonObject.getStr("accessToken"))) {
            return null;
        }
        BaseAccount baseAccount = jsonObject.toBean(BaseAccount.class);
        return baseAccount;
    }

    @Override
    public void removeAccessTokenRedis(String accountId) {
        redisCacheHandler.clearCacheByKey("accessToken:" + accountId);
    }

    @Override
    public void putBaseAccountRedis(String accountId, BaseAccount baseAccount) {
        JSONObject jsonObject = new JSONObject(baseAccount);
        redisCacheHandler.setCache("baseAccount:" + accountId, jsonObject.toString());
    }
    
    @Override
    public BaseAccount getBaseAccountRedis(String accountId) {
        Object object = redisCacheHandler.getCache("baseAccount:" + accountId);
        if(object == null) {
            return null;
        }
        String token = (String)object;
        if(StrUtil.isEmpty(token)) {
            return null;
        }
        JSONObject jsonObject = new JSONObject(token);
        BaseAccount baseAccount = jsonObject.toBean(BaseAccount.class);
        return baseAccount;
    }
    
    @Override
    public void removeBaseAccountRedis(String accountId) {
        redisCacheHandler.clearCacheByKey("baseAccount:" + accountId);
    }
}

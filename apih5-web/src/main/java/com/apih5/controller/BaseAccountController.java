package com.apih5.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.wechat.service.WeChatService;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.pojo.BaseAccount;
import com.apih5.service.BaseAccountService;
import com.google.common.collect.Maps;

import cn.hutool.json.JSONObject;

@RestController
public class BaseAccountController {

    @Autowired(required = true)
    private BaseAccountService baseAccountService;
	@Autowired(required = true)
	private WeChatService weChatService;

    @ApiOperation(value="查询基础账号", notes="查询基础账号")
    @ApiImplicitParam(name = "baseAccount", value = "基础账号entity", dataType = "BaseAccount")
    @PostMapping("/getCorpInfo")
    public ResponseEntity getCorpInfo(@RequestBody(required = false) BaseAccount baseAccount) {
        return baseAccountService.getCorpInfo(baseAccount);
    }
    
    @ApiOperation(value="查询基础账号", notes="查询基础账号")
    @ApiImplicitParam(name = "baseAccount", value = "基础账号entity", dataType = "BaseAccount")
    @RequireToken
    @PostMapping("/getBaseAccountList")
    public ResponseEntity getBaseAccountList(@RequestBody(required = false) BaseAccount baseAccount) {
        return baseAccountService.getBaseAccountListByCondition(baseAccount);
    }

    @ApiOperation(value="新增基础账号", notes="新增基础账号")
    @ApiImplicitParam(name = "baseAccount", value = "基础账号entity", dataType = "BaseAccount")
    @RequireToken
    @PostMapping("/addBaseAccount")
    public ResponseEntity addBaseAccount(@RequestBody(required = false) BaseAccount baseAccount) {
        return baseAccountService.saveBaseAccount(baseAccount);
    }

    @ApiOperation(value="更新基础账号", notes="更新基础账号")
    @ApiImplicitParam(name = "baseAccount", value = "基础账号entity", dataType = "BaseAccount")
    @RequireToken
    @PostMapping("/updateBaseAccount")
    public ResponseEntity updateBaseAccount(@RequestBody(required = false) BaseAccount baseAccount) {
        return baseAccountService.updateBaseAccount(baseAccount);
    }

    @ApiOperation(value="删除基础账号", notes="删除基础账号")
    @ApiImplicitParam(name = "baseAccountList", value = "基础账号List", dataType = "List<BaseAccount>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateBaseAccount")
    public ResponseEntity batchDeleteUpdateBaseAccount(@RequestBody(required = false) List<BaseAccount> baseAccountList) {
        return baseAccountService.batchDeleteUpdateBaseAccount(baseAccountList);
    }

    // --- 扩展
    @ApiOperation(value="查询基础账号", notes="查询基础账号")
    @ApiImplicitParam(name = "baseAccount", value = "基础账号entity", dataType = "BaseAccount")
//    @RequireToken
    @PostMapping("/getWeChatAccessTokenByFwh")
    public String getWeChatAccessTokenByFwh(@RequestBody(required = false) BaseAccount baseAccount) {
		Map<String, String> getParamMap = Maps.newHashMap();
		getParamMap.put("appid", baseAccount.getCorpId());
		getParamMap.put("secret", baseAccount.getSecret());
		String accessToken = weChatService.getToken(getParamMap);		
        return accessToken;
    }
    
    @ApiOperation(value="提供WeChattoken", notes="提供WeChattoken")
    @ApiImplicitParam(name = "baseAccount", value = "基础账号entity", dataType = "BaseAccount")
    @PostMapping("/syncWeChatAccessTokenByFrom")
    public ResponseEntity syncWeChatAccessTokenByFrom(@RequestBody(required = false) BaseAccount baseAccount) {
    	return baseAccountService.syncWeChatAccessTokenByFrom(baseAccount);
    }

    @ApiOperation(value="同步WeChattoken", notes="同步WeChattoken")
    @ApiImplicitParam(name = "baseAccount", value = "基础账号entity", dataType = "BaseAccount")
    @PostMapping("/syncWeChatAccessToken")
    public ResponseEntity syncWeChatAccessToken(@RequestBody(required = false) BaseAccount baseAccount) {
    	return baseAccountService.syncWeChatAccessToken(baseAccount);
    }
}

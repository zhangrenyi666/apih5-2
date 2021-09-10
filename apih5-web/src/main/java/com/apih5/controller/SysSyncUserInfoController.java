package com.apih5.controller;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.mybatis.pojo.SysSyncUserInfo;
import com.apih5.service.SysSyncUserInfoService;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@RestController
public class SysSyncUserInfoController {

    @Autowired
    private Apih5Properties apih5Properties;

    @Autowired(required = true)
    private SysSyncUserInfoService sysSyncUserInfoService;

    @ApiOperation(value="查询用户信息同步", notes="查询用户信息同步")
    @ApiImplicitParam(name = "sysSyncUserInfo", value = "用户信息同步entity", dataType = "SysSyncUserInfo")
    @RequireToken
    @PostMapping("/getSysSyncUserInfoList")
    public ResponseEntity getSysSyncUserInfoList(@RequestBody(required = false) SysSyncUserInfo sysSyncUserInfo) {
        return sysSyncUserInfoService.getSysSyncUserInfoListByCondition(sysSyncUserInfo);
    }

    @ApiOperation(value="查询详情用户信息同步", notes="查询详情用户信息同步")
    @ApiImplicitParam(name = "sysSyncUserInfo", value = "用户信息同步entity", dataType = "SysSyncUserInfo")
    @RequireToken
    @PostMapping("/getSysSyncUserInfoDetails")
    public ResponseEntity getSysSyncUserInfoDetails(@RequestBody(required = false) SysSyncUserInfo sysSyncUserInfo) {
        return sysSyncUserInfoService.getSysSyncUserInfoDetails(sysSyncUserInfo);
    }

    @ApiOperation(value="新增用户信息同步", notes="新增用户信息同步")
    @ApiImplicitParam(name = "sysSyncUserInfo", value = "用户信息同步entity", dataType = "SysSyncUserInfo")
    @RequireToken
    @PostMapping("/addSysSyncUserInfo")
    public ResponseEntity addSysSyncUserInfo(@RequestBody(required = false) SysSyncUserInfo sysSyncUserInfo) {
        return sysSyncUserInfoService.saveSysSyncUserInfo(sysSyncUserInfo);
    }

    @ApiOperation(value="更新用户信息同步", notes="更新用户信息同步")
    @ApiImplicitParam(name = "sysSyncUserInfo", value = "用户信息同步entity", dataType = "SysSyncUserInfo")
    @RequireToken
    @PostMapping("/updateSysSyncUserInfo")
    public ResponseEntity updateSysSyncUserInfo(@RequestBody(required = false) SysSyncUserInfo sysSyncUserInfo) {
        return sysSyncUserInfoService.updateSysSyncUserInfo(sysSyncUserInfo);
    }

    @ApiOperation(value="删除用户信息同步", notes="删除用户信息同步")
    @ApiImplicitParam(name = "sysSyncUserInfoList", value = "用户信息同步List", dataType = "List<SysSyncUserInfo>")
    @RequireToken
    @PostMapping("/batchDeleteUpdateSysSyncUserInfo")
    public ResponseEntity batchDeleteUpdateSysSyncUserInfo(@RequestBody(required = false) List<SysSyncUserInfo> sysSyncUserInfoList) {
        return sysSyncUserInfoService.batchDeleteUpdateSysSyncUserInfo(sysSyncUserInfoList);
    }
    
    // --扩展
    @ApiOperation(value="同步用户信息", notes="同步用户信息")
    @ApiImplicitParam(name = "sysSyncUserInfoList", value = "用户信息同步List", dataType = "List<SysSyncUserInfo>")
//    @RequireToken
    @PostMapping("/jobsSysSyncUserInfo")
    public ResponseEntity jobsSysSyncUserInfo(@RequestBody(required = false) JSONArray jsonArrayPar) {
        JSONArray jsonArray = new JSONArray();
        for (Iterator<Object> iterator = jsonArrayPar.iterator(); iterator.hasNext();) {
            JSONObject jsonObjectApiUrl = (JSONObject)iterator.next();
            String url = jsonObjectApiUrl.getStr("apiUrl");
            String flag = jsonObjectApiUrl.getStr("flag");
            String accountId = jsonObjectApiUrl.getStr("accountId");
            String userId = jsonObjectApiUrl.getStr("userId");
            
            try {
                JSONObject jsonObjectCompany = new JSONObject();
                jsonObjectCompany.set("apiUrl", url);
                jsonObjectCompany.set("companyId", "");
                jsonObjectCompany.set("flag", flag);
                jsonObjectCompany.set("accountId", accountId);
                jsonObjectCompany.set("loginType", "1");
                jsonObjectCompany.set("userId", userId);
                jsonObjectCompany.set("userPwd", apih5Properties.getDefaultPassword());
                
                String resultToken = HttpUtil.sendPostJson(url + "user/login", jsonObjectCompany.toString());
                JSONObject jsonObjectToken = new JSONObject(resultToken);
                if(jsonObjectToken.getBool("success")) {
                    jsonObjectCompany.set("token", jsonObjectToken.getJSONObject("data").getStr("token"));
                    jsonArray.add(jsonObjectCompany);
                }
            } catch (Exception e) {
            }
        }
    	return sysSyncUserInfoService.jobsSysSyncUserInfo(jsonArray);
    }

}

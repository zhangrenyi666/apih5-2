package com.apih5.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.service.SyncWeChatService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class SyncWeChatController {
    @Autowired(required = true)
    private ResponseEntity repEntity;
    
    @Autowired(required = true)
    private SyncWeChatService syncWeChatService;

    /**
	 * 从企业微信同步到本系统（部门、用户）
	 * 1、覆盖原账号，将所有微信信息同步到本系统
	 * 2、只同步新增的用户信息和部门信息
	 * 3、只同步新增的用户信息和部门信息并且将部门修改过的更新
	 * 
	 * @return 
	 */
	@ApiOperation(value = "企业微信同步到本系统", notes = "企业微信同步到本系统")
	@ApiImplicitParam(name = "sysUser", value = "角色entity", dataType = "SysUser")
	@RequireToken
	@PostMapping("/syncWeChatToSysInfo")
	public ResponseEntity syncWeChatToSysInfo(HttpServletRequest request) throws Exception {
		return syncWeChatService.syncWeChatToSysInfo();
	}
	
	/**
     * 从企业微信同步到本系统（部门、用户）
     * 1、覆盖原账号，将所有微信信息同步到本系统
     * 2、只同步新增的用户信息和部门信息
     * 3、只同步新增的用户信息和部门信息并且将部门修改过的更新
     * 
     * @return 
     */
    @ApiOperation(value = "企业微信同步到本系统", notes = "企业微信同步到本系统")
    @ApiImplicitParam(name = "sysUser", value = "角色entity", dataType = "SysUser")
    @RequireToken
    @PostMapping("/syncWeChatToSysInfoByUpdate")
    public ResponseEntity syncWeChatToSysInfoByUpdate(HttpServletRequest request) throws Exception {
        return syncWeChatService.syncWeChatToSysInfoByUpdate();
    }
    
//    /**
//     * 从企业微信同步到本系统--四局
//     * 1、覆盖原账号，将所有微信信息同步到本系统
//     * 2、只同步新增的用户信息和部门信息
//     * 3、只同步新增的用户信息和部门信息并且将部门修改过的更新
//     * 
//     * @return 
//     */
//    @ApiOperation(value = "企业微信同步到本系统", notes = "企业微信同步到本系统")
//    @ApiImplicitParam(name = "sysUser", value = "角色entity", dataType = "SysUser")
//    @RequireToken
//    @PostMapping("/syncWeChatToSysInfoBySiju")
//    public ResponseEntity syncWeChatToSysInfoBySiju(HttpServletRequest request) throws Exception {
//        return syncWeChatService.syncWeChatToSysInfoBySiju();
//    }
//    
//    /**
//     * 交建通更新本地库的部门名称
//     * 
//     * @param sysDepartment
//     * @return
//     */
//    @ApiOperation(value="交建通更新本地库的部门名称", notes="交建通更新本地库的部门名称")
//    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
//    @RequireToken
//    @PostMapping("/syncWeChatToUpdateSysDeptBySiju")
//    public ResponseEntity syncWeChatToUpdateSysDeptBySiju(@RequestBody(required = false) SysDepartment sysDepartment) {
//        return syncWeChatService.syncWeChatToUpdateSysDeptBySiju(sysDepartment);
//    }
	
    /**
     * 从本系统部门、人员同步到企业微信（四局）
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value="本系统同步到企业微信", notes="本系统同步到企业微信")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncSysInfoToWeChat")
    public ResponseEntity syncSysDepartmentToWeChat(@RequestBody(required = false) SysDepartment sysDepartment) {
    	return syncWeChatService.syncSysInfoToWeChat(sysDepartment);
    }
    
    /**
     * 从其他系统到本系统（部门、用户）
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value="从其他系统到本系统（部门、用户）", notes="从其他系统到本系统（部门、用户）")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncOtherOaToSysInfo")
    public ResponseEntity syncOtherOaToSysInfo(@RequestBody(required = false) SysDepartment sysDepartment) {
        return syncWeChatService.syncOtherOaToSysInfo();
    }

//    @PostMapping("/syncOtherOaToSysInfoStart")
//    public ResponseEntity syncOtherOaToSysInfoStart(HttpServletRequest request,
//            @RequestBody(required = false) SysDepartment sysDepartment) {
//        String accountId = request.getParameter("accountId");//zj_qyh_gqgs_id
//        String userId = request.getParameter("userId");//sdhb_qwerq
//        String userPwd = request.getParameter("userPwd");//Gqgs@2020.
//        try {
//            JSONObject jsonObjectCompany = new JSONObject();
//            jsonObjectCompany.put("accountId", accountId);
//            jsonObjectCompany.put("loginType", "1");
//            jsonObjectCompany.put("userId", userId);
//            jsonObjectCompany.put("userPwd", userPwd);
//            String resultToken = HttpUtil.sendPostJson(Apih5Properties.getWebUrl()+"user/login", jsonObjectCompany.toString());
//            JSONObject jsonObjectToken = new JSONObject(resultToken);
//            if(jsonObjectToken.getBool("success")) {
//                String token = jsonObjectToken.getJSONObject("data").getStr("token");
//                String result = HttpUtil.sendPostToken(Apih5Properties.getWebUrl()+"syncOtherOaToSysInfo", "", token);
//                JSONObject jsonObject = new JSONObject(result);
//                if(StrUtil.equals("3007", jsonObject.getStr("code"))) {
//                    JSONObject tokenPar = new JSONObject();
//                    tokenPar.put("token", token);
//                    result = HttpUtil.sendPostJson(Apih5Properties.getWebUrl()+"user/refreshAccessToken", tokenPar.toString());
//                    JSONObject tokenResult = new JSONObject(result);
//                    
//                    token = tokenResult.getStr("data");
//                    HttpUtil.sendPostToken(Apih5Properties.getWebUrl()+"syncOtherOaToSysInfo", "", token);
//                    jsonObject = new JSONObject(result);
//                }
//            }
//        } catch (Exception e) {
//        }
//        return repEntity.ok("");
//    }
}

package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.sysdb.service.SysUserDepartmentService;
import com.apih5.framework.api.sysdb.service.UserService;
import com.apih5.framework.api.wechatenterprise.entity.json.request.addressbook.DepartmentInfoReq;
import com.apih5.framework.api.wechatenterprise.entity.json.request.addressbook.UserInfoReq;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseDbService;
import com.apih5.framework.api.wechatenterprise.service.WeChatEnterpriseService;
import com.apih5.framework.api.zjoa.common.CachData;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.entity.OAMember;
import com.apih5.framework.api.zjoa.entity.ZjAccessCode;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.ifs.SequenceService;
import com.apih5.framework.utils.ConvertUtil;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysUserDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.service.SyncWeChatService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("syncWeChatService")
public class SyncWeChatServiceImpl implements SyncWeChatService {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    
	@Autowired(required = true)
	private ResponseEntity repEntity;

	@Autowired(required = true)
	private RequestHolderConfiguration requestHolderConfiguration;

	@Autowired
	private Apih5Properties apih5Properties;

	@Autowired(required = true)
	private WeChatEnterpriseService wechatEnterpriseService;
	
	@Autowired(required = true)
	private WeChatEnterpriseDbService weChatEnterpriseDbService;

	@Autowired(required = true)
	private SysUserMapper sysUserMapper;
	
	@Autowired(required = true)
	private SysDepartmentMapper sysDepartmentMapper;
	
	@Autowired
	private SysUserDepartmentService sysUserDepartmentService;

	@Autowired
	private SysUserDepartmentMapper sysUserDepartmentMapper;

	@Autowired
	private SysDepartmentService sysDepartmentService;
	
	@Autowired
	private UserService userService;
	@Autowired(required = true)
    private SequenceService sequenceService;

	/**
	 * 从企业微信同步到本系统（部门、用户）
	 * 
	 * @return
	 */
	@Override
	public ResponseEntity syncWeChatToSysInfo() throws Exception {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String accountId = TokenUtils.getAccountId(request);
		String userKey = TokenUtils.getUserKey(request);
		String realName = TokenUtils.getRealName(request);
		accountId = accountId+"_txl";
		List<SysUser> userList = Lists.newArrayList();
		// 获取AccessToken
		Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
		String accountCorpId = accessTokenMap.get("accountCorpId");
		String accountAppType = accessTokenMap.get("accountAppType");
		String accessToken = accessTokenMap.get("accessToken");
		Map<String, String> getParamMap = Maps.newHashMap();
		getParamMap.put("access_token", accessToken);
		// getParamMap.put("id", "1");//部门id。获取指定部门及其下的子部门。 如果不填，默认获取全量组织架构
		// 获取部门列表
		JSONObject departmentJson = wechatEnterpriseService.coreServiceByResurceAddressbook(5, getParamMap, null);
		if (departmentJson.getInt("errcode") == 0) {
			// 1、覆盖原账号，将所有微信信息同步到本系统
			if(true){
				// 删除所有相关人员信息（部门、部门关系、人员）
				SysUser sysUser = new SysUser();
				sysUser.setAccountCorpId(accountCorpId);
				sysUser.setAccountAppType(accountAppType);
				List<SysUser> sysUserList = sysUserMapper.selectBySysUserList(sysUser);
				for(SysUser sysUserDb : sysUserList){
					if(StrUtil.equals("admin", sysUserDb.getUserId())){
						continue;
					}
					// 删除所有部门关系表
					SysUserDepartment sysUserDepartment = new SysUserDepartment();
					sysUserDepartment.setUserKey(sysUserDb.getUserKey());
					List<SysUserDepartment> sysUserDepartmentList = sysUserDepartmentMapper.selectBySysUserDepartmentList(sysUserDepartment);
					for(SysUserDepartment sysUserDepartmentDb : sysUserDepartmentList){
						// 删除所有部门
						SysDepartment SysDepartment = new SysDepartment();
						SysDepartment.setDepartmentId(sysUserDepartmentDb.getDepartmentId());
						List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(SysDepartment);
						for(SysDepartment sysDepartmentDb : sysDepartmentList){
							sysDepartmentMapper.deleteByPrimaryKey(sysDepartmentDb.getDepartmentId());
						}
						sysUserDepartmentMapper.deleteByPrimaryKey(sysUserDepartment.getUserDepartmentId());
					}
					sysUserMapper.deleteByPrimaryKey(sysUserDb.getUserKey());
				}
			}

			JSONArray jsonArray = departmentJson.getJSONArray("department");
			for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
			 	JSONObject jsonObject = (JSONObject)iterator.next();
				// 部门实体并且保存数据
				SysDepartment sysDepartment = new SysDepartment();
				sysDepartment.setDepartmentId(jsonObject.getStr("id"));
				sysDepartment.setDepartmentName(jsonObject.getStr("name"));
				if(StrUtil.equals("75342", jsonObject.getStr("id"))) {
				    continue;
				}
				// 微信的根部门是1，但是本系统的根部门是0或者空
				if(!StrUtil.equals("75342", jsonObject.getStr("id"))){
				    if(StrUtil.equals("231312", jsonObject.getStr("parentid"))
				            || StrUtil.equals("231312", jsonObject.getStr("id"))) {
				        System.out.println("");
				    }
				    if(StrUtil.equals("75342", jsonObject.getStr("parentid"))) {
				        sysDepartment.setDepartmentParentId("9999999999");
				    }else {
				        sysDepartment.setDepartmentParentId(jsonObject.getStr("parentid"));
				    }
				} else {
					sysDepartment.setDepartmentParentId("0");
				}
				// 微信当前的ID
				sysDepartment.setOtherId(jsonObject.getStr("id"));
				sysDepartment.setSort(jsonObject.getInt("order"));
				SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(sysDepartment.getDepartmentId());
				if(dbSysDepartment == null){
					sysDepartmentService.saveSysDepartment(sysDepartment);
				}
				
				// 获取该部门的所有成员
				getParamMap = Maps.newHashMap();
				getParamMap.put("access_token", accessToken);
				getParamMap.put("department_id", jsonObject.getStr("id"));
				// 1/0：是否递归获取子部门下面的成员
				getParamMap.put("fetch_child", "0");
				JSONObject memberBydepartmentJson = wechatEnterpriseService.coreServiceByResurceAddressbook(12, getParamMap, null);
				JSONArray jsonArrayUser = memberBydepartmentJson.getJSONArray("userlist");
				if(jsonArrayUser != null){
					 for (Iterator<Object> iteratorUser = jsonArrayUser.iterator(); iteratorUser.hasNext();) {
						 JSONObject jsonObjectUser = (JSONObject)iteratorUser.next();
						 SysUser sysUser = new SysUser();
						 sysUser.setAccountId(accountId);
						 sysUser.setUserId(jsonObjectUser.getStr("userid"));
						 List<SysUser> sysUserList = sysUserMapper.selectBySysUserList(sysUser);
						 if(sysUserList == null || sysUserList.size() == 0){
							 sysUser.setUserKey(UuidUtil.createUUID());
							 sysUser.setRealName(jsonObjectUser.getStr("name"));
							 sysUser.setUserPwd(SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt()));
							 sysUser.setMobile(jsonObjectUser.getStr("mobile"));
							 sysUser.setGender(jsonObjectUser.getStr("gender"));
							 //	sysUser.setOpenid(jsonObjectUser.getStr("mobile"));
							 // 职位[职务]
							 sysUser.setPostionsName(jsonObjectUser.getStr("position"));
							 sysUser.setEmail(jsonObjectUser.getStr("email"));
//							 sysUser.setIdentityCard(identityCard);
//							 sysUser.setAge(age);
							 sysUser.setImageUrl(jsonObjectUser.getStr("avatar").replaceAll("wwlocal.qq.com", "jjt.ccccltd.cn").replaceAll("/0", ""));
							 sysUser.setUserType("1");
							 sysUser.setUserStatus(jsonObjectUser.getStr("status"));
							 sysUser.setAccountCorpId(accountCorpId);
							 sysUser.setAccountAppType(accountAppType);
							 sysUser.setCreateUserInfo(userKey, realName);
							 sysUserMapper.insert(sysUser);
//							 userService.addUserCommon(sysUser);
						 } else {
							 sysUser = sysUserList.get(0);
						 }

						 // 用户部门关系表
						 SysUserDepartment sysUserDepartment = new SysUserDepartment();
						 sysUserDepartment.setUserKey(sysUser.getUserKey());
						 sysUserDepartment.setDepartmentId(sysDepartment.getDepartmentId());
						 sysUserDepartmentService.saveSysUserDepartment(sysUserDepartment);
						 // 返给前台是置空
						 sysUser.setUserPwd("");
						 userList.add(sysUser);
					 }
				}
				else {
					return repEntity.error("sys.exception");
				}
			}
		} else {
			// 提示部门信息不存在，请确认！
			return repEntity.error("sys.exception");
		}
		return repEntity.ok(userList);
	}
	
	/**
     *  从企业微信同步到本系统 - 增量方式同步（部门、用户）
     * 
     * @return
     */
    @Override
    public ResponseEntity syncWeChatToSysInfoByUpdate() throws Exception {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String accountId = TokenUtils.getAccountId(request);
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        accountId = accountId+"_txl";
        List<SysUser> userList = Lists.newArrayList();
        // 获取AccessToken
        Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
        String accountCorpId = accessTokenMap.get("accountCorpId");
        String accountAppType = accessTokenMap.get("accountAppType");
        String accessToken = accessTokenMap.get("accessToken");
        Map<String, String> getParamMap = Maps.newHashMap();
        getParamMap.put("access_token", accessToken);
        getParamMap.put("id", "75342");//部门id。获取指定部门及其下的子部门。 如果不填，默认获取全量组织架构
        // 获取部门列表
        JSONObject departmentJson = wechatEnterpriseService.coreServiceByResurceAddressbook(5, getParamMap, null);

        if (departmentJson.getInt("errcode") == 0) {
            JSONArray jsonArray = departmentJson.getJSONArray("department");
            for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
                try {
                    JSONObject jsonObject = (JSONObject)iterator.next();
                    // 部门实体并且保存数据
                    SysDepartment sysDepartment = new SysDepartment();
                    sysDepartment.setDepartmentId(jsonObject.getStr("id"));
                    sysDepartment.setDepartmentName(jsonObject.getStr("name"));
                    if(StrUtil.equals("75342", jsonObject.getStr("id"))) {
                        continue;
                    }
                    // 微信的根部门是1，但是本系统的根部门是0或者空
                    if(!StrUtil.equals("75342", jsonObject.getStr("id"))){
                        if(StrUtil.equals("231312", jsonObject.getStr("parentid"))
                                || StrUtil.equals("231312", jsonObject.getStr("id"))) {
                            System.out.println("");
                        }
                        if(StrUtil.equals("75342", jsonObject.getStr("parentid"))) {
                            sysDepartment.setDepartmentParentId("9999999999");
                        }else {
                            sysDepartment.setDepartmentParentId(jsonObject.getStr("parentid"));
                        }
                    } else {
                        sysDepartment.setDepartmentParentId("0");
                    }
                    // 微信当前的ID
                    sysDepartment.setOtherId(jsonObject.getStr("id"));
                    sysDepartment.setSort(jsonObject.getInt("order"));
                    SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(sysDepartment.getDepartmentId());
                    if(dbSysDepartment == null){
                        sysDepartmentService.saveSysDepartment(sysDepartment);
                    } else {
                        // 名称有变化则修改
                        if(!StrUtil.equals(jsonObject.getStr("name"), dbSysDepartment.getDepartmentName())) {
                            String oldDepartmentName = dbSysDepartment.getDepartmentName();
                            dbSysDepartment.setDepartmentName(jsonObject.getStr("name"));
                            sysDepartmentMapper.updateByPrimaryKey(dbSysDepartment);
                            
                            // 更新path
                            SysDepartment sysDepartmentSelect = new SysDepartment();
                            sysDepartmentSelect.setDepartmentPath(jsonObject.getStr("id"));
                            List<SysDepartment> sysDepartmentList = sysDepartmentService.selectBySysDepartmentList(sysDepartmentSelect);
                            for(SysDepartment dbSysDepartmentPath:sysDepartmentList) {
                                String pathName = dbSysDepartmentPath.getDepartmentPathName().replaceAll(oldDepartmentName, jsonObject.getStr("name"));
                                dbSysDepartmentPath.setDepartmentPathName(pathName);
                                sysDepartmentMapper.updateByPrimaryKey(dbSysDepartmentPath);
                            }
                        }
                    }
                    
                    // 获取该部门的所有成员
                    getParamMap = Maps.newHashMap();
                    getParamMap.put("access_token", accessToken);
                    getParamMap.put("department_id", jsonObject.getStr("id"));
                    // 1/0：是否递归获取子部门下面的成员
                    getParamMap.put("fetch_child", "0");
                    JSONObject memberBydepartmentJson = wechatEnterpriseService.coreServiceByResurceAddressbook(12, getParamMap, null);
                    JSONArray jsonArrayUser = memberBydepartmentJson.getJSONArray("userlist");
                    if(jsonArrayUser != null){
                        for (Iterator<Object> iteratorUser = jsonArrayUser.iterator(); iteratorUser.hasNext();) {
                            JSONObject jsonObjectUser = (JSONObject)iteratorUser.next();
                            SysUser sysUser = new SysUser();
                            sysUser.setAccountId(accountId);
                            sysUser.setUserId(jsonObjectUser.getStr("userid"));
                            List<SysUser> sysUserList = sysUserMapper.selectBySysUserList(sysUser);
                            if(sysUserList == null || sysUserList.size() == 0){
                                sysUser.setUserKey(UuidUtil.createUUID());
                                sysUser.setRealName(jsonObjectUser.getStr("name"));
                                sysUser.setUserPwd(SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt()));
                                sysUser.setMobile(jsonObjectUser.getStr("mobile"));
                                sysUser.setGender(jsonObjectUser.getStr("gender"));
                                // sysUser.setOpenid(jsonObjectUser.getStr("mobile"));
                                // 职位[职务]
                                sysUser.setPostionsName(jsonObjectUser.getStr("position"));
                                sysUser.setEmail(jsonObjectUser.getStr("email"));
//                           sysUser.setIdentityCard(identityCard);
//                           sysUser.setAge(age);
                                sysUser.setImageUrl(jsonObjectUser.getStr("avatar").replaceAll("wwlocal.qq.com", "jjt.ccccltd.cn").replaceAll("/0", ""));
                                sysUser.setUserType("1");
                                sysUser.setUserStatus(jsonObjectUser.getStr("status"));
                                sysUser.setAccountCorpId(accountCorpId);
                                sysUser.setAccountAppType(accountAppType);
                                sysUser.setCreateUserInfo(userKey, realName);
                                sysUserMapper.insert(sysUser);
//                           userService.addUserCommon(sysUser);
                            } else {
                                sysUser = sysUserList.get(0);
                            }
                            
                            // 用户部门关系表
                            
                            SysUserDepartment sysUserDepartment = new SysUserDepartment();
                            sysUserDepartment.setUserKey(sysUser.getUserKey());
                            sysUserDepartment.setDepartmentId(sysDepartment.getDepartmentId());
                            
                            SysUserDepartment dbSysUserDepartment = sysUserDepartmentService.getRelationByUserKeyAndDepId(sysUserDepartment);
                            if(dbSysUserDepartment != null) {
                                sysUserDepartmentService.saveSysUserDepartment(sysUserDepartment);
                            }
                            // 返给前台是置空
                            sysUser.setUserPwd("");
                            userList.add(sysUser);
                        }
                    }
                    else {
//                        return repEntity.error("sys.exception");
                    }
                }catch (Exception e) {
                }
            }
        } else {
            // 提示部门信息不存在，请确认！
            return repEntity.error("sys.exception");
        }
        
        return repEntity.ok(userList);
    }

    /**
     *  从企业微信同步到本系统 - 四局
     * 
     * @return
     */
//    @Override
//    public ResponseEntity syncWeChatToSysInfoBySiju() throws Exception {
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String accountId = TokenUtils.getAccountId(request);
//        String userKey = TokenUtils.getUserKey(request);
//        String realName = TokenUtils.getRealName(request);
//        accountId = accountId+"_txl";
//        List<SysUser> userList = Lists.newArrayList();
//        // 获取AccessToken
//        Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
//        String accountCorpId = accessTokenMap.get("accountCorpId");
//        String accountAppType = accessTokenMap.get("accountAppType");
//        String accessToken = accessTokenMap.get("accessToken");
//        Map<String, String> getParamMap = Maps.newHashMap();
//        getParamMap.put("access_token", accessToken);
//        
//        // 获取所有人，和本地数据库比较，
//        getParamMap = Maps.newHashMap();
//        getParamMap.put("access_token", accessToken);
//        // 四局根节点
//        getParamMap.put("department_id", "75342");
//        // 1/0：是否递归获取子部门下面的成员
//        getParamMap.put("fetch_child", "1");
//        JSONObject memberBydepartmentJson = wechatEnterpriseService.coreServiceByResurceAddressbook(12, getParamMap, null);
//        JSONArray jsonArrayUser = memberBydepartmentJson.getJSONArray("userlist");
//        if(jsonArrayUser != null){
//            for (Iterator<Object> iteratorUser = jsonArrayUser.iterator(); iteratorUser.hasNext();) {
//                JSONObject jsonObjectUser = (JSONObject)iteratorUser.next();
//                SysUser sysUser = new SysUser();
//                sysUser.setAccountId(accountId);
//                sysUser.setUserId(jsonObjectUser.getStr("userid"));
//                List<SysUser> sysUserList = sysUserMapper.selectBySysUserList(sysUser);
//                // 1、如果人员存在
//                if(sysUserList != null && sysUserList.size() > 0){
//                    // 1.1 从企业微信中获取该用户的所有部门，查看在数据库是否存在部门
//                    JSONArray departmentJSONArray = jsonObjectUser.getJSONArray("department");
//                    for (Iterator<Object> iterator = departmentJSONArray.iterator(); iterator.hasNext();) {
//                        String departmentId = (String)iterator.next();
//                        
//                    }
//                    // 1.1 查看不部门关系表
//                    
//                    
//                    
//                } else {
//                    
//                }
//                // 1.2 否则添加人员
//                if(sysUserList == null || sysUserList.size() == 0){
//                    sysUser.setUserKey(UuidUtil.createUUID());
//                    sysUser.setRealName(jsonObjectUser.getStr("name"));
//                    sysUser.setUserPwd(SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt()));
//                    sysUser.setMobile(jsonObjectUser.getStr("mobile"));
//                    sysUser.setGender(jsonObjectUser.getStr("gender"));
//                    // 职位[职务]
//                    sysUser.setPostionsName(jsonObjectUser.getStr("position"));
//                    sysUser.setEmail(jsonObjectUser.getStr("email"));
//                    sysUser.setImageUrl(jsonObjectUser.getStr("avatar").replaceAll("wwlocal.qq.com", "jjt.ccccltd.cn").replaceAll("/0", ""));
//                    sysUser.setUserType("1");
//                    sysUser.setUserStatus(jsonObjectUser.getStr("status"));
//                    sysUser.setAccountCorpId(accountCorpId);
//                    sysUser.setAccountAppType(accountAppType);
//                    sysUser.setCreateUserInfo(userKey, realName);
//                    sysUserMapper.insert(sysUser);
//                } else {
//                    sysUser = sysUserList.get(0);
//                }
//                
//                // 用户部门关系表
//                SysUserDepartment sysUserDepartment = new SysUserDepartment();
//                sysUserDepartment.setUserKey(sysUser.getUserKey());
//                sysUserDepartment.setDepartmentId(sysDepartment.getDepartmentId());
//                sysUserDepartmentService.saveSysUserDepartment(sysUserDepartment);
//                // 返给前台是置空
//                sysUser.setUserPwd("");
//                userList.add(sysUser);
//            }
//            
//        }
//        
//        
//        // 2、如果人员不存在，则新增
//        
//        // 2.1 人员对应部门是否存在不存在，新增部门
//        
//        // 2.2 再添加人员
//        
//        
//        
//        
//        // getParamMap.put("id", "1");//部门id。获取指定部门及其下的子部门。 如果不填，默认获取全量组织架构
//        // 获取部门列表
//        JSONObject departmentJson = wechatEnterpriseService.coreServiceByResurceAddressbook(5, getParamMap, null);
//        
//        if (departmentJson.getInt("errcode") == 0) {
//            JSONArray jsonArray = departmentJson.getJSONArray("department");
//            for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
//                try {
//                    JSONObject jsonObject = (JSONObject)iterator.next();
//                    // 部门实体并且保存数据
//                    SysDepartment sysDepartment = new SysDepartment();
//                    sysDepartment.setDepartmentId(jsonObject.getStr("id"));
//                    sysDepartment.setDepartmentName(jsonObject.getStr("name"));
//                    if(StrUtil.equals("75342", jsonObject.getStr("id"))) {
//                        continue;
//                    }
//                    // 微信的根部门是1，但是本系统的根部门是0或者空
//                    if(!StrUtil.equals("75342", jsonObject.getStr("id"))){
//                        if(StrUtil.equals("231312", jsonObject.getStr("parentid"))
//                                || StrUtil.equals("231312", jsonObject.getStr("id"))) {
//                            System.out.println("");
//                        }
//                        if(StrUtil.equals("75342", jsonObject.getStr("parentid"))) {
//                            sysDepartment.setDepartmentParentId("9999999999");
//                        }else {
//                            sysDepartment.setDepartmentParentId(jsonObject.getStr("parentid"));
//                        }
//                    } else {
//                        sysDepartment.setDepartmentParentId("0");
//                    }
//                    // 微信当前的ID
//                    sysDepartment.setOtherId(jsonObject.getStr("id"));
//                    sysDepartment.setSort(jsonObject.getInt("order"));
//                    SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(sysDepartment.getDepartmentId());
//                    if(dbSysDepartment == null){
//                        sysDepartmentService.saveSysDepartment(sysDepartment);
//                    }
//                    
//                    // 获取该部门的所有成员
//                    getParamMap = Maps.newHashMap();
//                    getParamMap.put("access_token", accessToken);
//                    getParamMap.put("department_id", jsonObject.getStr("id"));
//                    // 1/0：是否递归获取子部门下面的成员
//                    getParamMap.put("fetch_child", "0");
//                    JSONObject memberBydepartmentJson = wechatEnterpriseService.coreServiceByResurceAddressbook(12, getParamMap, null);
//                    JSONArray jsonArrayUser = memberBydepartmentJson.getJSONArray("userlist");
//                    if(jsonArrayUser != null){
//                        for (Iterator<Object> iteratorUser = jsonArrayUser.iterator(); iteratorUser.hasNext();) {
//                            JSONObject jsonObjectUser = (JSONObject)iteratorUser.next();
//                            SysUser sysUser = new SysUser();
//                            sysUser.setAccountId(accountId);
//                            sysUser.setUserId(jsonObjectUser.getStr("userid"));
//                            List<SysUser> sysUserList = sysUserMapper.selectBySysUserList(sysUser);
//                            if(sysUserList == null || sysUserList.size() == 0){
//                                sysUser.setUserKey(UuidUtil.createUUID());
//                                sysUser.setRealName(jsonObjectUser.getStr("name"));
//                                sysUser.setUserPwd(SecureUtil.md5(apih5Properties.getDefaultPassword() + apih5Properties.getMd5Salt()));
//                                sysUser.setMobile(jsonObjectUser.getStr("mobile"));
//                                sysUser.setGender(jsonObjectUser.getStr("gender"));
//                                // sysUser.setOpenid(jsonObjectUser.getStr("mobile"));
//                                // 职位[职务]
//                                sysUser.setPostionsName(jsonObjectUser.getStr("position"));
//                                sysUser.setEmail(jsonObjectUser.getStr("email"));
////                           sysUser.setIdentityCard(identityCard);
////                           sysUser.setAge(age);
//                                sysUser.setImageUrl(jsonObjectUser.getStr("avatar").replaceAll("wwlocal.qq.com", "jjt.ccccltd.cn").replaceAll("/0", ""));
//                                sysUser.setUserType("1");
//                                sysUser.setUserStatus(jsonObjectUser.getStr("status"));
//                                sysUser.setAccountCorpId(accountCorpId);
//                                sysUser.setAccountAppType(accountAppType);
//                                sysUser.setCreateUserInfo(userKey, realName);
//                                sysUserMapper.insert(sysUser);
////                           userService.addUserCommon(sysUser);
//                            } else {
//                                sysUser = sysUserList.get(0);
//                            }
//                            
//                            // 用户部门关系表
//                            SysUserDepartment sysUserDepartment = new SysUserDepartment();
//                            sysUserDepartment.setUserKey(sysUser.getUserKey());
//                            sysUserDepartment.setDepartmentId(sysDepartment.getDepartmentId());
//                            sysUserDepartmentService.saveSysUserDepartment(sysUserDepartment);
//                            // 返给前台是置空
//                            sysUser.setUserPwd("");
//                            userList.add(sysUser);
//                        }
//                    }
//                    else {
////                        return repEntity.error("sys.exception");
//                    }
//                }catch (Exception e) {
//                }
//            }
//        } else {
//            // 提示部门信息不存在，请确认！
//            return repEntity.error("sys.exception");
//        }
//        
//        return repEntity.ok(userList);
//    }

//    /**
//     * 从本系统部门同步到企业微信
//     * 
//     * @return 
//     */
//    @Override
//    public ResponseEntity syncWeChatToUpdateSysDeptBySiju(SysDepartment sysDepartment) {
//        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
//        String accountId = TokenUtils.getAccountId(request);
//        String userKey = TokenUtils.getUserKey(request);
//        String realName = TokenUtils.getRealName(request);
//        accountId = accountId+"_txl";
//        List<SysUser> userList = Lists.newArrayList();
//        // 获取AccessToken
//        Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
//        String accountCorpId = accessTokenMap.get("accountCorpId");
//        String accountAppType = accessTokenMap.get("accountAppType");
//        String accessToken = accessTokenMap.get("accessToken");
//        Map<String, String> getParamMap = Maps.newHashMap();
//        getParamMap.put("access_token", accessToken);
//        getParamMap.put("id", "75342");//部门id。获取指定部门及其下的子部门。 如果不填，默认获取全量组织架构
//        // 获取部门列表
//        JSONObject departmentJson = wechatEnterpriseService.coreServiceByResurceAddressbook(5, getParamMap, null);
//        if (departmentJson.getInt("errcode") == 0) {
//            JSONArray jsonArray = departmentJson.getJSONArray("department");
//            for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
//                JSONObject jsonObject = (JSONObject)iterator.next();
//                // 部门实体并且保存数据
//                sysDepartment = new SysDepartment();
//                sysDepartment.setDepartmentId(jsonObject.getStr("id"));
//                sysDepartment.setDepartmentName(jsonObject.getStr("name"));
//                if(StrUtil.equals("75342", jsonObject.getStr("id"))) {
//                    continue;
//                }
//                
//                // 部门名称发生变化，则更新部门名称
//                SysDepartment dbSysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(jsonObject.getStr("id"));
//                if(dbSysDepartment != null) {
//                    // 名称有变化则修改
//                    if(!StrUtil.equals(jsonObject.getStr("name"), dbSysDepartment.getDepartmentName())) {
//                        String oldDepartmentName = dbSysDepartment.getDepartmentName();
//                        dbSysDepartment.setDepartmentName(jsonObject.getStr("name"));
//                        sysDepartmentMapper.updateByPrimaryKey(dbSysDepartment);
//                        
//                        // 更新path
//                        SysDepartment sysDepartmentSelect = new SysDepartment();
//                        sysDepartmentSelect.setDepartmentPath(jsonObject.getStr("id"));
//                        List<SysDepartment> sysDepartmentList = sysDepartmentService.selectBySysDepartmentList(sysDepartmentSelect);
//                        for(SysDepartment dbSysDepartmentPath:sysDepartmentList) {
//                            String pathName = dbSysDepartmentPath.getDepartmentPathName().replaceAll(oldDepartmentName, jsonObject.getStr("name"));
//                            dbSysDepartmentPath.setDepartmentPathName(pathName);
//                            sysDepartmentMapper.updateByPrimaryKey(dbSysDepartmentPath);
//                        }
//                    }
//                }
//            }
//        } else {
//            // 提示部门信息不存在，请确认！
//            return repEntity.error("sys.exception");
//        }
//        return repEntity.ok(userList);
//    }
    
    
	/**
	 * 从本系统部门同步到企业微信
	 * 
	 * @return 
	 */
	@Override
	public ResponseEntity syncSysInfoToWeChat(SysDepartment sysDepartment) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String accountId = TokenUtils.getAccountId(request)+"_txl";

		// 获取相关腾讯使用接口
		Map<String, String> accessTokenMap = weChatEnterpriseDbService.getWeChatAccessToken(accountId);
		String accessToken = accessTokenMap.get("accessToken");
		Map<String, String> getParamMap = new HashMap<String, String>();
		getParamMap.put("access_token", accessToken);
		
		// 设置检索部门参数
		SysDepartment sysDepartmentSelect = new SysDepartment();
		sysDepartmentSelect.setDepartmentPath(sysDepartment.getDepartmentId());
//		sysDepartmentSelect.setDepartmentParentId(sysDepartment.getDepartmentId());
		// 获取该部门ID下的所有部门数据
		List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
		for(SysDepartment sysDepartmentDb:sysDepartmentList){
//		    String str = "{\"errcode\":0,\"errmsg\":\"ok\",\"department\":[{\"id\":305,\"name\":\"项目领导\",\"parentid\":158,\"order\":999},{\"id\":345,\"name\":\"项目部\",\"parentid\":1,\"order\":77},{\"id\":41,\"name\":\"安六一工区\",\"parentid\":345,\"order\":0},{\"id\":44,\"name\":\"安六二工区\",\"parentid\":345,\"order\":1},{\"id\":57,\"name\":\"张吉怀二分部\",\"parentid\":345,\"order\":2},{\"id\":51,\"name\":\"永广铁路\",\"parentid\":345,\"order\":3},{\"id\":47,\"name\":\"赣深客专江西段GSJXZQ-1标第二项目分部\",\"parentid\":345,\"order\":5},{\"id\":54,\"name\":\"玉磨铁路项目经理部\",\"parentid\":345,\"order\":4},{\"id\":82,\"name\":\"北京新机场线08标\",\"parentid\":345,\"order\":8},{\"id\":38,\"name\":\"郑万高铁7标三分部\",\"parentid\":345,\"order\":6},{\"id\":36,\"name\":\"郑万高铁7标二分部\",\"parentid\":345,\"order\":7},{\"id\":85,\"name\":\"太凤高速公路TF-05标\",\"parentid\":345,\"order\":9},{\"id\":73,\"name\":\"太原地铁206标\",\"parentid\":345,\"order\":10},{\"id\":67,\"name\":\"房北02标\",\"parentid\":345,\"order\":12},{\"id\":79,\"name\":\"山西中部引黄工程施工26标项目部\",\"parentid\":345,\"order\":11},{\"id\":64,\"name\":\"成都天府国际机场地基处理及土石方工程施工10标段\",\"parentid\":345,\"order\":13},{\"id\":76,\"name\":\"武汉地铁8号线3标\",\"parentid\":345,\"order\":14},{\"id\":61,\"name\":\"郑州地铁5号线02标\",\"parentid\":345,\"order\":15},{\"id\":70,\"name\":\"青岛地铁四号线1工区\",\"parentid\":345,\"order\":16},{\"id\":97,\"name\":\"S1-TS-06标\",\"parentid\":345,\"order\":18},{\"id\":92,\"name\":\"拉林铁路指挥部\",\"parentid\":345,\"order\":17},{\"id\":136,\"name\":\"成兰四项目部\",\"parentid\":345,\"order\":20},{\"id\":135,\"name\":\"成兰一项目部\",\"parentid\":345,\"order\":19},{\"id\":137,\"name\":\"成兰铁路工程指挥部\",\"parentid\":345,\"order\":21},{\"id\":130,\"name\":\"二公司深圳国会项目\",\"parentid\":345,\"order\":22},{\"id\":167,\"name\":\"云南滇中引水楚雄2标\",\"parentid\":345,\"order\":23},{\"id\":146,\"name\":\"京沈辽宁段\",\"parentid\":345,\"order\":24},{\"id\":96,\"name\":\"以色列特拉维夫红线轻轨东标段\",\"parentid\":345,\"order\":26},{\"id\":109,\"name\":\"京石客专\",\"parentid\":345,\"order\":25},{\"id\":102,\"name\":\"准朔四标\",\"parentid\":345,\"order\":28},{\"id\":106,\"name\":\"北京南水北调河西支线八标项目部\",\"parentid\":345,\"order\":29},{\"id\":99,\"name\":\"北京地铁16号线17合同段\",\"parentid\":345,\"order\":30},{\"id\":100,\"name\":\"北京地铁17号线15标项目部\",\"parentid\":345,\"order\":31},{\"id\":101,\"name\":\"北京地铁17号线22标项目部\",\"parentid\":345,\"order\":32},{\"id\":113,\"name\":\"北京地铁燕房线\",\"parentid\":345,\"order\":33},{\"id\":129,\"name\":\"厦门地铁1号线一标二工区项目部\",\"parentid\":345,\"order\":34},{\"id\":128,\"name\":\"厦门地铁1号线二标二工区项目部\",\"parentid\":345,\"order\":35},{\"id\":155,\"name\":\"厦门地铁3号线\",\"parentid\":345,\"order\":36},{\"id\":116,\"name\":\"合肥地铁3号线14标项目部\",\"parentid\":345,\"order\":38},{\"id\":125,\"name\":\"合肥管片厂\",\"parentid\":345,\"order\":39},{\"id\":160,\"name\":\"哈尔滨地铁项目经理部\",\"parentid\":345,\"order\":41},{\"id\":142,\"name\":\"大南高速公路项目部\",\"parentid\":345,\"order\":43},{\"id\":119,\"name\":\"坂雪岗通道\",\"parentid\":345,\"order\":42},{\"id\":162,\"name\":\"太原地铁首开段\",\"parentid\":345,\"order\":45},{\"id\":104,\"name\":\"大瑞铁路三分部\",\"parentid\":345,\"order\":44},{\"id\":110,\"name\":\"太原管片厂\",\"parentid\":345,\"order\":46},{\"id\":145,\"name\":\"夹岩水利北干渠2标\",\"parentid\":345,\"order\":48},{\"id\":108,\"name\":\"宁西铁路增建二线\",\"parentid\":345,\"order\":47},{\"id\":124,\"name\":\"富阳市政二标\",\"parentid\":345,\"order\":50},{\"id\":98,\"name\":\"宝坪高速项目部\",\"parentid\":345,\"order\":49},{\"id\":141,\"name\":\"广大铁路项目部\",\"parentid\":345,\"order\":53},{\"id\":151,\"name\":\"平兴公路项目部\",\"parentid\":345,\"order\":51},{\"id\":143,\"name\":\"广佛环城际项目部\",\"parentid\":345,\"order\":52},{\"id\":114,\"name\":\"引黄13标\",\"parentid\":345,\"order\":55},{\"id\":152,\"name\":\"广珠铁路项目部\",\"parentid\":345,\"order\":54},{\"id\":169,\"name\":\"张吉怀四分部\",\"parentid\":345,\"order\":56},{\"id\":170,\"name\":\"张吉怀隧专分部\",\"parentid\":345,\"order\":57},{\"id\":126,\"name\":\"怀邵衡铁路项目部\",\"parentid\":345,\"order\":59},{\"id\":164,\"name\":\"徐州地铁10标\",\"parentid\":345,\"order\":58},{\"id\":103,\"name\":\"明望达红进塔铁路\",\"parentid\":345,\"order\":60},{\"id\":138,\"name\":\"成兰铁路五部\",\"parentid\":345,\"order\":61},{\"id\":134,\"name\":\"成贵铁路三分部\",\"parentid\":345,\"order\":62},{\"id\":122,\"name\":\"成贵铁路第二项目部\",\"parentid\":345,\"order\":63},{\"id\":123,\"name\":\"成贵铁路第四项目部\",\"parentid\":345,\"order\":64},{\"id\":133,\"name\":\"成都天府国际机场飞行区场道工程03标段施工\",\"parentid\":345,\"order\":65},{\"id\":165,\"name\":\"扬州市政\",\"parentid\":345,\"order\":66},{\"id\":117,\"name\":\"昆楚高速公路土建TJ-1标第三分部\",\"parentid\":345,\"order\":69},{\"id\":144,\"name\":\"杭绍城际铁路1标\",\"parentid\":345,\"order\":71},{\"id\":132,\"name\":\"朝凌客专\",\"parentid\":345,\"order\":70},{\"id\":157,\"name\":\"深圳地铁10号线1012-3A标项目经理部\",\"parentid\":345,\"order\":73},{\"id\":159,\"name\":\"沈阳地铁第二项目经理部\",\"parentid\":345,\"order\":72},{\"id\":105,\"name\":\"滇中引水工程红河段进场道路施工1标\",\"parentid\":345,\"order\":74},{\"id\":158,\"name\":\"沈阳地铁项目经理部\",\"parentid\":345,\"order\":76},{\"id\":120,\"name\":\"紫惠路面\",\"parentid\":345,\"order\":75},{\"id\":121,\"name\":\"盾构施工分公司\",\"parentid\":345,\"order\":79},{\"id\":107,\"name\":\"沪昆云南段五分部\",\"parentid\":345,\"order\":77},{\"id\":161,\"name\":\"苏州地铁3号线17标\",\"parentid\":345,\"order\":81},{\"id\":139,\"name\":\"维管中心\",\"parentid\":345,\"order\":80},{\"id\":131,\"name\":\"硬梁包水电站ZI标\",\"parentid\":345,\"order\":82},{\"id\":179,\"name\":\"紫惠高速公路T5标项目部\",\"parentid\":345,\"order\":83},{\"id\":111,\"name\":\"维通公路五合同段\",\"parentid\":345,\"order\":84},{\"id\":112,\"name\":\"西安地铁\",\"parentid\":345,\"order\":87},{\"id\":149,\"name\":\"蒙华11标四工区\",\"parentid\":345,\"order\":85},{\"id\":150,\"name\":\"蒙华铁路MHSS-2标段\",\"parentid\":345,\"order\":86},{\"id\":163,\"name\":\"西成客专\",\"parentid\":345,\"order\":88},{\"id\":115,\"name\":\"郑州市政控制性节点04标\",\"parentid\":345,\"order\":89},{\"id\":177,\"name\":\"重庆地铁6号线\",\"parentid\":345,\"order\":91},{\"id\":176,\"name\":\"郑州市政配套项目部\",\"parentid\":345,\"order\":90},{\"id\":178,\"name\":\"重庆轨道十八号线土建八标\",\"parentid\":345,\"order\":93},{\"id\":154,\"name\":\"青岛新机场场道1标\",\"parentid\":345,\"order\":97},{\"id\":175,\"name\":\"长治机场项目部\",\"parentid\":345,\"order\":99},{\"id\":118,\"name\":\"郑州管廊二标段\",\"parentid\":345,\"order\":100},{\"id\":153,\"name\":\"青岛地铁1号线土建二标项目部\",\"parentid\":345,\"order\":101},{\"id\":346,\"name\":\"深圳16号线七工区\",\"parentid\":345,\"order\":9999},{\"id\":356,\"name\":\"南通管片厂\",\"parentid\":345,\"order\":9999},{\"id\":349,\"name\":\"银西铁路陕西段YXZQ-5标段\",\"parentid\":345,\"order\":9999},{\"id\":37,\"name\":\"项目领导\",\"parentid\":36,\"order\":9999},{\"id\":39,\"name\":\"项目领导\",\"parentid\":38,\"order\":9999},{\"id\":40,\"name\":\"计划合同部\",\"parentid\":38,\"order\":9999},{\"id\":42,\"name\":\"项目领导\",\"parentid\":41,\"order\":9999},{\"id\":43,\"name\":\"计划合同部\",\"parentid\":41,\"order\":9999},{\"id\":45,\"name\":\"项目领导\",\"parentid\":44,\"order\":9999},{\"id\":46,\"name\":\"计划合同部\",\"parentid\":44,\"order\":9999},{\"id\":48,\"name\":\"项目领导\",\"parentid\":47,\"order\":9999},{\"id\":49,\"name\":\"计划合同部\",\"parentid\":47,\"order\":9999},{\"id\":50,\"name\":\"计划合同部\",\"parentid\":36,\"order\":9999},{\"id\":52,\"name\":\"项目领导\",\"parentid\":51,\"order\":9999},{\"id\":53,\"name\":\"计划合同部\",\"parentid\":51,\"order\":9999},{\"id\":55,\"name\":\"项目领导\",\"parentid\":54,\"order\":9999},{\"id\":56,\"name\":\"计划合同部\",\"parentid\":54,\"order\":9999},{\"id\":58,\"name\":\"项目领导\",\"parentid\":57,\"order\":9999},{\"id\":59,\"name\":\"计划合同部\",\"parentid\":57,\"order\":9999},{\"id\":62,\"name\":\"项目领导\",\"parentid\":61,\"order\":9999},{\"id\":63,\"name\":\"计划合同部\",\"parentid\":61,\"order\":9999},{\"id\":65,\"name\":\"项目领导\",\"parentid\":64,\"order\":9999},{\"id\":66,\"name\":\"计划合同部\",\"parentid\":64,\"order\":9999},{\"id\":68,\"name\":\"项目领导\",\"parentid\":67,\"order\":9999},{\"id\":69,\"name\":\"计划合同部\",\"parentid\":67,\"order\":9999},{\"id\":71,\"name\":\"项目领导\",\"parentid\":70,\"order\":9999},{\"id\":72,\"name\":\"计划合同部\",\"parentid\":70,\"order\":9999},{\"id\":74,\"name\":\"项目领导\",\"parentid\":73,\"order\":9999},{\"id\":75,\"name\":\"计划合同部\",\"parentid\":73,\"order\":9999},{\"id\":77,\"name\":\"项目领导\",\"parentid\":76,\"order\":9999},{\"id\":78,\"name\":\"计划合同部\",\"parentid\":76,\"order\":9999},{\"id\":80,\"name\":\"项目领导\",\"parentid\":79,\"order\":9999},{\"id\":81,\"name\":\"计划合同部\",\"parentid\":79,\"order\":9999},{\"id\":83,\"name\":\"项目领导\",\"parentid\":82,\"order\":9999},{\"id\":84,\"name\":\"计划合同部\",\"parentid\":82,\"order\":9999},{\"id\":86,\"name\":\"项目领导\",\"parentid\":85,\"order\":9999},{\"id\":87,\"name\":\"计划合同部\",\"parentid\":85,\"order\":9999},{\"id\":93,\"name\":\"项目领导\",\"parentid\":92,\"order\":9999},{\"id\":94,\"name\":\"计划合同部\",\"parentid\":92,\"order\":9999},{\"id\":182,\"name\":\"项目领导\",\"parentid\":96,\"order\":999},{\"id\":183,\"name\":\"计划合同部\",\"parentid\":96,\"order\":999},{\"id\":184,\"name\":\"项目领导\",\"parentid\":97,\"order\":999},{\"id\":185,\"name\":\"计划合同部\",\"parentid\":97,\"order\":999},{\"id\":186,\"name\":\"项目领导\",\"parentid\":98,\"order\":999},{\"id\":187,\"name\":\"计划合同部\",\"parentid\":98,\"order\":999},{\"id\":188,\"name\":\"项目领导\",\"parentid\":99,\"order\":999},{\"id\":189,\"name\":\"计划合同部\",\"parentid\":99,\"order\":999},{\"id\":190,\"name\":\"项目领导\",\"parentid\":100,\"order\":999},{\"id\":191,\"name\":\"计划合同部\",\"parentid\":100,\"order\":999},{\"id\":192,\"name\":\"项目领导\",\"parentid\":101,\"order\":999},{\"id\":193,\"name\":\"计划合同部\",\"parentid\":101,\"order\":999},{\"id\":194,\"name\":\"项目领导\",\"parentid\":102,\"order\":999},{\"id\":195,\"name\":\"计划合同部\",\"parentid\":102,\"order\":999},{\"id\":196,\"name\":\"项目领导\",\"parentid\":103,\"order\":999},{\"id\":197,\"name\":\"计划合同部\",\"parentid\":103,\"order\":999},{\"id\":198,\"name\":\"项目领导\",\"parentid\":104,\"order\":999},{\"id\":199,\"name\":\"计划合同部\",\"parentid\":104,\"order\":999},{\"id\":200,\"name\":\"项目领导\",\"parentid\":105,\"order\":999},{\"id\":201,\"name\":\"计划合同部\",\"parentid\":105,\"order\":999},{\"id\":202,\"name\":\"项目领导\",\"parentid\":106,\"order\":999},{\"id\":203,\"name\":\"计划合同部\",\"parentid\":106,\"order\":999},{\"id\":204,\"name\":\"项目领导\",\"parentid\":107,\"order\":999},{\"id\":205,\"name\":\"计划合同部\",\"parentid\":107,\"order\":999},{\"id\":206,\"name\":\"项目领导\",\"parentid\":108,\"order\":999},{\"id\":207,\"name\":\"计划合同部\",\"parentid\":108,\"order\":999},{\"id\":208,\"name\":\"项目领导\",\"parentid\":109,\"order\":999},{\"id\":209,\"name\":\"计划合同部\",\"parentid\":109,\"order\":999},{\"id\":210,\"name\":\"项目领导\",\"parentid\":110,\"order\":999},{\"id\":211,\"name\":\"计划合同部\",\"parentid\":110,\"order\":999},{\"id\":212,\"name\":\"项目领导\",\"parentid\":111,\"order\":999},{\"id\":213,\"name\":\"计划合同部\",\"parentid\":111,\"order\":999},{\"id\":214,\"name\":\"项目领导\",\"parentid\":112,\"order\":999},{\"id\":215,\"name\":\"计划合同部\",\"parentid\":112,\"order\":999},{\"id\":216,\"name\":\"项目领导\",\"parentid\":113,\"order\":999},{\"id\":217,\"name\":\"计划合同部\",\"parentid\":113,\"order\":999},{\"id\":218,\"name\":\"项目领导\",\"parentid\":114,\"order\":999},{\"id\":219,\"name\":\"计划合同部\",\"parentid\":114,\"order\":999},{\"id\":220,\"name\":\"项目领导\",\"parentid\":115,\"order\":999},{\"id\":221,\"name\":\"计划合同部\",\"parentid\":115,\"order\":999},{\"id\":222,\"name\":\"项目领导\",\"parentid\":116,\"order\":999},{\"id\":223,\"name\":\"计划合同部\",\"parentid\":116,\"order\":999},{\"id\":224,\"name\":\"项目领导\",\"parentid\":117,\"order\":999},{\"id\":225,\"name\":\"计划合同部\",\"parentid\":117,\"order\":999},{\"id\":226,\"name\":\"项目领导\",\"parentid\":118,\"order\":999},{\"id\":227,\"name\":\"计划合同部\",\"parentid\":118,\"order\":999},{\"id\":228,\"name\":\"项目领导\",\"parentid\":119,\"order\":999},{\"id\":229,\"name\":\"计划合同部\",\"parentid\":119,\"order\":999},{\"id\":230,\"name\":\"项目领导\",\"parentid\":120,\"order\":999},{\"id\":231,\"name\":\"计划合同部\",\"parentid\":120,\"order\":999},{\"id\":232,\"name\":\"项目领导\",\"parentid\":121,\"order\":999},{\"id\":233,\"name\":\"计划合同部\",\"parentid\":121,\"order\":999},{\"id\":234,\"name\":\"项目领导\",\"parentid\":122,\"order\":999},{\"id\":235,\"name\":\"计划合同部\",\"parentid\":122,\"order\":999},{\"id\":236,\"name\":\"项目领导\",\"parentid\":123,\"order\":999},{\"id\":237,\"name\":\"计划合同部\",\"parentid\":123,\"order\":999},{\"id\":238,\"name\":\"项目领导\",\"parentid\":124,\"order\":999},{\"id\":239,\"name\":\"计划合同部\",\"parentid\":124,\"order\":999},{\"id\":240,\"name\":\"项目领导\",\"parentid\":125,\"order\":999},{\"id\":241,\"name\":\"计划合同部\",\"parentid\":125,\"order\":999},{\"id\":242,\"name\":\"计划合同部\",\"parentid\":126,\"order\":999},{\"id\":245,\"name\":\"项目领导\",\"parentid\":128,\"order\":999},{\"id\":246,\"name\":\"计划合同部\",\"parentid\":128,\"order\":999},{\"id\":247,\"name\":\"项目领导\",\"parentid\":129,\"order\":999},{\"id\":249,\"name\":\"项目领导\",\"parentid\":130,\"order\":999},{\"id\":248,\"name\":\"计划合同部\",\"parentid\":129,\"order\":999},{\"id\":250,\"name\":\"计划合同部\",\"parentid\":130,\"order\":999},{\"id\":251,\"name\":\"项目领导\",\"parentid\":131,\"order\":999},{\"id\":252,\"name\":\"计划合同部\",\"parentid\":131,\"order\":999},{\"id\":253,\"name\":\"项目领导\",\"parentid\":132,\"order\":999},{\"id\":254,\"name\":\"计划合同部\",\"parentid\":132,\"order\":999},{\"id\":255,\"name\":\"项目领导\",\"parentid\":133,\"order\":999},{\"id\":256,\"name\":\"计划合同部\",\"parentid\":133,\"order\":999},{\"id\":257,\"name\":\"项目领导\",\"parentid\":134,\"order\":999},{\"id\":258,\"name\":\"计划合同部\",\"parentid\":134,\"order\":999},{\"id\":259,\"name\":\"项目领导\",\"parentid\":135,\"order\":999},{\"id\":260,\"name\":\"计划合同部\",\"parentid\":135,\"order\":999},{\"id\":261,\"name\":\"项目领导\",\"parentid\":136,\"order\":999},{\"id\":262,\"name\":\"计划合同部\",\"parentid\":136,\"order\":999},{\"id\":263,\"name\":\"项目领导\",\"parentid\":137,\"order\":999},{\"id\":264,\"name\":\"计划合同部\",\"parentid\":137,\"order\":999},{\"id\":265,\"name\":\"项目领导\",\"parentid\":138,\"order\":999},{\"id\":266,\"name\":\"计划合同部\",\"parentid\":138,\"order\":999},{\"id\":267,\"name\":\"项目领导\",\"parentid\":139,\"order\":999},{\"id\":268,\"name\":\"计划合同部\",\"parentid\":139,\"order\":999},{\"id\":271,\"name\":\"项目领导\",\"parentid\":141,\"order\":999},{\"id\":272,\"name\":\"计划合同部\",\"parentid\":141,\"order\":999},{\"id\":273,\"name\":\"项目领导\",\"parentid\":142,\"order\":999},{\"id\":274,\"name\":\"计划合同部\",\"parentid\":142,\"order\":999},{\"id\":275,\"name\":\"项目领导\",\"parentid\":143,\"order\":999},{\"id\":276,\"name\":\"计划合同部\",\"parentid\":143,\"order\":999},{\"id\":277,\"name\":\"项目领导\",\"parentid\":144,\"order\":999},{\"id\":278,\"name\":\"计划合同部\",\"parentid\":144,\"order\":999},{\"id\":279,\"name\":\"项目领导\",\"parentid\":145,\"order\":999},{\"id\":280,\"name\":\"计划合同部\",\"parentid\":145,\"order\":999},{\"id\":281,\"name\":\"项目领导\",\"parentid\":146,\"order\":999},{\"id\":282,\"name\":\"计划合同部\",\"parentid\":146,\"order\":999},{\"id\":287,\"name\":\"项目领导\",\"parentid\":149,\"order\":999},{\"id\":288,\"name\":\"计划合同部\",\"parentid\":149,\"order\":999},{\"id\":289,\"name\":\"项目领导\",\"parentid\":150,\"order\":999},{\"id\":290,\"name\":\"计划合同部\",\"parentid\":150,\"order\":999},{\"id\":291,\"name\":\"项目领导\",\"parentid\":151,\"order\":999},{\"id\":292,\"name\":\"计划合同部\",\"parentid\":151,\"order\":999},{\"id\":293,\"name\":\"项目领导\",\"parentid\":152,\"order\":999},{\"id\":294,\"name\":\"计划合同部\",\"parentid\":152,\"order\":999},{\"id\":295,\"name\":\"项目领导\",\"parentid\":153,\"order\":999},{\"id\":296,\"name\":\"计划合同部\",\"parentid\":153,\"order\":999},{\"id\":297,\"name\":\"项目领导\",\"parentid\":154,\"order\":999},{\"id\":298,\"name\":\"计划合同部\",\"parentid\":154,\"order\":999},{\"id\":299,\"name\":\"项目领导\",\"parentid\":155,\"order\":999},{\"id\":300,\"name\":\"计划合同部\",\"parentid\":155,\"order\":999},{\"id\":303,\"name\":\"项目领导\",\"parentid\":157,\"order\":999},{\"id\":304,\"name\":\"计划合同部\",\"parentid\":157,\"order\":999},{\"id\":306,\"name\":\"计划合同部\",\"parentid\":158,\"order\":999},{\"id\":307,\"name\":\"项目领导\",\"parentid\":159,\"order\":999},{\"id\":308,\"name\":\"计划合同部\",\"parentid\":159,\"order\":999},{\"id\":309,\"name\":\"项目领导\",\"parentid\":160,\"order\":999},{\"id\":310,\"name\":\"计划合同部\",\"parentid\":160,\"order\":999},{\"id\":311,\"name\":\"项目领导\",\"parentid\":161,\"order\":999},{\"id\":312,\"name\":\"计划合同部\",\"parentid\":161,\"order\":999},{\"id\":313,\"name\":\"项目领导\",\"parentid\":162,\"order\":999},{\"id\":314,\"name\":\"计划合同部\",\"parentid\":162,\"order\":999},{\"id\":315,\"name\":\"项目领导\",\"parentid\":163,\"order\":999},{\"id\":316,\"name\":\"计划合同部\",\"parentid\":163,\"order\":999},{\"id\":317,\"name\":\"项目领导\",\"parentid\":164,\"order\":999},{\"id\":318,\"name\":\"计划合同部\",\"parentid\":164,\"order\":999},{\"id\":319,\"name\":\"项目领导\",\"parentid\":165,\"order\":999},{\"id\":320,\"name\":\"计划合同部\",\"parentid\":165,\"order\":999},{\"id\":323,\"name\":\"项目领导\",\"parentid\":167,\"order\":999},{\"id\":324,\"name\":\"计划合同部\",\"parentid\":167,\"order\":999},{\"id\":327,\"name\":\"项目领导\",\"parentid\":169,\"order\":999},{\"id\":328,\"name\":\"计划合同部\",\"parentid\":169,\"order\":999},{\"id\":329,\"name\":\"项目领导\",\"parentid\":170,\"order\":999},{\"id\":330,\"name\":\"计划合同部\",\"parentid\":170,\"order\":999},{\"id\":335,\"name\":\"项目领导\",\"parentid\":175,\"order\":999},{\"id\":336,\"name\":\"计划合同部\",\"parentid\":175,\"order\":999},{\"id\":337,\"name\":\"项目领导\",\"parentid\":176,\"order\":999},{\"id\":338,\"name\":\"计划合同部\",\"parentid\":176,\"order\":999},{\"id\":339,\"name\":\"项目领导\",\"parentid\":177,\"order\":999},{\"id\":340,\"name\":\"计划合同部\",\"parentid\":177,\"order\":999},{\"id\":341,\"name\":\"项目领导\",\"parentid\":178,\"order\":999},{\"id\":342,\"name\":\"计划合同部\",\"parentid\":178,\"order\":999},{\"id\":343,\"name\":\"项目领导\",\"parentid\":179,\"order\":999},{\"id\":344,\"name\":\"计划合同部\",\"parentid\":179,\"order\":999},{\"id\":347,\"name\":\"计划合同部\",\"parentid\":346,\"order\":9999},{\"id\":348,\"name\":\"项目领导\",\"parentid\":346,\"order\":9999},{\"id\":350,\"name\":\"计划合同部\",\"parentid\":349,\"order\":9999},{\"id\":351,\"name\":\"项目领导\",\"parentid\":349,\"order\":9999},{\"id\":357,\"name\":\"计划合同部\",\"parentid\":356,\"order\":9999},{\"id\":358,\"name\":\"项目领导\",\"parentid\":356,\"order\":9999},{\"id\":417,\"name\":\"厦门地铁翔安机场站\",\"parentid\":345,\"order\":9999},{\"id\":418,\"name\":\"计划合同部\",\"parentid\":417,\"order\":9999},{\"id\":419,\"name\":\"项目领导\",\"parentid\":417,\"order\":9999},{\"id\":420,\"name\":\"苏州Ⅶ-TS-07标\",\"parentid\":345,\"order\":9999},{\"id\":421,\"name\":\"项目领导\",\"parentid\":420,\"order\":9999},{\"id\":422,\"name\":\"计划合同部\",\"parentid\":420,\"order\":9999},{\"id\":407,\"name\":\"通州08地块\",\"parentid\":345,\"order\":9999},{\"id\":408,\"name\":\"计划合同部\",\"parentid\":407,\"order\":9999},{\"id\":409,\"name\":\"项目领导\",\"parentid\":407,\"order\":9999},{\"id\":377,\"name\":\"京沈京冀客专Ⅱ标段\",\"parentid\":345,\"order\":0},{\"id\":378,\"name\":\"计划合同部\",\"parentid\":377,\"order\":100000000},{\"id\":379,\"name\":\"项目领导\",\"parentid\":377,\"order\":99999000},{\"id\":423,\"name\":\"乐清EPC\",\"parentid\":345,\"order\":9999},{\"id\":424,\"name\":\"项目领导\",\"parentid\":423,\"order\":9999},{\"id\":425,\"name\":\"计划合同部\",\"parentid\":423,\"order\":9999},{\"id\":426,\"name\":\"南京地铁9号线\",\"parentid\":345,\"order\":9999},{\"id\":427,\"name\":\"计划合同部\",\"parentid\":426,\"order\":9999},{\"id\":428,\"name\":\"项目领导\",\"parentid\":426,\"order\":9999},{\"id\":429,\"name\":\"德贡公路孔雀山隧道工程\",\"parentid\":345,\"order\":9999},{\"id\":430,\"name\":\"计划合同部\",\"parentid\":429,\"order\":9999},{\"id\":431,\"name\":\"项目领导\",\"parentid\":429,\"order\":9999},{\"id\":388,\"name\":\"新香维二合同段\",\"parentid\":345,\"order\":0},{\"id\":389,\"name\":\"计划合同部\",\"parentid\":388,\"order\":100000000},{\"id\":390,\"name\":\"项目领导\",\"parentid\":388,\"order\":99999000},{\"id\":432,\"name\":\"成自高铁二分部\",\"parentid\":345,\"order\":9999},{\"id\":433,\"name\":\"计划合同部\",\"parentid\":432,\"order\":9999},{\"id\":434,\"name\":\"项目领导\",\"parentid\":432,\"order\":9999},{\"id\":435,\"name\":\"文山阿卡公路工程\",\"parentid\":345,\"order\":9999},{\"id\":436,\"name\":\"计划合同部\",\"parentid\":435,\"order\":9999},{\"id\":437,\"name\":\"项目领导\",\"parentid\":435,\"order\":9999},{\"id\":438,\"name\":\"31044工程项目部\",\"parentid\":345,\"order\":9999},{\"id\":439,\"name\":\"计划合同部\",\"parentid\":438,\"order\":9999},{\"id\":440,\"name\":\"项目领导\",\"parentid\":438,\"order\":9999},{\"id\":360,\"name\":\"北京地铁6号线二期21标\",\"parentid\":345,\"order\":88},{\"id\":359,\"name\":\"青岛地铁2号线一期二标07\",\"parentid\":345,\"order\":92},{\"id\":404,\"name\":\"9441工程项目部\",\"parentid\":345,\"order\":9999},{\"id\":394,\"name\":\"准朔二标\",\"parentid\":345,\"order\":9999},{\"id\":401,\"name\":\"呼和浩特新机场临时道路项目\",\"parentid\":345,\"order\":9999},{\"id\":391,\"name\":\"张唐铁路第二项目部\",\"parentid\":345,\"order\":9999},{\"id\":368,\"name\":\"小磨公路改扩建11标\",\"parentid\":345,\"order\":9999},{\"id\":414,\"name\":\"沈阳辽西北供水配套施工三标\",\"parentid\":345,\"order\":9999},{\"id\":371,\"name\":\"天龙山道路工程二项目部\",\"parentid\":345,\"order\":9999},{\"id\":365,\"name\":\"太古供热隧道工程项目部\",\"parentid\":345,\"order\":9999},{\"id\":398,\"name\":\"湖杭铁路三分部\",\"parentid\":345,\"order\":9999},{\"id\":396,\"name\":\"芜湖长江公路二桥E-2标\",\"parentid\":345,\"order\":9999},{\"id\":369,\"name\":\"计划合同部\",\"parentid\":368,\"order\":9999},{\"id\":366,\"name\":\"计划合同部\",\"parentid\":365,\"order\":9999},{\"id\":363,\"name\":\"计划合同部\",\"parentid\":360,\"order\":9999},{\"id\":372,\"name\":\"计划合同部\",\"parentid\":371,\"order\":9999},{\"id\":392,\"name\":\"计划合同部\",\"parentid\":391,\"order\":9999},{\"id\":400,\"name\":\"计划合同部\",\"parentid\":398,\"order\":9999},{\"id\":395,\"name\":\"计划合同部\",\"parentid\":394,\"order\":9999},{\"id\":403,\"name\":\"计划合同部\",\"parentid\":401,\"order\":9999},{\"id\":406,\"name\":\"计划合同部\",\"parentid\":404,\"order\":9999},{\"id\":415,\"name\":\"计划合同部\",\"parentid\":414,\"order\":9999},{\"id\":374,\"name\":\"重庆地铁四号线\",\"parentid\":345,\"order\":9999},{\"id\":386,\"name\":\"雁门关隧道\",\"parentid\":345,\"order\":9999},{\"id\":364,\"name\":\"项目领导\",\"parentid\":360,\"order\":9999},{\"id\":370,\"name\":\"项目领导\",\"parentid\":368,\"order\":9999},{\"id\":367,\"name\":\"项目领导\",\"parentid\":365,\"order\":9999},{\"id\":373,\"name\":\"项目领导\",\"parentid\":371,\"order\":9999},{\"id\":376,\"name\":\"项目领导\",\"parentid\":374,\"order\":9999},{\"id\":393,\"name\":\"项目领导\",\"parentid\":391,\"order\":9999},{\"id\":397,\"name\":\"项目领导\",\"parentid\":396,\"order\":9999},{\"id\":399,\"name\":\"项目领导\",\"parentid\":398,\"order\":9999},{\"id\":402,\"name\":\"项目领导\",\"parentid\":401,\"order\":9999},{\"id\":405,\"name\":\"项目领导\",\"parentid\":404,\"order\":9999},{\"id\":416,\"name\":\"项目领导\",\"parentid\":414,\"order\":9999},{\"id\":380,\"name\":\"麻昭公路B3工区\",\"parentid\":345,\"order\":9999},{\"id\":362,\"name\":\"计划合同部\",\"parentid\":359,\"order\":10000},{\"id\":361,\"name\":\"项目领导\",\"parentid\":359,\"order\":10001},{\"id\":375,\"name\":\"计划合同部\",\"parentid\":374,\"order\":9999},{\"id\":387,\"name\":\"计划合同部\",\"parentid\":386,\"order\":9999},{\"id\":381,\"name\":\"计划合同部\",\"parentid\":380,\"order\":9999},{\"id\":382,\"name\":\"项目领导\",\"parentid\":380,\"order\":9999}]}";
//			if(str.indexOf(sysDepartmentDb.getOtherId())>=0) {
//			    continue;
//			}
			if(StrUtil.equals("0", sysDepartmentDb.getDepartmentParentId())) {
			    continue;
			}
		    // 创建部门，如果部门创建失败则去更新处理
			DepartmentInfoReq departmentInfoReq = new DepartmentInfoReq();
			departmentInfoReq.setId(sysDepartmentDb.getOtherId());
//			departmentInfoReq.setId(String.valueOf(sequenceService.getSequenceNextval("wechat_department_id")));
			SysDepartment sysDepartmentPid = sysDepartmentMapper.selectByPrimaryKey(sysDepartmentDb.getDepartmentParentId());
			departmentInfoReq.setParentid(sysDepartmentPid.getOtherId());
			if(sysDepartmentDb.getDepartmentName().length()>31) {
			    departmentInfoReq.setName(sysDepartmentDb.getDepartmentName().substring(0, 31));
			} else {
			    departmentInfoReq.setName(sysDepartmentDb.getDepartmentName());
			}
			departmentInfoReq.setOrder(String.valueOf(sysDepartmentDb.getSort()));
			JSONObject jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(2, getParamMap, departmentInfoReq);
			if(jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))){
				// 创建失败则去更新
//				jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(3, getParamMap, departmentInfoReq);
//				if(jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))){
//					return repEntity.layerMessage("no", jsonObject.getStr("errmsg") + "(" + jsonObject.getStr("errcode") + ")");
//				}
			}
			
			// 同步到腾讯：创建成员
			SysUser sysUserSelect = new SysUser();
			sysUserSelect.setDepartmentId(sysDepartmentDb.getDepartmentId());
			List<SysUser> sysUserList = sysUserMapper.selectBySysUserListByDepartment(sysUserSelect);
			for(SysUser sysUserDb:sysUserList){
				// 将部门ID整理到list
				List sysDepartmentIdList = Lists.newArrayList();
				sysDepartmentSelect = new SysDepartment();
				sysDepartmentSelect.setUserKey(sysUserDb.getUserKey());
				sysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(sysDepartmentSelect);
				for(SysDepartment sysDepartmentByUser:sysDepartmentList){
					sysDepartmentIdList.add(sysDepartmentByUser.getOtherId());
				}

				// 微信用户信息同步
				UserInfoReq userInfoReq = new UserInfoReq();
				userInfoReq.setUserid(sysUserDb.getUserId());
				userInfoReq.setName(sysUserDb.getRealName());
				userInfoReq.setEnable(1);
				userInfoReq.setDepartment(sysDepartmentIdList);
				userInfoReq.setMobile(sysUserDb.getMobile());
				// 更新部门下的所有人员
				jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(6, getParamMap, userInfoReq);
				if(jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))){
					jsonObject = wechatEnterpriseService.coreServiceByResurceAddressbook(7, getParamMap, userInfoReq);
					if(jsonObject == null || !StrUtil.equals("0", jsonObject.getStr("errcode"))){
//						return repEntity.layerMessage("no", jsonObject.getStr("errmsg") + "(" + jsonObject.getStr("errcode") + ")");
					}
				}
			}
		}
		return repEntity.layerMessage("ok", "成功完成系统部门数据同步到微信！");
	}
	
	/**
     * 从其他系统到本系统（部门、用户）
     * 
     * @return 部门列表
     */
    @Override
    public ResponseEntity syncOtherOaToSysInfo() {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String oaUserId = TokenUtils.getUserId(request);
        String companyId = TokenUtils.getCompanyId(request);
        String companyUrl = TokenUtils.getCompanyUrl(request);
        if (!Apih5Properties.isUseEhCache()) {
            SysUser dbSysUser = sysUserMapper.selectByPrimaryKey(userKey);
            oaUserId = dbSysUser.getUserId();
        }

        // 2、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
        CachData.setInterfaseUrl(oaUserId, companyUrl);
        CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);

        // 3、获取OA系统安全认证的token
        CachData.getAcceeToken(oaUserId);
        ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);
        
        // 德余、重庆部门获取
        if(true) {
            // 4、获取OA系统部门信息
            String url = CachData.oaInterfaceMap.get(oaUserId).getGET_ORG_BY_PARENT().replace("METHOD", "getOrgByParent")
                    .replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
                    .replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("PARENT_BSID", "00001");
            String jsonStr = HttpUtil.sendGet(url);
            
            List<OADepartment> listDepartment = new ArrayList<OADepartment>();
            JSONObject json = new JSONObject(jsonStr);
            if (json.get("data") != null && !json.get("data").equals("")) {
                JSONArray dataArray = (JSONArray) json.get("data");
                // List list = (List) JSONArray.toCollection(dataArray, Map.class);
                List list = (List) dataArray;
                for (int i = 0; i < list.size(); i++) {
                    Map accessMap = (Map) list.get(i);
                    OADepartment department = new OADepartment();
                    department.setId(accessMap.get("BSID").toString());
                    department.setParentid(accessMap.get("parentBSID").toString());
                    department.setName(accessMap.get("orgName").toString());
                    department.setOrgId(accessMap.get("orgID").toString());
                    department.setOrder(i);
                    listDepartment.add(department);
                }
            }
            
            int i=1;
            for(OADepartment OADepartment:listDepartment) {
                SysDepartment dbSysDepartment = sysDepartmentMapper.selectByPrimaryKey(OADepartment.getOrgId());
                if(dbSysDepartment != null) {
                    continue;
                }
                SysDepartment sysDepartment = new SysDepartment();
                sysDepartment.setDepartmentId(OADepartment.getOrgId());
                sysDepartment.setDepartmentParentId(getOADepartment(listDepartment, OADepartment.getParentid()));
                sysDepartment.setDepartmentName(OADepartment.getName());
                // 层级获取
                LoggerUtils.printLogger(logger, "syncOtherOaToSysInfo==+ 部门pid获取==" +sysDepartment.getDepartmentId()+ "   "+sysDepartment.getDepartmentParentId());
                SysDepartment sysDepartmentParent = sysDepartmentMapper.selectByPrimaryKey(sysDepartment.getDepartmentParentId());
                sysDepartment.setDepartmentPath(sysDepartmentParent.getDepartmentPath()+","+OADepartment.getOrgId());
                sysDepartment.setDepartmentPathName(sysDepartmentParent.getDepartmentPathName()+","+OADepartment.getName());
                sysDepartment.setOtherId(String.valueOf(sequenceService.getSequenceNextval("wechat_department_id")));
                sysDepartment.setSort(i);
                sysDepartment.setCreateUserInfo("拉取导入", "拉取导入");
                i++;
                try {
                    sysDepartmentMapper.insert(sysDepartment);
                    // 水利公司的是时候更新到微信端，其余情况注释
                    syncSysInfoToWeChat(sysDepartment);
                }catch (Exception e) {
                }
                
            }
        }
        
        // 德余、重庆人员获取
        if(true) {
            SysDepartment sysDepartmentSelect = new SysDepartment();
            sysDepartmentSelect.setDepartmentPath(companyId);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
            for(SysDepartment sysDepartment:sysDepartmentList) {
                String url = CachData.oaInterfaceMap.get(oaUserId).getGET_USER_BY_ORG().replace("METHOD", "getUserByOrg")
                        .replace("USERID", CachData.oaInterfaceUserIdMap.get(oaUserId))
                        .replace("ACCESSCODE", accessCodeBean.getAccessCode()).replace("ORG_ID", sysDepartment.getDepartmentId());
                String jsonStr = HttpUtil.sendGet(url);
//                LoggerUtils.printLogger(logger, "oa--人员获取-----"+url);
                List<OAMember> listMember = new ArrayList<OAMember>();
                JSONObject json = new JSONObject(jsonStr);// .fromObject(jsonStr);
                if (json.get("data") != null && !json.get("data").equals("")) {
                    JSONArray dataArray = (JSONArray) json.get("data");
//                    LoggerUtils.printLogger(logger, "oa--人员获取----====-"+dataArray.toString());
                    List list = (List) dataArray;
                    for (int i = 0; i < list.size(); i++) {
                        Map accessMap = (Map) list.get(i);
                        SysUser sysUser = new SysUser();
                        sysUser.setUserId(accessMap.get("userAccount").toString());
                        sysUser.setRealName(accessMap.get("userName").toString());
                        sysUser.setMobile(accessMap.get("phone").toString());
                        if(StrUtil.isNotEmpty(sysUser.getMobile())) {
                            sysUser.setSort(i);
                            JSONArray jsonArray = new JSONArray();
                            JSONObject jsonObject = new JSONObject();
                            jsonObject.set("value", sysDepartment.getDepartmentId());
                            jsonObject.set("label", sysDepartment.getDepartmentName());
                            jsonObject.set("valuePid", sysDepartment.getDepartmentParentId());
                            jsonArray.add(jsonObject);
                            sysUser.setSysDepartmentList(jsonArray);
                            ResponseEntity responseEntity = null;
                            try {
                                sysUser.setUserStatus("1");
                                sysUser.setUserType("1");
                                
                                responseEntity = userService.addSysUserInfoByBg(sysUser);
                                // 微信手机号如果存在，则只更新本库，不更新微信后台（60104：手机号码已存在  ）
                                if(responseEntity.getMessage().indexOf("60104")>=0) {
                                    sysUser.setUseSyncWeChatPar(false);// 不同步微信
                                    responseEntity = userService.addSysUserInfoByBg(sysUser);
                                }
                            } catch (Exception e) {
                                // 微信手机号如果存在，则只更新本库，不更新微信后台（60104：手机号码已存在  ）
                                if(e.getMessage().indexOf("60104")>=0) {
                                    try {
                                        sysUser.setUseSyncWeChatPar(false);// 不同步微信
                                        responseEntity = userService.addSysUserInfoByBg(sysUser);
                                    } catch (Exception e1) {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
       
        return repEntity.ok("");
    }
    
	private String getOADepartment(List<OADepartment> listDepartment, String parentBSID){
	    for(OADepartment OADepartment:listDepartment) {
	        if(StrUtil.equals(OADepartment.getId(), parentBSID)) {
	            return OADepartment.getOrgId();
	        }
        }
	    return "";
	}
}

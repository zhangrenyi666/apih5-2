package com.apih5.service.impl;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.entity.SysUserWebEntity;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.constant.ConfigConst;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysWoaHintInformationMapper;
import com.apih5.mybatis.dao.SysWoaLargeModuleMapper;
import com.apih5.mybatis.dao.SysWoaRoleMapper;
import com.apih5.mybatis.dao.SysWoaSmallModuleMapper;
import com.apih5.mybatis.pojo.SysMenu;
import com.apih5.mybatis.pojo.SysWoaHintInformation;
import com.apih5.mybatis.pojo.SysWoaLargeModule;
import com.apih5.mybatis.pojo.SysWoaRole;
import com.apih5.mybatis.pojo.SysWoaSmallModule;
import com.apih5.service.SysWoaLargeModuleService;
import com.apih5.utils.ApkInfoUtil;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;

@Service("sysWoaLargeModuleService")
public class SysWoaLargeModuleServiceImpl implements SysWoaLargeModuleService {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	public static Map<String, List<SysWoaLargeModule>> cacheMapSysWoaLargeModule = new HashMap<String, List<SysWoaLargeModule>>();
	
    @Autowired(required = true)
    private ResponseEntity repEntity;
	@ApolloConfig(ConfigConst.PUBLIC_OTHER_API)
	private Config publicConfig;
	
    @Autowired(required = true)
    private RequestHolderConfiguration requestHolderConfiguration;

    @Autowired(required = true)
    private SysWoaLargeModuleMapper sysWoaLargeModuleMapper;
    
    @Autowired(required = true)
    private SysWoaHintInformationMapper sysWoaHintInformationMapper;
    @Autowired(required = true)
    private SysWoaSmallModuleMapper sysWoaSmallModuleMapper;
    @Autowired(required = true)
    private SysWoaRoleMapper sysWoaRoleMapper;
    @Autowired(required = true)
    private SysDepartmentService sysDepartmentService;

    @Override
    public ResponseEntity getSysWoaLargeModuleListByCondition(SysWoaLargeModule sysWoaLargeModule) {
        if (sysWoaLargeModule == null) {
            sysWoaLargeModule = new SysWoaLargeModule();
        }
        // ????????????
        PageHelper.startPage(sysWoaLargeModule.getPage(),sysWoaLargeModule.getLimit());
        // ????????????
        List<SysWoaLargeModule> sysWoaLargeModuleList = sysWoaLargeModuleMapper.selectBySysWoaLargeModuleList(sysWoaLargeModule);
        // ??????????????????
        PageInfo<SysWoaLargeModule> p = new PageInfo<>(sysWoaLargeModuleList);

        return repEntity.okList(sysWoaLargeModuleList, p.getTotal());
    }

    @Override
    public ResponseEntity saveSysWoaLargeModule(SysWoaLargeModule sysWoaLargeModule) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        sysWoaLargeModule.setLargeModuleId(UuidUtil.generate());
        sysWoaLargeModule.setCreateUserInfo(userKey, realName);
        int flag = sysWoaLargeModuleMapper.insert(sysWoaLargeModule);
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.sava", sysWoaLargeModule);
        }
    }

    @Override
    public ResponseEntity updateSysWoaLargeModule(SysWoaLargeModule sysWoaLargeModule) {
        HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        String userKey = TokenUtils.getUserKey(request);
        String realName = TokenUtils.getRealName(request);
        int flag = 0;
        SysWoaLargeModule dbsysWoaLargeModule = sysWoaLargeModuleMapper.selectByPrimaryKey(sysWoaLargeModule.getLargeModuleId());
        if (dbsysWoaLargeModule != null && StrUtil.isNotEmpty(dbsysWoaLargeModule.getLargeModuleId())) {
           // ??????ID
           dbsysWoaLargeModule.setRoleId(sysWoaLargeModule.getRoleId());
           // ????????????
           dbsysWoaLargeModule.setRoleName(sysWoaLargeModule.getRoleName());
           // ??????????????????
           dbsysWoaLargeModule.setLargeModuleType(sysWoaLargeModule.getLargeModuleType());
           // ???????????????
           dbsysWoaLargeModule.setLargeModuleTitle(sysWoaLargeModule.getLargeModuleTitle());
           // ??????
           dbsysWoaLargeModule.setLargeModuleSort(sysWoaLargeModule.getLargeModuleSort());
           // ??????
           dbsysWoaLargeModule.setModifyUserInfo(userKey, realName);
           flag = sysWoaLargeModuleMapper.updateByPrimaryKey(dbsysWoaLargeModule);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.update",sysWoaLargeModule);
        }
    }

    @Override
    public ResponseEntity batchDeleteUpdateSysWoaLargeModule(List<SysWoaLargeModule> sysWoaLargeModuleList) {
        int flag = 0;
        if (sysWoaLargeModuleList != null && sysWoaLargeModuleList.size() > 0) {
           flag = sysWoaLargeModuleMapper.batchDeleteUpdateSysWoaLargeModule(sysWoaLargeModuleList);
        }
        // ??????
        if (flag == 0) {
            return repEntity.errorSave();
        }
        else {
            return repEntity.ok("sys.data.delete",sysWoaLargeModuleList);
        }
    }
	
	@Override
	public ResponseEntity getSysMobilIndex(SysWoaLargeModule sysWoaLargeModule) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
        if (sysWoaLargeModule == null) {
        	sysWoaLargeModule = new SysWoaLargeModule();
        }
        
        JSONObject returnJsonObject = new JSONObject();
        String token = TokenUtils.getToken(request);
//        String userKey = TokenUtils.getUserKey(request);
//        String companyId = TokenUtils.getCompanyId(request);
        JSONObject sysUserWebEntityJSONObject = (JSONObject)TokenUtils.getSysUserWebObject(token);
//        SysUserWebEntity sysUserWebEntity = (SysUserWebEntity)Object;
        
        // ?????????sql???in??????
        String sqlIn = "";
        // ??????????????????????????????Id???userKey
        List<String> permissionList = TokenUtils.getPermissionIds(request);
        for (int i=0; i<permissionList.size(); i++) {
            String departmentId = permissionList.get(i);
            SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(departmentId);
            if(sysDepartment != null) {
                String[] sysDepartments = sysDepartment.getDepartmentPath().split(",");
                for(String sysDepartmentId:sysDepartments) {
                    sqlIn += "'" + sysDepartmentId + "',";
                }
            } else {
                sqlIn += "'" + departmentId + "',";
            }
        }
        sqlIn = sqlIn.substring(0, sqlIn.length() - 1);
        
        String roleId = "?????????";
		SysWoaRole sysWoaRole = new SysWoaRole();
		sysWoaRole.setUserKeys(sqlIn);
        List<SysWoaRole> sysWoaRoleList = sysWoaRoleMapper.selectBySysWoaRoleListInUserKeys(sysWoaRole);
        if(sysWoaRoleList != null && sysWoaRoleList.size()>0) {
            // ????????????????????????????????????????????????
        	roleId = sysWoaRoleList.get(0).getRoleId();
        } else {
        	return repEntity.layerMessage("no", "??????????????????????????????????????????");
        }
        cacheMapSysWoaLargeModule = new HashMap<String, List<SysWoaLargeModule>>();
		// 1?????????
		if(cacheMapSysWoaLargeModule != null && cacheMapSysWoaLargeModule.size() > 0
				&& cacheMapSysWoaLargeModule.get("sysWoaLargeModule" + roleId) != null 
				&& cacheMapSysWoaLargeModule.get("sysWoaLargeModule" + roleId).size() > 0) {
			List<SysWoaLargeModule> sysWoaLargeModuleList = cacheMapSysWoaLargeModule.get("sysWoaLargeModule" + roleId);
			List<SysWoaLargeModule> newSysWoaLargeModuleList = Lists.newArrayList(); 
			for(SysWoaLargeModule dbSysWoaLargeModule:sysWoaLargeModuleList) {
				// 5:????????????
				if(StrUtil.equals("5", dbSysWoaLargeModule.getLargeModuleType())) {
					// ??????????????????
                    SysWoaHintInformation sysWoaHintInformation = new SysWoaHintInformation();
                    DateTime nowDate= DateTime.now();
                    sysWoaHintInformation.setStartTime(nowDate);
                    sysWoaHintInformation.setEndTime(nowDate);
                    List<SysWoaHintInformation> sysWoaHintInformationList = sysWoaHintInformationMapper.selectBySysWoaHintInformationListByTime(sysWoaHintInformation);
                    dbSysWoaLargeModule.setSysWoaSmallModuleList(sysWoaHintInformationList);
				} 
				newSysWoaLargeModuleList.add(dbSysWoaLargeModule);
			}
			returnJsonObject.set("homeLargeModuleList", newSysWoaLargeModuleList);
		} else {
			SysWoaLargeModule sysWoaLargeModuleSelect = new SysWoaLargeModule();
			sysWoaLargeModuleSelect.setRoleId(roleId);
			List<SysWoaLargeModule> sysWoaLargeModuleList = sysWoaLargeModuleMapper.selectBySysWoaLargeModuleList(sysWoaLargeModuleSelect);
			List<SysWoaLargeModule> newSysWoaLargeModuleList = Lists.newArrayList();
			for(SysWoaLargeModule dbSysWoaLargeModule:sysWoaLargeModuleList) {
				SysWoaSmallModule sysWoaSmallModule = new SysWoaSmallModule();
				sysWoaSmallModule.setLargelModuleId(dbSysWoaLargeModule.getLargeModuleId());
				// 5:????????????
				if(StrUtil.equals("5", dbSysWoaLargeModule.getLargeModuleType())) {
					// ??????????????????
					SysWoaHintInformation sysWoaHintInformation = new SysWoaHintInformation();
					DateTime nowDate= DateTime.now();
					sysWoaHintInformation.setStartTime(nowDate);
					sysWoaHintInformation.setEndTime(nowDate);
					List<SysWoaHintInformation> sysWoaHintInformationList = sysWoaHintInformationMapper.selectBySysWoaHintInformationListByTime(sysWoaHintInformation);
					dbSysWoaLargeModule.setSysWoaSmallModuleList(sysWoaHintInformationList);
					newSysWoaLargeModuleList.add(dbSysWoaLargeModule);
				} else {
				    sysWoaSmallModule.setPcMenuRoleFlag("0");
					List<SysWoaSmallModule> sysWoaSmallModuleList = sysWoaSmallModuleMapper.selectBySysWoaSmallModuleList(sysWoaSmallModule);
					
					// ???pc?????????????????????????????????
					if(sysUserWebEntityJSONObject != null) {
					    sysWoaSmallModule.setPcMenuRoleFlag("1");
					    List<SysWoaSmallModule> pcSysWoaSmallModuleList = sysWoaSmallModuleMapper.selectBySysWoaSmallModuleList(sysWoaSmallModule);
					    if(pcSysWoaSmallModuleList != null && pcSysWoaSmallModuleList.size()>0) {
					        for(SysWoaSmallModule pcSysWoaSmallModule:pcSysWoaSmallModuleList) {
					            if(sysUserWebEntityJSONObject.toString().indexOf(pcSysWoaSmallModule.getPcMenuId())>=0) {
					                sysWoaSmallModuleList.add(pcSysWoaSmallModule);
					            }
					        }
					    }
					}
					
				    // ??????
			        Collections.sort(sysWoaSmallModuleList, new Comparator<SysWoaSmallModule>() {
			            @Override
			            public int compare(SysWoaSmallModule o1, SysWoaSmallModule o2) {
			                return o1.getSmallModuleSort() - o2.getSmallModuleSort();
			            }
			        });
			        
					// ????????????
					dbSysWoaLargeModule.setSysWoaSmallModuleList(sysWoaSmallModuleList);
					newSysWoaLargeModuleList.add(dbSysWoaLargeModule);
				}
			}
			returnJsonObject.put("homeLargeModuleList", newSysWoaLargeModuleList);
			cacheMapSysWoaLargeModule.put("sysWoaLargeModule" + roleId, newSysWoaLargeModuleList);
		}
	
		// 1?????????app??????
		String apkPath = ApkInfoUtil.getFilePath(Apih5Properties.getFilePath() + "upload/apk/");
		String versionCode = ApkInfoUtil.readAPK(apkPath);
		if (StrUtil.isEmpty(versionCode)) {
			returnJsonObject.put("version", "0");
		} else {
			returnJsonObject.put("version", versionCode);
			File updateFile = new File(Apih5Properties.getFilePath() + "upload/apk/update.txt");
			if(!FileUtil.isEmpty(updateFile)) {
				String updateContent = FileUtil.readString(updateFile, CharsetUtil.UTF_8);
				returnJsonObject.put("updateContent", updateContent);
			}
			File file = new File(apkPath);
			returnJsonObject.put("fileLength", file.length());
		}
		return repEntity.ok(returnJsonObject);
	}

	@Override
	public ResponseEntity getSysMobilIndexByData(SysWoaLargeModule sysWoaLargeModule) {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
    	if (sysWoaLargeModule == null) {
    		sysWoaLargeModule = new SysWoaLargeModule();
    	}
//    	String userKey = TokenUtils.getUserKey(request);
//    	String companyId = TokenUtils.getCompanyId(request);
    	String token = TokenUtils.getToken(request);
    	
        // ?????????sql???in??????
        String sqlIn = "";
        // ??????????????????????????????Id???userKey
        List<String> permissionList = TokenUtils.getPermissionIds(request);
        for (int i=0; i<permissionList.size(); i++) {
            String departmentId = permissionList.get(i);
            SysDepartment sysDepartment = sysDepartmentService.getSysDepartmentByPrimaryKey(departmentId);
            if(sysDepartment != null) {
                String[] sysDepartments = sysDepartment.getDepartmentPath().split(",");
                for(String sysDepartmentId:sysDepartments) {
                    sqlIn += "'" + sysDepartmentId + "',";
                }
            } else {
                sqlIn += "'" + departmentId + "',";
            }
        }
        sqlIn = sqlIn.substring(0, sqlIn.length() - 1);
        
        String roleId = "?????????";
        SysWoaRole sysWoaRole = new SysWoaRole();
        sysWoaRole.setUserKeys(sqlIn);
        List<SysWoaRole> sysWoaRoleList = sysWoaRoleMapper.selectBySysWoaRoleListInUserKeys(sysWoaRole);
        if(sysWoaRoleList != null && sysWoaRoleList.size()>0) {
            // ????????????????????????????????????????????????
            roleId = sysWoaRoleList.get(0).getRoleId();
        }
        
    	JSONObject jsonObject = new JSONObject();
    	// 2?????????????????????
    	if(cacheMapSysWoaLargeModule != null && cacheMapSysWoaLargeModule.size() > 0
    			&& cacheMapSysWoaLargeModule.get("sysWoaLargeModule" + roleId) != null 
    			&& cacheMapSysWoaLargeModule.get("sysWoaLargeModule" + roleId).size() > 0) {
        	// 1??????????????????????????????
    		JSONObject jsonObjectCount = new JSONObject();
    		String taskingInfo = publicConfig.getProperty("mobile.home.data.api.name", "");
    		if(StrUtil.isNotEmpty(taskingInfo)) {
    			String url = Apih5Properties.getWebUrl() + taskingInfo;
    			LoggerUtils.printDebugLogger(logger, "getSysMobilIndexByData url===="+url);
    			try {
    			    JSONObject paramJSONObject = new JSONObject(sysWoaLargeModule);
    				jsonObjectCount = new JSONObject(HttpUtil.sendPostToken(url, paramJSONObject.toString(), token));
    			} catch (Exception e) {
    				jsonObject = new JSONObject();
				}
    		}
    		
    		JSONObject jsonObjectData = new JSONObject();
    		List<SysWoaLargeModule> sysWoaLargeModuleList = cacheMapSysWoaLargeModule.get("sysWoaLargeModule" + roleId);
    		if(sysWoaLargeModuleList != null && sysWoaLargeModuleList.size()>0) {
    		    for(SysWoaLargeModule dbSysWoaLargeModule:sysWoaLargeModuleList) {
    		        // 5.????????????
    		        if(StrUtil.equals("5", dbSysWoaLargeModule.getLargeModuleType())) {
    		            List<SysWoaHintInformation> sysWoaHintInformationList = Lists.newArrayList();
    		            // ??????????????????
    		            SysWoaHintInformation sysWoaHintInformation = new SysWoaHintInformation();
    		            DateTime nowDate= DateTime.now();
    		            sysWoaHintInformation.setStartTime(nowDate);
    		            sysWoaHintInformation.setEndTime(nowDate);
    		            sysWoaHintInformationList = sysWoaHintInformationMapper.selectBySysWoaHintInformationListByTime(sysWoaHintInformation);
    		            jsonObject.set("dataMsgList", sysWoaHintInformationList);
    		        }
    		        // ???????????????
    		        else if(StrUtil.equals("1", dbSysWoaLargeModule.getLargeModuleType())
    		                || StrUtil.equals("8", dbSysWoaLargeModule.getLargeModuleType())) {
    		            List<SysWoaSmallModule> sysWoaSmallModuleList = dbSysWoaLargeModule.getSysWoaSmallModuleList();
    		            if(sysWoaSmallModuleList != null && sysWoaSmallModuleList.size()>0) {
    		                for(SysWoaSmallModule dbSysWoaSmallModule:sysWoaSmallModuleList) {
    		                    if(StrUtil.equals("1", dbSysWoaSmallModule.getTodoShow())) {
    		                        String key = dbSysWoaSmallModule.getSmallModuleId();
    		                        jsonObjectData.set(key, jsonObjectCount.getStr(dbSysWoaSmallModule.getSmallDataCountId()));
    		                    }
    		                }
    		                jsonObject.set("dataInfoiList", jsonObjectData);
    		            }
    		        }
    		    }
    		} else {
    		    jsonObject.set("dataInfoiList", new JSONObject());
                jsonObject.set("dataMsgList", new JSONArray());
    		}
    	} else {
    		jsonObject.set("dataInfoiList", new JSONObject());
    		jsonObject.set("dataMsgList", new JSONArray());
    	}
    	
    	return repEntity.ok(jsonObject);
	}
}

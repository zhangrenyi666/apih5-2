package com.apih5.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apih5.entity.SysUserWebEntity;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.cache.EhCacheCacheHandler;
import com.apih5.framework.cache.RedisCacheService;
import com.apih5.framework.components.Apih5Properties;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TokenEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.TokenUtils;
import com.apih5.mybatis.dao.SysDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.service.TempService;
import com.apih5.service.TempSyncZjUserInfoService;
import com.google.gson.Gson;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

@Service
public class TempServiceImpl implements TempService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired(required = true)
	private ResponseEntity repEntity;
	@Autowired(required = true)
	private RedisCacheService sysRedisCacheService;
	@Autowired(required = true)
	private SysDepartmentMapper sysDepartmentMapper;
	
	@Autowired(required = true)
	private SysUserMapper sysUserMapper;
	
	@Override
	public ResponseEntity tempSysUserExt10() {
		List<SysUser> list = sysUserMapper.selectBySysUserList(null);
		for(SysUser sysUser : list){
		    if(StrUtil.isNotEmpty(sysUser.getExt10())) {
		        continue;
		    }
			JSONArray jsonArray = new JSONArray();
			jsonArray.add(sysUser.getUserKey());
//			String ext10= "'" + sysUser.getUserKey() + "',";
			SysDepartment record = new SysDepartment();
			record.setUserKey(sysUser.getUserKey());
			List<SysDepartment> sysDepartmentList = sysDepartmentMapper.getSysDepartmentListByUserkey(record);
			for(SysDepartment sysDepartment:sysDepartmentList) {
//				ext10 += "'" + sysDepartment.getDepartmentId() + "',";
				jsonArray.add(sysDepartment.getDepartmentId());
			}
//			ext10 = ext10.substring(0, ext10.lastIndexOf(","));
			sysUser.setExt10(jsonArray.toString());
			sysUserMapper.updateByPrimaryKey(sysUser);
			String userEhCacheKey = sysUser.getUserKey() + "-" + sysUser.getAccountId();
			cacheExt10ByPermissionIds(userEhCacheKey, jsonArray.toString());
		}
		return repEntity.ok("");
	}

	/**
	 * 权限缓存更新
	 * 
	 * @param parUserEhCacheKey
	 * @param ext10
	 */
	private void cacheExt10ByPermissionIds(String parUserEhCacheKey, String ext10) {
		CacheManager cacheManager = CacheManager.create();
		// 2. 获取缓存对象
		Cache cache = cacheManager.getCache("userTokenEhCache");
		if(cache != null) {
			List<String> list = cache.getKeys();
			if(list != null && list.size() > 0) {
				for(String userEhCacheKey:list) {
					if(StrUtil.equals(parUserEhCacheKey, userEhCacheKey)) {
						Element value = cache.get(userEhCacheKey);
						TokenEntity tokenEntity = (TokenEntity) value.getObjectValue();
						// 更新权限
						tokenEntity.setExt10(ext10);
						// 更新缓存
//						EhCacheCacheHandler.putUserTokenEhCache(userEhCacheKey, tokenEntity);
						sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
					}
				}
			}
		}
	}
	
	@Override
	public ResponseEntity tempSyncDepartmentToBaoKu() {
		SysDepartment sysDepartment = new SysDepartment();

		JSONObject jsonObject = new JSONObject();
		List<Map<String, Object>> deptList = new ArrayList<Map<String,Object>>();
		// 跟节点同步
//		if(true) {
//			Map<String, Object> deptMap = new HashMap<>();
//			deptMap.put("deptName", "中交一公局机关");
//			deptMap.put("departmentId", "a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d");
//			deptMap.put("parentDeptId", "system");
//			deptMap.put("issystem", "1");
//			deptMap.put("isparent", "1");
//			deptList.add(deptMap);
//		}
		
		// 部门
//		if(true) {
//		    int i=0;
//		    String companyId = "a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d";
//		    sysDepartment.setDepartmentParentId(companyId);
//		    List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
//		    for(SysDepartment dbSysDepartment:sysDepartmentList) {
//		        Map<String, Object> deptMap = new HashMap<>();
//		        deptMap.put("deptName", dbSysDepartment.getDepartmentName());
//		        deptMap.put("departmentId", dbSysDepartment.getDepartmentId());
//		        deptMap.put("parentDeptId", dbSysDepartment.getDepartmentParentId());
//		        SysDepartment sysDepartmentSelect = new SysDepartment();
//		        sysDepartmentSelect.setDepartmentParentId(dbSysDepartment.getDepartmentParentId());
//		        List<SysDepartment> sysDepartmentSelectList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartmentSelect);
//		        deptMap.put("status", "1");
//		        deptMap.put("issystem", "0");
//		        // 是否有子节点1=有
//		        deptMap.put("isparent", "0");
//		        if(sysDepartmentSelectList != null && sysDepartmentSelectList.size()>0) {
//		            deptMap.put("isparent", "1");
//		        }
//                  if(i==0) {
//                      break;
//                  }
//                  i++;
//                  deptList.add(deptMap);
//		    }
//		    jsonObject.put("deptList", deptList);
//		    jsonObject.put("ticketingId", "10002");
//		    String url = Apih5Properties.getWebUrl() + "syncDepartmentToBaoKu";
//		    String result = HttpUtil.sendPostJson(url, jsonObject.toString());
//		}
		
		
		// 部门下面的人
        if(true) {
            int j=0;
            String companyId = "a5d82aM11cf9cf7329M65b09eb8996c077cbd7a49b1f0d7c83d";
            sysDepartment.setDepartmentParentId(companyId);
            List<SysDepartment> sysDepartmentList = sysDepartmentMapper.selectBySysDepartmentList(sysDepartment);
            for(SysDepartment dbSysDepartment:sysDepartmentList) {
                SysUser sysUserSelect = new SysUser();
                sysUserSelect.setDepartmentId(dbSysDepartment.getDepartmentId());
                sysUserSelect.setAccountCorpId("zj_qyh");
                sysUserSelect.setAccountAppType("1");
                List<SysUser> list = sysUserMapper.selectBySysUserListByDepartment(sysUserSelect);
                List<Map<String, Object>> userList = new ArrayList<Map<String,Object>>();
                for(SysUser sysUser : list){
                    Map<String, Object> userMap = new HashMap<>();
                    userMap.put("employeeId", sysUser.getUserId());
                    userMap.put("employeeNum", sysUser.getUserId());
                    userMap.put("name", sysUser.getRealName());
                    userMap.put("termination", "1");
                    userMap.put("isApp", "0");
                    if(StrUtil.equals("2", sysUser.getGender())) {
                    } else {
                        userMap.put("set", "1");
                    }
                    SysDepartment record = new SysDepartment();
                    record.setUserKey(sysUser.getUserKey());
                    userMap.put("deptName", dbSysDepartment.getDepartmentName());
                    userMap.put("deptId", dbSysDepartment.getDepartmentId());
                    userList.add(userMap);
                }
                
                jsonObject.put("userList", userList);
                jsonObject.put("ticketingId", "10002");
                String url = Apih5Properties.getWebUrl() + "syncUserToBaoKu";
                String result = HttpUtil.sendPostJson(url, jsonObject.toString());
//                if(j==1) {
//                    break;
//                }
//                j++;
            }
//            jsonObject.put("deptList", deptList);
//            jsonObject.put("ticketingId", "10002");
//            String url = Apih5Properties.getWebUrl() + "syncDepartmentToBaoKu";
//            String result = HttpUtil.sendPostJson(url, jsonObject.toString());
        }
      
		return repEntity.ok("");
	}
}

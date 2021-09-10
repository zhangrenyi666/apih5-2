package com.apih5.controller;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.entity.SysUserWebEntity;
import com.apih5.framework.cache.EhCacheCacheHandler;
import com.apih5.framework.cache.RedisCacheService;
import com.apih5.framework.components.RequestHolderConfiguration;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.entity.TokenEntity;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.service.TempService;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;


/**
 * 用户HUI页面的接口功能，涉及到部门、人员
 * 
 * @author www.apih5.com
 *
 */
@RestController
public class TempController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private RequestHolderConfiguration requestHolderConfiguration;
    @Autowired(required = true)
    private RedisCacheService sysRedisCacheService;
	@Autowired
	private TempService tempService;
	@Autowired(required = true)
	private ResponseEntity repEntity;
	/**
     * 部门列表
     * 
     * @return 部门列表
     */
    @ApiOperation(value="查询角色", notes="查询角色")
    @ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
    @GetMapping(path = "/Test123")
    public String Test123() {
        System.out.println("1");
        return "ok";
    }
    
	/**
	 * 部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@PostMapping(path = "/tempSysUserExt10")
	public ResponseEntity tempSysUserExt10() {
		return tempService.tempSysUserExt10();
	}
	
	/**
	 * 用于服务重启缓存切换
	 * 
	 * @return 切换完成
	 */
	@ApiOperation(value="用于服务重启缓存切换", notes="用于服务重启缓存切换")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@GetMapping(path = "/tempRefreshUserCache")
	public ResponseEntity tempRefreshUserCache() {
		HttpServletRequest request = requestHolderConfiguration.getHttpServletRequest();
		String url = request.getParameter("url");
		CacheManager cacheManager = CacheManager.create();
		// 2. 获取缓存对象
		Cache cache = cacheManager.getCache("userTokenEhCache");
		if(cache != null) {
			List<String> list = cache.getKeys();
			if(list == null || list.size()==0) {
				return repEntity.layerMessage("no", "本服务没有缓存！");
			}
			JSONArray jsonArray = new JSONArray();
			for(String userEhCacheKey:list) {
				Element value = cache.get(userEhCacheKey);
				TokenEntity tokenEntity = (TokenEntity) value.getObjectValue();
				jsonArray.add(tokenEntity);
			}

			String str = HttpUtil.sendPostJson(url + "tempPutUserCache", jsonArray.toString());
			if(str == null) {
				return repEntity.layerMessage("no", "url地址错误或服务未启动！");
			}
			JSONObject resultJson = new JSONObject(str);
			boolean success = resultJson.getBool("success");
			if(!success) {
				return repEntity.layerMessage("no", "同步失败！");
			}
		}
		return repEntity.ok("切换完成");
	}

	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "tempPutUserCache", value = "角色entity", dataType = "OADepartment")
	@PostMapping(path = "/tempPutUserCache")
	public ResponseEntity tempPutUserCache(@RequestBody(required = false) List<TokenEntity> tokenEntityList) {
		LoggerUtils.printLogger(logger, "tempPutUserCache 启动....");
		if(tokenEntityList == null || tokenEntityList.size()==0) {
			return repEntity.layerMessage("no", "缓存数据是空的");
		}
		LoggerUtils.printLogger(logger, "tempPutUserCache 进入for....");
		for(TokenEntity tokenEntity:tokenEntityList) {
			JSONObject jsonObjectWeb = new JSONObject(tokenEntity.getSysUserWebObject());
			SysUserWebEntity sysUserWebEntity = JSONUtil.toBean(jsonObjectWeb, SysUserWebEntity.class);
			String userEhCacheKey = sysUserWebEntity.getUserKey() + "-" + tokenEntity.getAccountId();
//			EhCacheCacheHandler.putUserTokenEhCache(userEhCacheKey, tokenEntity);
			sysRedisCacheService.putUserTokenRedis(userEhCacheKey, tokenEntity);
		}
		LoggerUtils.printLogger(logger, "tempPutUserCache 切换完成");
		return repEntity.ok("切换完成");
	}
	
	/**
	 * 部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="票务系统同步", notes="票务系统同步")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@PostMapping(path = "/tempSyncDepartmentToBaoKu")
	public ResponseEntity tempSyncDepartmentToBaoKu() {
		return tempService.tempSyncDepartmentToBaoKu();
	}
}

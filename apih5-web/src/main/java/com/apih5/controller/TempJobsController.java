package com.apih5.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.sysdb.entity.SysUserDepartment;
import com.apih5.framework.api.sysdb.service.SysDepartmentService;
import com.apih5.framework.api.zjoa.common.CachData;
import com.apih5.framework.api.zjoa.entity.ZjAccessCode;
import com.apih5.framework.utils.HttpUtil;
import com.apih5.framework.utils.LoggerUtils;
import com.apih5.framework.utils.UuidUtil;
import com.apih5.mybatis.dao.SysUserDepartmentMapper;
import com.apih5.mybatis.dao.SysUserMapper;
import com.apih5.service.SysMenuService;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
public class TempJobsController {
	private final Logger logger = LoggerFactory.getLogger(getClass());

//	@Autowired
//	private TempSyncZjUserInfoService tempSyncZjUserInfoService;
	  
	@Autowired
	private SysDepartmentService sysDepartmentService;
	
	@Autowired
	private SysMenuService sysMenuService;
	
	@Autowired
	private SysUserDepartmentMapper sysUserDepartmentMapper;
	
	@Autowired
	private SysUserMapper sysUserMapper;
    
//	//OA同步5分钟同步一次
//	@GetMapping("/tempSyncZjSysInfo")
//    public void tempSyncZjSysInfo() {
//    	 tempSyncZjUserInfoService.SyncZjSysInfo();
//    	 LoggerUtils.printLogger(this.logger, "OA同步5分钟同步一次（将zjyj数据库的数据拉到新系统）....");
//    }

    //更新部门表中的全路径5分钟
	@GetMapping("/tempUpdateDepartmentPath")
    public void tempUpdateDepartmentPath() {
    	sysDepartmentService.tempUpdateDepartmentPath(null);
	    LoggerUtils.printLogger(this.logger, "更新部门表中的全路径5分钟（将sys-department表中的path更新）....");
    }

	//更新菜单表中的全路径5分钟
	@GetMapping("/tempMenuPath")
	public void tempMenuPath() {
		sysMenuService.tempMenuPath();
		LoggerUtils.printLogger(this.logger, "更新部门表中的全路径5分钟（将sys-department表中的path更新）....");
	}
	
    @ApiOperation(value="分公司引用身份", notes="分公司引用身份")
    @ApiImplicitParam(name = "zjWoaDocWorkListEntity", value = "oa流程entity", dataType = "ZjWoaDocWorkListEntity")
    @GetMapping("/tempYinYongShenFen")
//    @Transactional(rollbackFor = Exception.class)
    public JSONObject tempHaiweiYinYongShenFen(HttpServletRequest request) {
		JSONObject jsonObject = new JSONObject();
//    	String oaUserId = request.getParameter("oaUserId");//"haiwei_lijian";
		String companyUrl = request.getParameter("oaUrl");//"http://haiwei-oa.fheb.cn:8083";
		String companyId = request.getParameter("companyId");//"2fcb4fM11e4c91e321Mcac6cc7252f26ad204ac504de95ccb51";
		String companyFlag = request.getParameter("companyFlag");//"haiwei_";
		
		if(StrUtil.isEmpty(companyUrl)
				|| StrUtil.isEmpty(companyId)
				|| StrUtil.isEmpty(companyFlag)
				) {
			jsonObject.put("msg","错误");
			return jsonObject;
		}
		
		// woa的数据库人员表
		SysUser sysUserSelect = new SysUser();
		sysUserSelect.setUserId(companyFlag);
    	List<SysUser> list = sysUserMapper.selectSysUserListBySync(sysUserSelect);
    	int count=0;
    	for(SysUser dbSysUser:list) {
    		System.out.println(dbSysUser.getUserId());
    		if(dbSysUser.getUserId().indexOf(companyFlag)>=0) {
    			count++;
    			try {
//    			    LoggerUtils.printDebugLogger(logger, "tempYinYongShenFen start = "+count++);
    				String oaUserId = dbSysUser.getUserId();
    				String result ="";
    				try {
        				// 1、把IP分发地址存入相应的userId缓存中，防止每次从接口获取
            			CachData.setInterfaseUrl(oaUserId, companyUrl);
            			CachData.oaInterfaceUserIdMap.put(oaUserId, oaUserId);
            			// 2、获取OA系统安全认证的token
            			CachData.getAcceeToken(oaUserId);
            			ZjAccessCode accessCodeBean = (ZjAccessCode) CachData.accessMap.get(oaUserId);
        				// 3、获取OA系统部门信息
        				String url = CachData.oaInterfaceMap.get(oaUserId).getGET_PART_ORG_INFO().replace("METHOD", "getPartOrgInfo")
        						.replace("USERID", CachData.oaInterfaceUserIdMap.get(dbSysUser.getUserId()))
        						.replace("ACCESSCODE", accessCodeBean.getAccessCode())
        						.replace("COMPANY_ID", companyId);
        				result = HttpUtil.sendGet(url);
    				}catch (Exception e) {
    				    LoggerUtils.printExceptionLogger(logger, "tempYinYongShenFen err= " + e.getMessage());
    				}
    				LoggerUtils.printLogger(logger, "tempYinYongShenFen = " +result);
    				
    				JSONObject resultJsonObject = new JSONObject(result);
    				JSONArray jsonArray = resultJsonObject.getJSONArray("data");
    				if(jsonArray != null){
    					 JSONObject JSONObject = new JSONObject();
    					 for (Iterator<Object> iterator = jsonArray.iterator(); iterator.hasNext();) {
    						 JSONObject jsonObject2 = (JSONObject)iterator.next();
    						 // 根据userKey、departmentId查找是否有数据，有则不动，无则新增、冗余数据则删除
    						 SysUserDepartment sysUserDepartment = new SysUserDepartment();
    						 sysUserDepartment.setUserKey(dbSysUser.getUserKey());
    						 sysUserDepartment.setDepartmentId(jsonObject2.getStr("partorgId"));
    						 List<SysUserDepartment> userDepartmentList = sysUserDepartmentMapper.selectBySysUserDepartmentList(sysUserDepartment);
    						 if(userDepartmentList == null || userDepartmentList.size()==0) {
    							 sysUserDepartment.setUserDepartmentId(UuidUtil.generate());
    							 sysUserDepartment.setCreateUserInfo(companyFlag, "手动");
    							 sysUserDepartmentMapper.insert(sysUserDepartment);
    							 JSONObject.put(sysUserDepartment.getUserDepartmentId(), sysUserDepartment.getUserDepartmentId());
    						 } else {
    							 JSONObject.put(userDepartmentList.get(0).getUserDepartmentId(), userDepartmentList.get(0).getUserDepartmentId());
    						 }
    					 }

    					 // 遍历删除不存在的数据
    					 SysUserDepartment sysUserDepartment = new SysUserDepartment();
    					 sysUserDepartment.setUserKey(dbSysUser.getUserKey());
    					 List<SysUserDepartment> userDepartmentList = sysUserDepartmentMapper.selectBySysUserDepartmentList(sysUserDepartment);
    					 for(SysUserDepartment dbSysUserDepartment:userDepartmentList) {
    						 if(StrUtil.isEmpty(JSONObject.getStr(dbSysUserDepartment.getUserDepartmentId()))) {
    							 sysUserDepartmentMapper.deleteByPrimaryKey(dbSysUserDepartment.getUserDepartmentId());
    						 }
    					 }
    				}
    			}catch (Exception e) {
				}
    		}
    	}
    	jsonObject.put("userCount=", count);
    	return jsonObject;
    }
}

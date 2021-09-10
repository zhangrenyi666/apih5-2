package com.apih5.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.apih5.entity.QfDepartmentEntity;
import com.apih5.entity.QfUserEntity;
import com.apih5.framework.annotation.RequireToken;
import com.apih5.framework.api.sysdb.entity.SysDepartment;
import com.apih5.framework.api.sysdb.entity.SysUser;
import com.apih5.framework.api.zjoa.entity.OADepartment;
import com.apih5.framework.api.zjoa.service.ZjOaApiService;
import com.apih5.framework.entity.ResponseEntity;
import com.apih5.framework.utils.LoggerUtils;
import com.google.common.collect.Lists;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;


/**
 * 微办公系统相关中转接口
 * 
 * @author www.apih5.com
 *
 */
@RestController
public class ZjOaApiController {
//	private static final Logger logger = LoggerFactory.getLogger(ZjPcOaApiController.class);
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private ZjOaApiService zjPcOaApiService;
	
//	@Autowired
//	private SysDepartmentMapper sysDepartmentMapper;

	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping(path = "/apiPostSysDepartmentList/{otherCompanyFlag}")
	public ResponseEntity apiPostSysDepartmentList(@PathVariable("otherCompanyFlag") String otherCompanyFlag) {
		return zjPcOaApiService.apiPostSysDepartmentList(otherCompanyFlag);
	}
	
	//--------------↓↓↓暂时过度↓↓↓------------------------
	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping("/getDepartment")
	public ResponseEntity getDepartment() {
		return zjPcOaApiService.getDepartmentList();
	}
	
	/**
	 * 根据登录者获取中交系统的用户列表
	 * 
	 * @return 用户列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/getMember")
	public ResponseEntity getMember(@RequestBody(required = false) OADepartment oaDepartment) {
		return zjPcOaApiService.getMemberList(oaDepartment);
	}
	
	
	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping("/apiPostDepartment")
	public ResponseEntity apiPostDepartment() {
		return zjPcOaApiService.getPostDepartmentList();
	}
	
	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping("/tempPostDepartmentXiamen")
	public ResponseEntity tempPostDepartmentXiamen() {
		return zjPcOaApiService.tempPostDepartmentXiamen();
	}
	
	/**
	 * 根据登录者获取中交系统的用户列表
	 * 
	 * @return 用户列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/tempMemberXiamen")
	public ResponseEntity tempMemberXiamen(@RequestBody(required = false) OADepartment oaDepartment) {
		return zjPcOaApiService.tempMemberListXiamen(oaDepartment);
	}
	
	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping("/apiPostDepartment2")
	public ResponseEntity apiPostDepartment2() {
		return zjPcOaApiService.getPostDepartmentList2();
	}
	/**
	 * 根据登录者获取中交系统的用户列表
	 * 
	 * @return 用户列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
	@RequireToken
	@PostMapping("/apiPostMember")
	public ResponseEntity apiPostMember(@RequestBody(required = false) OADepartment oaDepartment) {
		return zjPcOaApiService.getPostMemberList(oaDepartment);
	}
	//--------------↑↑↑暂时过度↑↑↑------------------------

	/**
	 * 根据登录者获取中交系统的部门列表
	 * 
	 * @return 部门列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping("/getDepartmentList")
	public ResponseEntity getDepartmentList() {
		return zjPcOaApiService.getDepartmentList();
	}
	
	/**
	 * 根据登录者获取中交系统的用户列表
	 * 
	 * @return 用户列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
	@RequireToken
	@PostMapping("/getMemberList")
	public ResponseEntity getMemberList(@RequestBody(required = false) OADepartment oaDepartment) {
		return zjPcOaApiService.getMemberList(oaDepartment);
	}
	
	/**
	 * 根据登录者获取改公司整体部门+人员
	 * 
	 * @return 部门+人员列表
	 */
	@ApiOperation(value="查询角色", notes="查询角色")
	@ApiImplicitParam(name = "department", value = "角色entity", dataType = "OADepartment")
	@RequireToken
	@PostMapping("/getDepartmentMemberList")
	public ResponseEntity getDepartmentMemberList() {
		return zjPcOaApiService.getDepartmentMemberList();
	}
	
	/**
	 * 根据登录者获取中交系统用户组列表
	 * 
	 * @return 系统用户组列表
	 */
	@ApiOperation(value="系统用户组列", notes="系统用户组列")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/getSysGroupList")
	public ResponseEntity getSysGroupList() {
		return zjPcOaApiService.getSysGroupList();
	}
	
	/**
	 * 根据登录者获取中交系统用户组人员列表
	 * 
	 * @param wdpVote
	 * @param request
	 * @return
	 */
	@ApiOperation(value="系统用户组人员列表", notes="系统用户组人员列表")
    @ApiImplicitParam(name = "sysRole", value = "角色entity", dataType = "SysRole")
    @RequireToken
    @PostMapping("/getSysGroupMemberList")
	public ResponseEntity getSysGroupMemberList(@RequestBody(required = false) OADepartment oaDepartment) {
		return zjPcOaApiService.getSysGroupMemberList(oaDepartment);
	}

	//----------↓↓↓ 千方组织机构人员处理 ↓↓↓-------------------
    /**
     * 千方系统的部门同步给本系统-新增
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value="千方系统的部门同步给本系统-新增", notes="千方系统的部门同步给本系统-新增")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncZjQfToSysDeptByAdd")
    public JSONObject syncZjQfToZxDepartmentByAdd(@RequestBody(required = false)
    	QfDepartmentEntity qfDepartmentEntity) {
    	if(qfDepartmentEntity == null) {
    		return null;
    	}
       	JSONObject jsonObject = new JSONObject(qfDepartmentEntity);
    	LoggerUtils.printLogger(logger, "syncZjQfToSysDeptByAdd===" + jsonObject.toString());
    	SysDepartment sysDepartment = qfDepartmentEntity.toSysDepartmentInfo();
    	return zjPcOaApiService.syncZjQfToZxDepartmentByAdd(sysDepartment);
    }

    /**
     * 千方系统的部门同步给本系统-修改
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value="千方系统的部门同步给本系统-修改", notes="千方系统的部门同步给本系统-修改")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncZjQfToSysDeptByUpd")
    public JSONObject syncZjQfToZxDepartmentByUpd(@RequestBody(required = false) QfDepartmentEntity qfDepartmentEntity) {
     	if(qfDepartmentEntity == null) {
    		return null;
    	}
      	JSONObject jsonObject = new JSONObject(qfDepartmentEntity);
    	LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByUpd===" + jsonObject.toString());
    	SysDepartment sysDepartment = qfDepartmentEntity.toSysDepartmentInfo();
    	if(StrUtil.equals("-1", qfDepartmentEntity.getOid())) {
    		sysDepartment.setDepartmentParentId("a5d82aM11cf9cea644M65b09eb8996c077cbd7a49b1f0d7c83d");
    	}
    	return zjPcOaApiService.syncZjQfToZxDepartmentByUpd(sysDepartment);
    }
    
    /**
     * 千方系统的部门同步给本系统-删除
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value="千方系统的部门同步给本系统-删除", notes="千方系统的部门同步给本系统-删除")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncZjQfToSysDeptByDel")
    public JSONObject syncZjQfToZxDepartmentByDel(@RequestBody(required = false) QfDepartmentEntity qfDepartmentEntity) {
    	if(qfDepartmentEntity == null) {
    		return null;
    	}
      	JSONObject jsonObject = new JSONObject(qfDepartmentEntity);
    	LoggerUtils.printLogger(logger, "syncZjQfToZxDepartmentByDel===" + jsonObject.toString());
    	SysDepartment sysDepartment = qfDepartmentEntity.toSysDepartmentInfo();
    	if(StrUtil.equals("-1", qfDepartmentEntity.getOid())) {
    		sysDepartment.setDepartmentParentId("a5d82aM11cf9cea644M65b09eb8996c077cbd7a49b1f0d7c83d");
    	}
     	List<SysDepartment> sysDepartmentList = Lists.newArrayList();
 		sysDepartmentList.add(sysDepartment);
    	return zjPcOaApiService.syncZjQfToZxDepartmentByDel(sysDepartmentList);
    }
    
    /**
     * 千方系统的人员同步给本系统-新增
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value="千方系统的人员同步给本系统-新增", notes="千方系统的人员同步给本系统-新增")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncZjQfToZxUserInfoByAdd")
    public JSONObject syncZjQfToZxUserInfoByAdd(@RequestBody(required = false) QfUserEntity qfUserEntity) {
    	if(qfUserEntity == null) {
    		return null;
    	}
    	JSONObject jsonObject = new JSONObject(qfUserEntity);
    	LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByAdd===" + jsonObject.toString());
    	SysUser sysUser = qfUserEntity.toSysUserInfo();
    	return zjPcOaApiService.syncZjQfToZxUserInfoByAdd(sysUser);
    }
    
    /**
     * 千方系统的人员同步给本系统-修改
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value="千方系统的人员同步给本系统-新增", notes="千方系统的人员同步给本系统-新增")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncZjQfToZxUserInfoByUpd")
    public JSONObject syncZjQfToZxUserInfoByUpd(@RequestBody(required = false) QfUserEntity qfUserEntity) {
    	if(qfUserEntity == null) {
    		return null;
    	}
    	JSONObject jsonObject = new JSONObject(qfUserEntity);
    	LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByUpd===" + jsonObject.toString());
    	SysUser sysUser = qfUserEntity.toSysUserInfo();
    	return zjPcOaApiService.syncZjQfToZxUserInfoByUpd(sysUser);
    }
    
    /**
     * 千方系统的人员同步给本系统-删除
     * 
     * @param sysDepartment
     * @return
     */
    @ApiOperation(value="千方系统的人员同步给本系统-新增", notes="千方系统的人员同步给本系统-新增")
    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
    @RequireToken
    @PostMapping("/syncZjQfToZxUserInfoByDel")
    public JSONObject syncZjQfToZxUserInfoByDel(@RequestBody(required = false) QfUserEntity qfUserEntity) {
    	if(qfUserEntity == null) {
    		return null;
    	}
    	JSONObject jsonObject = new JSONObject(qfUserEntity);
    	LoggerUtils.printLogger(logger, "syncZjQfToZxUserInfoByDel===" + jsonObject.toString());
    	SysUser sysUser = qfUserEntity.toSysUserInfo();
    	return zjPcOaApiService.syncZjQfToZxUserInfoByDel(sysUser);
    }

//  // 12局导入
//	@Autowired
//	private SysDepartmentMapper sysDepartmentMapper;
//	@Autowired
//	private SysDepartmentService sysDepartmentService;
//	@Autowired
//	private UserService userService;
//	
//  @ApiOperation(value="千方系统的人员同步给本系统-新增", notes="千方系统的人员同步给本系统-新增")
//  @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
//  @RequireToken
//  @PostMapping("/importZjQfToDepartment")
//  public JSONObject importZjQfToDepartment(@RequestBody(required = false) QfDepartmentEntity qfDepartmentEntity) {
//  	if(qfDepartmentEntity == null) {
//  		return null;
//  	}
//  	ExcelReader reader = ExcelUtil.getReader("d:/cca.xlsx");
//  	try {
//  		List<Map<String,Object>> readAll = reader.readAll();
//  		int i=1;
//      	for(Map<String,Object> map:readAll) {
//      		JSONObject json = new JSONObject(map);
//      		
//      		if(json.getStr("部门").indexOf("计划合同部")>0) {
//      			System.out.println(json.getStr("部门"));
//      		}
//      		
//      		SysDepartment record = new SysDepartment();
//      		record.setDepartmentPathName(json.getStr("部门").replace("中铁十二局集团第二工程有限公司", "中铁十二局二公司").replaceAll("/", ","));
//      		List<SysDepartment> list = sysDepartmentMapper.selectBySysDepartmentList(record);
//      		if(list != null && list.size()>0) {
//      			System.out.println("====="+i);
//      			i++;
//      			
//  			SysUser dbSysUser = userService.getSysUserByUserId(json.getStr("帐号"));
//if(dbSysUser == null) {
//      			LoggerUtils.printDebugLogger(logger, "-------========---"+json.getStr("帐号")+"  "+json.getStr("姓名"));
//      			SysUser sysUser = new SysUser();
//      			sysUser.setUserId(json.getStr("帐号"));
//      			sysUser.setRealName(json.getStr("姓名"));
//      			sysUser.setMobile(json.getStr("帐号"));
//      		    			List<Map> sysDepartmentList = Lists.newArrayList();
//      		    			Map map1 = Maps.newHashMap();
//      		    			map1.put("departmentId", list.get(0).getDepartmentId());
//      		    			sysDepartmentList.add(map1);
//      			sysUser.setSysDepartmentList(sysDepartmentList);
//      			sysUser.setUserType("1");
//      			sysUser.setUserStatus("1");
//      			userService.addSysUserInfoByBg(sysUser);
//}	
//      			continue;
//      		}
//      		
//      		String[] str = json.getStr("部门").replace("中铁十二局集团第二工程有限公司", "中铁十二局二公司").split("/");
////      		if(true) {
////      				SysDepartment sysDepartment1 = new SysDepartment();
////      			String str1 = str[1];
////      			sysDepartment1.setDepartmentId(UuidUtil.generate());
////      			sysDepartment1.setDepartmentParentId("9999999999");
////      			sysDepartment1.setDepartmentName(str1);
////      			sysDepartment1.setSort(999);
////      			sysDepartmentService.saveSysDepartment(sysDepartment1);
////      		}
//      		
//      		 // 与上面的交替注释
//      		SysDepartment sysDepartment2 = new SysDepartment();
//      		if(true) {
//      			record = new SysDepartment();
//      			String d = json.getStr("部门").replace("中铁十二局集团第二工程有限公司", "中铁十二局二公司").replaceAll("/", ",");
//      			d=d.substring(0, d.lastIndexOf(","));
//          		record.setDepartmentPathName(d);
//          		list = sysDepartmentMapper.selectBySysDepartmentList(record);
//          		
//      			String str2=str[2];
//      			sysDepartment2.setDepartmentId(UuidUtil.generate());
//      			sysDepartment2.setDepartmentParentId(list.get(0).getDepartmentId());
//      			sysDepartment2.setDepartmentName(str2);
//      			sysDepartment2.setSort(999);
//      			sysDepartmentService.saveSysDepartment(sysDepartment2);
//      		}
//      		// 人员录入
//      		SysUser dbSysUser = userService.getSysUserByUserId(json.getStr("帐号"));
//      		if(dbSysUser == null) {
//      			LoggerUtils.printDebugLogger(logger, "----------"+json.getStr("帐号")+"  "+json.getStr("姓名"));
//      			SysUser sysUser = new SysUser();
//      			sysUser.setUserId(json.getStr("帐号"));
//      			sysUser.setRealName(json.getStr("姓名"));
//      			sysUser.setMobile(json.getStr("帐号"));
//      			List<Map> sysDepartmentList = Lists.newArrayList();
//      			Map map1 = Maps.newHashMap();
//      			map1.put("departmentId", sysDepartment2.getDepartmentId());
//      			sysDepartmentList.add(map1);
//      			sysUser.setSysDepartmentList(sysDepartmentList);
//      			sysUser.setUserType("1");
//      			sysUser.setUserStatus("1");
//      			userService.addSysUserInfoByBg(sysUser);
//      		}
//      		
//      		
//      		
//      	}
//  	} catch (Exception e) {
//  		e.printStackTrace();
//  		reader.close();
//		}
//  	JSONObject j = new JSONObject();
//  	j.put("msg", "ok");
//  	return j;
//  }
    
//    @ApiOperation(value="千方系统的人员同步给本系统-新增", notes="千方系统的人员同步给本系统-新增")
//    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
//    @RequireToken
//    @PostMapping("/importZjQfToDepartment")
//    public JSONObject importZjQfToDepartment(@RequestBody(required = false) QfDepartmentEntity qfDepartmentEntity) {
//    	if(qfDepartmentEntity == null) {
//    		return null;
//    	}
//    	ExcelReader reader = ExcelUtil.getReader("d:/cca.xlsx");
//    	try {
//    		List<Map<String,Object>> readAll = reader.readAll();
//        	for(Map<String,Object> map:readAll) {
//        		JSONObject json = new JSONObject(map);
//        		SysDepartment dbSysDepartmentPid = sysDepartmentMapper.selectByPrimaryKey(json.getStr("PID"));
//        		if(dbSysDepartmentPid == null) {
//        			continue;
//        		}
//        		SysDepartment sysDepartment = new SysDepartment();
//            	sysDepartment.setDepartmentId(json.getStr("ID"));
//            	sysDepartment.setDepartmentParentId(json.getStr("PID"));
//            	sysDepartment.setDepartmentName(json.getStr("NAME"));
//            	sysDepartment.setSort(json.getInt("ORDNUM"));
//            	System.out.println("======+"+json.getStr("ID") + "   "+ json.getStr("NAME"));
//            	zjPcOaApiService.syncZjQfToZxDepartmentByAdd(sysDepartment);
//        	}
//    	} catch (Exception e) {
//    		reader.close();
//		}
//    	JSONObject j = new JSONObject();
//    	j.put("msg", "ok");
//    	return j;
//    }
//    
//    @ApiOperation(value="千方系统的人员同步给本系统-新增", notes="千方系统的人员同步给本系统-新增")
//    @ApiImplicitParam(name = "sysDepartment", value = "部门entity", dataType = "SysDepartment")
//    @RequireToken
//    @PostMapping("/importZjQfToUser")
//    public JSONObject importZjQfToUser(@RequestBody(required = false) QfUserEntity qfUserEntity) {
//    	ExcelReader reader = ExcelUtil.getReader("d:/user.xlsx");
//    	try {
//        	List<Map<String,Object>> readAll = reader.readAll();
//        	for(Map<String,Object> map:readAll) {
//        		JSONObject json = new JSONObject(map);
//        		if(StrUtil.isNotEmpty(json.getStr("MOBILE"))) {
//        			SysUser sysUser = new SysUser();
//        			// userID
//        			sysUser.setUserId(json.getStr("LOGIN_ID"));
//        			// 姓名
//        			sysUser.setRealName(json.getStr("USER_NAME"));
//        			sysUser.setMobile(json.getStr("MOBILE"));
//        			// 顺序号【用】
//        			// sysUser.setSort(json.getInt("ORDNUM"));
//        			List<Map> sysDepartmentList = Lists.newArrayList();
//        			Map map1 = Maps.newHashMap();
//        			map1.put("departmentId", json.getStr("DEPTID"));
//        			sysDepartmentList.add(map1);
//        			sysUser.setSysDepartmentList(sysDepartmentList);
//        			zjPcOaApiService.syncZjQfToZxUserInfoByAdd(sysUser);
//        		}
//        	}
//    	} catch (Exception e) {
//    		reader.close();
//		}
//    	JSONObject j = new JSONObject();
//    	j.put("msg", "ok");
//    	return j;
//    }
    //----------↑↑↑ 千方组织机构人员处理  ↑↑↑-------------------
}

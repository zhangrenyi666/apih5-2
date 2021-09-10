package com.apih5.entity;

import java.util.List;
import java.util.Map;

import com.apih5.framework.api.sysdb.entity.SysUser;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import cn.hutool.core.util.StrUtil;

public class QfUserEntity {
	private String loginId;//账号
	private String userName;//姓名
	private String deptid;//门户部门ID
	private String firstOaId;//同望部门ID
	private String mobile;//手机号
	private String ordnum;//手机号
	private String syncFlag;//数据系统标识（1、同望OA；2、其他）

	public SysUser toSysUserInfo() {
		SysUser sysUser = new SysUser();
		List<Map> sysDepartmentList = Lists.newArrayList();
//		SysDepartment sysDepartment = new SysDepartment();
		Map map = Maps.newHashMap();
		
    	if(StrUtil.equals("1", this.syncFlag)) {
    		//同望部门ID【用】
//    		sysUser.setDepartmentId(this.getFirstOaId());
//    		sysDepartment.setDepartmentId(this.getFirstOaId());
    		map.put("departmentId", this.getFirstOaId());
    	} else {
    		//部门数据ID 【用】
//    		sysUser.setDepartmentId(String.valueOf(this.getDeptid()));
//    		sysDepartment.setDepartmentId(String.valueOf(this.getDeptid()));
    		map.put("departmentId", "suidj-" + this.getDeptid());
    	}
    	sysDepartmentList.add(map);
    	sysUser.setSysDepartmentList(sysDepartmentList);
    	sysUser.setUserId(this.loginId);
    	// 姓名
    	sysUser.setRealName(this.userName);
    	sysUser.setMobile(this.mobile);
    	// 顺序号【用】
    	if(StrUtil.isNotEmpty(this.ordnum)) {
    		sysUser.setSort(Integer.valueOf(this.ordnum));
    	}
    	return sysUser;
	}
	
	public String getLoginId() {
		return loginId;
	}
	public String getUserName() {
		return userName;
	}
	public String getDeptid() {
		return deptid;
	}
	public String getFirstOaId() {
		return firstOaId;
	}
	public String getMobile() {
		return mobile;
	}
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setDeptid(String deptid) {
		this.deptid = deptid;
	}
	public void setFirstOaId(String firstOaId) {
		this.firstOaId = firstOaId;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getOrdnum() {
		return ordnum;
	}

	public void setOrdnum(String ordnum) {
		this.ordnum = ordnum;
	}

	public String getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(String syncFlag) {
		this.syncFlag = syncFlag;
	}
	
//	private String tel;
//	private String password;
//	private String loginIdLow;
//	private Long id;//门户数据ID
//	private String deptCode;//部门ID
//	private String jobNumber;
//	private String sex;
//	private Date birthday;
//	private String nation;
//	private String highestEducation;
//	private String jobTitle;
//	private String post;
//	private String nativePlace;
//	private String email;
//	private String address;
//	private Long inheritDeptAuth;
//	private Long authTag;
//	private String caKey;
//	private String ipAddress;
//	private String macAddress;
//	private String distid;
//	private Integer jobState;
//	private Integer deleteFlag = 1;//删除标记  2表示已逻辑删除
//	private String tunnelOaId;
//	private String tunnelPmId;

}

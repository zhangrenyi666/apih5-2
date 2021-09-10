package com.apih5.entity;

import com.apih5.framework.api.sysdb.entity.SysDepartment;

import cn.hutool.core.util.StrUtil;

public class QfDepartmentEntity {
	private String oid;//部门数据ID 【用】
	private String pid;//父节点ID【用】
	private String name;// 部门名称【用】
	private String firstOaId;//同望部门ID【用】
	private String pdeptCode;//同望父节点ID【用】
	private String ordnum;// 顺序号【用】
	private String syncFlag;//数据系统标识（1、同望OA；2、其他）
	
	private String address;
	private String leader;
	private String tel;
	private String districtId;
	private String tunnelOaId;
	private String tunnelPmId;
	private String deptType;//1、2、4、5 其中1是公司、2是职能部门、4是项目、5是非职能部门
	
	public SysDepartment toSysDepartmentInfo() {
		SysDepartment sysDepartment = new SysDepartment();
    	if(StrUtil.equals("1", this.syncFlag)) {
    		//同望部门ID【用】
    		sysDepartment.setDepartmentId(this.getFirstOaId());
    		//同望父节点ID【用】
    		sysDepartment.setDepartmentParentId(this.getPdeptCode());
    	} else {
    		//部门数据ID 【用】
    		sysDepartment.setDepartmentId("suidj-" + this.getOid());
    		//父节点ID【用】
    		sysDepartment.setDepartmentParentId("suidj-" + this.getPid());
    	}
    	// 部门名称【用】
    	sysDepartment.setDepartmentName(this.getName());
    	// 顺序号【用】
    	if(StrUtil.isNotEmpty(this.ordnum)) {
    		sysDepartment.setSort(Integer.valueOf(this.ordnum));
    	}
    	return sysDepartment;
	}
	
	public String getOid() {
		return oid;
	}
	public String getPid() {
		return pid;
	}
	public String getOrdnum() {
		return ordnum;
	}
	public String getName() {
		return name;
	}
	public String getAddress() {
		return address;
	}
	public String getLeader() {
		return leader;
	}
	public String getTel() {
		return tel;
	}
	public String getDistrictId() {
		return districtId;
	}
	public String getTunnelOaId() {
		return tunnelOaId;
	}
	public String getTunnelPmId() {
		return tunnelPmId;
	}
	public String getFirstOaId() {
		return firstOaId;
	}
	public String getDeptType() {
		return deptType;
	}
	public String getPdeptCode() {
		return pdeptCode;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public void setPid(String pid) {
		this.pid = pid;
	}
	public void setOrdnum(String ordnum) {
		this.ordnum = ordnum;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setLeader(String leader) {
		this.leader = leader;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	public void setDistrictId(String districtId) {
		this.districtId = districtId;
	}
	public void setTunnelOaId(String tunnelOaId) {
		this.tunnelOaId = tunnelOaId;
	}
	public void setTunnelPmId(String tunnelPmId) {
		this.tunnelPmId = tunnelPmId;
	}
	public void setFirstOaId(String firstOaId) {
		this.firstOaId = firstOaId;
	}
	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}
	public void setPdeptCode(String pdeptCode) {
		this.pdeptCode = pdeptCode;
	}

	public String getSyncFlag() {
		return syncFlag;
	}

	public void setSyncFlag(String syncFlag) {
		this.syncFlag = syncFlag;
	}
}

package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjTzPermission extends BasePojo {
    private String permissionId;

    private String permissionName;

    private String projectId;

    private String projectName;

    private String projectShortName;

    private String ext1;

    private String ext2;

    private String ext3;

    private String ext4;

    private String ext5;

    private String ext6;

    private String remark;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String value;

    private List<ZjTzPermissionUser> zjTzPermissionUserList;

    private String selectFlag;
    
    private String type;

    private String companyId;

    private String companyName;
    
    private String allFlag ;
    
    private String proProcessId;
    
    private String analySubject;
    // 规模控制主体
    private String sizeControlSubject;
    
    public String getPermissionId() {
        return permissionId == null ? "" : permissionId.trim();
    }

    public void setPermissionId(String permissionId) {
        this.permissionId = permissionId == null ? null : permissionId.trim();
    }

    public String getPermissionName() {
        return permissionName == null ? "" : permissionName.trim();
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName == null ? null : permissionName.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public String getProjectShortName() {
        return projectShortName == null ? "" : projectShortName.trim();
    }

    public void setProjectShortName(String projectShortName) {
        this.projectShortName = projectShortName == null ? null : projectShortName.trim();
    }

    public String getExt1() {
        return ext1 == null ? "" : ext1.trim();
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2 == null ? "" : ext2.trim();
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getExt3() {
        return ext3 == null ? "" : ext3.trim();
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }

    public String getExt4() {
        return ext4 == null ? "" : ext4.trim();
    }

    public void setExt4(String ext4) {
        this.ext4 = ext4 == null ? null : ext4.trim();
    }

    public String getExt5() {
        return ext5 == null ? "" : ext5.trim();
    }

    public void setExt5(String ext5) {
        this.ext5 = ext5 == null ? null : ext5.trim();
    }

    public String getExt6() {
        return ext6 == null ? "" : ext6.trim();
    }

    public void setExt6(String ext6) {
        this.ext6 = ext6 == null ? null : ext6.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getDelFlag() {
        return delFlag == null ? "" : delFlag.trim();
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag == null ? null : delFlag.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser == null ? "" : createUser.trim();
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public String getCreateUserName() {
        return createUserName == null ? "" : createUserName.trim();
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName == null ? null : createUserName.trim();
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUser() {
        return modifyUser == null ? "" : modifyUser.trim();
    }

    public void setModifyUser(String modifyUser) {
        this.modifyUser = modifyUser == null ? null : modifyUser.trim();
    }

    public String getModifyUserName() {
        return modifyUserName == null ? "" : modifyUserName.trim();
    }

    public void setModifyUserName(String modifyUserName) {
        this.modifyUserName = modifyUserName == null ? null : modifyUserName.trim();
    }

    public String getValue() {
        return value == null ? "" : value.trim();
    }

    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    public List<ZjTzPermissionUser> getZjTzPermissionUserList() {
        return zjTzPermissionUserList;
    }

    public void setZjTzPermissionUserList(List<ZjTzPermissionUser> zjTzPermissionUserList) {
        this.zjTzPermissionUserList = zjTzPermissionUserList;
    }

    public String getSelectFlag () {
        return selectFlag  == null ? "" : selectFlag .trim();
    }

    public void setSelectFlag (String selectFlag ) {
        this.selectFlag  = selectFlag  == null ? null : selectFlag .trim();
    }

    public String getType() {
        return type == null ? "" : type.trim();
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getAllFlag() {
        return allFlag == null ? "" : allFlag.trim();
    }

    public void setAllFlag(String allFlag) {
        this.allFlag = allFlag == null ? null : allFlag.trim();
    }
    
    public String getProProcessId() {
        return proProcessId == null ? "" : proProcessId.trim();
    }

    public void setProProcessId(String proProcessId) {
        this.proProcessId = proProcessId == null ? null : proProcessId.trim();
    }

	public String getAnalySubject() {
		return analySubject == null ? "" : analySubject.trim();
	}

	public void setAnalySubject(String analySubject) {
		this.analySubject = analySubject == null ? null : analySubject.trim();
	}

	public String getSizeControlSubject() {
		return sizeControlSubject == null ? "" : sizeControlSubject.trim();
	}

	public void setSizeControlSubject(String sizeControlSubject) {
		this.sizeControlSubject = sizeControlSubject == null ? null : sizeControlSubject.trim();
	}
    
}


package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectSetupPersonnel extends BasePojo {
    private String zcOaId;

    private String otherId;

    private String otherType;

    private String oaDepartmentMemberFlag;

    private String oaUserId;

    private String oaUserName;

    private String oaOrgId;

    private String departmentId;

    private String departmentName;

    private String deptOrgId;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private OADepartmentMember projectDeptList;//考核部门
    
    private OADepartmentMember projectMemberList;//考核部门
    
    public OADepartmentMember getProjectDeptList() {
		return projectDeptList;
	}

	public void setProjectDeptList(OADepartmentMember projectDeptList) {
		this.projectDeptList = projectDeptList;
	}

	public OADepartmentMember getProjectMemberList() {
		return projectMemberList;
	}

	public void setProjectMemberList(OADepartmentMember projectMemberList) {
		this.projectMemberList = projectMemberList;
	}

	public String getZcOaId() {
        return zcOaId == null ? "" : zcOaId.trim();
    }

    public void setZcOaId(String zcOaId) {
        this.zcOaId = zcOaId == null ? null : zcOaId.trim();
    }

    public String getOtherId() {
        return otherId == null ? "" : otherId.trim();
    }

    public void setOtherId(String otherId) {
        this.otherId = otherId == null ? null : otherId.trim();
    }

    public String getOtherType() {
        return otherType == null ? "" : otherType.trim();
    }

    public void setOtherType(String otherType) {
        this.otherType = otherType == null ? null : otherType.trim();
    }

    public String getOaDepartmentMemberFlag() {
        return oaDepartmentMemberFlag == null ? "" : oaDepartmentMemberFlag.trim();
    }

    public void setOaDepartmentMemberFlag(String oaDepartmentMemberFlag) {
        this.oaDepartmentMemberFlag = oaDepartmentMemberFlag == null ? null : oaDepartmentMemberFlag.trim();
    }

    public String getOaUserId() {
        return oaUserId == null ? "" : oaUserId.trim();
    }

    public void setOaUserId(String oaUserId) {
        this.oaUserId = oaUserId == null ? null : oaUserId.trim();
    }

    public String getOaUserName() {
        return oaUserName == null ? "" : oaUserName.trim();
    }

    public void setOaUserName(String oaUserName) {
        this.oaUserName = oaUserName == null ? null : oaUserName.trim();
    }

    public String getOaOrgId() {
        return oaOrgId == null ? "" : oaOrgId.trim();
    }

    public void setOaOrgId(String oaOrgId) {
        this.oaOrgId = oaOrgId == null ? null : oaOrgId.trim();
    }

    public String getDepartmentId() {
        return departmentId == null ? "" : departmentId.trim();
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId == null ? null : departmentId.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getDeptOrgId() {
        return deptOrgId == null ? "" : deptOrgId.trim();
    }

    public void setDeptOrgId(String deptOrgId) {
        this.deptOrgId = deptOrgId == null ? null : deptOrgId.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
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

}


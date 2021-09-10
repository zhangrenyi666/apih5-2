package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectDepartmentHead extends BasePojo {
    private String departmentHeadId;

    private String departmentId;

    private String departmentName;
    
    private String cadreName;
    
    private String staffName;

    private String departmentOrgId;
    
    private String userKey;
    
    private String userName;

    private String chargeLeaderName;

    private String executiveLeaderName;

    private String comChargeLeaderName;
    
    private String comExecutiveLeaderName;
    
    private String projectOfficeName;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private OADepartmentMember departmentHead;//部门负责人
    
    private OADepartmentMember projectStaff;//部门负责人
    
    private OADepartmentMember proectCadre;//项目中层干部
    
    private OADepartmentMember chargeLeader;//部门分管理领导
    
    private OADepartmentMember executiveLeader;//部门主管领导
    
    private OADepartmentMember comChargeLeader;//公司分管领导
    
    private OADepartmentMember comExecutiveLeader;//公司主管领导
    
    private OADepartmentMember projectOffice;//公司主管领导
    
    private OADepartmentMember department;//所属部门
    
    private String oaUserId;
    
    private String oaUserName;
    
    private String oaOrgId;
    
    public String getProjectOfficeName() {
		return projectOfficeName;
	}

	public void setProjectOfficeName(String projectOfficeName) {
		this.projectOfficeName = projectOfficeName;
	}

	public OADepartmentMember getProjectOffice() {
		return projectOffice;
	}

	public void setProjectOffice(OADepartmentMember projectOffice) {
		this.projectOffice = projectOffice;
	}

	public OADepartmentMember getComExecutiveLeader() {
		return comExecutiveLeader;
	}

	public void setComExecutiveLeader(OADepartmentMember comExecutiveLeader) {
		this.comExecutiveLeader = comExecutiveLeader;
	}

	public String getComExecutiveLeaderName() {
		return comExecutiveLeaderName;
	}

	public void setComExecutiveLeaderName(String comExecutiveLeaderName) {
		this.comExecutiveLeaderName = comExecutiveLeaderName;
	}

	public OADepartmentMember getProjectStaff() {
		return projectStaff;
	}

	public void setProjectStaff(OADepartmentMember projectStaff) {
		this.projectStaff = projectStaff;
	}

	public String getStaffName() {
		return staffName;
	}

	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	public String getCadreName() {
		return cadreName;
	}

	public void setCadreName(String cadreName) {
		this.cadreName = cadreName;
	}

	public OADepartmentMember getProectCadre() {
		return proectCadre;
	}

	public void setProectCadre(OADepartmentMember proectCadre) {
		this.proectCadre = proectCadre;
	}

	public OADepartmentMember getComChargeLeader() {
		return comChargeLeader;
	}

	public void setComChargeLeader(OADepartmentMember comChargeLeader) {
		this.comChargeLeader = comChargeLeader;
	}

	public String getUserKey() {
		return userKey;
	}

	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}

	public OADepartmentMember getDepartmentHead() {
		return departmentHead;
	}

	public void setDepartmentHead(OADepartmentMember departmentHead) {
		this.departmentHead = departmentHead;
	}

	public OADepartmentMember getChargeLeader() {
		return chargeLeader;
	}

	public void setChargeLeader(OADepartmentMember chargeLeader) {
		this.chargeLeader = chargeLeader;
	}

	public OADepartmentMember getExecutiveLeader() {
		return executiveLeader;
	}

	public void setExecutiveLeader(OADepartmentMember executiveLeader) {
		this.executiveLeader = executiveLeader;
	}

	public OADepartmentMember getDepartment() {
		return department;
	}

	public void setDepartment(OADepartmentMember department) {
		this.department = department;
	}

	public String getOaUserId() {
		return oaUserId;
	}

	public void setOaUserId(String oaUserId) {
		this.oaUserId = oaUserId;
	}

	public String getOaUserName() {
		return oaUserName;
	}

	public void setOaUserName(String oaUserName) {
		this.oaUserName = oaUserName;
	}

	public String getOaOrgId() {
		return oaOrgId;
	}

	public void setOaOrgId(String oaOrgId) {
		this.oaOrgId = oaOrgId;
	}

	public String getDepartmentHeadId() {
        return departmentHeadId == null ? "" : departmentHeadId.trim();
    }

    public void setDepartmentHeadId(String departmentHeadId) {
        this.departmentHeadId = departmentHeadId == null ? null : departmentHeadId.trim();
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

    public String getDepartmentOrgId() {
        return departmentOrgId == null ? "" : departmentOrgId.trim();
    }

    public void setDepartmentOrgId(String departmentOrgId) {
        this.departmentOrgId = departmentOrgId == null ? null : departmentOrgId.trim();
    }

    public String getUserName() {
        return userName == null ? "" : userName.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getChargeLeaderName() {
        return chargeLeaderName == null ? "" : chargeLeaderName.trim();
    }

    public void setChargeLeaderName(String chargeLeaderName) {
        this.chargeLeaderName = chargeLeaderName == null ? null : chargeLeaderName.trim();
    }

    public String getExecutiveLeaderName() {
        return executiveLeaderName == null ? "" : executiveLeaderName.trim();
    }

    public void setExecutiveLeaderName(String executiveLeaderName) {
        this.executiveLeaderName = executiveLeaderName == null ? null : executiveLeaderName.trim();
    }

    public String getComChargeLeaderName() {
        return comChargeLeaderName == null ? "" : comChargeLeaderName.trim();
    }

    public void setComChargeLeaderName(String comChargeLeaderName) {
        this.comChargeLeaderName = comChargeLeaderName == null ? null : comChargeLeaderName.trim();
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


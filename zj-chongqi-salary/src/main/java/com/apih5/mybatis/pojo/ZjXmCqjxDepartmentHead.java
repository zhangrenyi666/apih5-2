package com.apih5.mybatis.pojo;

import java.util.Date;

import com.apih5.framework.api.zjoa.entity.OADepartmentMember;
import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxDepartmentHead extends BasePojo {
    private String departmentHeadId;

    private String departmentId;

    private String departmentName;

    private String departmentOrgId;

    private String userKey;

    private String userName;

    private String userOrgId;
    
    private String chargeLeaderId;
    
    private String chargeLeaderName;
    
    private String chargeLeaderOrgId;
    
    private String executiveLeaderId;
    
    private String executiveLeaderName;
    
    private String executiveLeaderOrgId;
    
    private String departmentLeaderName;
    
    private String projectLeaderName;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private OADepartmentMember departmentHead;//部门负责人
    
    private OADepartmentMember chargeLeader;//部门分管理领导
    
    private OADepartmentMember executiveLeader;//部门主管领导
    
    private OADepartmentMember departmentLeader;//副总师、经理助理和部门正、副职
    
    private OADepartmentMember projectLeader;//所属项目正职
    
    private OADepartmentMember department;//所属部门
    
    private String oaUserId;
    
    private String oaUserName;
    
    private String oaOrgId;
    
    public String getDepartmentLeaderName() {
		return departmentLeaderName;
	}

	public void setDepartmentLeaderName(String departmentLeaderName) {
		this.departmentLeaderName = departmentLeaderName;
	}

	public String getProjectLeaderName() {
		return projectLeaderName;
	}

	public void setProjectLeaderName(String projectLeaderName) {
		this.projectLeaderName = projectLeaderName;
	}

	public OADepartmentMember getDepartmentLeader() {
		return departmentLeader;
	}

	public void setDepartmentLeader(OADepartmentMember departmentLeader) {
		this.departmentLeader = departmentLeader;
	}

	public OADepartmentMember getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(OADepartmentMember projectLeader) {
		this.projectLeader = projectLeader;
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

	public String getChargeLeaderId() {
		return chargeLeaderId;
	}

	public void setChargeLeaderId(String chargeLeaderId) {
		this.chargeLeaderId = chargeLeaderId;
	}

	public String getChargeLeaderName() {
		return chargeLeaderName;
	}

	public void setChargeLeaderName(String chargeLeaderName) {
		this.chargeLeaderName = chargeLeaderName;
	}

	public String getChargeLeaderOrgId() {
		return chargeLeaderOrgId;
	}

	public void setChargeLeaderOrgId(String chargeLeaderOrgId) {
		this.chargeLeaderOrgId = chargeLeaderOrgId;
	}

	public String getExecutiveLeaderId() {
		return executiveLeaderId;
	}

	public void setExecutiveLeaderId(String executiveLeaderId) {
		this.executiveLeaderId = executiveLeaderId;
	}

	public String getExecutiveLeaderName() {
		return executiveLeaderName;
	}

	public void setExecutiveLeaderName(String executiveLeaderName) {
		this.executiveLeaderName = executiveLeaderName;
	}

	public String getExecutiveLeaderOrgId() {
		return executiveLeaderOrgId;
	}

	public void setExecutiveLeaderOrgId(String executiveLeaderOrgId) {
		this.executiveLeaderOrgId = executiveLeaderOrgId;
	}

	public OADepartmentMember getDepartmentHead() {
		return departmentHead;
	}

	public void setDepartmentHead(OADepartmentMember departmentHead) {
		this.departmentHead = departmentHead;
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

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    public String getUserName() {
        return userName == null ? "" : userName.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserOrgId() {
        return userOrgId == null ? "" : userOrgId.trim();
    }

    public void setUserOrgId(String userOrgId) {
        this.userOrgId = userOrgId == null ? null : userOrgId.trim();
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


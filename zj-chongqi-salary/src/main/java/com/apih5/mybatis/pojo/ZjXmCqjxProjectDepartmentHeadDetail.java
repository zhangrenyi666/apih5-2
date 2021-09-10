package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectDepartmentHeadDetail extends BasePojo {
    private String deptDetailId;

    private String otherId;

    private String otherType;

    private String oaDepartmentMemberFlag;

    private String oaUserId;

    private String oaUserName;

    private String oaOrgId;

    private String oaOrgName;
    
    private String oaParentId;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getOaParentId() {
		return oaParentId;
	}

	public void setOaParentId(String oaParentId) {
		this.oaParentId = oaParentId;
	}

	public String getDeptDetailId() {
        return deptDetailId == null ? "" : deptDetailId.trim();
    }

    public void setDeptDetailId(String deptDetailId) {
        this.deptDetailId = deptDetailId == null ? null : deptDetailId.trim();
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

    public String getOaOrgName() {
        return oaOrgName == null ? "" : oaOrgName.trim();
    }

    public void setOaOrgName(String oaOrgName) {
        this.oaOrgName = oaOrgName == null ? null : oaOrgName.trim();
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


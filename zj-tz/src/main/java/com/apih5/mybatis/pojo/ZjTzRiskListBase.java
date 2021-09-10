package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzRiskListBase extends BasePojo {
    private String riskListBaseId;

    private String typeId;

    private String typeName;

    private String riskName;

    private String managLever;

    private String applicableItemType;

    private String remarks;

    private String lockFlag;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String groupByFlagTypeId;

    private String number;

    private List<ZjTzRiskListBase> children;

    private String mainFlag;
    
    public String getRiskListBaseId() {
        return riskListBaseId == null ? "" : riskListBaseId.trim();
    }

    public void setRiskListBaseId(String riskListBaseId) {
        this.riskListBaseId = riskListBaseId == null ? null : riskListBaseId.trim();
    }

    public String getTypeId() {
        return typeId == null ? "" : typeId.trim();
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId == null ? null : typeId.trim();
    }

    public String getTypeName() {
        return typeName == null ? "" : typeName.trim();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

    public String getRiskName() {
        return riskName == null ? "" : riskName.trim();
    }

    public void setRiskName(String riskName) {
        this.riskName = riskName == null ? null : riskName.trim();
    }

    public String getManagLever() {
        return managLever == null ? "" : managLever.trim();
    }

    public void setManagLever(String managLever) {
        this.managLever = managLever == null ? null : managLever.trim();
    }

    public String getApplicableItemType() {
        return applicableItemType == null ? "" : applicableItemType.trim();
    }

    public void setApplicableItemType(String applicableItemType) {
        this.applicableItemType = applicableItemType == null ? null : applicableItemType.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getLockFlag() {
        return lockFlag == null ? "" : lockFlag.trim();
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag == null ? null : lockFlag.trim();
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

    public String getGroupByFlagTypeId() {
        return groupByFlagTypeId == null ? "" : groupByFlagTypeId.trim();
    }

    public void setGroupByFlagTypeId(String groupByFlagTypeId) {
        this.groupByFlagTypeId = groupByFlagTypeId == null ? null : groupByFlagTypeId.trim();
    }

    public String getNumber() {
        return number == null ? "" : number.trim();
    }

    public void setNumber(String number) {
        this.number = number == null ? null : number.trim();
    }

    public List<ZjTzRiskListBase> getChildren() {
        return children;
    }

    public void setChildren(List<ZjTzRiskListBase> children) {
        this.children = children;
    }

	public String getMainFlag() {
		return mainFlag;
	}

	public void setMainFlag(String mainFlag) {
		this.mainFlag = mainFlag;
	}
    
}


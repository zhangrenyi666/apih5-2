package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzRiskDetail extends BasePojo {
    private String riskDetailId;

    private String riskId;

    private String typeId;

    private String typeName;

    private String riskName;

    private String managLever;

    private String applicableItemType;

    private String remarks;

    private String existRiskFlag;

    private String specificDesc;

    private Date planTime;

    private Date actualTime;

    private String lockFlag;

    private String addFlag;

    private String solution;

    private String personInCharge;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String uncompletedAnalysis;

    private String resultAnalysis;

    private List<ZjTzFile> zjTzFileList;

    private String colourFlag;

    private String groupByFlagTypeId;

    private String number;

    private List<ZjTzRiskDetail> children;

    private String mainFlag;

    private List<ZjTzRiskDetail> zjTzRiskDetailList;
    
    private String projectNameAndSub;
    
    private String planTimeName;

    private String actualTimeName;

    public String getRiskDetailId() {
        return riskDetailId == null ? "" : riskDetailId.trim();
    }

    public void setRiskDetailId(String riskDetailId) {
        this.riskDetailId = riskDetailId == null ? null : riskDetailId.trim();
    }

    public String getRiskId() {
        return riskId == null ? "" : riskId.trim();
    }

    public void setRiskId(String riskId) {
        this.riskId = riskId == null ? null : riskId.trim();
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

    public String getExistRiskFlag() {
        return existRiskFlag == null ? "" : existRiskFlag.trim();
    }

    public void setExistRiskFlag(String existRiskFlag) {
        this.existRiskFlag = existRiskFlag == null ? null : existRiskFlag.trim();
    }

    public String getSpecificDesc() {
        return specificDesc == null ? "" : specificDesc.trim();
    }

    public void setSpecificDesc(String specificDesc) {
        this.specificDesc = specificDesc == null ? null : specificDesc.trim();
    }

    public Date getPlanTime() {
        return planTime;
    }

    public void setPlanTime(Date planTime) {
        this.planTime = planTime;
    }

    public Date getActualTime() {
        return actualTime;
    }

    public void setActualTime(Date actualTime) {
        this.actualTime = actualTime;
    }

    public String getLockFlag() {
        return lockFlag == null ? "" : lockFlag.trim();
    }

    public void setLockFlag(String lockFlag) {
        this.lockFlag = lockFlag == null ? null : lockFlag.trim();
    }

    public String getAddFlag() {
        return addFlag == null ? "" : addFlag.trim();
    }

    public void setAddFlag(String addFlag) {
        this.addFlag = addFlag == null ? null : addFlag.trim();
    }

    public String getSolution() {
        return solution == null ? "" : solution.trim();
    }

    public void setSolution(String solution) {
        this.solution = solution == null ? null : solution.trim();
    }

    public String getPersonInCharge() {
        return personInCharge == null ? "" : personInCharge.trim();
    }

    public void setPersonInCharge(String personInCharge) {
        this.personInCharge = personInCharge == null ? null : personInCharge.trim();
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

    public String getUncompletedAnalysis() {
        return uncompletedAnalysis == null ? "" : uncompletedAnalysis.trim();
    }

    public void setUncompletedAnalysis(String uncompletedAnalysis) {
        this.uncompletedAnalysis = uncompletedAnalysis == null ? null : uncompletedAnalysis.trim();
    }

    public String getResultAnalysis() {
        return resultAnalysis == null ? "" : resultAnalysis.trim();
    }

    public void setResultAnalysis(String resultAnalysis) {
        this.resultAnalysis = resultAnalysis == null ? null : resultAnalysis.trim();
    }

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }

    public String getColourFlag() {
        return colourFlag == null ? "" : colourFlag.trim();
    }

    public void setColourFlag(String colourFlag) {
        this.colourFlag = colourFlag == null ? null : colourFlag.trim();
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

    public List<ZjTzRiskDetail> getChildren() {
        return children;
    }

    public void setChildren(List<ZjTzRiskDetail> children) {
        this.children = children;
    }

    public String getMainFlag() {
        return mainFlag == null ? "" : mainFlag.trim();
    }

    public void setMainFlag(String mainFlag) {
        this.mainFlag = mainFlag == null ? null : mainFlag.trim();
    }

    public List<ZjTzRiskDetail> getZjTzRiskDetailList() {
        return zjTzRiskDetailList;
    }

    public void setZjTzRiskDetailList(List<ZjTzRiskDetail> zjTzRiskDetailList) {
        this.zjTzRiskDetailList = zjTzRiskDetailList;
    }
    
	public String getProjectNameAndSub() {
		return projectNameAndSub == null ? "" : projectNameAndSub.trim();
	}

	public void setProjectNameAndSub(String projectNameAndSub) {
		this.projectNameAndSub = projectNameAndSub == null ? null : projectNameAndSub.trim();
	}

	public String getPlanTimeName() {
		return planTimeName == null ? "" : planTimeName.trim();
	}

	public void setPlanTimeName(String planTimeName) {
		this.planTimeName = planTimeName == null ? null : planTimeName.trim();
	}

	public String getActualTimeName() {
		return actualTimeName == null ? "" : actualTimeName.trim();
	}

	public void setActualTimeName(String actualTimeName) {
		this.actualTimeName = actualTimeName == null ? null : actualTimeName.trim();
	}
	
}
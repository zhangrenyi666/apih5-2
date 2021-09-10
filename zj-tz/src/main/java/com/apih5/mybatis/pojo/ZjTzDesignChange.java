package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzDesignChange extends BasePojo {
    private String designChangeId;

    private String projectId;

    private String projectName;

    private String subprojectInfoId;

    private String subprojectName;

    private String designStageId;

    private String designStageName;

    private Integer changeNum;

    private String designChangeNumber;

    private String designChangeName;

    private String changeLevelId;

    private String changeLevelName;

    private String changeNatureId;

    private String changeNatureName;

    private String dynamicId;

    private String dynamicName;

    private BigDecimal changeAmount;

    private Date changeDate;

    private String innerCheckId;

    private String innerCheckName;

    private String legalId;

    private String legalName;

    private String content;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private String projectIdSql;
    
    // 新增字段：是否更新
    private String renew1;
    
    private String renew2;
    
    private String renew3;
    
    private String renew4;
    
    public String getDesignChangeId() {
        return designChangeId == null ? "" : designChangeId.trim();
    }

    public void setDesignChangeId(String designChangeId) {
        this.designChangeId = designChangeId == null ? null : designChangeId.trim();
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

    public String getSubprojectInfoId() {
        return subprojectInfoId == null ? "" : subprojectInfoId.trim();
    }

    public void setSubprojectInfoId(String subprojectInfoId) {
        this.subprojectInfoId = subprojectInfoId == null ? null : subprojectInfoId.trim();
    }

    public String getSubprojectName() {
        return subprojectName == null ? "" : subprojectName.trim();
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName == null ? null : subprojectName.trim();
    }

    public String getDesignStageId() {
        return designStageId == null ? "" : designStageId.trim();
    }

    public void setDesignStageId(String designStageId) {
        this.designStageId = designStageId == null ? null : designStageId.trim();
    }

    public String getDesignStageName() {
        return designStageName == null ? "" : designStageName.trim();
    }

    public void setDesignStageName(String designStageName) {
        this.designStageName = designStageName == null ? null : designStageName.trim();
    }

    public Integer getChangeNum() {
        return changeNum;
    }

    public void setChangeNum(Integer changeNum) {
        this.changeNum = changeNum;
    }

    public String getDesignChangeNumber() {
        return designChangeNumber == null ? "" : designChangeNumber.trim();
    }

    public void setDesignChangeNumber(String designChangeNumber) {
        this.designChangeNumber = designChangeNumber == null ? null : designChangeNumber.trim();
    }

    public String getDesignChangeName() {
        return designChangeName == null ? "" : designChangeName.trim();
    }

    public void setDesignChangeName(String designChangeName) {
        this.designChangeName = designChangeName == null ? null : designChangeName.trim();
    }

    public String getChangeLevelId() {
        return changeLevelId == null ? "" : changeLevelId.trim();
    }

    public void setChangeLevelId(String changeLevelId) {
        this.changeLevelId = changeLevelId == null ? null : changeLevelId.trim();
    }

    public String getChangeLevelName() {
        return changeLevelName == null ? "" : changeLevelName.trim();
    }

    public void setChangeLevelName(String changeLevelName) {
        this.changeLevelName = changeLevelName == null ? null : changeLevelName.trim();
    }

    public String getChangeNatureId() {
        return changeNatureId == null ? "" : changeNatureId.trim();
    }

    public void setChangeNatureId(String changeNatureId) {
        this.changeNatureId = changeNatureId == null ? null : changeNatureId.trim();
    }

    public String getChangeNatureName() {
        return changeNatureName == null ? "" : changeNatureName.trim();
    }

    public void setChangeNatureName(String changeNatureName) {
        this.changeNatureName = changeNatureName == null ? null : changeNatureName.trim();
    }

    public String getDynamicId() {
        return dynamicId == null ? "" : dynamicId.trim();
    }

    public void setDynamicId(String dynamicId) {
        this.dynamicId = dynamicId == null ? null : dynamicId.trim();
    }

    public String getDynamicName() {
        return dynamicName == null ? "" : dynamicName.trim();
    }

    public void setDynamicName(String dynamicName) {
        this.dynamicName = dynamicName == null ? null : dynamicName.trim();
    }

    public BigDecimal getChangeAmount() {
        return changeAmount;
    }

    public void setChangeAmount(BigDecimal changeAmount) {
        this.changeAmount = changeAmount;
    }

    public Date getChangeDate() {
        return changeDate;
    }

    public void setChangeDate(Date changeDate) {
        this.changeDate = changeDate;
    }

    public String getInnerCheckId() {
        return innerCheckId == null ? "" : innerCheckId.trim();
    }

    public void setInnerCheckId(String innerCheckId) {
        this.innerCheckId = innerCheckId == null ? null : innerCheckId.trim();
    }

    public String getInnerCheckName() {
        return innerCheckName == null ? "" : innerCheckName.trim();
    }

    public void setInnerCheckName(String innerCheckName) {
        this.innerCheckName = innerCheckName == null ? null : innerCheckName.trim();
    }

    public String getLegalId() {
        return legalId == null ? "" : legalId.trim();
    }

    public void setLegalId(String legalId) {
        this.legalId = legalId == null ? null : legalId.trim();
    }

    public String getLegalName() {
        return legalName == null ? "" : legalName.trim();
    }

    public void setLegalName(String legalName) {
        this.legalName = legalName == null ? null : legalName.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
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

    public List<ZjTzFile> getZjTzFileList() {
        return zjTzFileList;
    }

    public void setZjTzFileList(List<ZjTzFile> zjTzFileList) {
        this.zjTzFileList = zjTzFileList;
    }
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

	public String getRenew1() {
		return renew1 == null ? "" : renew1.trim();
	}

	public void setRenew1(String renew1) {
		this.renew1 = renew1 == null ? null : renew1.trim();
	}

	public String getRenew2() {
		return renew2 == null ? "" : renew2.trim();
	}

	public void setRenew2(String renew2) {
		this.renew2 = renew2 == null ? null : renew2.trim();
	}

	public String getRenew3() {
		return renew3 == null ? "" : renew3.trim();
	}

	public void setRenew3(String renew3) {
		this.renew3 = renew3 == null ? null : renew3.trim();
	}

	public String getRenew4() {
		return renew4 == null ? "" : renew4.trim();
	}

	public void setRenew4(String renew4) {
		this.renew4 = renew4 == null ? null : renew4.trim();
	}
    
}


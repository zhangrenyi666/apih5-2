package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzDesignAdvistoryUnitRecord extends BasePojo {
    private String designAdvistoryUnitRecordId;

    private String designAdvistoryUnitId;

    private String typeId;

    private String typeName;

    private String designAdvistoryUnitStandardId;

    private String projectId;

    private String projectName;

    private String subprojectInfoId;

    private String subprojectName;

    private String designStageId;

    private String designStageName;

    private String selectModeId;

    private String selectModeName;

    private String content;

    private BigDecimal amount1;

    private BigDecimal amount2;

    private String proLeader;

    private String proLeaderTel;

    private String evaluateOrderId;

    private String evaluateOrderName;

    private String evaluatePerson;

    private Date evaluateDate;

    private String remarks;

    private String releaseId;

    private String releaseName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> zjTzFileList;

    private String unitName;

    private String orgCode;

    private List<ZjTzQuality> zjTzQualityList;

    private String correspondQualityName;
    
    private String ext1SeeFlag;
    
    private String ext1Flag1;
    
    private String ext1Flag2;
    
    private String ext1Flag3;
    // 新增字段：是否更新
    private String renew1;
    
    private String renew2;
    
    private String renew3;
    
    private String renew4;
    
    public String getDesignAdvistoryUnitRecordId() {
        return designAdvistoryUnitRecordId == null ? "" : designAdvistoryUnitRecordId.trim();
    }

    public void setDesignAdvistoryUnitRecordId(String designAdvistoryUnitRecordId) {
        this.designAdvistoryUnitRecordId = designAdvistoryUnitRecordId == null ? null : designAdvistoryUnitRecordId.trim();
    }

    public String getDesignAdvistoryUnitId() {
        return designAdvistoryUnitId == null ? "" : designAdvistoryUnitId.trim();
    }

    public void setDesignAdvistoryUnitId(String designAdvistoryUnitId) {
        this.designAdvistoryUnitId = designAdvistoryUnitId == null ? null : designAdvistoryUnitId.trim();
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

    public String getDesignAdvistoryUnitStandardId() {
        return designAdvistoryUnitStandardId == null ? "" : designAdvistoryUnitStandardId.trim();
    }

    public void setDesignAdvistoryUnitStandardId(String designAdvistoryUnitStandardId) {
        this.designAdvistoryUnitStandardId = designAdvistoryUnitStandardId == null ? null : designAdvistoryUnitStandardId.trim();
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

    public String getSelectModeId() {
        return selectModeId == null ? "" : selectModeId.trim();
    }

    public void setSelectModeId(String selectModeId) {
        this.selectModeId = selectModeId == null ? null : selectModeId.trim();
    }

    public String getSelectModeName() {
        return selectModeName == null ? "" : selectModeName.trim();
    }

    public void setSelectModeName(String selectModeName) {
        this.selectModeName = selectModeName == null ? null : selectModeName.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public BigDecimal getAmount1() {
        return amount1;
    }

    public void setAmount1(BigDecimal amount1) {
        this.amount1 = amount1;
    }

    public BigDecimal getAmount2() {
        return amount2;
    }

    public void setAmount2(BigDecimal amount2) {
        this.amount2 = amount2;
    }

    public String getProLeader() {
        return proLeader == null ? "" : proLeader.trim();
    }

    public void setProLeader(String proLeader) {
        this.proLeader = proLeader == null ? null : proLeader.trim();
    }

    public String getProLeaderTel() {
        return proLeaderTel == null ? "" : proLeaderTel.trim();
    }

    public void setProLeaderTel(String proLeaderTel) {
        this.proLeaderTel = proLeaderTel == null ? null : proLeaderTel.trim();
    }

    public String getEvaluateOrderId() {
        return evaluateOrderId == null ? "" : evaluateOrderId.trim();
    }

    public void setEvaluateOrderId(String evaluateOrderId) {
        this.evaluateOrderId = evaluateOrderId == null ? null : evaluateOrderId.trim();
    }

    public String getEvaluateOrderName() {
        return evaluateOrderName == null ? "" : evaluateOrderName.trim();
    }

    public void setEvaluateOrderName(String evaluateOrderName) {
        this.evaluateOrderName = evaluateOrderName == null ? null : evaluateOrderName.trim();
    }

    public String getEvaluatePerson() {
        return evaluatePerson == null ? "" : evaluatePerson.trim();
    }

    public void setEvaluatePerson(String evaluatePerson) {
        this.evaluatePerson = evaluatePerson == null ? null : evaluatePerson.trim();
    }

    public Date getEvaluateDate() {
        return evaluateDate;
    }

    public void setEvaluateDate(Date evaluateDate) {
        this.evaluateDate = evaluateDate;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getReleaseId() {
        return releaseId == null ? "" : releaseId.trim();
    }

    public void setReleaseId(String releaseId) {
        this.releaseId = releaseId == null ? null : releaseId.trim();
    }

    public String getReleaseName() {
        return releaseName == null ? "" : releaseName.trim();
    }

    public void setReleaseName(String releaseName) {
        this.releaseName = releaseName == null ? null : releaseName.trim();
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

    public String getUnitName() {
        return unitName == null ? "" : unitName.trim();
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName == null ? null : unitName.trim();
    }

    public String getOrgCode() {
        return orgCode == null ? "" : orgCode.trim();
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode == null ? null : orgCode.trim();
    }

    public List<ZjTzQuality> getZjTzQualityList() {
        return zjTzQualityList;
    }

    public void setZjTzQualityList(List<ZjTzQuality> zjTzQualityList) {
        this.zjTzQualityList = zjTzQualityList;
    }

    public String getCorrespondQualityName() {
        return correspondQualityName == null ? "" : correspondQualityName.trim();
    }

    public void setCorrespondQualityName(String correspondQualityName) {
        this.correspondQualityName = correspondQualityName == null ? null : correspondQualityName.trim();
    }
    
    public String getExt1SeeFlag() {
		return ext1SeeFlag == null ? "" : ext1SeeFlag.trim();
	}

	public void setExt1SeeFlag(String ext1SeeFlag) {
		this.ext1SeeFlag = ext1SeeFlag == null ? null : ext1SeeFlag.trim();
	}

	public String getExt1Flag1() {
		return ext1Flag1 == null ? "" : ext1Flag1.trim();
	}

	public void setExt1Flag1(String ext1Flag1) {
		this.ext1Flag1 = ext1Flag1 == null ? null : ext1Flag1.trim();
	}

	public String getExt1Flag2() {
		return ext1Flag2 == null ? "" : ext1Flag2.trim();
	}

	public void setExt1Flag2(String ext1Flag2) {
		this.ext1Flag2 = ext1Flag2 == null ? null : ext1Flag2.trim();
	}

	public String getExt1Flag3() {
		return ext1Flag3 == null ? "" : ext1Flag3.trim();
	}

	public void setExt1Flag3(String ext1Flag3) {
		this.ext1Flag3 = ext1Flag3 == null ? null : ext1Flag3.trim();
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


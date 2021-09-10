package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzSizeControl extends BasePojo {
    private String sizeControlId;

    private String projectId;

    private String subprojectInfoId;

    private String subprojectName;

    private Integer changeNumber;

    private String changePropertyId;

    private String changePropertyName;

    private BigDecimal amount1;

    private BigDecimal amount2;

    private BigDecimal amount3;

    private String secondNegotiateId;

    private String secondNegotiateName;

    private String scheme;

    private String thirdReplyId;

    private String thirdReplyName;

    private String localGovId;

    private String localGovName;

    private String juId;

    private String juName;

    private String chinaId;

    private String chinaName;

    private Date registerDate;

    private String registrant;

    private String zeroChangePropertyId;

    private String zeroChangePropertyName;

    private BigDecimal zeroAmount1;

    private BigDecimal zeroAmount2;

    private BigDecimal zeroAmount3;

    private String lastChangePropertyId;

    private String lastChangePropertyName;

    private BigDecimal lastAmount1;

    private BigDecimal lastAmount2;

    private BigDecimal lastAmount3;

    private String addFlag;

    private String remarks;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private List<ZjTzFile> schemeFileList;

    private List<ZjTzFile> thirdReplyFileList;

    private List<ZjTzFile> localGovFileList;

    private List<ZjTzFile> juFileList;

    private List<ZjTzFile> chinaFileList;

    private String contractConditionId;

    private Date registerDate1;

    private String registrant1;

    private String investId;

    private String investName;

    private BigDecimal juShare;

    private String juId1;

    private String juName1;

    private String zcbId;

    private String zcbName;

    private BigDecimal zcbShare;

    private String ext1;

    private String ext2;

    private String ext3;

    private List<ZjTzFile> zjTzFileList;

    private String projectIdSql;
    
    private String projectName;
    
    // 规模控制主体
    private String sizeControlSubject;
    // 新增字段：是否更新
    private String renew1;
    
    private String renew2;
    
    private String renew3;
    
    private String renew4;
    
    public String getSizeControlId() {
        return sizeControlId == null ? "" : sizeControlId.trim();
    }

    public void setSizeControlId(String sizeControlId) {
        this.sizeControlId = sizeControlId == null ? null : sizeControlId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
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

    public Integer getChangeNumber() {
        return changeNumber;
    }

    public void setChangeNumber(Integer changeNumber) {
        this.changeNumber = changeNumber;
    }

    public String getChangePropertyId() {
        return changePropertyId == null ? "" : changePropertyId.trim();
    }

    public void setChangePropertyId(String changePropertyId) {
        this.changePropertyId = changePropertyId == null ? null : changePropertyId.trim();
    }

    public String getChangePropertyName() {
        return changePropertyName == null ? "" : changePropertyName.trim();
    }

    public void setChangePropertyName(String changePropertyName) {
        this.changePropertyName = changePropertyName == null ? null : changePropertyName.trim();
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

    public BigDecimal getAmount3() {
        return amount3;
    }

    public void setAmount3(BigDecimal amount3) {
        this.amount3 = amount3;
    }

    public String getSecondNegotiateId() {
        return secondNegotiateId == null ? "" : secondNegotiateId.trim();
    }

    public void setSecondNegotiateId(String secondNegotiateId) {
        this.secondNegotiateId = secondNegotiateId == null ? null : secondNegotiateId.trim();
    }

    public String getSecondNegotiateName() {
        return secondNegotiateName == null ? "" : secondNegotiateName.trim();
    }

    public void setSecondNegotiateName(String secondNegotiateName) {
        this.secondNegotiateName = secondNegotiateName == null ? null : secondNegotiateName.trim();
    }

    public String getScheme() {
        return scheme == null ? "" : scheme.trim();
    }

    public void setScheme(String scheme) {
        this.scheme = scheme == null ? null : scheme.trim();
    }

    public String getThirdReplyId() {
        return thirdReplyId == null ? "" : thirdReplyId.trim();
    }

    public void setThirdReplyId(String thirdReplyId) {
        this.thirdReplyId = thirdReplyId == null ? null : thirdReplyId.trim();
    }

    public String getThirdReplyName() {
        return thirdReplyName == null ? "" : thirdReplyName.trim();
    }

    public void setThirdReplyName(String thirdReplyName) {
        this.thirdReplyName = thirdReplyName == null ? null : thirdReplyName.trim();
    }

    public String getLocalGovId() {
        return localGovId == null ? "" : localGovId.trim();
    }

    public void setLocalGovId(String localGovId) {
        this.localGovId = localGovId == null ? null : localGovId.trim();
    }

    public String getLocalGovName() {
        return localGovName == null ? "" : localGovName.trim();
    }

    public void setLocalGovName(String localGovName) {
        this.localGovName = localGovName == null ? null : localGovName.trim();
    }

    public String getJuId() {
        return juId == null ? "" : juId.trim();
    }

    public void setJuId(String juId) {
        this.juId = juId == null ? null : juId.trim();
    }

    public String getJuName() {
        return juName == null ? "" : juName.trim();
    }

    public void setJuName(String juName) {
        this.juName = juName == null ? null : juName.trim();
    }

    public String getChinaId() {
        return chinaId == null ? "" : chinaId.trim();
    }

    public void setChinaId(String chinaId) {
        this.chinaId = chinaId == null ? null : chinaId.trim();
    }

    public String getChinaName() {
        return chinaName == null ? "" : chinaName.trim();
    }

    public void setChinaName(String chinaName) {
        this.chinaName = chinaName == null ? null : chinaName.trim();
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegistrant() {
        return registrant == null ? "" : registrant.trim();
    }

    public void setRegistrant(String registrant) {
        this.registrant = registrant == null ? null : registrant.trim();
    }

    public String getZeroChangePropertyId() {
        return zeroChangePropertyId == null ? "" : zeroChangePropertyId.trim();
    }

    public void setZeroChangePropertyId(String zeroChangePropertyId) {
        this.zeroChangePropertyId = zeroChangePropertyId == null ? null : zeroChangePropertyId.trim();
    }

    public String getZeroChangePropertyName() {
        return zeroChangePropertyName == null ? "" : zeroChangePropertyName.trim();
    }

    public void setZeroChangePropertyName(String zeroChangePropertyName) {
        this.zeroChangePropertyName = zeroChangePropertyName == null ? null : zeroChangePropertyName.trim();
    }

    public BigDecimal getZeroAmount1() {
        return zeroAmount1;
    }

    public void setZeroAmount1(BigDecimal zeroAmount1) {
        this.zeroAmount1 = zeroAmount1;
    }

    public BigDecimal getZeroAmount2() {
        return zeroAmount2;
    }

    public void setZeroAmount2(BigDecimal zeroAmount2) {
        this.zeroAmount2 = zeroAmount2;
    }

    public BigDecimal getZeroAmount3() {
        return zeroAmount3;
    }

    public void setZeroAmount3(BigDecimal zeroAmount3) {
        this.zeroAmount3 = zeroAmount3;
    }

    public String getLastChangePropertyId() {
        return lastChangePropertyId == null ? "" : lastChangePropertyId.trim();
    }

    public void setLastChangePropertyId(String lastChangePropertyId) {
        this.lastChangePropertyId = lastChangePropertyId == null ? null : lastChangePropertyId.trim();
    }

    public String getLastChangePropertyName() {
        return lastChangePropertyName == null ? "" : lastChangePropertyName.trim();
    }

    public void setLastChangePropertyName(String lastChangePropertyName) {
        this.lastChangePropertyName = lastChangePropertyName == null ? null : lastChangePropertyName.trim();
    }

    public BigDecimal getLastAmount1() {
        return lastAmount1;
    }

    public void setLastAmount1(BigDecimal lastAmount1) {
        this.lastAmount1 = lastAmount1;
    }

    public BigDecimal getLastAmount2() {
        return lastAmount2;
    }

    public void setLastAmount2(BigDecimal lastAmount2) {
        this.lastAmount2 = lastAmount2;
    }

    public BigDecimal getLastAmount3() {
        return lastAmount3;
    }

    public void setLastAmount3(BigDecimal lastAmount3) {
        this.lastAmount3 = lastAmount3;
    }

    public String getAddFlag() {
        return addFlag == null ? "" : addFlag.trim();
    }

    public void setAddFlag(String addFlag) {
        this.addFlag = addFlag == null ? null : addFlag.trim();
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

    public List<ZjTzFile> getSchemeFileList() {
        return schemeFileList;
    }

    public void setSchemeFileList(List<ZjTzFile> schemeFileList) {
        this.schemeFileList = schemeFileList;
    }

    public List<ZjTzFile> getThirdReplyFileList() {
        return thirdReplyFileList;
    }

    public void setThirdReplyFileList(List<ZjTzFile> thirdReplyFileList) {
        this.thirdReplyFileList = thirdReplyFileList;
    }

    public List<ZjTzFile> getLocalGovFileList() {
        return localGovFileList;
    }

    public void setLocalGovFileList(List<ZjTzFile> localGovFileList) {
        this.localGovFileList = localGovFileList;
    }

    public List<ZjTzFile> getJuFileList() {
        return juFileList;
    }

    public void setJuFileList(List<ZjTzFile> juFileList) {
        this.juFileList = juFileList;
    }

    public List<ZjTzFile> getChinaFileList() {
        return chinaFileList;
    }

    public void setChinaFileList(List<ZjTzFile> chinaFileList) {
        this.chinaFileList = chinaFileList;
    }

    public String getContractConditionId() {
        return contractConditionId == null ? "" : contractConditionId.trim();
    }

    public void setContractConditionId(String contractConditionId) {
        this.contractConditionId = contractConditionId == null ? null : contractConditionId.trim();
    }

    public Date getRegisterDate1() {
        return registerDate1;
    }

    public void setRegisterDate1(Date registerDate1) {
        this.registerDate1 = registerDate1;
    }

    public String getRegistrant1() {
        return registrant1 == null ? "" : registrant1.trim();
    }

    public void setRegistrant1(String registrant1) {
        this.registrant1 = registrant1 == null ? null : registrant1.trim();
    }

    public String getInvestId() {
        return investId == null ? "" : investId.trim();
    }

    public void setInvestId(String investId) {
        this.investId = investId == null ? null : investId.trim();
    }

    public String getInvestName() {
        return investName == null ? "" : investName.trim();
    }

    public void setInvestName(String investName) {
        this.investName = investName == null ? null : investName.trim();
    }

    public BigDecimal getJuShare() {
        return juShare;
    }

    public void setJuShare(BigDecimal juShare) {
        this.juShare = juShare;
    }

    public String getJuId1() {
        return juId1 == null ? "" : juId1.trim();
    }

    public void setJuId1(String juId1) {
        this.juId1 = juId1 == null ? null : juId1.trim();
    }

    public String getJuName1() {
        return juName1 == null ? "" : juName1.trim();
    }

    public void setJuName1(String juName1) {
        this.juName1 = juName1 == null ? null : juName1.trim();
    }

    public String getZcbId() {
        return zcbId == null ? "" : zcbId.trim();
    }

    public void setZcbId(String zcbId) {
        this.zcbId = zcbId == null ? null : zcbId.trim();
    }

    public String getZcbName() {
        return zcbName == null ? "" : zcbName.trim();
    }

    public void setZcbName(String zcbName) {
        this.zcbName = zcbName == null ? null : zcbName.trim();
    }

    public BigDecimal getZcbShare() {
        return zcbShare;
    }

    public void setZcbShare(BigDecimal zcbShare) {
        this.zcbShare = zcbShare;
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
    
    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

	public String getSizeControlSubject() {
		return sizeControlSubject == null ? "" : sizeControlSubject.trim();
	}

	public void setSizeControlSubject(String sizeControlSubject) {
		this.sizeControlSubject = sizeControlSubject == null ? null : sizeControlSubject.trim();
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


package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzComplianceDeal extends BasePojo {
    private String complianceDealId;

    private String projectId;

    private String projectName;

    private String subprojectInfoId;

    private String subprojectName;

    private Date establishDate;

    private String remarks;

    private String base1;

    private String base2;

    private String base3;

    private String base4;

    private String base5;

    private String base6;

    private String base7;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String colourFlag;
    
    private String warnFlag;

    private List<ZjTzComplianceDetail> zjTzComplianceDetailList;
    
    private String projectIdSql;
    
    private String companyName;
    //环评批复预警
    private String base8;
    
    public String getComplianceDealId() {
        return complianceDealId == null ? "" : complianceDealId.trim();
    }

    public void setComplianceDealId(String complianceDealId) {
        this.complianceDealId = complianceDealId == null ? null : complianceDealId.trim();
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

    public Date getEstablishDate() {
        return establishDate;
    }

    public void setEstablishDate(Date establishDate) {
        this.establishDate = establishDate;
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public String getBase1() {
        return base1 == null ? "" : base1.trim();
    }

    public void setBase1(String base1) {
        this.base1 = base1 == null ? null : base1.trim();
    }

    public String getBase2() {
        return base2 == null ? "" : base2.trim();
    }

    public void setBase2(String base2) {
        this.base2 = base2 == null ? null : base2.trim();
    }

    public String getBase3() {
        return base3 == null ? "" : base3.trim();
    }

    public void setBase3(String base3) {
        this.base3 = base3 == null ? null : base3.trim();
    }

    public String getBase4() {
        return base4 == null ? "" : base4.trim();
    }

    public void setBase4(String base4) {
        this.base4 = base4 == null ? null : base4.trim();
    }

    public String getBase5() {
        return base5 == null ? "" : base5.trim();
    }

    public void setBase5(String base5) {
        this.base5 = base5 == null ? null : base5.trim();
    }

    public String getBase6() {
        return base6 == null ? "" : base6.trim();
    }

    public void setBase6(String base6) {
        this.base6 = base6 == null ? null : base6.trim();
    }

    public String getBase7() {
        return base7 == null ? "" : base7.trim();
    }

    public void setBase7(String base7) {
        this.base7 = base7 == null ? null : base7.trim();
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

    public String getColourFlag() {
        return colourFlag == null ? "" : colourFlag.trim();
    }

    public void setColourFlag(String colourFlag) {
        this.colourFlag = colourFlag == null ? null : colourFlag.trim();
    }

    public List<ZjTzComplianceDetail> getZjTzComplianceDetailList() {
        return zjTzComplianceDetailList;
    }

    public void setZjTzComplianceDetailList(List<ZjTzComplianceDetail> zjTzComplianceDetailList) {
        this.zjTzComplianceDetailList = zjTzComplianceDetailList;
    }

	public String getWarnFlag() {
		return warnFlag;
	}

	public void setWarnFlag(String warnFlag) {
		this.warnFlag = warnFlag;
	}

	public String getProjectIdSql() {
		return projectIdSql == null ? "" : projectIdSql.trim();
	}

	public void setProjectIdSql(String projectIdSql) {
		this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getBase8() {
		return base8 == null ? "" : base8.trim();
	}

	public void setBase8(String base8) {
		this.base8 = base8 == null ? null : base8.trim();
	}
	
}


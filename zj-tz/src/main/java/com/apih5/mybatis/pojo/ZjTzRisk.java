package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import com.apih5.framework.entity.BasePojo;

public class ZjTzRisk extends BasePojo {
    private String riskId;

    private String projectId;

    private String subprojectInfoId;

    private String projectName;

    private Integer num1;

    private Integer num2;

    private Integer num3;

    private Integer num4;

    private String releaseId;

    private String releaseName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    private String colourFlag;

    private List<ZjTzRiskDetail> zjTzRiskDetailList;

    private String subprojectInfoName;
    
    private String projectIdSql;
    
    private String ext1SeeFlag;

    public String getRiskId() {
        return riskId == null ? "" : riskId.trim();
    }

    public void setRiskId(String riskId) {
        this.riskId = riskId == null ? null : riskId.trim();
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

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
    }

    public Integer getNum1() {
        return num1;
    }

    public void setNum1(Integer num1) {
        this.num1 = num1;
    }

    public Integer getNum2() {
        return num2;
    }

    public void setNum2(Integer num2) {
        this.num2 = num2;
    }

    public Integer getNum3() {
        return num3;
    }

    public void setNum3(Integer num3) {
        this.num3 = num3;
    }

    public Integer getNum4() {
        return num4;
    }

    public void setNum4(Integer num4) {
        this.num4 = num4;
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

    public String getColourFlag() {
        return colourFlag == null ? "" : colourFlag.trim();
    }

    public void setColourFlag(String colourFlag) {
        this.colourFlag = colourFlag == null ? null : colourFlag.trim();
    }

    public List<ZjTzRiskDetail> getZjTzRiskDetailList() {
        return zjTzRiskDetailList;
    }

    public void setZjTzRiskDetailList(List<ZjTzRiskDetail> zjTzRiskDetailList) {
        this.zjTzRiskDetailList = zjTzRiskDetailList;
    }

    public String getSubprojectInfoName() {
        return subprojectInfoName == null ? "" : subprojectInfoName.trim();
    }

    public void setSubprojectInfoName(String subprojectInfoName) {
        this.subprojectInfoName = subprojectInfoName == null ? null : subprojectInfoName.trim();
    }
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }

    public String getExt1SeeFlag() {
		return ext1SeeFlag == null ? "" : ext1SeeFlag.trim();
	}

	public void setExt1SeeFlag(String ext1SeeFlag) {
		this.ext1SeeFlag = ext1SeeFlag == null ? null : ext1SeeFlag.trim();
	}
    

}


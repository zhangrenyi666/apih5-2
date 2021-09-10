package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjTzGeneralContractQualityDuty extends BasePojo {
    private String generalContractQualityId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String generalContractQualityTitle;

    private String generalContractQualityContent;

    private Date generalContractQualityDate;

    private String generalContractQualityUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<ZjTzFile> genConQualityFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
    public List<ZjTzFile> getGenConQualityFileList() {
		return genConQualityFileList;
	}

	public void setGenConQualityFileList(List<ZjTzFile> genConQualityFileList) {
		this.genConQualityFileList = genConQualityFileList;
	}

	public String getGeneralContractQualityId() {
        return generalContractQualityId == null ? "" : generalContractQualityId.trim();
    }

    public void setGeneralContractQualityId(String generalContractQualityId) {
        this.generalContractQualityId = generalContractQualityId == null ? null : generalContractQualityId.trim();
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

    public String getSubprojectsId() {
        return subprojectsId == null ? "" : subprojectsId.trim();
    }

    public void setSubprojectsId(String subprojectsId) {
        this.subprojectsId = subprojectsId == null ? null : subprojectsId.trim();
    }

    public String getSubprojectsName() {
        return subprojectsName == null ? "" : subprojectsName.trim();
    }

    public void setSubprojectsName(String subprojectsName) {
        this.subprojectsName = subprojectsName == null ? null : subprojectsName.trim();
    }

    public String getGeneralContractQualityTitle() {
        return generalContractQualityTitle == null ? "" : generalContractQualityTitle.trim();
    }

    public void setGeneralContractQualityTitle(String generalContractQualityTitle) {
        this.generalContractQualityTitle = generalContractQualityTitle == null ? null : generalContractQualityTitle.trim();
    }

    public String getGeneralContractQualityContent() {
        return generalContractQualityContent == null ? "" : generalContractQualityContent.trim();
    }

    public void setGeneralContractQualityContent(String generalContractQualityContent) {
        this.generalContractQualityContent = generalContractQualityContent == null ? null : generalContractQualityContent.trim();
    }

    public Date getGeneralContractQualityDate() {
        return generalContractQualityDate;
    }

    public void setGeneralContractQualityDate(Date generalContractQualityDate) {
        this.generalContractQualityDate = generalContractQualityDate;
    }

    public String getGeneralContractQualityUser() {
        return generalContractQualityUser == null ? "" : generalContractQualityUser.trim();
    }

    public void setGeneralContractQualityUser(String generalContractQualityUser) {
        this.generalContractQualityUser = generalContractQualityUser == null ? null : generalContractQualityUser.trim();
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


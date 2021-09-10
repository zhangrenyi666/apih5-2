package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjTzSuperiorUnitQualityDuty extends BasePojo {
    private String superiorUnitQualityId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String superiorUnitQualityTitle;

    private String superiorUnitQualityContent;

    private Date superiorUnitQualityDate;

    private String superiorUnitQualityUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<ZjTzFile> unitQualityFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
    public List<ZjTzFile> getUnitQualityFileList() {
		return unitQualityFileList;
	}

	public void setUnitQualityFileList(List<ZjTzFile> unitQualityFileList) {
		this.unitQualityFileList = unitQualityFileList;
	}

	public String getSuperiorUnitQualityId() {
        return superiorUnitQualityId == null ? "" : superiorUnitQualityId.trim();
    }

    public void setSuperiorUnitQualityId(String superiorUnitQualityId) {
        this.superiorUnitQualityId = superiorUnitQualityId == null ? null : superiorUnitQualityId.trim();
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

    public String getSuperiorUnitQualityTitle() {
        return superiorUnitQualityTitle == null ? "" : superiorUnitQualityTitle.trim();
    }

    public void setSuperiorUnitQualityTitle(String superiorUnitQualityTitle) {
        this.superiorUnitQualityTitle = superiorUnitQualityTitle == null ? null : superiorUnitQualityTitle.trim();
    }

    public String getSuperiorUnitQualityContent() {
        return superiorUnitQualityContent == null ? "" : superiorUnitQualityContent.trim();
    }

    public void setSuperiorUnitQualityContent(String superiorUnitQualityContent) {
        this.superiorUnitQualityContent = superiorUnitQualityContent == null ? null : superiorUnitQualityContent.trim();
    }

    public Date getSuperiorUnitQualityDate() {
        return superiorUnitQualityDate;
    }

    public void setSuperiorUnitQualityDate(Date superiorUnitQualityDate) {
        this.superiorUnitQualityDate = superiorUnitQualityDate;
    }

    public String getSuperiorUnitQualityUser() {
        return superiorUnitQualityUser == null ? "" : superiorUnitQualityUser.trim();
    }

    public void setSuperiorUnitQualityUser(String superiorUnitQualityUser) {
        this.superiorUnitQualityUser = superiorUnitQualityUser == null ? null : superiorUnitQualityUser.trim();
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


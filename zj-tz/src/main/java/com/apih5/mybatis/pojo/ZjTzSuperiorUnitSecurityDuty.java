package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjTzSuperiorUnitSecurityDuty extends BasePojo {
    private String superiorUnitSecurityId;

    private String projectId;

    private String projectName;

    private String subprojectsId;

    private String subprojectsName;

    private String superiorUnitSecurityTitle;

    private String superiorUnitSecurityContent;

    private Date superiorUnitSecurityDate;

    private String superiorUnitSecurityUser;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    private List<ZjTzFile> unitSecurityFileList;
    
    private String projectIdSql;
    
    public String getProjectIdSql() {
        return projectIdSql == null ? "" : projectIdSql.trim();
    }

    public void setProjectIdSql(String projectIdSql) {
        this.projectIdSql = projectIdSql == null ? null : projectIdSql.trim();
    }
    
    public List<ZjTzFile> getUnitSecurityFileList() {
		return unitSecurityFileList;
	}

	public void setUnitSecurityFileList(List<ZjTzFile> unitSecurityFileList) {
		this.unitSecurityFileList = unitSecurityFileList;
	}

	public String getSuperiorUnitSecurityId() {
        return superiorUnitSecurityId == null ? "" : superiorUnitSecurityId.trim();
    }

    public void setSuperiorUnitSecurityId(String superiorUnitSecurityId) {
        this.superiorUnitSecurityId = superiorUnitSecurityId == null ? null : superiorUnitSecurityId.trim();
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

    public String getSuperiorUnitSecurityTitle() {
        return superiorUnitSecurityTitle == null ? "" : superiorUnitSecurityTitle.trim();
    }

    public void setSuperiorUnitSecurityTitle(String superiorUnitSecurityTitle) {
        this.superiorUnitSecurityTitle = superiorUnitSecurityTitle == null ? null : superiorUnitSecurityTitle.trim();
    }

    public String getSuperiorUnitSecurityContent() {
        return superiorUnitSecurityContent == null ? "" : superiorUnitSecurityContent.trim();
    }

    public void setSuperiorUnitSecurityContent(String superiorUnitSecurityContent) {
        this.superiorUnitSecurityContent = superiorUnitSecurityContent == null ? null : superiorUnitSecurityContent.trim();
    }

    public Date getSuperiorUnitSecurityDate() {
        return superiorUnitSecurityDate;
    }

    public void setSuperiorUnitSecurityDate(Date superiorUnitSecurityDate) {
        this.superiorUnitSecurityDate = superiorUnitSecurityDate;
    }

    public String getSuperiorUnitSecurityUser() {
        return superiorUnitSecurityUser == null ? "" : superiorUnitSecurityUser.trim();
    }

    public void setSuperiorUnitSecurityUser(String superiorUnitSecurityUser) {
        this.superiorUnitSecurityUser = superiorUnitSecurityUser == null ? null : superiorUnitSecurityUser.trim();
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


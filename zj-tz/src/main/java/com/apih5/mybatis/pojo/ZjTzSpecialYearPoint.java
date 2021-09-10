package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;
import cn.hutool.json.JSONObject;
import com.apih5.framework.entity.BasePojo;

public class ZjTzSpecialYearPoint extends BasePojo {
    private String yearPointId;

    private String comId;

    private String companyId;

    private String companyName;

    private String yearName;

    private Date yearDate;

    private Date registerDate;

    private String registerPerson;

    private String content;

    private String homeShow;

    private Date homeShowTime;

    private Date homeShowStartDate;

    private Date homeShowEndDate;

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

    private List<JSONObject> projectList;

    private String typeName;

    public String getYearPointId() {
        return yearPointId == null ? "" : yearPointId.trim();
    }

    public void setYearPointId(String yearPointId) {
        this.yearPointId = yearPointId == null ? null : yearPointId.trim();
    }

    public String getComId() {
        return comId == null ? "" : comId.trim();
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getCompanyId() {
        return companyId == null ? "" : companyId.trim();
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId == null ? null : companyId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getYearName() {
        return yearName == null ? "" : yearName.trim();
    }

    public void setYearName(String yearName) {
        this.yearName = yearName == null ? null : yearName.trim();
    }

    public Date getYearDate() {
        return yearDate;
    }

    public void setYearDate(Date yearDate) {
        this.yearDate = yearDate;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public String getRegisterPerson() {
        return registerPerson == null ? "" : registerPerson.trim();
    }

    public void setRegisterPerson(String registerPerson) {
        this.registerPerson = registerPerson == null ? null : registerPerson.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getHomeShow() {
        return homeShow == null ? "" : homeShow.trim();
    }

    public void setHomeShow(String homeShow) {
        this.homeShow = homeShow == null ? null : homeShow.trim();
    }

    public Date getHomeShowTime() {
        return homeShowTime;
    }

    public void setHomeShowTime(Date homeShowTime) {
        this.homeShowTime = homeShowTime;
    }

    public Date getHomeShowStartDate() {
        return homeShowStartDate;
    }

    public void setHomeShowStartDate(Date homeShowStartDate) {
        this.homeShowStartDate = homeShowStartDate;
    }

    public Date getHomeShowEndDate() {
        return homeShowEndDate;
    }

    public void setHomeShowEndDate(Date homeShowEndDate) {
        this.homeShowEndDate = homeShowEndDate;
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

    public List<JSONObject> getProjectList() {
        return projectList;
    }

    public void setProjectList(List<JSONObject> projectList) {
        this.projectList = projectList;
    }

    public String getTypeName() {
        return typeName == null ? "" : typeName.trim();
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName == null ? null : typeName.trim();
    }

}


package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmCqjxProjectDisciplineAssessmentDetailed extends BasePojo {
    private String disciplineDetailedId;

    private String disciplineId;

    private String userKey;

    private String userName;

    private String departmentName;

    private String mobile;

    private String disciplineDetailedScore;

    private String disciplineDetailedContent;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getDisciplineDetailedId() {
        return disciplineDetailedId == null ? "" : disciplineDetailedId.trim();
    }

    public void setDisciplineDetailedId(String disciplineDetailedId) {
        this.disciplineDetailedId = disciplineDetailedId == null ? null : disciplineDetailedId.trim();
    }

    public String getDisciplineId() {
        return disciplineId == null ? "" : disciplineId.trim();
    }

    public void setDisciplineId(String disciplineId) {
        this.disciplineId = disciplineId == null ? null : disciplineId.trim();
    }

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    public String getUserName() {
        return userName == null ? "" : userName.trim();
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public String getMobile() {
        return mobile == null ? "" : mobile.trim();
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getDisciplineDetailedScore() {
        return disciplineDetailedScore == null ? "" : disciplineDetailedScore.trim();
    }

    public void setDisciplineDetailedScore(String disciplineDetailedScore) {
        this.disciplineDetailedScore = disciplineDetailedScore == null ? null : disciplineDetailedScore.trim();
    }

    public String getDisciplineDetailedContent() {
        return disciplineDetailedContent == null ? "" : disciplineDetailedContent.trim();
    }

    public void setDisciplineDetailedContent(String disciplineDetailedContent) {
        this.disciplineDetailedContent = disciplineDetailedContent == null ? null : disciplineDetailedContent.trim();
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


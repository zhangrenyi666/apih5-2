package com.apih5.mybatis.pojo;

import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzEmployeeEducation extends BasePojo {
    private String educationId;

    private String employeeInfoId;

    private Date stratTime;

    private Date graduateTime;

    private String graduatSchool;

    private String studyForm;

    private String majorIn;

    private String education;

    private String degree;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    //入学时间字符串
    private String stratTimeString;

    //毕业时间字符串
    private String graduateTimeString;

    //学历名称
    private String educationName;
    //学位名称
    private String degreeName;

    public String getEducationName() {
        return educationName;
    }

    public void setEducationName(String educationName) {
        this.educationName = educationName;
    }

    public String getDegreeName() {
        return degreeName;
    }

    public void setDegreeName(String degreeName) {
        this.degreeName = degreeName;
    }

    public String getStratTimeString() {
        return stratTimeString;
    }

    public void setStratTimeString(String stratTimeString) {
        this.stratTimeString = stratTimeString;
    }

    public String getGraduateTimeString() {
        return graduateTimeString;
    }

    public void setGraduateTimeString(String graduateTimeString) {
        this.graduateTimeString = graduateTimeString;
    }

    public String getEducationId() {
        return educationId == null ? "" : educationId.trim();
    }

    public void setEducationId(String educationId) {
        this.educationId = educationId == null ? null : educationId.trim();
    }

    public String getEmployeeInfoId() {
        return employeeInfoId == null ? "" : employeeInfoId.trim();
    }

    public void setEmployeeInfoId(String employeeInfoId) {
        this.employeeInfoId = employeeInfoId == null ? null : employeeInfoId.trim();
    }

    public Date getStratTime() {
        return stratTime;
    }

    public void setStratTime(Date stratTime) {
        this.stratTime = stratTime;
    }

    public Date getGraduateTime() {
        return graduateTime;
    }

    public void setGraduateTime(Date graduateTime) {
        this.graduateTime = graduateTime;
    }

    public String getGraduatSchool() {
        return graduatSchool == null ? "" : graduatSchool.trim();
    }

    public void setGraduatSchool(String graduatSchool) {
        this.graduatSchool = graduatSchool == null ? null : graduatSchool.trim();
    }

    public String getStudyForm() {
        return studyForm == null ? "" : studyForm.trim();
    }

    public void setStudyForm(String studyForm) {
        this.studyForm = studyForm == null ? null : studyForm.trim();
    }

    public String getMajorIn() {
        return majorIn == null ? "" : majorIn.trim();
    }

    public void setMajorIn(String majorIn) {
        this.majorIn = majorIn == null ? null : majorIn.trim();
    }

    public String getEducation() {
        return education == null ? "" : education.trim();
    }

    public void setEducation(String education) {
        this.education = education == null ? null : education.trim();
    }

    public String getDegree() {
        return degree == null ? "" : degree.trim();
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
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


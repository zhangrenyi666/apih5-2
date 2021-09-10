package com.apih5.mybatis.pojo;

import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZjTzEmployeeInfo extends BasePojo {
    private String employeeInfoId;

    private String companyName;

    private String personnelUnit;

    private String departmentName;

    private Date joinTime;

    private String employeeName;

    private String idCard;

    private Date birthDate;

    private String nativePlace;

    private String sex;

    private String nation;

    private Date jobTime;

    private Date enterPriseTime;

    private String marital;

    private String health;

    private String registeredResidence;

    private String post;

    private String employeeTypeId;

    private String laborTypeId;

    private String phone;

    private String mailBox;

    private String contractCharacterId;

    private String politicalId;

    private String leaderCategoryId;

    private Date attendPartyTime;

    private String projectId;

    private String projectName;

    private String preProjectId;

    private String preProjectName;

    private Date preJoinTime;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    //政治面貌
    private String politicalName;

    //人才类别
    private String employeeTypeName;

    //用工类型
    private String laborTypeName;

    //合同性质
    private String contractCharacterName;

    //职称情况表 - 专业技术资格名称 逗号隔开
    private String professionalName;

    //出生日期字符串类型的
    private String birthDateString;

    private Integer type;

    private List<Date> timeList;

    public List<Date> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Date> timeList) {
        this.timeList = timeList;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBirthDateString() {
        return birthDateString;
    }

    public void setBirthDateString(String birthDateString) {
        this.birthDateString = birthDateString;
    }

    public String getProfessionalName() {
        return professionalName;
    }

    public void setProfessionalName(String professionalName) {
        this.professionalName = professionalName;
    }

    public String getPoliticalName() {
        return politicalName;
    }

    public void setPoliticalName(String politicalName) {
        this.politicalName = politicalName;
    }

    public String getEmployeeTypeName() {
        return employeeTypeName;
    }

    public void setEmployeeTypeName(String employeeTypeName) {
        this.employeeTypeName = employeeTypeName;
    }

    public String getLaborTypeName() {
        return laborTypeName;
    }

    public void setLaborTypeName(String laborTypeName) {
        this.laborTypeName = laborTypeName;
    }

    public String getContractCharacterName() {
        return contractCharacterName;
    }

    public void setContractCharacterName(String contractCharacterName) {
        this.contractCharacterName = contractCharacterName;
    }

    private List<ZjTzFile> zjTzFileList;

    private List<ZjTzEmployeeQualification> qualificationList;

    private List<ZjTzEmployeeProfessional> professionalList;

    private List<ZjTzEmployeeWork> workList;

    private List<ZjTzEmployeeEducation> educationList;

    private List<ZjTzEmployeeAward> awardList;

    private List<ZjTzEmployeeInfo> zjTzEmployeeInfoList;

    private String projectEmployeeId;

    public List<ZjTzEmployeeInfo> getZjTzEmployeeInfoList() {
        return zjTzEmployeeInfoList;
    }

    public void setZjTzEmployeeInfoList(List<ZjTzEmployeeInfo> zjTzEmployeeInfoList) {
        this.zjTzEmployeeInfoList = zjTzEmployeeInfoList;
    }

    public String getEmployeeInfoId() {
        return employeeInfoId == null ? "" : employeeInfoId.trim();
    }

    public void setEmployeeInfoId(String employeeInfoId) {
        this.employeeInfoId = employeeInfoId == null ? null : employeeInfoId.trim();
    }

    public String getCompanyName() {
        return companyName == null ? "" : companyName.trim();
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getPersonnelUnit() {
        return personnelUnit == null ? "" : personnelUnit.trim();
    }

    public void setPersonnelUnit(String personnelUnit) {
        this.personnelUnit = personnelUnit == null ? null : personnelUnit.trim();
    }

    public String getDepartmentName() {
        return departmentName == null ? "" : departmentName.trim();
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName == null ? null : departmentName.trim();
    }

    public Date getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(Date joinTime) {
        this.joinTime = joinTime;
    }

    public String getEmployeeName() {
        return employeeName == null ? "" : employeeName.trim();
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName == null ? null : employeeName.trim();
    }

    public String getIdCard() {
        return idCard == null ? "" : idCard.trim();
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard == null ? null : idCard.trim();
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getNativePlace() {
        return nativePlace == null ? "" : nativePlace.trim();
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace == null ? null : nativePlace.trim();
    }

    public String getSex() {
        return sex == null ? "" : sex.trim();
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public String getNation() {
        return nation == null ? "" : nation.trim();
    }

    public void setNation(String nation) {
        this.nation = nation == null ? null : nation.trim();
    }

    public Date getJobTime() {
        return jobTime;
    }

    public void setJobTime(Date jobTime) {
        this.jobTime = jobTime;
    }

    public Date getEnterPriseTime() {
        return enterPriseTime;
    }

    public void setEnterPriseTime(Date enterPriseTime) {
        this.enterPriseTime = enterPriseTime;
    }

    public String getMarital() {
        return marital == null ? "" : marital.trim();
    }

    public void setMarital(String marital) {
        this.marital = marital == null ? null : marital.trim();
    }

    public String getHealth() {
        return health == null ? "" : health.trim();
    }

    public void setHealth(String health) {
        this.health = health == null ? null : health.trim();
    }

    public String getRegisteredResidence() {
        return registeredResidence == null ? "" : registeredResidence.trim();
    }

    public void setRegisteredResidence(String registeredResidence) {
        this.registeredResidence = registeredResidence == null ? null : registeredResidence.trim();
    }

    public String getPost() {
        return post == null ? "" : post.trim();
    }

    public void setPost(String post) {
        this.post = post == null ? null : post.trim();
    }

    public String getEmployeeTypeId() {
        return employeeTypeId == null ? "" : employeeTypeId.trim();
    }

    public void setEmployeeTypeId(String employeeTypeId) {
        this.employeeTypeId = employeeTypeId == null ? null : employeeTypeId.trim();
    }

    public String getLaborTypeId() {
        return laborTypeId == null ? "" : laborTypeId.trim();
    }

    public void setLaborTypeId(String laborTypeId) {
        this.laborTypeId = laborTypeId == null ? null : laborTypeId.trim();
    }

    public String getPhone() {
        return phone == null ? "" : phone.trim();
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getMailBox() {
        return mailBox == null ? "" : mailBox.trim();
    }

    public void setMailBox(String mailBox) {
        this.mailBox = mailBox == null ? null : mailBox.trim();
    }

    public String getContractCharacterId() {
        return contractCharacterId == null ? "" : contractCharacterId.trim();
    }

    public void setContractCharacterId(String contractCharacterId) {
        this.contractCharacterId = contractCharacterId == null ? null : contractCharacterId.trim();
    }

    public String getPoliticalId() {
        return politicalId == null ? "" : politicalId.trim();
    }

    public void setPoliticalId(String politicalId) {
        this.politicalId = politicalId == null ? null : politicalId.trim();
    }

    public String getLeaderCategoryId() {
        return leaderCategoryId == null ? "" : leaderCategoryId.trim();
    }

    public void setLeaderCategoryId(String leaderCategoryId) {
        this.leaderCategoryId = leaderCategoryId == null ? null : leaderCategoryId.trim();
    }

    public Date getAttendPartyTime() {
        return attendPartyTime;
    }

    public void setAttendPartyTime(Date attendPartyTime) {
        this.attendPartyTime = attendPartyTime;
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

    public String getPreProjectId() {
        return preProjectId == null ? "" : preProjectId.trim();
    }

    public void setPreProjectId(String preProjectId) {
        this.preProjectId = preProjectId == null ? null : preProjectId.trim();
    }

    public String getPreProjectName() {
        return preProjectName == null ? "" : preProjectName.trim();
    }

    public void setPreProjectName(String preProjectName) {
        this.preProjectName = preProjectName == null ? null : preProjectName.trim();
    }

    public Date getPreJoinTime() {
        return preJoinTime;
    }

    public void setPreJoinTime(Date preJoinTime) {
        this.preJoinTime = preJoinTime;
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

    public List<ZjTzEmployeeQualification> getQualificationList() {
        return qualificationList;
    }

    public void setQualificationList(List<ZjTzEmployeeQualification> qualificationList) {
        this.qualificationList = qualificationList;
    }

    public List<ZjTzEmployeeProfessional> getProfessionalList() {
        return professionalList;
    }

    public void setProfessionalList(List<ZjTzEmployeeProfessional> professionalList) {
        this.professionalList = professionalList;
    }

    public List<ZjTzEmployeeWork> getWorkList() {
        return workList;
    }

    public void setWorkList(List<ZjTzEmployeeWork> workList) {
        this.workList = workList;
    }

    public List<ZjTzEmployeeEducation> getEducationList() {
        return educationList;
    }

    public void setEducationList(List<ZjTzEmployeeEducation> educationList) {
        this.educationList = educationList;
    }

    public List<ZjTzEmployeeAward> getAwardList() {
        return awardList;
    }

    public void setAwardList(List<ZjTzEmployeeAward> awardList) {
        this.awardList = awardList;
    }

    public String getProjectEmployeeId() {
        return projectEmployeeId == null ? "" : projectEmployeeId.trim();
    }

    public void setProjectEmployeeId(String projectEmployeeId) {
        this.projectEmployeeId = projectEmployeeId == null ? null : projectEmployeeId.trim();
    }

}


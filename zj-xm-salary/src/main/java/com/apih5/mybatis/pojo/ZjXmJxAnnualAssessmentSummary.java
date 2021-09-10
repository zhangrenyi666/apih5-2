package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmJxAnnualAssessmentSummary extends BasePojo {
    private String summaryId;

    private String projectId;

    private String projectName;

    private String deptId;

    private String deptName;

    private String userKey;

    private String realName;

    private String idNumber;

    private String position;

    private String rankPositionType;

    private String lastPositionType;

    private String userType;

    private Date year;

    private BigDecimal januaryScore;

    private BigDecimal februaryScore;

    private BigDecimal marchScore;

    private BigDecimal aprilScore;

    private BigDecimal mayScore;

    private BigDecimal juneScore;

    private BigDecimal julyScore;

    private BigDecimal augustScore;

    private BigDecimal septemberScore;

    private BigDecimal octoberScore;

    private BigDecimal novemberScore;

    private BigDecimal decemberScore;

    private BigDecimal averageScore;

    private String lastPersonMonth;

    private Integer groupSort;

    private String summaryType;

    private String opinionField1;

    private String opinionField2;

    private String opinionField3;

    private String opinionField4;

    private String opinionField5;

    private String apih5FlowId;

    private String workId;

    private String apih5FlowStatus;

    private String apih5FlowNodeStatus;

    private String annualFlowId;

    private String remarks;

    private Integer sort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getSummaryId() {
        return summaryId == null ? "" : summaryId.trim();
    }

    public void setSummaryId(String summaryId) {
        this.summaryId = summaryId == null ? null : summaryId.trim();
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

    public String getDeptId() {
        return deptId == null ? "" : deptId.trim();
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId == null ? null : deptId.trim();
    }

    public String getDeptName() {
        return deptName == null ? "" : deptName.trim();
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName == null ? null : deptName.trim();
    }

    public String getUserKey() {
        return userKey == null ? "" : userKey.trim();
    }

    public void setUserKey(String userKey) {
        this.userKey = userKey == null ? null : userKey.trim();
    }

    public String getRealName() {
        return realName == null ? "" : realName.trim();
    }

    public void setRealName(String realName) {
        this.realName = realName == null ? null : realName.trim();
    }

    public String getIdNumber() {
        return idNumber == null ? "" : idNumber.trim();
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getPosition() {
        return position == null ? "" : position.trim();
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getRankPositionType() {
        return rankPositionType == null ? "" : rankPositionType.trim();
    }

    public void setRankPositionType(String rankPositionType) {
        this.rankPositionType = rankPositionType == null ? null : rankPositionType.trim();
    }

    public String getLastPositionType() {
        return lastPositionType == null ? "" : lastPositionType.trim();
    }

    public void setLastPositionType(String lastPositionType) {
        this.lastPositionType = lastPositionType == null ? null : lastPositionType.trim();
    }

    public String getUserType() {
        return userType == null ? "" : userType.trim();
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public Date getYear() {
        return year;
    }

    public void setYear(Date year) {
        this.year = year;
    }

    public BigDecimal getJanuaryScore() {
        return januaryScore;
    }

    public void setJanuaryScore(BigDecimal januaryScore) {
        this.januaryScore = januaryScore;
    }

    public BigDecimal getFebruaryScore() {
        return februaryScore;
    }

    public void setFebruaryScore(BigDecimal februaryScore) {
        this.februaryScore = februaryScore;
    }

    public BigDecimal getMarchScore() {
        return marchScore;
    }

    public void setMarchScore(BigDecimal marchScore) {
        this.marchScore = marchScore;
    }

    public BigDecimal getAprilScore() {
        return aprilScore;
    }

    public void setAprilScore(BigDecimal aprilScore) {
        this.aprilScore = aprilScore;
    }

    public BigDecimal getMayScore() {
        return mayScore;
    }

    public void setMayScore(BigDecimal mayScore) {
        this.mayScore = mayScore;
    }

    public BigDecimal getJuneScore() {
        return juneScore;
    }

    public void setJuneScore(BigDecimal juneScore) {
        this.juneScore = juneScore;
    }

    public BigDecimal getJulyScore() {
        return julyScore;
    }

    public void setJulyScore(BigDecimal julyScore) {
        this.julyScore = julyScore;
    }

    public BigDecimal getAugustScore() {
        return augustScore;
    }

    public void setAugustScore(BigDecimal augustScore) {
        this.augustScore = augustScore;
    }

    public BigDecimal getSeptemberScore() {
        return septemberScore;
    }

    public void setSeptemberScore(BigDecimal septemberScore) {
        this.septemberScore = septemberScore;
    }

    public BigDecimal getOctoberScore() {
        return octoberScore;
    }

    public void setOctoberScore(BigDecimal octoberScore) {
        this.octoberScore = octoberScore;
    }

    public BigDecimal getNovemberScore() {
        return novemberScore;
    }

    public void setNovemberScore(BigDecimal novemberScore) {
        this.novemberScore = novemberScore;
    }

    public BigDecimal getDecemberScore() {
        return decemberScore;
    }

    public void setDecemberScore(BigDecimal decemberScore) {
        this.decemberScore = decemberScore;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
    }

    public String getLastPersonMonth() {
        return lastPersonMonth == null ? "" : lastPersonMonth.trim();
    }

    public void setLastPersonMonth(String lastPersonMonth) {
        this.lastPersonMonth = lastPersonMonth == null ? null : lastPersonMonth.trim();
    }

    public Integer getGroupSort() {
        return groupSort;
    }

    public void setGroupSort(Integer groupSort) {
        this.groupSort = groupSort;
    }

    public String getSummaryType() {
        return summaryType == null ? "" : summaryType.trim();
    }

    public void setSummaryType(String summaryType) {
        this.summaryType = summaryType == null ? null : summaryType.trim();
    }

    public String getOpinionField1() {
        return opinionField1 == null ? "" : opinionField1.trim();
    }

    public void setOpinionField1(String opinionField1) {
        this.opinionField1 = opinionField1 == null ? null : opinionField1.trim();
    }

    public String getOpinionField2() {
        return opinionField2 == null ? "" : opinionField2.trim();
    }

    public void setOpinionField2(String opinionField2) {
        this.opinionField2 = opinionField2 == null ? null : opinionField2.trim();
    }

    public String getOpinionField3() {
        return opinionField3 == null ? "" : opinionField3.trim();
    }

    public void setOpinionField3(String opinionField3) {
        this.opinionField3 = opinionField3 == null ? null : opinionField3.trim();
    }

    public String getOpinionField4() {
        return opinionField4 == null ? "" : opinionField4.trim();
    }

    public void setOpinionField4(String opinionField4) {
        this.opinionField4 = opinionField4 == null ? null : opinionField4.trim();
    }

    public String getOpinionField5() {
        return opinionField5 == null ? "" : opinionField5.trim();
    }

    public void setOpinionField5(String opinionField5) {
        this.opinionField5 = opinionField5 == null ? null : opinionField5.trim();
    }

    public String getApih5FlowId() {
        return apih5FlowId == null ? "" : apih5FlowId.trim();
    }

    public void setApih5FlowId(String apih5FlowId) {
        this.apih5FlowId = apih5FlowId == null ? null : apih5FlowId.trim();
    }

    public String getWorkId() {
        return workId == null ? "" : workId.trim();
    }

    public void setWorkId(String workId) {
        this.workId = workId == null ? null : workId.trim();
    }

    public String getApih5FlowStatus() {
        return apih5FlowStatus == null ? "" : apih5FlowStatus.trim();
    }

    public void setApih5FlowStatus(String apih5FlowStatus) {
        this.apih5FlowStatus = apih5FlowStatus == null ? null : apih5FlowStatus.trim();
    }

    public String getApih5FlowNodeStatus() {
        return apih5FlowNodeStatus == null ? "" : apih5FlowNodeStatus.trim();
    }

    public void setApih5FlowNodeStatus(String apih5FlowNodeStatus) {
        this.apih5FlowNodeStatus = apih5FlowNodeStatus == null ? null : apih5FlowNodeStatus.trim();
    }

    public String getAnnualFlowId() {
        return annualFlowId == null ? "" : annualFlowId.trim();
    }

    public void setAnnualFlowId(String annualFlowId) {
        this.annualFlowId = annualFlowId == null ? null : annualFlowId.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
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


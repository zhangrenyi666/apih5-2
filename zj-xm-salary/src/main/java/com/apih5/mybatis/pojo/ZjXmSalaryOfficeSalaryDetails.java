package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;

import com.apih5.framework.entity.BasePojo;

public class ZjXmSalaryOfficeSalaryDetails extends BasePojo {
    private String detailsId;

    private String userKey;

    private String realName;

    private String idType;

    private String idNumber;

    private String userType;

    private String projectId;

    private BigDecimal salaryBase;

    private BigDecimal attendanceDays;

    private BigDecimal calendarDays;

    private BigDecimal positionSalary;

    private String levelId;

    private String salaryId;

    private String levelSalaryId;

    private BigDecimal educationAllowance;

    private BigDecimal workYears;

    private BigDecimal yearAllowance;

    private BigDecimal titleAllowance;

    private BigDecimal childAllowance;

    private BigDecimal plateauAllowance;

    private BigDecimal highTempAllowance;

    private BigDecimal certificateAllowance;

    private BigDecimal performanceAllowance;

    private BigDecimal otherWages;

    private BigDecimal endowmentInsurance;

    private BigDecimal unemploymentInsurance;

    private BigDecimal medicalInsurance;

    private BigDecimal housingFund;

    private BigDecimal enterpriseAnnuity;

    private BigDecimal individualIncomeTax;

    private BigDecimal personalThreshold;

    private BigDecimal personalSpecialDeduction;

    private BigDecimal otherTaxableWages;

    private BigDecimal communicationAllowance;

    private BigDecimal currentTaxableIncome;

    private BigDecimal accumulatedTaxableIncome;

    private BigDecimal accumulatedPersonalIncomeTax;

    private BigDecimal accumulatedIndividualIncomeTaxDeducted;

    private BigDecimal totalPaid;

    private String opinionField1;

    private String opinionField2;

    private String opinionField3;

    private String opinionField4;

    private String opinionField5;

    private String apih5FlowId;

    private String workId;

    private String apih5FlowStatus;

    private String apih5FlowNodeStatus;

    private String remarks;

    private Integer sort;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;

    public String getDetailsId() {
        return detailsId == null ? "" : detailsId.trim();
    }

    public void setDetailsId(String detailsId) {
        this.detailsId = detailsId == null ? null : detailsId.trim();
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

    public String getIdType() {
        return idType == null ? "" : idType.trim();
    }

    public void setIdType(String idType) {
        this.idType = idType == null ? null : idType.trim();
    }

    public String getIdNumber() {
        return idNumber == null ? "" : idNumber.trim();
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber == null ? null : idNumber.trim();
    }

    public String getUserType() {
        return userType == null ? "" : userType.trim();
    }

    public void setUserType(String userType) {
        this.userType = userType == null ? null : userType.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public BigDecimal getSalaryBase() {
        return salaryBase;
    }

    public void setSalaryBase(BigDecimal salaryBase) {
        this.salaryBase = salaryBase;
    }

    public BigDecimal getAttendanceDays() {
        return attendanceDays;
    }

    public void setAttendanceDays(BigDecimal attendanceDays) {
        this.attendanceDays = attendanceDays;
    }

    public BigDecimal getCalendarDays() {
        return calendarDays;
    }

    public void setCalendarDays(BigDecimal calendarDays) {
        this.calendarDays = calendarDays;
    }

    public BigDecimal getPositionSalary() {
        return positionSalary;
    }

    public void setPositionSalary(BigDecimal positionSalary) {
        this.positionSalary = positionSalary;
    }

    public String getLevelId() {
        return levelId == null ? "" : levelId.trim();
    }

    public void setLevelId(String levelId) {
        this.levelId = levelId == null ? null : levelId.trim();
    }

    public String getSalaryId() {
        return salaryId == null ? "" : salaryId.trim();
    }

    public void setSalaryId(String salaryId) {
        this.salaryId = salaryId == null ? null : salaryId.trim();
    }

    public String getLevelSalaryId() {
        return levelSalaryId == null ? "" : levelSalaryId.trim();
    }

    public void setLevelSalaryId(String levelSalaryId) {
        this.levelSalaryId = levelSalaryId == null ? null : levelSalaryId.trim();
    }

    public BigDecimal getEducationAllowance() {
        return educationAllowance;
    }

    public void setEducationAllowance(BigDecimal educationAllowance) {
        this.educationAllowance = educationAllowance;
    }

    public BigDecimal getWorkYears() {
        return workYears;
    }

    public void setWorkYears(BigDecimal workYears) {
        this.workYears = workYears;
    }

    public BigDecimal getYearAllowance() {
        return yearAllowance;
    }

    public void setYearAllowance(BigDecimal yearAllowance) {
        this.yearAllowance = yearAllowance;
    }

    public BigDecimal getTitleAllowance() {
        return titleAllowance;
    }

    public void setTitleAllowance(BigDecimal titleAllowance) {
        this.titleAllowance = titleAllowance;
    }

    public BigDecimal getChildAllowance() {
        return childAllowance;
    }

    public void setChildAllowance(BigDecimal childAllowance) {
        this.childAllowance = childAllowance;
    }

    public BigDecimal getPlateauAllowance() {
        return plateauAllowance;
    }

    public void setPlateauAllowance(BigDecimal plateauAllowance) {
        this.plateauAllowance = plateauAllowance;
    }

    public BigDecimal getHighTempAllowance() {
        return highTempAllowance;
    }

    public void setHighTempAllowance(BigDecimal highTempAllowance) {
        this.highTempAllowance = highTempAllowance;
    }

    public BigDecimal getCertificateAllowance() {
        return certificateAllowance;
    }

    public void setCertificateAllowance(BigDecimal certificateAllowance) {
        this.certificateAllowance = certificateAllowance;
    }

    public BigDecimal getPerformanceAllowance() {
        return performanceAllowance;
    }

    public void setPerformanceAllowance(BigDecimal performanceAllowance) {
        this.performanceAllowance = performanceAllowance;
    }

    public BigDecimal getOtherWages() {
        return otherWages;
    }

    public void setOtherWages(BigDecimal otherWages) {
        this.otherWages = otherWages;
    }

    public BigDecimal getEndowmentInsurance() {
        return endowmentInsurance;
    }

    public void setEndowmentInsurance(BigDecimal endowmentInsurance) {
        this.endowmentInsurance = endowmentInsurance;
    }

    public BigDecimal getUnemploymentInsurance() {
        return unemploymentInsurance;
    }

    public void setUnemploymentInsurance(BigDecimal unemploymentInsurance) {
        this.unemploymentInsurance = unemploymentInsurance;
    }

    public BigDecimal getMedicalInsurance() {
        return medicalInsurance;
    }

    public void setMedicalInsurance(BigDecimal medicalInsurance) {
        this.medicalInsurance = medicalInsurance;
    }

    public BigDecimal getHousingFund() {
        return housingFund;
    }

    public void setHousingFund(BigDecimal housingFund) {
        this.housingFund = housingFund;
    }

    public BigDecimal getEnterpriseAnnuity() {
        return enterpriseAnnuity;
    }

    public void setEnterpriseAnnuity(BigDecimal enterpriseAnnuity) {
        this.enterpriseAnnuity = enterpriseAnnuity;
    }

    public BigDecimal getIndividualIncomeTax() {
        return individualIncomeTax;
    }

    public void setIndividualIncomeTax(BigDecimal individualIncomeTax) {
        this.individualIncomeTax = individualIncomeTax;
    }

    public BigDecimal getPersonalThreshold() {
        return personalThreshold;
    }

    public void setPersonalThreshold(BigDecimal personalThreshold) {
        this.personalThreshold = personalThreshold;
    }

    public BigDecimal getPersonalSpecialDeduction() {
        return personalSpecialDeduction;
    }

    public void setPersonalSpecialDeduction(BigDecimal personalSpecialDeduction) {
        this.personalSpecialDeduction = personalSpecialDeduction;
    }

    public BigDecimal getOtherTaxableWages() {
        return otherTaxableWages;
    }

    public void setOtherTaxableWages(BigDecimal otherTaxableWages) {
        this.otherTaxableWages = otherTaxableWages;
    }

    public BigDecimal getCommunicationAllowance() {
        return communicationAllowance;
    }

    public void setCommunicationAllowance(BigDecimal communicationAllowance) {
        this.communicationAllowance = communicationAllowance;
    }

    public BigDecimal getCurrentTaxableIncome() {
        return currentTaxableIncome;
    }

    public void setCurrentTaxableIncome(BigDecimal currentTaxableIncome) {
        this.currentTaxableIncome = currentTaxableIncome;
    }

    public BigDecimal getAccumulatedTaxableIncome() {
        return accumulatedTaxableIncome;
    }

    public void setAccumulatedTaxableIncome(BigDecimal accumulatedTaxableIncome) {
        this.accumulatedTaxableIncome = accumulatedTaxableIncome;
    }

    public BigDecimal getAccumulatedPersonalIncomeTax() {
        return accumulatedPersonalIncomeTax;
    }

    public void setAccumulatedPersonalIncomeTax(BigDecimal accumulatedPersonalIncomeTax) {
        this.accumulatedPersonalIncomeTax = accumulatedPersonalIncomeTax;
    }

    public BigDecimal getAccumulatedIndividualIncomeTaxDeducted() {
        return accumulatedIndividualIncomeTaxDeducted;
    }

    public void setAccumulatedIndividualIncomeTaxDeducted(BigDecimal accumulatedIndividualIncomeTaxDeducted) {
        this.accumulatedIndividualIncomeTaxDeducted = accumulatedIndividualIncomeTaxDeducted;
    }

    public BigDecimal getTotalPaid() {
        return totalPaid;
    }

    public void setTotalPaid(BigDecimal totalPaid) {
        this.totalPaid = totalPaid;
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


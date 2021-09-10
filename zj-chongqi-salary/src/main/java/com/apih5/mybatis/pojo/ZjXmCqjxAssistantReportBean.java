package com.apih5.mybatis.pojo;

import java.util.Date;

public class ZjXmCqjxAssistantReportBean {
	
    private String executiveId;
    
    private String assessmentType;

    private Date assessmentYears;

    private String assessmentTitle;

    private String assessmentQuarter;

    private String assessmentState;

    private String position;

    private String departmentId;

    private double quarterScore;
    
    private double taskScore;
    
    private double cooperationScore;
    
    private double disciplineScore;
    
    private String disciplineScoreStr;
    
    private String executiveScoreSumStr;
    
    private String quarterScoreStr;

    private String createUserName;

    private String modifyUserName;
    
    private String years;//日期检索条件使用
    
    private String departmentName;
    
    private String deptCoefficient;
    
    private String workType;

    private String workPlan;

    private String workTarget;

    private String completion;

    private double assessmentScore;
    
    private double chargeLeaderScore;
    
    private double executiveLeaderScore;
    
    private double executiveScore;
    
    private String index;
    
    private String type;
    
    private String executiveSubtotal;//主管领导评分小计
    
    private String changeSubtotal;//分管领导小计
    
    private String deptWorkPlan;
    
    private String deptWorkTarget;
    
    private String deptCompletion;
    
    private String deptChargeLeaderScore;
    
    private String deptExecutiveLeaderScore;
    
    private String itemOneScore;
    
    private String itemTwoScore;
    
    private String itemThreeScore;
    
    private String itemFourScore;
    
    private String itemFiveScore;
    
    private String averageScore;
    
    private String firstQuarterScore;
    
    private String secondQuarterScore;
    
    private String thirdQuarterScore;
    
    private String fourthQuarterScore;
    
    private String comprehensiveScore;//年度综合得分
    
	public String getComprehensiveScore() {
		return comprehensiveScore;
	}

	public void setComprehensiveScore(String comprehensiveScore) {
		this.comprehensiveScore = comprehensiveScore;
	}

	public String getFirstQuarterScore() {
		return firstQuarterScore;
	}

	public void setFirstQuarterScore(String firstQuarterScore) {
		this.firstQuarterScore = firstQuarterScore;
	}

	public String getSecondQuarterScore() {
		return secondQuarterScore;
	}

	public void setSecondQuarterScore(String secondQuarterScore) {
		this.secondQuarterScore = secondQuarterScore;
	}

	public String getThirdQuarterScore() {
		return thirdQuarterScore;
	}

	public void setThirdQuarterScore(String thirdQuarterScore) {
		this.thirdQuarterScore = thirdQuarterScore;
	}

	public String getFourthQuarterScore() {
		return fourthQuarterScore;
	}

	public void setFourthQuarterScore(String fourthQuarterScore) {
		this.fourthQuarterScore = fourthQuarterScore;
	}

	public String getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(String averageScore) {
		this.averageScore = averageScore;
	}

	public String getItemOneScore() {
		return itemOneScore;
	}

	public void setItemOneScore(String itemOneScore) {
		this.itemOneScore = itemOneScore;
	}

	public String getItemTwoScore() {
		return itemTwoScore;
	}

	public void setItemTwoScore(String itemTwoScore) {
		this.itemTwoScore = itemTwoScore;
	}

	public String getItemThreeScore() {
		return itemThreeScore;
	}

	public void setItemThreeScore(String itemThreeScore) {
		this.itemThreeScore = itemThreeScore;
	}

	public String getItemFourScore() {
		return itemFourScore;
	}

	public void setItemFourScore(String itemFourScore) {
		this.itemFourScore = itemFourScore;
	}

	public String getItemFiveScore() {
		return itemFiveScore;
	}

	public void setItemFiveScore(String itemFiveScore) {
		this.itemFiveScore = itemFiveScore;
	}

	public String getDeptWorkPlan() {
		return deptWorkPlan;
	}

	public void setDeptWorkPlan(String deptWorkPlan) {
		this.deptWorkPlan = deptWorkPlan;
	}

	public String getDeptWorkTarget() {
		return deptWorkTarget;
	}

	public void setDeptWorkTarget(String deptWorkTarget) {
		this.deptWorkTarget = deptWorkTarget;
	}

	public String getDeptCompletion() {
		return deptCompletion;
	}

	public void setDeptCompletion(String deptCompletion) {
		this.deptCompletion = deptCompletion;
	}

	public String getDeptChargeLeaderScore() {
		return deptChargeLeaderScore;
	}

	public void setDeptChargeLeaderScore(String deptChargeLeaderScore) {
		this.deptChargeLeaderScore = deptChargeLeaderScore;
	}

	public String getDeptExecutiveLeaderScore() {
		return deptExecutiveLeaderScore;
	}

	public void setDeptExecutiveLeaderScore(String deptExecutiveLeaderScore) {
		this.deptExecutiveLeaderScore = deptExecutiveLeaderScore;
	}

	public String getExecutiveSubtotal() {
		return executiveSubtotal;
	}

	public void setExecutiveSubtotal(String executiveSubtotal) {
		this.executiveSubtotal = executiveSubtotal;
	}

	public String getChangeSubtotal() {
		return changeSubtotal;
	}

	public void setChangeSubtotal(String changeSubtotal) {
		this.changeSubtotal = changeSubtotal;
	}

	public String getExecutiveId() {
		return executiveId;
	}

	public void setExecutiveId(String executiveId) {
		this.executiveId = executiveId;
	}

	public String getExecutiveScoreSumStr() {
		return executiveScoreSumStr;
	}

	public void setExecutiveScoreSumStr(String executiveScoreSumStr) {
		this.executiveScoreSumStr = executiveScoreSumStr;
	}

	public String getQuarterScoreStr() {
		return quarterScoreStr;
	}

	public void setQuarterScoreStr(String quarterScoreStr) {
		this.quarterScoreStr = quarterScoreStr;
	}

	public String getDisciplineScoreStr() {
		return disciplineScoreStr;
	}

	public void setDisciplineScoreStr(String disciplineScoreStr) {
		this.disciplineScoreStr = disciplineScoreStr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getWorkType() {
		return workType;
	}

	public void setWorkType(String workType) {
		this.workType = workType;
	}

	public String getWorkPlan() {
		return workPlan;
	}

	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}

	public String getWorkTarget() {
		return workTarget;
	}

	public void setWorkTarget(String workTarget) {
		this.workTarget = workTarget;
	}

	public String getCompletion() {
		return completion;
	}

	public void setCompletion(String completion) {
		this.completion = completion;
	}

	public double getAssessmentScore() {
		return assessmentScore;
	}

	public void setAssessmentScore(double assessmentScore) {
		this.assessmentScore = assessmentScore;
	}

	public double getChargeLeaderScore() {
		return chargeLeaderScore;
	}

	public void setChargeLeaderScore(double chargeLeaderScore) {
		this.chargeLeaderScore = chargeLeaderScore;
	}

	public double getExecutiveLeaderScore() {
		return executiveLeaderScore;
	}

	public void setExecutiveLeaderScore(double executiveLeaderScore) {
		this.executiveLeaderScore = executiveLeaderScore;
	}

	public double getExecutiveScore() {
		return executiveScore;
	}

	public void setExecutiveScore(double executiveScore) {
		this.executiveScore = executiveScore;
	}
	
	public String getAssessmentType() {
		return assessmentType;
	}

	public void setAssessmentType(String assessmentType) {
		this.assessmentType = assessmentType;
	}

	public Date getAssessmentYears() {
		return assessmentYears;
	}

	public void setAssessmentYears(Date assessmentYears) {
		this.assessmentYears = assessmentYears;
	}

	public String getAssessmentTitle() {
		return assessmentTitle;
	}

	public void setAssessmentTitle(String assessmentTitle) {
		this.assessmentTitle = assessmentTitle;
	}

	public String getAssessmentQuarter() {
		return assessmentQuarter;
	}

	public void setAssessmentQuarter(String assessmentQuarter) {
		this.assessmentQuarter = assessmentQuarter;
	}

	public String getAssessmentState() {
		return assessmentState;
	}

	public void setAssessmentState(String assessmentState) {
		this.assessmentState = assessmentState;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public double getQuarterScore() {
		return quarterScore;
	}

	public void setQuarterScore(double quarterScore) {
		this.quarterScore = quarterScore;
	}

	public double getTaskScore() {
		return taskScore;
	}

	public void setTaskScore(double taskScore) {
		this.taskScore = taskScore;
	}

	public double getCooperationScore() {
		return cooperationScore;
	}

	public void setCooperationScore(double cooperationScore) {
		this.cooperationScore = cooperationScore;
	}

	public double getDisciplineScore() {
		return disciplineScore;
	}

	public void setDisciplineScore(double disciplineScore) {
		this.disciplineScore = disciplineScore;
	}
	
	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getModifyUserName() {
		return modifyUserName;
	}

	public void setModifyUserName(String modifyUserName) {
		this.modifyUserName = modifyUserName;
	}

	public String getYears() {
		return years;
	}

	public void setYears(String years) {
		this.years = years;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDeptCoefficient() {
		return deptCoefficient;
	}

	public void setDeptCoefficient(String deptCoefficient) {
		this.deptCoefficient = deptCoefficient;
	}
    
}

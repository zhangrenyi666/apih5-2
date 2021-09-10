package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import com.apih5.framework.entity.BasePojo;

public class ZjTzProSubprojectInfo extends BasePojo {
    private String subprojectInfoId;

    private String projectId;

    private String subprojectNumber;

    private String subprojectName;

    private String delFlag;

    private Date createTime;

    private String createUser;

    private String createUserName;

    private Date modifyTime;

    private String modifyUser;

    private String modifyUserName;
    
    //新增字段
    //建设期结束标志
    private String constructEnd;
    //建设期（月）
    private BigDecimal constructPeriod;
    //实际开工日期
    private Date startDateActual;
    //实际交工日期
    private Date handoverDateActual;
    //实际竣工日期
    private Date complateDateActual;
    //合同约定交工日期
    private Date handoverDatePlan;
    //合同约定竣工日期
    private Date complateDatePlan;
    //策划批复交工日期
    private Date approvalHandoverDate;
    //策划批复竣工日期
    private Date approvalCompleteDate;
    //预警分类
    private String warnType;
    // 策划批复开工时间
    private Date approvalStartDate;
    // 合同约定开工时间
    private Date signDate3;
    // 合同约定运营 / 回购开始时间
    private Date runDate1;
    // 合同约定运营 / 回购结束时间
    private Date runDate3;
    // 实际运营 / 回购开始时间
    private Date runDate2;
    // 实际运营 / 回购结束时间
    private Date runDate4;
    // 中标投资额（元）
    private BigDecimal amount1;
    // 中标建安费（元）
    private BigDecimal amount3;
    
    public String getSubprojectInfoId() {
        return subprojectInfoId == null ? "" : subprojectInfoId.trim();
    }

    public void setSubprojectInfoId(String subprojectInfoId) {
        this.subprojectInfoId = subprojectInfoId == null ? null : subprojectInfoId.trim();
    }

    public String getProjectId() {
        return projectId == null ? "" : projectId.trim();
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId == null ? null : projectId.trim();
    }

    public String getSubprojectNumber() {
        return subprojectNumber == null ? "" : subprojectNumber.trim();
    }

    public void setSubprojectNumber(String subprojectNumber) {
        this.subprojectNumber = subprojectNumber == null ? null : subprojectNumber.trim();
    }

    public String getSubprojectName() {
        return subprojectName == null ? "" : subprojectName.trim();
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName == null ? null : subprojectName.trim();
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

	public String getConstructEnd() {
		return constructEnd == null ? "" : constructEnd.trim();
	}

	public void setConstructEnd(String constructEnd) {
		this.constructEnd = constructEnd == null ? null : constructEnd.trim();
	}

	public BigDecimal getConstructPeriod() {
		return constructPeriod;
	}

	public void setConstructPeriod(BigDecimal constructPeriod) {
		this.constructPeriod = constructPeriod;
	}

	public Date getStartDateActual() {
		return startDateActual;
	}

	public void setStartDateActual(Date startDateActual) {
		this.startDateActual = startDateActual;
	}

	public Date getHandoverDateActual() {
		return handoverDateActual;
	}

	public void setHandoverDateActual(Date handoverDateActual) {
		this.handoverDateActual = handoverDateActual;
	}

	public Date getComplateDateActual() {
		return complateDateActual;
	}

	public void setComplateDateActual(Date complateDateActual) {
		this.complateDateActual = complateDateActual;
	}

	public Date getHandoverDatePlan() {
		return handoverDatePlan;
	}

	public void setHandoverDatePlan(Date handoverDatePlan) {
		this.handoverDatePlan = handoverDatePlan;
	}

	public Date getComplateDatePlan() {
		return complateDatePlan;
	}

	public void setComplateDatePlan(Date complateDatePlan) {
		this.complateDatePlan = complateDatePlan;
	}

	public Date getApprovalHandoverDate() {
		return approvalHandoverDate;
	}

	public void setApprovalHandoverDate(Date approvalHandoverDate) {
		this.approvalHandoverDate = approvalHandoverDate;
	}

	public Date getApprovalCompleteDate() {
		return approvalCompleteDate;
	}

	public void setApprovalCompleteDate(Date approvalCompleteDate) {
		this.approvalCompleteDate = approvalCompleteDate;
	}

	public String getWarnType() {
		return warnType;
	}

	public void setWarnType(String warnType) {
		this.warnType = warnType;
	}

	public Date getApprovalStartDate() {
		return approvalStartDate;
	}

	public void setApprovalStartDate(Date approvalStartDate) {
		this.approvalStartDate = approvalStartDate;
	}

	public Date getSignDate3() {
		return signDate3;
	}

	public void setSignDate3(Date signDate3) {
		this.signDate3 = signDate3;
	}

	public Date getRunDate1() {
		return runDate1;
	}

	public void setRunDate1(Date runDate1) {
		this.runDate1 = runDate1;
	}

	public Date getRunDate3() {
		return runDate3;
	}

	public void setRunDate3(Date runDate3) {
		this.runDate3 = runDate3;
	}

	public Date getRunDate2() {
		return runDate2;
	}

	public void setRunDate2(Date runDate2) {
		this.runDate2 = runDate2;
	}

	public Date getRunDate4() {
		return runDate4;
	}

	public void setRunDate4(Date runDate4) {
		this.runDate4 = runDate4;
	}

	public BigDecimal getAmount1() {
		return amount1;
	}

	public void setAmount1(BigDecimal amount1) {
		this.amount1 = amount1;
	}

	public BigDecimal getAmount3() {
		return amount3;
	}

	public void setAmount3(BigDecimal amount3) {
		this.amount3 = amount3;
	}

}


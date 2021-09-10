package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.apih5.framework.entity.BasePojo;

public class ZxCrProjectEvaluation extends BasePojo {
    // 主键
    private String zxCrProjectEvaluationId;

    // 项目id
    private String orgId;

    private String orgID;

    // 项目名称
    private String orgName;

    // 协作单位id
    private String customerId;

    // 协作单位名称
    private String customerName;

    // 组织机构代码证
    private String orgCertificate;

    // 分类代码
    private String resCode;

    // 分类ID
    private String resID;

    // 分类名称
    private String resName;

    // 专业类别代码
    private String catCode;

    // 专业类别id
    private String catID;

    // 专业类别
    private String catName;

    // 考核期次
    private String period;
    
    // 考核期次时间型
    private Date periodDate;

	// 考核日期
    private Date checkDate;

    // 进场日期
    private Date inDate;

    // 退场日期
    private Date outDate;

    // 承建工程合同额（万元）
    private BigDecimal contractAmt;

    // 考核总得分
    private BigDecimal totalScore;

	// 协作单位负责人
    private String chargeMan;

    // editTime
    private Date editTime;

    // comID
    private String comID;

    // comName
    private String comName;

    // comOrders
    private String comOrders;

    // 审核状态
    private String auditStatus;

    // 填报人
    private String preparer;

    // 审核人
    private String auditor;

    // 考核标准
    private String checkStandard;

    // 负责人联系电话
    private String chargeManPhone;

    // 备注
    private String remarks;
    
    // 季度
    private String quarter;

	// 排序
    private int sort=0;
    
    // 附件
    private List<ZxErpFile> fileList;
    
    // 关联id
    private String parentID;

	// 打分考核表List
    private List<ZxCrProjectEvaluationScore> projectEvaluationScoreList;

	// 严重不良行为考核表
    private List<ZxCrProjectEvaluationBad> projectEvaluationBadList;
    
    // 所属公司名称
    private String companyName; 

	// 所属公司ID
    private String companyId;

    // 项目ID
    private String projectId;

    // 项目ID
    private String periodYear;

    // 项目ID
    private Integer periodQuarter;
    
    public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
    
	public String getZxCrProjectEvaluationId() {
        return zxCrProjectEvaluationId == null ? "" : zxCrProjectEvaluationId.trim();
    }

    public void setZxCrProjectEvaluationId(String zxCrProjectEvaluationId) {
        this.zxCrProjectEvaluationId = zxCrProjectEvaluationId == null ? null : zxCrProjectEvaluationId.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getCustomerId() {
        return customerId == null ? "" : customerId.trim();
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getCustomerName() {
        return customerName == null ? "" : customerName.trim();
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getOrgCertificate() {
        return orgCertificate == null ? "" : orgCertificate.trim();
    }

    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getCatCode() {
        return catCode == null ? "" : catCode.trim();
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode == null ? null : catCode.trim();
    }

    public String getCatID() {
        return catID == null ? "" : catID.trim();
    }

    public void setCatID(String catID) {
        this.catID = catID == null ? null : catID.trim();
    }

    public String getCatName() {
        return catName == null ? "" : catName.trim();
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public Date getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(Date checkDate) {
        this.checkDate = checkDate;
    }

    public Date getInDate() {
        return inDate;
    }

    public void setInDate(Date inDate) {
        this.inDate = inDate;
    }

    public Date getOutDate() {
        return outDate;
    }

    public void setOutDate(Date outDate) {
        this.outDate = outDate;
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public BigDecimal getTotalScore() {
		return totalScore;
	}

	public void setTotalScore(BigDecimal totalScore) {
		this.totalScore = totalScore;
	}

    public String getChargeMan() {
        return chargeMan == null ? "" : chargeMan.trim();
    }

    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan == null ? null : chargeMan.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getPreparer() {
        return preparer == null ? "" : preparer.trim();
    }

    public void setPreparer(String preparer) {
        this.preparer = preparer == null ? null : preparer.trim();
    }

    public String getAuditor() {
        return auditor == null ? "" : auditor.trim();
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public String getCheckStandard() {
        return checkStandard == null ? "" : checkStandard.trim();
    }

    public void setCheckStandard(String checkStandard) {
        this.checkStandard = checkStandard == null ? null : checkStandard.trim();
    }

    public String getChargeManPhone() {
        return chargeManPhone == null ? "" : chargeManPhone.trim();
    }

    public void setChargeManPhone(String chargeManPhone) {
        this.chargeManPhone = chargeManPhone == null ? null : chargeManPhone.trim();
    }

    public String getRemarks() {
        return remarks == null ? "" : remarks.trim();
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
	
	public List<ZxErpFile> getFileList() {
		return fileList;
	}

	public void setFileList(List<ZxErpFile> fileList) {
		this.fileList = fileList;
	}

	public Date getPeriodDate() {
		return periodDate;
	}

	public void setPeriodDate(Date periodDate) {
		this.periodDate = periodDate;
	}
	
	public List<ZxCrProjectEvaluationScore> getProjectEvaluationScoreList() {
		return projectEvaluationScoreList;
	}

	public void setProjectEvaluationScoreList(List<ZxCrProjectEvaluationScore> projectEvaluationScoreList) {
		this.projectEvaluationScoreList = projectEvaluationScoreList;
	}

	public List<ZxCrProjectEvaluationBad> getProjectEvaluationBadList() {
		return projectEvaluationBadList;
	}

	public void setProjectEvaluationBadList(List<ZxCrProjectEvaluationBad> projectEvaluationBadList) {
		this.projectEvaluationBadList = projectEvaluationBadList;
	}
	
    public String getQuarter() {
		return quarter;
	}

	public void setQuarter(String quarter) {
		this.quarter = quarter;
	}
	
	public String getParentID() {
		return parentID;
	}

	public void setParentID(String parentID) {
		this.parentID = parentID;
	}

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(String periodYear) {
        this.periodYear = periodYear;
    }

    public Integer getPeriodQuarter() {
        return periodQuarter;
    }

    public void setPeriodQuarter(Integer periodQuarter) {
        this.periodQuarter = periodQuarter;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }
}

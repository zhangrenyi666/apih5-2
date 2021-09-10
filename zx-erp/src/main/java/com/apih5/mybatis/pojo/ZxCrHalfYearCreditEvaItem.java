package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCrHalfYearCreditEvaItem extends BasePojo {
    // 主键
    private String zxCrHalfYearCreditEvaItemId;

    // 主表ID
    private String masterID;

    // 协作单位id
    private String customerId;

    // 组织机构代码
    private String orgCertificate;

    // 协作单位名称
    private String customerName;

    // 协作单位负责人
    private String chargeMan;

    // 工程所属项目ID
    private String projectID;

    // 工程所属项目
    private String projectName;

    // 进场日期
    private Date inDate;

    // 退场日期
    private Date outDate;

    // 承建工程合同额（万元）
    private BigDecimal contractAmt;

    // 工程所属项目个数
    private String projectNum;

    // 专业类别
    private String catName;

    // 专业类别ID
    private String catID;

    // 专业类别代码
    private String catCode;

    // 评价次数
    private int checkNum=0;

    // 公司信用评价得分（第一次）
    private String firstSoce;

    // 评价等级(第一次)
    private String firstLevel;

    // 公司信用评价得分（第二次）
    private String secondScore;

    // 评价等级(第二次)
    private String secondLevel;

    // 有无直接降为D的行为
    private String dLevel;

	// 最终信用评价得分
    private String lastScore;

    // 信用评价等级
    private String lastLevel;

    // editTime
    private Date editTime;

    // comID
    private String comID;

    // comName
    private String comName;

    // comOrders
    private String comOrders;

    // 季度信用评价ID
    private String quartEvalID;

    // orderNo
    private String orderNo;

    // isHaveD
    private String isHaveD;
    
    //pp1
    private String pp1;
    
    //pp2
    private String pp2;

	// 期次
    private Date period;

	// 备注
    private String remarks;

    // 排序
    private int sort = 0;

    //期次年份
    private String periodYear;

    private String resID;

    private String companyId;

    // 工程所属项目ID
    private String projectId;

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getZxCrHalfYearCreditEvaItemId() {
        return zxCrHalfYearCreditEvaItemId == null ? "" : zxCrHalfYearCreditEvaItemId.trim();
    }

    public void setZxCrHalfYearCreditEvaItemId(String zxCrHalfYearCreditEvaItemId) {
        this.zxCrHalfYearCreditEvaItemId = zxCrHalfYearCreditEvaItemId == null ? null : zxCrHalfYearCreditEvaItemId.trim();
    }

    public String getMasterID() {
        return masterID == null ? "" : masterID.trim();
    }

    public void setMasterID(String masterID) {
        this.masterID = masterID == null ? null : masterID.trim();
    }

    public String getCustomerId() {
        return customerId == null ? "" : customerId.trim();
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId == null ? null : customerId.trim();
    }

    public String getOrgCertificate() {
        return orgCertificate == null ? "" : orgCertificate.trim();
    }

    public void setOrgCertificate(String orgCertificate) {
        this.orgCertificate = orgCertificate == null ? null : orgCertificate.trim();
    }

    public String getCustomerName() {
        return customerName == null ? "" : customerName.trim();
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName == null ? null : customerName.trim();
    }

    public String getChargeMan() {
        return chargeMan == null ? "" : chargeMan.trim();
    }

    public void setChargeMan(String chargeMan) {
        this.chargeMan = chargeMan == null ? null : chargeMan.trim();
    }

    public String getProjectID() {
        return projectID == null ? "" : projectID.trim();
    }

    public void setProjectID(String projectID) {
        this.projectID = projectID == null ? null : projectID.trim();
    }

    public String getProjectName() {
        return projectName == null ? "" : projectName.trim();
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName == null ? null : projectName.trim();
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

    public String getProjectNum() {
        return projectNum == null ? "" : projectNum.trim();
    }

    public void setProjectNum(String projectNum) {
        this.projectNum = projectNum == null ? null : projectNum.trim();
    }

    public String getCatName() {
        return catName == null ? "" : catName.trim();
    }

    public void setCatName(String catName) {
        this.catName = catName == null ? null : catName.trim();
    }

    public String getCatID() {
        return catID == null ? "" : catID.trim();
    }

    public void setCatID(String catID) {
        this.catID = catID == null ? null : catID.trim();
    }

    public String getCatCode() {
        return catCode == null ? "" : catCode.trim();
    }

    public void setCatCode(String catCode) {
        this.catCode = catCode == null ? null : catCode.trim();
    }

    public int getCheckNum() {
        return checkNum;
    }

    public void setCheckNum(int checkNum) {
        this.checkNum = checkNum;
    }

    public String getFirstSoce() {
        return firstSoce == null ? "" : firstSoce.trim();
    }

    public void setFirstSoce(String firstSoce) {
        this.firstSoce = firstSoce == null ? null : firstSoce.trim();
    }

    public String getFirstLevel() {
        return firstLevel == null ? "" : firstLevel.trim();
    }

    public void setFirstLevel(String firstLevel) {
        this.firstLevel = firstLevel == null ? null : firstLevel.trim();
    }

    public String getSecondScore() {
        return secondScore == null ? "" : secondScore.trim();
    }

    public void setSecondScore(String secondScore) {
        this.secondScore = secondScore == null ? null : secondScore.trim();
    }

    public String getSecondLevel() {
        return secondLevel == null ? "" : secondLevel.trim();
    }

    public void setSecondLevel(String secondLevel) {
        this.secondLevel = secondLevel == null ? null : secondLevel.trim();
    }

    public String getdLevel() {
		return dLevel;
	}

	public void setdLevel(String dLevel) {
		this.dLevel = dLevel;
	}

    public String getLastScore() {
        return lastScore == null ? "" : lastScore.trim();
    }

    public void setLastScore(String lastScore) {
        this.lastScore = lastScore == null ? null : lastScore.trim();
    }

    public String getLastLevel() {
        return lastLevel == null ? "" : lastLevel.trim();
    }

    public void setLastLevel(String lastLevel) {
        this.lastLevel = lastLevel == null ? null : lastLevel.trim();
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

    public String getQuartEvalID() {
        return quartEvalID == null ? "" : quartEvalID.trim();
    }

    public void setQuartEvalID(String quartEvalID) {
        this.quartEvalID = quartEvalID == null ? null : quartEvalID.trim();
    }

    public String getOrderNo() {
        return orderNo == null ? "" : orderNo.trim();
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getIsHaveD() {
        return isHaveD == null ? "" : isHaveD.trim();
    }

    public void setIsHaveD(String isHaveD) {
        this.isHaveD = isHaveD == null ? null : isHaveD.trim();
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

    public Date getPeriod() {
        return period;
    }

    public void setPeriod(Date period) {
        this.period = period;
    }

    public String getPp1() {
  		return pp1;
  	}

  	public void setPp1(String pp1) {
  		this.pp1 = pp1;
  	}
  	
	public String getPp2() {
		return pp2;
	}

	public void setPp2(String pp2) {
		this.pp2 = pp2;
	}

    public String getPeriodYear() {
        return periodYear;
    }

    public void setPeriodYear(String periodYear) {
        this.periodYear = periodYear;
    }

    public String getResID() {
        return resID;
    }

    public void setResID(String resID) {
        this.resID = resID;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }
}

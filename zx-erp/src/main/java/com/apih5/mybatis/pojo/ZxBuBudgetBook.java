package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuBudgetBook extends BasePojo {
    // 主键
    private String zxBuBudgetBookId;

    // 项目
    private String orgID;

    // 项目
    private String workBookID;

    // 编制机构
    private String reportOrgID;

    // 编制机构
    private String reportOrgName;

    // 编制人
    private String reporter;

    // 编制日期
    private Date reportDate;

    // 审核人
    private String auditor;

    // 审核日期
    private Date auditDate;

    // 预算类型
    private int budgetType=0;

    // 预算级别
    private int budgetLevel=0;

    // 状态
    private String status;

    // 类型
    private String systemType;

    // null
    private String combProp;

    // null
    private String pp1;

    // null
    private String pp2;

    // null
    private String pp3;

    // null
    private String pp4;

    // null
    private String pp5;

    // null
    private String pp6;

    // 
    private String pp7;

    // 
    private String pp8;

    // 
    private String pp9;

    // 
    private String pp10;

    // 是否历史项目
    private String isHistory;

    // 
    private Date editTime;

    // 期次
    private String period;

    // 项目所属省份
    private String projFea;

    // 项目所属区域
    private String area;

    // 是否首次
    private String isFirst;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //预算金额
    private BigDecimal budgetAmt;

    //附件
    private List<ZxErpFile> fileList;

    public BigDecimal getBudgetAmt() {
        return budgetAmt;
    }

    public void setBudgetAmt(BigDecimal budgetAmt) {
        this.budgetAmt = budgetAmt;
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public String getZxBuBudgetBookId() {
        return zxBuBudgetBookId == null ? "" : zxBuBudgetBookId.trim();
    }

    public void setZxBuBudgetBookId(String zxBuBudgetBookId) {
        this.zxBuBudgetBookId = zxBuBudgetBookId == null ? null : zxBuBudgetBookId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getWorkBookID() {
        return workBookID == null ? "" : workBookID.trim();
    }

    public void setWorkBookID(String workBookID) {
        this.workBookID = workBookID == null ? null : workBookID.trim();
    }

    public String getReportOrgID() {
        return reportOrgID == null ? "" : reportOrgID.trim();
    }

    public void setReportOrgID(String reportOrgID) {
        this.reportOrgID = reportOrgID == null ? null : reportOrgID.trim();
    }

    public String getReportOrgName() {
        return reportOrgName == null ? "" : reportOrgName.trim();
    }

    public void setReportOrgName(String reportOrgName) {
        this.reportOrgName = reportOrgName == null ? null : reportOrgName.trim();
    }

    public String getReporter() {
        return reporter == null ? "" : reporter.trim();
    }

    public void setReporter(String reporter) {
        this.reporter = reporter == null ? null : reporter.trim();
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getAuditor() {
        return auditor == null ? "" : auditor.trim();
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor == null ? null : auditor.trim();
    }

    public Date getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(Date auditDate) {
        this.auditDate = auditDate;
    }

    public int getBudgetType() {
        return budgetType;
    }

    public void setBudgetType(int budgetType) {
        this.budgetType = budgetType;
    }

    public int getBudgetLevel() {
        return budgetLevel;
    }

    public void setBudgetLevel(int budgetLevel) {
        this.budgetLevel = budgetLevel;
    }

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getSystemType() {
        return systemType == null ? "" : systemType.trim();
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType == null ? null : systemType.trim();
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getPp2() {
        return pp2 == null ? "" : pp2.trim();
    }

    public void setPp2(String pp2) {
        this.pp2 = pp2 == null ? null : pp2.trim();
    }

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public String getPp4() {
        return pp4 == null ? "" : pp4.trim();
    }

    public void setPp4(String pp4) {
        this.pp4 = pp4 == null ? null : pp4.trim();
    }

    public String getPp5() {
        return pp5 == null ? "" : pp5.trim();
    }

    public void setPp5(String pp5) {
        this.pp5 = pp5 == null ? null : pp5.trim();
    }

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
    }

    public String getPp7() {
        return pp7 == null ? "" : pp7.trim();
    }

    public void setPp7(String pp7) {
        this.pp7 = pp7 == null ? null : pp7.trim();
    }

    public String getPp8() {
        return pp8 == null ? "" : pp8.trim();
    }

    public void setPp8(String pp8) {
        this.pp8 = pp8 == null ? null : pp8.trim();
    }

    public String getPp9() {
        return pp9 == null ? "" : pp9.trim();
    }

    public void setPp9(String pp9) {
        this.pp9 = pp9 == null ? null : pp9.trim();
    }

    public String getPp10() {
        return pp10 == null ? "" : pp10.trim();
    }

    public void setPp10(String pp10) {
        this.pp10 = pp10 == null ? null : pp10.trim();
    }

    public String getIsHistory() {
        return isHistory == null ? "" : isHistory.trim();
    }

    public void setIsHistory(String isHistory) {
        this.isHistory = isHistory == null ? null : isHistory.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getProjFea() {
        return projFea == null ? "" : projFea.trim();
    }

    public void setProjFea(String projFea) {
        this.projFea = projFea == null ? null : projFea.trim();
    }

    public String getArea() {
        return area == null ? "" : area.trim();
    }

    public void setArea(String area) {
        this.area = area == null ? null : area.trim();
    }

    public String getIsFirst() {
        return isFirst == null ? "" : isFirst.trim();
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst == null ? null : isFirst.trim();
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

}

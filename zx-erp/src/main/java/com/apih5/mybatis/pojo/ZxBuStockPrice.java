package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuStockPrice extends BasePojo {
    // 主键
    private String zxBuStockPriceId;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 编制机构ID
    private String reportOrgID;

    // 编制机构名称
    private String reportOrgName;

    // 编制人
    private String reporter;

    // 编制日期
    private Date reportDate;

    // 审核人
    private String auditor;

    // 审核日期
    private Date auditDate;

    // 状态
    private String status;

    // 编辑时间
    private Date editTime;

    // 
    private String combProp;

    // 
    private String pp1;

    // 
    private String pp2;

    // 
    private String pp3;

    // 
    private String pp4;

    // 
    private String pp5;

    // 
    private String pp6;

    // 
    private String pp7;

    // 
    private String pp8;

    // 
    private String pp9;

    // 
    private String pp10;

    // 期次
    private String period;

    // 类型
    private String budgetType;

    // mcsgAPrice
    private BigDecimal mcsgAPrice;

    // 分类属性
    private String businessType;

    // 公司id
    private String comID;

    // 公司名称
    private String comName;

    // 标后初次预算是否审核
    private String isBHAudit;

    // 施工初次预算是否审核
    private String isSGAudit;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private List<ZxBuStockPriceItem> zxBuStockPriceItemList;

    private List<ZxErpFile> fileList;

    public List<ZxBuStockPriceItem> getZxBuStockPriceItemList() {
        return zxBuStockPriceItemList;
    }

    public void setZxBuStockPriceItemList(List<ZxBuStockPriceItem> zxBuStockPriceItemList) {
        this.zxBuStockPriceItemList = zxBuStockPriceItemList;
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public String getZxBuStockPriceId() {
        return zxBuStockPriceId == null ? "" : zxBuStockPriceId.trim();
    }

    public void setZxBuStockPriceId(String zxBuStockPriceId) {
        this.zxBuStockPriceId = zxBuStockPriceId == null ? null : zxBuStockPriceId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
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

    public String getStatus() {
        return status == null ? "" : status.trim();
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getBudgetType() {
        return budgetType == null ? "" : budgetType.trim();
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType == null ? null : budgetType.trim();
    }

    public BigDecimal getMcsgAPrice() {
        return mcsgAPrice;
    }

    public void setMcsgAPrice(BigDecimal mcsgAPrice) {
        this.mcsgAPrice = mcsgAPrice;
    }

    public String getBusinessType() {
        return businessType == null ? "" : businessType.trim();
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
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

    public String getIsBHAudit() {
        return isBHAudit == null ? "" : isBHAudit.trim();
    }

    public void setIsBHAudit(String isBHAudit) {
        this.isBHAudit = isBHAudit == null ? null : isBHAudit.trim();
    }

    public String getIsSGAudit() {
        return isSGAudit == null ? "" : isSGAudit.trim();
    }

    public void setIsSGAudit(String isSGAudit) {
        this.isSGAudit = isSGAudit == null ? null : isSGAudit.trim();
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

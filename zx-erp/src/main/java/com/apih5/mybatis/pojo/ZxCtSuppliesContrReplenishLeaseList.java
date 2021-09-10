package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesContrReplenishLeaseList extends BasePojo {
    // 主键
    private String zxCtSuppliesContrReplenishLeaseListId;

    // 状态
    private String auditStatus;

    // 需要公司协助
    private String companyHelp;

    // 提出单位
    private String proposer;

    // 生效操作日期
    private Date takeEffectDate;

    // 生效操作人
    private String takeEffectMan;

    // 申报延期天数
    private String applyDelay;

    // 申报文号
    private String applyNo;

    // 申报日期
    private Date applyDate;

    // 上期末变更后税额
    private BigDecimal upAlterContractSumTax;

    // 上期末变更后金额不含税
    private BigDecimal upAlterContractSumNoTax;

    // 上期末变更后金额
    private BigDecimal upAlterContractSum;

    // 批复状态
    private String replyStatus;

    // 批复延期天数
    private String replyDelay;

    // 批复日期
    private Date replyDate;

    // 批复单位
    private String pp1;

    // 记录日期
    private Date recordDate;

    // 记录人
    private String recorder;

    // 机构ID
    private String orgID;

    // 合同税额(万元)
    private BigDecimal pp2Tax;

    // 合同ID
    private String contractID;

    // 含税合同金额(万元)
    private String pp2;

    // 发生时间
    private Date happenDate;

    // 不含税合同金额(万元)
    private BigDecimal pp2NoTax;

    // 补充协议书编号
    private String replyNo;

    // 补充协议书ID
    private String pp3;

    // 变更原因
    private String alterReason;

    // 变更内容
    private String alterContent;

    // 变更后税额(万元)
    private BigDecimal replyAmountTax;

    // 变更后含税金额(万元)
    private BigDecimal replyAmount;

    // 变更后不含税金额(万元)
    private BigDecimal replyAmountNoTax;

    // 变更等级
    private String alterLevel;

    // 本期增减税额(万元)
    private BigDecimal applyAmountTax;

    // 本期含税增减金额(万元)
    private BigDecimal applyAmount;

    // 本期不含税增减金额(万元)
    private BigDecimal applyAmountNoTax;

    // (IP的合同ID)
    private String pp6;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCtSuppliesContrReplenishLeaseListId() {
        return zxCtSuppliesContrReplenishLeaseListId == null ? "" : zxCtSuppliesContrReplenishLeaseListId.trim();
    }

    public void setZxCtSuppliesContrReplenishLeaseListId(String zxCtSuppliesContrReplenishLeaseListId) {
        this.zxCtSuppliesContrReplenishLeaseListId = zxCtSuppliesContrReplenishLeaseListId == null ? null : zxCtSuppliesContrReplenishLeaseListId.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getCompanyHelp() {
        return companyHelp == null ? "" : companyHelp.trim();
    }

    public void setCompanyHelp(String companyHelp) {
        this.companyHelp = companyHelp == null ? null : companyHelp.trim();
    }

    public String getProposer() {
        return proposer == null ? "" : proposer.trim();
    }

    public void setProposer(String proposer) {
        this.proposer = proposer == null ? null : proposer.trim();
    }

    public Date getTakeEffectDate() {
        return takeEffectDate;
    }

    public void setTakeEffectDate(Date takeEffectDate) {
        this.takeEffectDate = takeEffectDate;
    }

    public String getTakeEffectMan() {
        return takeEffectMan == null ? "" : takeEffectMan.trim();
    }

    public void setTakeEffectMan(String takeEffectMan) {
        this.takeEffectMan = takeEffectMan == null ? null : takeEffectMan.trim();
    }

    public String getApplyDelay() {
        return applyDelay == null ? "" : applyDelay.trim();
    }

    public void setApplyDelay(String applyDelay) {
        this.applyDelay = applyDelay == null ? null : applyDelay.trim();
    }

    public String getApplyNo() {
        return applyNo == null ? "" : applyNo.trim();
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public BigDecimal getUpAlterContractSumTax() {
        return upAlterContractSumTax;
    }

    public void setUpAlterContractSumTax(BigDecimal upAlterContractSumTax) {
        this.upAlterContractSumTax = upAlterContractSumTax;
    }

    public BigDecimal getUpAlterContractSumNoTax() {
        return upAlterContractSumNoTax;
    }

    public void setUpAlterContractSumNoTax(BigDecimal upAlterContractSumNoTax) {
        this.upAlterContractSumNoTax = upAlterContractSumNoTax;
    }

    public BigDecimal getUpAlterContractSum() {
        return upAlterContractSum;
    }

    public void setUpAlterContractSum(BigDecimal upAlterContractSum) {
        this.upAlterContractSum = upAlterContractSum;
    }

    public String getReplyStatus() {
        return replyStatus == null ? "" : replyStatus.trim();
    }

    public void setReplyStatus(String replyStatus) {
        this.replyStatus = replyStatus == null ? null : replyStatus.trim();
    }

    public String getReplyDelay() {
        return replyDelay == null ? "" : replyDelay.trim();
    }

    public void setReplyDelay(String replyDelay) {
        this.replyDelay = replyDelay == null ? null : replyDelay.trim();
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
    }

    public String getRecorder() {
        return recorder == null ? "" : recorder.trim();
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder == null ? null : recorder.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public BigDecimal getPp2Tax() {
        return pp2Tax;
    }

    public void setPp2Tax(BigDecimal pp2Tax) {
        this.pp2Tax = pp2Tax;
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getPp2() {
        return pp2 == null ? "" : pp2.trim();
    }

    public void setPp2(String pp2) {
        this.pp2 = pp2 == null ? null : pp2.trim();
    }

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public BigDecimal getPp2NoTax() {
        return pp2NoTax;
    }

    public void setPp2NoTax(BigDecimal pp2NoTax) {
        this.pp2NoTax = pp2NoTax;
    }

    public String getReplyNo() {
        return replyNo == null ? "" : replyNo.trim();
    }

    public void setReplyNo(String replyNo) {
        this.replyNo = replyNo == null ? null : replyNo.trim();
    }

    public String getPp3() {
        return pp3 == null ? "" : pp3.trim();
    }

    public void setPp3(String pp3) {
        this.pp3 = pp3 == null ? null : pp3.trim();
    }

    public String getAlterReason() {
        return alterReason == null ? "" : alterReason.trim();
    }

    public void setAlterReason(String alterReason) {
        this.alterReason = alterReason == null ? null : alterReason.trim();
    }

    public String getAlterContent() {
        return alterContent == null ? "" : alterContent.trim();
    }

    public void setAlterContent(String alterContent) {
        this.alterContent = alterContent == null ? null : alterContent.trim();
    }

    public BigDecimal getReplyAmountTax() {
        return replyAmountTax;
    }

    public void setReplyAmountTax(BigDecimal replyAmountTax) {
        this.replyAmountTax = replyAmountTax;
    }

    public BigDecimal getReplyAmount() {
        return replyAmount;
    }

    public void setReplyAmount(BigDecimal replyAmount) {
        this.replyAmount = replyAmount;
    }

    public BigDecimal getReplyAmountNoTax() {
        return replyAmountNoTax;
    }

    public void setReplyAmountNoTax(BigDecimal replyAmountNoTax) {
        this.replyAmountNoTax = replyAmountNoTax;
    }

    public String getAlterLevel() {
        return alterLevel == null ? "" : alterLevel.trim();
    }

    public void setAlterLevel(String alterLevel) {
        this.alterLevel = alterLevel == null ? null : alterLevel.trim();
    }

    public BigDecimal getApplyAmountTax() {
        return applyAmountTax;
    }

    public void setApplyAmountTax(BigDecimal applyAmountTax) {
        this.applyAmountTax = applyAmountTax;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public BigDecimal getApplyAmountNoTax() {
        return applyAmountNoTax;
    }

    public void setApplyAmountNoTax(BigDecimal applyAmountNoTax) {
        this.applyAmountNoTax = applyAmountNoTax;
    }

    public String getPp6() {
        return pp6 == null ? "" : pp6.trim();
    }

    public void setPp6(String pp6) {
        this.pp6 = pp6 == null ? null : pp6.trim();
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

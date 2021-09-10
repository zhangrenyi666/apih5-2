package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCcConstCoAlter extends BasePojo {
    // 主键
    private String id;

    // 协作清单书ID
    private String coBookId;

    // 变更等级
    private String alterLevel;

    // 补充协议名称
    private String proposer;

    // 变更内容
    private String alterContent;

    // 变更原因
    private String alterReason;

    // 发生时间
    private Date happenDate;

    // 本期含税变更增减金额(万元)
    private BigDecimal applyAmount;

    // 申报日期
    private Date applyDate;

    // 申报文号
    private String applyNo;

    // 申报延期天数
    private String applyDelay;

    // 变更后含税合同金额(万元)
    private BigDecimal replyAmount;

    // 日期
    private Date replyDate;

    // 补充协议书编号
    private String replyNo;

    // 批复延期天数
    private String replyDelay;

    // 批复状态
    private String replyStatus;

    // 记录人
    private String recorder;

    // 记录日期
    private Date recordDate;

    // 生效操作日期
    private Date takeEffectDate;

    // 生效操作人
    private String takeEffectMan;

    // auditStatus
    private String auditStatus;

    // combProp
    private String combProp;

    // 批复单位
    private String replyUnit;

    // 原含税合同金额(万元)
    private BigDecimal originalContractAmountTax;

    // 评审ID
    private String reviewId;

    // pp4
    private String pp4;

    // pp5
    private String pp5;

    // IP的合同ID
    private String ipContractId;

    // pp7
    private String pp7;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 合同ID
    private String contractId;

    // 机构ID
    private String orgId;

    // 本期不含税变更增减金额(万元)
    private BigDecimal applyAmountNoTax;

    // 本期变更增减税额(万元)
    private BigDecimal applyAmountTax;

    // 变更后不含税合同金额(万元)
    private BigDecimal replyAmountNoTax;

    // 变更后合同税额(万元)
    private BigDecimal replyAmountTax;

    // 原不含税合同金额(万元)
    private BigDecimal originalContractAmountNoTax;

    // 原合同税额(万元)
    private BigDecimal originalContractTax;

    // 备注
    private String remark;

    // 补充协议清单明细
    private List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorkList;

    // 附件
    private List<ZxErpFile> fileList;

    // 排序
    private int sort=0;

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCoBookId() {
        return coBookId == null ? "" : coBookId.trim();
    }

    public void setCoBookId(String coBookId) {
        this.coBookId = coBookId == null ? null : coBookId.trim();
    }

    public String getAlterLevel() {
        return alterLevel == null ? "" : alterLevel.trim();
    }

    public void setAlterLevel(String alterLevel) {
        this.alterLevel = alterLevel == null ? null : alterLevel.trim();
    }

    public String getProposer() {
        return proposer == null ? "" : proposer.trim();
    }

    public void setProposer(String proposer) {
        this.proposer = proposer == null ? null : proposer.trim();
    }

    public String getAlterContent() {
        return alterContent == null ? "" : alterContent.trim();
    }

    public void setAlterContent(String alterContent) {
        this.alterContent = alterContent == null ? null : alterContent.trim();
    }

    public String getAlterReason() {
        return alterReason == null ? "" : alterReason.trim();
    }

    public void setAlterReason(String alterReason) {
        this.alterReason = alterReason == null ? null : alterReason.trim();
    }

    public Date getHappenDate() {
        return happenDate;
    }

    public void setHappenDate(Date happenDate) {
        this.happenDate = happenDate;
    }

    public BigDecimal getApplyAmount() {
        return applyAmount;
    }

    public void setApplyAmount(BigDecimal applyAmount) {
        this.applyAmount = applyAmount;
    }

    public Date getApplyDate() {
        return applyDate;
    }

    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }

    public String getApplyNo() {
        return applyNo == null ? "" : applyNo.trim();
    }

    public void setApplyNo(String applyNo) {
        this.applyNo = applyNo == null ? null : applyNo.trim();
    }

    public String getApplyDelay() {
        return applyDelay == null ? "" : applyDelay.trim();
    }

    public void setApplyDelay(String applyDelay) {
        this.applyDelay = applyDelay == null ? null : applyDelay.trim();
    }

    public BigDecimal getReplyAmount() {
        return replyAmount;
    }

    public void setReplyAmount(BigDecimal replyAmount) {
        this.replyAmount = replyAmount;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public String getReplyNo() {
        return replyNo == null ? "" : replyNo.trim();
    }

    public void setReplyNo(String replyNo) {
        this.replyNo = replyNo == null ? null : replyNo.trim();
    }

    public String getReplyDelay() {
        return replyDelay == null ? "" : replyDelay.trim();
    }

    public void setReplyDelay(String replyDelay) {
        this.replyDelay = replyDelay == null ? null : replyDelay.trim();
    }

    public String getReplyStatus() {
        return replyStatus == null ? "" : replyStatus.trim();
    }

    public void setReplyStatus(String replyStatus) {
        this.replyStatus = replyStatus == null ? null : replyStatus.trim();
    }

    public String getRecorder() {
        return recorder == null ? "" : recorder.trim();
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder == null ? null : recorder.trim();
    }

    public Date getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(Date recordDate) {
        this.recordDate = recordDate;
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

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
    }

    public String getReplyUnit() {
        return replyUnit == null ? "" : replyUnit.trim();
    }

    public void setReplyUnit(String replyUnit) {
        this.replyUnit = replyUnit == null ? null : replyUnit.trim();
    }

    public BigDecimal getOriginalContractAmountTax() {
        return originalContractAmountTax;
    }

    public void setOriginalContractAmountTax(BigDecimal originalContractAmountTax) {
        this.originalContractAmountTax = originalContractAmountTax;
    }

    public String getReviewId() {
        return reviewId == null ? "" : reviewId.trim();
    }

    public void setReviewId(String reviewId) {
        this.reviewId = reviewId == null ? null : reviewId.trim();
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

    public String getIpContractId() {
        return ipContractId == null ? "" : ipContractId.trim();
    }

    public void setIpContractId(String ipContractId) {
        this.ipContractId = ipContractId == null ? null : ipContractId.trim();
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

    public String getContractId() {
        return contractId == null ? "" : contractId.trim();
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public BigDecimal getApplyAmountNoTax() {
        return applyAmountNoTax;
    }

    public void setApplyAmountNoTax(BigDecimal applyAmountNoTax) {
        this.applyAmountNoTax = applyAmountNoTax;
    }

    public BigDecimal getApplyAmountTax() {
        return applyAmountTax;
    }

    public void setApplyAmountTax(BigDecimal applyAmountTax) {
        this.applyAmountTax = applyAmountTax;
    }

    public BigDecimal getReplyAmountNoTax() {
        return replyAmountNoTax;
    }

    public void setReplyAmountNoTax(BigDecimal replyAmountNoTax) {
        this.replyAmountNoTax = replyAmountNoTax;
    }

    public BigDecimal getReplyAmountTax() {
        return replyAmountTax;
    }

    public void setReplyAmountTax(BigDecimal replyAmountTax) {
        this.replyAmountTax = replyAmountTax;
    }

    public BigDecimal getOriginalContractAmountNoTax() {
        return originalContractAmountNoTax;
    }

    public void setOriginalContractAmountNoTax(BigDecimal originalContractAmountNoTax) {
        this.originalContractAmountNoTax = originalContractAmountNoTax;
    }

    public BigDecimal getOriginalContractTax() {
        return originalContractTax;
    }

    public void setOriginalContractTax(BigDecimal originalContractTax) {
        this.originalContractTax = originalContractTax;
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public List<ZxCcConstCoAlterWork> getZxCcConstCoAlterWorkList() {
        return zxCcConstCoAlterWorkList;
    }

    public void setZxCcConstCoAlterWorkList(List<ZxCcConstCoAlterWork> zxCcConstCoAlterWorkList) {
        this.zxCcConstCoAlterWorkList = zxCcConstCoAlterWorkList;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}

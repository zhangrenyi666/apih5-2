package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCcCoAlter extends BasePojo {
    // 主键
    private String ccCoAlterId;

    // 协作清单书ID(coBookID)
    private String ccCoBookId;

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

    // 审核状态
    private String auditStatus;

    // 废弃字段不要了
    private String combProp;

    // 批复单位
    private String pp1;

    // 原含税合同金额(万元)
    private String pp2;

    // 补充协议ID
    private String pp3;

    // pp4
    private String pp4;

    // pp5
    private String pp5;

    // (IP的合同ID)
    private String pp6;

    // pp7
    private String pp7;

    // pp8
    private String pp8;

    // pp9
    private String pp9;

    // pp10
    private String pp10;

    // 批复单位(pp1)
    private String approvalUnit;

    // 原含税合同金额(万元)(pp2)
    private BigDecimal originalTaxAmount;

    // 补充协议ID(pp3)
    private String ctContrApplyId;

    // (IP的合同ID)(pp6)
    private String ipContractId;

    // 合同ID(contractID)
    private String ctContractId;

    // 机构ID
    private String orgID;

    // 最后编辑时间
    private Date editTime;

    // 本期不含税变更增减金额(万元)
    private BigDecimal applyAmountNoTax;

    // 本期变更增减税额(万元)
    private BigDecimal applyAmountTax;

    // 变更后不含税合同金额(万元)
    private BigDecimal replyAmountNoTax;

    // 变更后合同税额(万元)
    private BigDecimal replyAmountTax;

    // 原不含税合同金额(万元)
    private BigDecimal pp2NoTax;

    // 原合同税额(万元)
    private BigDecimal pp2Tax;

    // 备注
    private String opinionField1;

    // 备注
    private String opinionField2;

    // 备注
    private String opinionField3;

    // 备注
    private String opinionField4;

    // 备注
    private String opinionField5;

    // 备注
    private String opinionField6;

    // 备注
    private String opinionField7;

    // 备注
    private String opinionField8;

    // 备注
    private String opinionField9;

    // 备注
    private String opinionField10;

    // 流程id
    private String apih5FlowId;

    // work_id
    private String workId;

    // 工序审核状态
    private String apih5FlowStatus;

    // 流程状态
    private String apih5FlowNodeStatus;

    // 补充协议清单明细集合
    private List<ZxGcsgCcCoAlterWork> alterWorkList;

    // 附件集合
    private List<ZxGcsgCommonAttachment> attachmentList;

    // 原合同名称
    private String contractName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getCcCoAlterId() {
        return ccCoAlterId == null ? "" : ccCoAlterId.trim();
    }

    public void setCcCoAlterId(String ccCoAlterId) {
        this.ccCoAlterId = ccCoAlterId == null ? null : ccCoAlterId.trim();
    }

    public String getCcCoBookId() {
        return ccCoBookId == null ? "" : ccCoBookId.trim();
    }

    public void setCcCoBookId(String ccCoBookId) {
        this.ccCoBookId = ccCoBookId == null ? null : ccCoBookId.trim();
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

    public String getApprovalUnit() {
        return approvalUnit == null ? "" : approvalUnit.trim();
    }

    public void setApprovalUnit(String approvalUnit) {
        this.approvalUnit = approvalUnit == null ? null : approvalUnit.trim();
    }

    public BigDecimal getOriginalTaxAmount() {
        return originalTaxAmount;
    }

    public void setOriginalTaxAmount(BigDecimal originalTaxAmount) {
        this.originalTaxAmount = originalTaxAmount;
    }

    public String getCtContrApplyId() {
        return ctContrApplyId == null ? "" : ctContrApplyId.trim();
    }

    public void setCtContrApplyId(String ctContrApplyId) {
        this.ctContrApplyId = ctContrApplyId == null ? null : ctContrApplyId.trim();
    }

    public String getIpContractId() {
        return ipContractId == null ? "" : ipContractId.trim();
    }

    public void setIpContractId(String ipContractId) {
        this.ipContractId = ipContractId == null ? null : ipContractId.trim();
    }

    public String getCtContractId() {
        return ctContractId == null ? "" : ctContractId.trim();
    }

    public void setCtContractId(String ctContractId) {
        this.ctContractId = ctContractId == null ? null : ctContractId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public BigDecimal getPp2NoTax() {
        return pp2NoTax;
    }

    public void setPp2NoTax(BigDecimal pp2NoTax) {
        this.pp2NoTax = pp2NoTax;
    }

    public BigDecimal getPp2Tax() {
        return pp2Tax;
    }

    public void setPp2Tax(BigDecimal pp2Tax) {
        this.pp2Tax = pp2Tax;
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

    public String getOpinionField6() {
        return opinionField6 == null ? "" : opinionField6.trim();
    }

    public void setOpinionField6(String opinionField6) {
        this.opinionField6 = opinionField6 == null ? null : opinionField6.trim();
    }

    public String getOpinionField7() {
        return opinionField7 == null ? "" : opinionField7.trim();
    }

    public void setOpinionField7(String opinionField7) {
        this.opinionField7 = opinionField7 == null ? null : opinionField7.trim();
    }

    public String getOpinionField8() {
        return opinionField8 == null ? "" : opinionField8.trim();
    }

    public void setOpinionField8(String opinionField8) {
        this.opinionField8 = opinionField8 == null ? null : opinionField8.trim();
    }

    public String getOpinionField9() {
        return opinionField9 == null ? "" : opinionField9.trim();
    }

    public void setOpinionField9(String opinionField9) {
        this.opinionField9 = opinionField9 == null ? null : opinionField9.trim();
    }

    public String getOpinionField10() {
        return opinionField10 == null ? "" : opinionField10.trim();
    }

    public void setOpinionField10(String opinionField10) {
        this.opinionField10 = opinionField10 == null ? null : opinionField10.trim();
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

    public List<ZxGcsgCcCoAlterWork> getAlterWorkList() {
        return alterWorkList == null ? Lists.newArrayList() : alterWorkList;
    }

    public void setAlterWorkList(List<ZxGcsgCcCoAlterWork> alterWorkList) {
        this.alterWorkList = alterWorkList == null ? null : alterWorkList;
    }

    public List<ZxGcsgCommonAttachment> getAttachmentList() {
        return attachmentList == null ? Lists.newArrayList() : attachmentList;
    }

    public void setAttachmentList(List<ZxGcsgCommonAttachment> attachmentList) {
        this.attachmentList = attachmentList == null ? null : attachmentList;
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
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

package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.apih5.framework.entity.BasePojoFlow;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSkPurchase extends BasePojoFlow {
    // 主键
    private String id;

    // 单据编号
    private String billNo;

    // 机构名称
    private String orgName;

    // 项目名称ID
    private String orgID;

    // 购置金额(万元)
    private BigDecimal purchaseAmt;

    // 审批内容
    private String approval;

    // 发起人
    private String beginPer;

    // 评审状态
    private String auditStatus;

    // 流程实例ID
    private String instProcessId;

    // 公文任务ID
    private String workitemID;

    // 是否局审批
    private String isFlag;

    // 发送局判断ID
    private String sendToJuID;

    // 是否局指审批
    private String isFlagZhb;

    // 发送局指判断ID
    private String sendToZhbID;

    // comID
    private String comID;

    // 公司名称
    private String comName;

    // comOrders
    private String comOrders;

    // 填报日期
    private Date reportDate;

    // appInsHistID
    private String appInsHistID;

    // appInsHistIDZhb
    private String appInsHistIDZhb;

    // isReportZhb
    private String isReportZhb;

    // isReport
    private String isReport;

    // combProp
    private String combProp;

    // 流程id
    private String workId;

    // 流程状态
    private String apih5FlowStatus;

    // 意见1
    private String opinionField1;

    // 意见2
    private String opinionField2;

    // 意见3
    private String opinionField3;

    // 意见4
    private String opinionField4;

    // 意见5
    private String opinionField5;

    // 意见6
    private String opinionField6;

    // 意见7
    private String opinionField7;

    // 意见8
    private String opinionField8;

    // 意见9
    private String opinionField9;

    // 意见10
    private String opinionField10;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //附件
    private List<ZxErpFile> fileList;

    public List<ZxErpFile> getFileList() {
        return fileList;
    }

    public void setFileList(List<ZxErpFile> fileList) {
        this.fileList = fileList;
    }

    public String getId() {
        return id == null ? "" : id.trim();
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public BigDecimal getPurchaseAmt() {
        return purchaseAmt;
    }

    public void setPurchaseAmt(BigDecimal purchaseAmt) {
        this.purchaseAmt = purchaseAmt;
    }

    public String getApproval() {
        return approval == null ? "" : approval.trim();
    }

    public void setApproval(String approval) {
        this.approval = approval == null ? null : approval.trim();
    }

    public String getBeginPer() {
        return beginPer == null ? "" : beginPer.trim();
    }

    public void setBeginPer(String beginPer) {
        this.beginPer = beginPer == null ? null : beginPer.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getInstProcessId() {
        return instProcessId == null ? "" : instProcessId.trim();
    }

    public void setInstProcessId(String instProcessId) {
        this.instProcessId = instProcessId == null ? null : instProcessId.trim();
    }

    public String getWorkitemID() {
        return workitemID == null ? "" : workitemID.trim();
    }

    public void setWorkitemID(String workitemID) {
        this.workitemID = workitemID == null ? null : workitemID.trim();
    }

    public String getIsFlag() {
        return isFlag == null ? "" : isFlag.trim();
    }

    public void setIsFlag(String isFlag) {
        this.isFlag = isFlag == null ? null : isFlag.trim();
    }

    public String getSendToJuID() {
        return sendToJuID == null ? "" : sendToJuID.trim();
    }

    public void setSendToJuID(String sendToJuID) {
        this.sendToJuID = sendToJuID == null ? null : sendToJuID.trim();
    }

    public String getIsFlagZhb() {
        return isFlagZhb == null ? "" : isFlagZhb.trim();
    }

    public void setIsFlagZhb(String isFlagZhb) {
        this.isFlagZhb = isFlagZhb == null ? null : isFlagZhb.trim();
    }

    public String getSendToZhbID() {
        return sendToZhbID == null ? "" : sendToZhbID.trim();
    }

    public void setSendToZhbID(String sendToZhbID) {
        this.sendToZhbID = sendToZhbID == null ? null : sendToZhbID.trim();
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

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getAppInsHistID() {
        return appInsHistID == null ? "" : appInsHistID.trim();
    }

    public void setAppInsHistID(String appInsHistID) {
        this.appInsHistID = appInsHistID == null ? null : appInsHistID.trim();
    }

    public String getAppInsHistIDZhb() {
        return appInsHistIDZhb == null ? "" : appInsHistIDZhb.trim();
    }

    public void setAppInsHistIDZhb(String appInsHistIDZhb) {
        this.appInsHistIDZhb = appInsHistIDZhb == null ? null : appInsHistIDZhb.trim();
    }

    public String getIsReportZhb() {
        return isReportZhb == null ? "" : isReportZhb.trim();
    }

    public void setIsReportZhb(String isReportZhb) {
        this.isReportZhb = isReportZhb == null ? null : isReportZhb.trim();
    }

    public String getIsReport() {
        return isReport == null ? "" : isReport.trim();
    }

    public void setIsReport(String isReport) {
        this.isReport = isReport == null ? null : isReport.trim();
    }

    public String getCombProp() {
        return combProp == null ? "" : combProp.trim();
    }

    public void setCombProp(String combProp) {
        this.combProp = combProp == null ? null : combProp.trim();
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
    private String orgID2;

    public String getOrgID2() {
        return orgID2;
    }

    public void setOrgID2(String orgID2) {
        this.orgID2 = orgID2;
    }
}

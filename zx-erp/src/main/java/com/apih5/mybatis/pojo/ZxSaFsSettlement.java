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

public class ZxSaFsSettlement extends BasePojoFlow {
    // 主键
    private String zxSaFsSettlementId;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 签认单编号
    private String signedNo;

    // 结算期次
    private String period;

    // 附属合同ID
    private String zxCtFsContractId;

    // 合同编号
    private String contractNo;

    // 合同名称
    private String contractName;

    // 乙方ID
    private String secondID;

    // 乙方名称
    private String secondName;

    // 填报日期
    private Date reportDate;

    // 是否首次结算
    private String isFirst;

    // 签认单初始化顺序号
    private String initSerialNumber;

    // 完成下列内容的开始日期
    private Date startDate;

    // 填报人
    private String reportPerson;

    // 附图及附表(可另附页计算)
    private String otherInfo;

    // 验收情况
    private String appraisal;

    // 审核状态
    private String auditStatus;

    // 流水号
    private String serialNumber;

    // 最后编辑时间
    private Date editTime;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 是否被引用
    private int useCount=0;

    // 是否最大期次
    private int isMaxPeriod=0;

    // 结算单编号
    private String billNo;

    // 业务日期
    private Date businessDate;

    // 含税合同金额(万元)
    private BigDecimal contractAmt;

    // 变更后含税合同金额(万元)
    private BigDecimal changeAmt;

    // 结算类型
    private String billType;

    // 本期结算含税金额(元)
    private BigDecimal thisAmt;

    // 开累结算含税金额(元)
    private BigDecimal totalAmt;

    // 开累结算不含税金额(元)
    private BigDecimal totalAmtNoTax;

    // 本期清单结算金额(元)
    private BigDecimal thisEquipAmt;

    // 开累清单结算金额(元)
    private BigDecimal totalEquipAmt;

    // 本期支付金额(元)
    private BigDecimal thisPayAmt;

    // 开累支付金额(元)
    private BigDecimal totalPayAmt;

    // 是否完工后结算
    private String isFished;

    // 本期结算不含税金额
    private BigDecimal thisAmtNoTax;

    // 本期结算税额
    private BigDecimal thisAmtTax;

    // 引用签认单未审核数量
    private int useSignedOrder=0;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 物资结算表ID
    private String billID;

    // 合同类型
    private String contrType;

    // 结算期限开始时间
    private Date beginDate;

    // 结算期限结束时间
    private Date endDate;

    // 意见1
    private String opinionField1;

    // 意见3
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

    // 流程ID
    private String apih5FlowId;

    // work_id
    private String workId;

    // 审核状态
    private String apih5FlowStatus;

    // 审核节点状态
    private String apih5FlowNodeStatus;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private Long periodTime;

    //文件列表（文件工具）
    private List<ZxErpFile> zxErpFileList;

    //文件列表（文件工具）
    private List<ZxErpFile> zxZhengWenFileList;

    private int countNo;

    //计算人
    private String countPerson;

    //复核人
    private String reCountPerson;

    //结算内容
    private String content;

    private List<ZxSaFsInventorySettlementDetail> uReportList;

    public List<ZxErpFile> getZxZhengWenFileList() {
        return zxZhengWenFileList;
    }

    public void setZxZhengWenFileList(List<ZxErpFile> zxZhengWenFileList) {
        this.zxZhengWenFileList = zxZhengWenFileList;
    }

    public List<ZxSaFsInventorySettlementDetail> getuReportList() {
        return uReportList;
    }

    public void setuReportList(List<ZxSaFsInventorySettlementDetail> uReportList) {
        this.uReportList = uReportList;
    }

    public String getCountPerson() {
        return countPerson == null ? "" : countPerson.trim();
    }

    public void setCountPerson(String countPerson) {
        this.countPerson = countPerson == null ? null : countPerson.trim();
    }

    public String getReCountPerson() {
        return reCountPerson == null ? "" : reCountPerson.trim();
    }

    public void setReCountPerson(String reCountPerson) {
        this.reCountPerson = reCountPerson == null ? null : reCountPerson.trim();
    }

    public String getContent() {
        return content == null ? "" : content.trim();
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public int getCountNo() {
        return countNo;
    }

    public void setCountNo(int countNo) {
        this.countNo = countNo;
    }

    public List<ZxErpFile> getZxErpFileList() {
        return zxErpFileList;
    }

    public void setZxErpFileList(List<ZxErpFile> zxErpFileList) {
        this.zxErpFileList = zxErpFileList;
    }

    public Long getPeriodTime() {
        return periodTime;
    }

    public void setPeriodTime(Long periodTime) {
        this.periodTime = periodTime;
    }

    public String getZxSaFsSettlementId() {
        return zxSaFsSettlementId == null ? "" : zxSaFsSettlementId.trim();
    }

    public void setZxSaFsSettlementId(String zxSaFsSettlementId) {
        this.zxSaFsSettlementId = zxSaFsSettlementId == null ? null : zxSaFsSettlementId.trim();
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

    public String getSignedNo() {
        return signedNo == null ? "" : signedNo.trim();
    }

    public void setSignedNo(String signedNo) {
        this.signedNo = signedNo == null ? null : signedNo.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getZxCtFsContractId() {
        return zxCtFsContractId == null ? "" : zxCtFsContractId.trim();
    }

    public void setZxCtFsContractId(String zxCtFsContractId) {
        this.zxCtFsContractId = zxCtFsContractId == null ? null : zxCtFsContractId.trim();
    }

    public String getContractNo() {
        return contractNo == null ? "" : contractNo.trim();
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo == null ? null : contractNo.trim();
    }

    public String getContractName() {
        return contractName == null ? "" : contractName.trim();
    }

    public void setContractName(String contractName) {
        this.contractName = contractName == null ? null : contractName.trim();
    }

    public String getSecondID() {
        return secondID == null ? "" : secondID.trim();
    }

    public void setSecondID(String secondID) {
        this.secondID = secondID == null ? null : secondID.trim();
    }

    public String getSecondName() {
        return secondName == null ? "" : secondName.trim();
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName == null ? null : secondName.trim();
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getIsFirst() {
        return isFirst == null ? "" : isFirst.trim();
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst == null ? null : isFirst.trim();
    }

    public String getInitSerialNumber() {
        return initSerialNumber == null ? "" : initSerialNumber.trim();
    }

    public void setInitSerialNumber(String initSerialNumber) {
        this.initSerialNumber = initSerialNumber == null ? null : initSerialNumber.trim();
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getReportPerson() {
        return reportPerson == null ? "" : reportPerson.trim();
    }

    public void setReportPerson(String reportPerson) {
        this.reportPerson = reportPerson == null ? null : reportPerson.trim();
    }

    public String getOtherInfo() {
        return otherInfo == null ? "" : otherInfo.trim();
    }

    public void setOtherInfo(String otherInfo) {
        this.otherInfo = otherInfo == null ? null : otherInfo.trim();
    }

    public String getAppraisal() {
        return appraisal == null ? "" : appraisal.trim();
    }

    public void setAppraisal(String appraisal) {
        this.appraisal = appraisal == null ? null : appraisal.trim();
    }

    public String getAuditStatus() {
        return auditStatus == null ? "" : auditStatus.trim();
    }

    public void setAuditStatus(String auditStatus) {
        this.auditStatus = auditStatus == null ? null : auditStatus.trim();
    }

    public String getSerialNumber() {
        return serialNumber == null ? "" : serialNumber.trim();
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
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

    public int getUseCount() {
        return useCount;
    }

    public void setUseCount(int useCount) {
        this.useCount = useCount;
    }

    public int getIsMaxPeriod() {
        return isMaxPeriod;
    }

    public void setIsMaxPeriod(int isMaxPeriod) {
        this.isMaxPeriod = isMaxPeriod;
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public Date getBusinessDate() {
        return businessDate;
    }

    public void setBusinessDate(Date businessDate) {
        this.businessDate = businessDate;
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public BigDecimal getChangeAmt() {
        return changeAmt;
    }

    public void setChangeAmt(BigDecimal changeAmt) {
        this.changeAmt = changeAmt;
    }

    public String getBillType() {
        return billType == null ? "" : billType.trim();
    }

    public void setBillType(String billType) {
        this.billType = billType == null ? null : billType.trim();
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public BigDecimal getThisEquipAmt() {
        return thisEquipAmt;
    }

    public void setThisEquipAmt(BigDecimal thisEquipAmt) {
        this.thisEquipAmt = thisEquipAmt;
    }

    public BigDecimal getTotalEquipAmt() {
        return totalEquipAmt;
    }

    public void setTotalEquipAmt(BigDecimal totalEquipAmt) {
        this.totalEquipAmt = totalEquipAmt;
    }

    public BigDecimal getThisPayAmt() {
        return thisPayAmt;
    }

    public void setThisPayAmt(BigDecimal thisPayAmt) {
        this.thisPayAmt = thisPayAmt;
    }

    public BigDecimal getTotalPayAmt() {
        return totalPayAmt;
    }

    public void setTotalPayAmt(BigDecimal totalPayAmt) {
        this.totalPayAmt = totalPayAmt;
    }

    public String getIsFished() {
        return isFished == null ? "" : isFished.trim();
    }

    public void setIsFished(String isFished) {
        this.isFished = isFished == null ? null : isFished.trim();
    }

    public BigDecimal getThisAmtNoTax() {
        return thisAmtNoTax;
    }

    public void setThisAmtNoTax(BigDecimal thisAmtNoTax) {
        this.thisAmtNoTax = thisAmtNoTax;
    }

    public BigDecimal getThisAmtTax() {
        return thisAmtTax;
    }

    public void setThisAmtTax(BigDecimal thisAmtTax) {
        this.thisAmtTax = thisAmtTax;
    }

    public int getUseSignedOrder() {
        return useSignedOrder;
    }

    public void setUseSignedOrder(int useSignedOrder) {
        this.useSignedOrder = useSignedOrder;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

    public Date getBeginDate() {
        return beginDate;
    }

    public void setBeginDate(Date beginDate) {
        this.beginDate = beginDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
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

    public BigDecimal getTotalAmtNoTax() {
        return totalAmtNoTax;
    }

    public void setTotalAmtNoTax(BigDecimal totalAmtNoTax) {
        this.totalAmtNoTax = totalAmtNoTax;
    }

}

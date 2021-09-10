package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaFsInventorySettlement extends BasePojo {
    // 主键
    private String zxSaFsInventorySettlementId;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 附属合同ID
    private String zxCtFsContractId;

    // 附属类结算表ID
    private String zxSaFsSettlementId;

    // 结算单编号
    private String billNo;

    // 结算期次
    private String period;

    // 签认单ID
    private String signedOrders;

    // 签认单编号
    private String signedNos;

    // 含税合同金额(万元)
    private BigDecimal contractAmt;

    // 变更后含税合同金额(万元)
    private BigDecimal changeAmt;

    // 本期清单结算含税金额(元)
    private BigDecimal thisAmt;

    // 累计清单结算含税金额(元)
    private BigDecimal totalAmt;

    // 上期末清单结算金额(元)
    private BigDecimal upAmt;

    // 最后编辑时间
    private Date editTime;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 合同类型
    private String contrType;

    // 本期清单结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期清单结算税额(元)
    private BigDecimal thisAmtTax;

    // 是否抵扣
    private String isDeduct;

    // 税率(%)
    private String taxRate;

    // 是否首次结算
    private String isFirst;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;
    private List<ZxSaFsInventorySettlementDetail> detailList;

    public List<ZxSaFsInventorySettlementDetail> getDetailList() {
        return detailList;
    }

    public void setDetailList(List<ZxSaFsInventorySettlementDetail> detailList) {
        this.detailList = detailList;
    }

    public String getZxSaFsInventorySettlementId() {
        return zxSaFsInventorySettlementId == null ? "" : zxSaFsInventorySettlementId.trim();
    }

    public void setZxSaFsInventorySettlementId(String zxSaFsInventorySettlementId) {
        this.zxSaFsInventorySettlementId = zxSaFsInventorySettlementId == null ? null : zxSaFsInventorySettlementId.trim();
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

    public String getZxCtFsContractId() {
        return zxCtFsContractId == null ? "" : zxCtFsContractId.trim();
    }

    public void setZxCtFsContractId(String zxCtFsContractId) {
        this.zxCtFsContractId = zxCtFsContractId == null ? null : zxCtFsContractId.trim();
    }

    public String getZxSaFsSettlementId() {
        return zxSaFsSettlementId == null ? "" : zxSaFsSettlementId.trim();
    }

    public void setZxSaFsSettlementId(String zxSaFsSettlementId) {
        this.zxSaFsSettlementId = zxSaFsSettlementId == null ? null : zxSaFsSettlementId.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getSignedOrders() {
        return signedOrders == null ? "" : signedOrders.trim();
    }

    public void setSignedOrders(String signedOrders) {
        this.signedOrders = signedOrders == null ? null : signedOrders.trim();
    }

    public String getSignedNos() {
        return signedNos == null ? "" : signedNos.trim();
    }

    public void setSignedNos(String signedNos) {
        this.signedNos = signedNos == null ? null : signedNos.trim();
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

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
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

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
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

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
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

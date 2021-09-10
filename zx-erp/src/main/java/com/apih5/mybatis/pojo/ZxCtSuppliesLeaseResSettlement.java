package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesLeaseResSettlement extends BasePojo {
    // 主键
    private String zxCtSuppliesLeaseResSettlementId;

    // 最后编辑时间
    private Date editTime;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 上期末清单结算金额(元)
    private BigDecimal upAmt;

    // 结算期次
    private String period;

    // 合同ID
    private String contractID;

    // 含税合同金额(万)
    private BigDecimal contractAmt;

    // 本期清单结算含税金额(元)
    private BigDecimal thisAmt;

    // 累计清单结算含税金额(元)
    private BigDecimal totalAmt;

    // 项目ID
    private String orgID;

    // 项目名称
    private String orgName;

    // 租赁类结算表ID
    private String billID;

    // 结算单编号
    private String billNo;

    // 签认单编号
    private String signedNos;

    // 本期清单结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期清单结算税额(元)
    private BigDecimal thisAmtTax;

    // 签认单ID
    private String signedOrders;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCtSuppliesLeaseResSettlementId() {
        return zxCtSuppliesLeaseResSettlementId == null ? "" : zxCtSuppliesLeaseResSettlementId.trim();
    }

    public void setZxCtSuppliesLeaseResSettlementId(String zxCtSuppliesLeaseResSettlementId) {
        this.zxCtSuppliesLeaseResSettlementId = zxCtSuppliesLeaseResSettlementId == null ? null : zxCtSuppliesLeaseResSettlementId.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getComID() {
        return comID == null ? "" : comID.trim();
    }

    public void setComID(String comID) {
        this.comID = comID == null ? null : comID.trim();
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
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

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getSignedNos() {
        return signedNos == null ? "" : signedNos.trim();
    }

    public void setSignedNos(String signedNos) {
        this.signedNos = signedNos == null ? null : signedNos.trim();
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

    public String getSignedOrders() {
        return signedOrders == null ? "" : signedOrders.trim();
    }

    public void setSignedOrders(String signedOrders) {
        this.signedOrders = signedOrders == null ? null : signedOrders.trim();
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

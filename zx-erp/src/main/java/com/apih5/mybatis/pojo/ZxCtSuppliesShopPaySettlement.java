package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesShopPaySettlement extends BasePojo {
    // 主键
    private String zxCtSuppliesShopPaySettlementId;

    // 最后编辑时间
    private Date editTime;

    // 项目名称
    private String orgName;

    // 项目ID
    private String orgID;

    // 物资结算表ID
    private String billID;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 上期末运杂费(元)
    private BigDecimal upTransportAmt;

    // 上期末其他款项(元)
    private BigDecimal upOtherAmt;

    // 上期末累计支付项结算金额(元)
    private BigDecimal upAmt;

    // 上期末奖罚金(元)
    private BigDecimal upFineAmt;

    // 上期末垫资费(元)
    private BigDecimal upPadTariffAmt;

    // 累计支付项结算金额(元)
    private BigDecimal totalAmt;

    // 结算期次
    private String period;

    // 合同ID
    private String contractID;

    // 本期支付项结算税额
    private BigDecimal thisAmtTax;

    // 本期支付项结算金额(元)
    private BigDecimal thisAmt;

    // 本期支付项结算不含税金额
    private BigDecimal thisAmtNoTax;

    // 本期运杂费(元)
    private BigDecimal transportAmt;

    // 本期其他款项(元)
    private BigDecimal otherAmt;

    // 本期奖罚金(元)
    private BigDecimal fineAmt;

    // 本期垫资费(元)
    private BigDecimal padTariffAmt;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

	public String getZxCtSuppliesShopPaySettlementId() {
        return zxCtSuppliesShopPaySettlementId == null ? "" : zxCtSuppliesShopPaySettlementId.trim();
    }

    public void setZxCtSuppliesShopPaySettlementId(String zxCtSuppliesShopPaySettlementId) {
        this.zxCtSuppliesShopPaySettlementId = zxCtSuppliesShopPaySettlementId == null ? null : zxCtSuppliesShopPaySettlementId.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
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

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
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

    public BigDecimal getUpTransportAmt() {
        return upTransportAmt;
    }

    public void setUpTransportAmt(BigDecimal upTransportAmt) {
        this.upTransportAmt = upTransportAmt;
    }

    public BigDecimal getUpOtherAmt() {
        return upOtherAmt;
    }

    public void setUpOtherAmt(BigDecimal upOtherAmt) {
        this.upOtherAmt = upOtherAmt;
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public BigDecimal getUpFineAmt() {
        return upFineAmt;
    }

    public void setUpFineAmt(BigDecimal upFineAmt) {
        this.upFineAmt = upFineAmt;
    }

    public BigDecimal getUpPadTariffAmt() {
        return upPadTariffAmt;
    }

    public void setUpPadTariffAmt(BigDecimal upPadTariffAmt) {
        this.upPadTariffAmt = upPadTariffAmt;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
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

    public BigDecimal getThisAmtTax() {
        return thisAmtTax;
    }

    public void setThisAmtTax(BigDecimal thisAmtTax) {
        this.thisAmtTax = thisAmtTax;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getThisAmtNoTax() {
        return thisAmtNoTax;
    }

    public void setThisAmtNoTax(BigDecimal thisAmtNoTax) {
        this.thisAmtNoTax = thisAmtNoTax;
    }

    public BigDecimal getTransportAmt() {
        return transportAmt;
    }

    public void setTransportAmt(BigDecimal transportAmt) {
        this.transportAmt = transportAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(BigDecimal fineAmt) {
        this.fineAmt = fineAmt;
    }

    public BigDecimal getPadTariffAmt() {
        return padTariffAmt;
    }

    public void setPadTariffAmt(BigDecimal padTariffAmt) {
        this.padTariffAmt = padTariffAmt;
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

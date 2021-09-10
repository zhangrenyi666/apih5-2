package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesLeasePaySettlement extends BasePojo {
    // 主键
    private String zxCtSuppliesLeasePaySettlementId;

    // 最后编辑时间
    private Date editTime;

    // 运杂费本期结算小计(元)
    private BigDecimal inOutAmt;

    // 项目名称
    private String orgName;

    // 项目ID
    private String orgID;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 损耗本期结算小计(元)
    private BigDecimal foodAmt;

    // 上期运杂费结算小计(元)
    private BigDecimal upInOutAmt;

    // 上期损耗结算小计(元)
    private BigDecimal upFoodAmt;

    // 上期其它款项结算小计(元)
    private BigDecimal upOtherAmt;

    // 上期末累计支付项结算金额(元)
    private BigDecimal upAmt;

    // 上期奖罚结算小计(元)
    private BigDecimal upFineAmt;

    // 其它款项本期结算小计(元)
    private BigDecimal otherAmt;

    // 累计支付项结算金额(元)
    private BigDecimal totalAmt;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 结算表ID
    private String billID;

    // 奖罚本期结算小计(元)
    private BigDecimal fineAmt;

    // 合同类型
    private String contrType;

    // 合同ID
    private String contractID;

    // 本期支付项结算税额(元)
    private BigDecimal thisAmtTax;

    // 本期支付项结算金额(元)
    private BigDecimal thisAmt;

    // 本期支付项结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCtSuppliesLeasePaySettlementId() {
        return zxCtSuppliesLeasePaySettlementId == null ? "" : zxCtSuppliesLeasePaySettlementId.trim();
    }

    public void setZxCtSuppliesLeasePaySettlementId(String zxCtSuppliesLeasePaySettlementId) {
        this.zxCtSuppliesLeasePaySettlementId = zxCtSuppliesLeasePaySettlementId == null ? null : zxCtSuppliesLeasePaySettlementId.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public BigDecimal getInOutAmt() {
        return inOutAmt;
    }

    public void setInOutAmt(BigDecimal inOutAmt) {
        this.inOutAmt = inOutAmt;
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

    public BigDecimal getFoodAmt() {
        return foodAmt;
    }

    public void setFoodAmt(BigDecimal foodAmt) {
        this.foodAmt = foodAmt;
    }

    public BigDecimal getUpInOutAmt() {
        return upInOutAmt;
    }

    public void setUpInOutAmt(BigDecimal upInOutAmt) {
        this.upInOutAmt = upInOutAmt;
    }

    public BigDecimal getUpFoodAmt() {
        return upFoodAmt;
    }

    public void setUpFoodAmt(BigDecimal upFoodAmt) {
        this.upFoodAmt = upFoodAmt;
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

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
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

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public BigDecimal getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(BigDecimal fineAmt) {
        this.fineAmt = fineAmt;
    }

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
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

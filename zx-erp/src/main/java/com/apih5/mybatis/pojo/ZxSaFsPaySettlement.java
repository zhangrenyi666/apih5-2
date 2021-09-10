package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaFsPaySettlement extends BasePojo {
    // 主键
    private String zxSaFsPaySettlementId;

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

    // 本期支付项结算金额(元)
    private BigDecimal thisAmt;

    // 累计支付项结算金额(元)
    private BigDecimal totalAmt;

    // 上期末累计支付项结算金额(元)
    private BigDecimal upAmt;

    // 最后编辑时间
    private Date editTime;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 本期进退场费
    private BigDecimal inOutAmt;

    // 上期末进退场费
    private BigDecimal upInOutAmt;

    // 本期奖罚
    private BigDecimal fineAmt;

    // 上期末奖罚
    private BigDecimal upFineAmt;

    // 本期伙食费
    private BigDecimal foodAmt;

    // 上期末伙食费
    private BigDecimal upFoodAmt;

    // 本期其它结算支付项
    private BigDecimal otherAmt;

    // 上期末其它结算支付项
    private BigDecimal upOtherAmt;

    // 合同类型
    private String contrType;

    // 本期支付项结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期支付项结算税额(元)
    private BigDecimal thisAmtTax;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private long totalNumber=0;

    public long getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(long totalNumber) {
        this.totalNumber = totalNumber;
    }

    private List<ZxSaFsPaySettlementDetail> payList;

    public List<ZxSaFsPaySettlementDetail> getPayList() {
        return payList;
    }

    public void setPayList(List<ZxSaFsPaySettlementDetail> payList) {
        this.payList = payList;
    }

    public String getZxSaFsPaySettlementId() {
        return zxSaFsPaySettlementId == null ? "" : zxSaFsPaySettlementId.trim();
    }

    public void setZxSaFsPaySettlementId(String zxSaFsPaySettlementId) {
        this.zxSaFsPaySettlementId = zxSaFsPaySettlementId == null ? null : zxSaFsPaySettlementId.trim();
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

    public BigDecimal getInOutAmt() {
        return inOutAmt;
    }

    public void setInOutAmt(BigDecimal inOutAmt) {
        this.inOutAmt = inOutAmt;
    }

    public BigDecimal getUpInOutAmt() {
        return upInOutAmt;
    }

    public void setUpInOutAmt(BigDecimal upInOutAmt) {
        this.upInOutAmt = upInOutAmt;
    }

    public BigDecimal getFineAmt() {
        return fineAmt;
    }

    public void setFineAmt(BigDecimal fineAmt) {
        this.fineAmt = fineAmt;
    }

    public BigDecimal getUpFineAmt() {
        return upFineAmt;
    }

    public void setUpFineAmt(BigDecimal upFineAmt) {
        this.upFineAmt = upFineAmt;
    }

    public BigDecimal getFoodAmt() {
        return foodAmt;
    }

    public void setFoodAmt(BigDecimal foodAmt) {
        this.foodAmt = foodAmt;
    }

    public BigDecimal getUpFoodAmt() {
        return upFoodAmt;
    }

    public void setUpFoodAmt(BigDecimal upFoodAmt) {
        this.upFoodAmt = upFoodAmt;
    }

    public BigDecimal getOtherAmt() {
        return otherAmt;
    }

    public void setOtherAmt(BigDecimal otherAmt) {
        this.otherAmt = otherAmt;
    }

    public BigDecimal getUpOtherAmt() {
        return upOtherAmt;
    }

    public void setUpOtherAmt(BigDecimal upOtherAmt) {
        this.upOtherAmt = upOtherAmt;
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

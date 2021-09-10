package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuBudgetDetails extends BasePojo {
    // 主键
    private String zxBuBudgetDetailsId;

    // null
    private String budgetBookID;

    // 预算类型
    private String budgetType;

    // 预算元素类型
    private String budgetElementType;

    // 预算元素
    private String budgetElement;

    // 预算元素单位
    private String budgetElementUnit;

    // 预算元素首要费用
    private BigDecimal budgetElementFirstFree;

    // 预算元素当前的费用
    private BigDecimal budgetElementCurrFree;

    // null
    private int audited=0;

    // null
    private int needDeduct=0;

    // null
    private Date editTime;

    // null
    private String scBudgetDetailsID;

    // null
    private BigDecimal firstJtFee;

    // null
    private BigDecimal firstDwFee;

    // null
    private BigDecimal currJtFee;

    // null
    private BigDecimal currDwFee;

    // null
    private BigDecimal remfirstJtFee;

    // null
    private BigDecimal remfirstDwFee;

    // null
    private BigDecimal remcurrJtFee;

    // null
    private BigDecimal remcurrDwFee;

    // null
    private BigDecimal finFirst;

    // null
    private BigDecimal finCurr;

    // nulll
    private BigDecimal remFirst;

    // null
    private BigDecimal remCurr;

    // null
    private BigDecimal finConPrice;

    // null
    private BigDecimal finChgAmount;

    // null
    private BigDecimal finAwdPenalty;

    // null
    private BigDecimal finMtladjustment;

    // null
    private BigDecimal finTaxPrice;

    // null
    private BigDecimal finOther;

    // null
    private BigDecimal fintotal;

    // null
    private BigDecimal remConPrice;

    // null
    private BigDecimal remChgAmount;

    // null
    private BigDecimal remAwdPenalty;

    // null
    private BigDecimal remMtladjustment;

    // null
    private BigDecimal remTaxPrice;

    // null
    private BigDecimal remOther;

    // null
    private BigDecimal remtotal;

    // null
    private BigDecimal firstJtSca;

    // null
    private BigDecimal firstDwSca;

    // null
    private BigDecimal currJtSca;

    // null
    private BigDecimal currDwSca;

    // null
    private String orderNo;

    // 顺序
    private String serialNumber;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    private String orgID;

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getZxBuBudgetDetailsId() {
        return zxBuBudgetDetailsId == null ? "" : zxBuBudgetDetailsId.trim();
    }

    public void setZxBuBudgetDetailsId(String zxBuBudgetDetailsId) {
        this.zxBuBudgetDetailsId = zxBuBudgetDetailsId == null ? null : zxBuBudgetDetailsId.trim();
    }

    public String getBudgetBookID() {
        return budgetBookID == null ? "" : budgetBookID.trim();
    }

    public void setBudgetBookID(String budgetBookID) {
        this.budgetBookID = budgetBookID == null ? null : budgetBookID.trim();
    }

    public String getBudgetType() {
        return budgetType == null ? "" : budgetType.trim();
    }

    public void setBudgetType(String budgetType) {
        this.budgetType = budgetType == null ? null : budgetType.trim();
    }

    public String getBudgetElementType() {
        return budgetElementType == null ? "" : budgetElementType.trim();
    }

    public void setBudgetElementType(String budgetElementType) {
        this.budgetElementType = budgetElementType == null ? null : budgetElementType.trim();
    }

    public String getBudgetElement() {
        return budgetElement == null ? "" : budgetElement.trim();
    }

    public void setBudgetElement(String budgetElement) {
        this.budgetElement = budgetElement == null ? null : budgetElement.trim();
    }

    public String getBudgetElementUnit() {
        return budgetElementUnit == null ? "" : budgetElementUnit.trim();
    }

    public void setBudgetElementUnit(String budgetElementUnit) {
        this.budgetElementUnit = budgetElementUnit == null ? null : budgetElementUnit.trim();
    }

    public BigDecimal getBudgetElementFirstFree() {
        return budgetElementFirstFree;
    }

    public void setBudgetElementFirstFree(BigDecimal budgetElementFirstFree) {
        this.budgetElementFirstFree = budgetElementFirstFree;
    }

    public BigDecimal getBudgetElementCurrFree() {
        return budgetElementCurrFree;
    }

    public void setBudgetElementCurrFree(BigDecimal budgetElementCurrFree) {
        this.budgetElementCurrFree = budgetElementCurrFree;
    }

    public int getAudited() {
        return audited;
    }

    public void setAudited(int audited) {
        this.audited = audited;
    }

    public int getNeedDeduct() {
        return needDeduct;
    }

    public void setNeedDeduct(int needDeduct) {
        this.needDeduct = needDeduct;
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getScBudgetDetailsID() {
        return scBudgetDetailsID == null ? "" : scBudgetDetailsID.trim();
    }

    public void setScBudgetDetailsID(String scBudgetDetailsID) {
        this.scBudgetDetailsID = scBudgetDetailsID == null ? null : scBudgetDetailsID.trim();
    }

    public BigDecimal getFirstJtFee() {
        return firstJtFee;
    }

    public void setFirstJtFee(BigDecimal firstJtFee) {
        this.firstJtFee = firstJtFee;
    }

    public BigDecimal getFirstDwFee() {
        return firstDwFee;
    }

    public void setFirstDwFee(BigDecimal firstDwFee) {
        this.firstDwFee = firstDwFee;
    }

    public BigDecimal getCurrJtFee() {
        return currJtFee;
    }

    public void setCurrJtFee(BigDecimal currJtFee) {
        this.currJtFee = currJtFee;
    }

    public BigDecimal getCurrDwFee() {
        return currDwFee;
    }

    public void setCurrDwFee(BigDecimal currDwFee) {
        this.currDwFee = currDwFee;
    }

    public BigDecimal getRemfirstJtFee() {
        return remfirstJtFee;
    }

    public void setRemfirstJtFee(BigDecimal remfirstJtFee) {
        this.remfirstJtFee = remfirstJtFee;
    }

    public BigDecimal getRemfirstDwFee() {
        return remfirstDwFee;
    }

    public void setRemfirstDwFee(BigDecimal remfirstDwFee) {
        this.remfirstDwFee = remfirstDwFee;
    }

    public BigDecimal getRemcurrJtFee() {
        return remcurrJtFee;
    }

    public void setRemcurrJtFee(BigDecimal remcurrJtFee) {
        this.remcurrJtFee = remcurrJtFee;
    }

    public BigDecimal getRemcurrDwFee() {
        return remcurrDwFee;
    }

    public void setRemcurrDwFee(BigDecimal remcurrDwFee) {
        this.remcurrDwFee = remcurrDwFee;
    }

    public BigDecimal getFinFirst() {
        return finFirst;
    }

    public void setFinFirst(BigDecimal finFirst) {
        this.finFirst = finFirst;
    }

    public BigDecimal getFinCurr() {
        return finCurr;
    }

    public void setFinCurr(BigDecimal finCurr) {
        this.finCurr = finCurr;
    }

    public BigDecimal getRemFirst() {
        return remFirst;
    }

    public void setRemFirst(BigDecimal remFirst) {
        this.remFirst = remFirst;
    }

    public BigDecimal getRemCurr() {
        return remCurr;
    }

    public void setRemCurr(BigDecimal remCurr) {
        this.remCurr = remCurr;
    }

    public BigDecimal getFinConPrice() {
        return finConPrice;
    }

    public void setFinConPrice(BigDecimal finConPrice) {
        this.finConPrice = finConPrice;
    }

    public BigDecimal getFinChgAmount() {
        return finChgAmount;
    }

    public void setFinChgAmount(BigDecimal finChgAmount) {
        this.finChgAmount = finChgAmount;
    }

    public BigDecimal getFinAwdPenalty() {
        return finAwdPenalty;
    }

    public void setFinAwdPenalty(BigDecimal finAwdPenalty) {
        this.finAwdPenalty = finAwdPenalty;
    }

    public BigDecimal getFinMtladjustment() {
        return finMtladjustment;
    }

    public void setFinMtladjustment(BigDecimal finMtladjustment) {
        this.finMtladjustment = finMtladjustment;
    }

    public BigDecimal getFinTaxPrice() {
        return finTaxPrice;
    }

    public void setFinTaxPrice(BigDecimal finTaxPrice) {
        this.finTaxPrice = finTaxPrice;
    }

    public BigDecimal getFinOther() {
        return finOther;
    }

    public void setFinOther(BigDecimal finOther) {
        this.finOther = finOther;
    }

    public BigDecimal getFintotal() {
        return fintotal;
    }

    public void setFintotal(BigDecimal fintotal) {
        this.fintotal = fintotal;
    }

    public BigDecimal getRemConPrice() {
        return remConPrice;
    }

    public void setRemConPrice(BigDecimal remConPrice) {
        this.remConPrice = remConPrice;
    }

    public BigDecimal getRemChgAmount() {
        return remChgAmount;
    }

    public void setRemChgAmount(BigDecimal remChgAmount) {
        this.remChgAmount = remChgAmount;
    }

    public BigDecimal getRemAwdPenalty() {
        return remAwdPenalty;
    }

    public void setRemAwdPenalty(BigDecimal remAwdPenalty) {
        this.remAwdPenalty = remAwdPenalty;
    }

    public BigDecimal getRemMtladjustment() {
        return remMtladjustment;
    }

    public void setRemMtladjustment(BigDecimal remMtladjustment) {
        this.remMtladjustment = remMtladjustment;
    }

    public BigDecimal getRemTaxPrice() {
        return remTaxPrice;
    }

    public void setRemTaxPrice(BigDecimal remTaxPrice) {
        this.remTaxPrice = remTaxPrice;
    }

    public BigDecimal getRemOther() {
        return remOther;
    }

    public void setRemOther(BigDecimal remOther) {
        this.remOther = remOther;
    }

    public BigDecimal getRemtotal() {
        return remtotal;
    }

    public void setRemtotal(BigDecimal remtotal) {
        this.remtotal = remtotal;
    }

    public BigDecimal getFirstJtSca() {
        return firstJtSca;
    }

    public void setFirstJtSca(BigDecimal firstJtSca) {
        this.firstJtSca = firstJtSca;
    }

    public BigDecimal getFirstDwSca() {
        return firstDwSca;
    }

    public void setFirstDwSca(BigDecimal firstDwSca) {
        this.firstDwSca = firstDwSca;
    }

    public BigDecimal getCurrJtSca() {
        return currJtSca;
    }

    public void setCurrJtSca(BigDecimal currJtSca) {
        this.currJtSca = currJtSca;
    }

    public BigDecimal getCurrDwSca() {
        return currDwSca;
    }

    public void setCurrDwSca(BigDecimal currDwSca) {
        this.currDwSca = currDwSca;
    }

    public String getOrderNo() {
        return orderNo == null ? "" : orderNo.trim();
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo == null ? null : orderNo.trim();
    }

    public String getSerialNumber() {
        return serialNumber == null ? "" : serialNumber.trim();
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber == null ? null : serialNumber.trim();
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

package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxBuYgjResTechnics extends BasePojo {
    // 主键
    private String zxBuYgjResTechnicsId;

    // 工序名称
    private String name;

    // billID
    private String billID;

    // orgID
    private String orgID;

    // orderFlag
    private int orderFlag=0;

    // money
    private BigDecimal money;

    // 标准工序ID
    private String standardTechID;

    // techType
    private int techType=0;

    // comPrice
    private BigDecimal comPrice;

    // 序号
    private String techOrder;

    // 不含税单价
    private BigDecimal price;

    // 数量
    private BigDecimal qty;

    // 工序单位
    private String unit;

    // budgetBookID
    private String budgetBookID;

    // isGroup
    private String isGroup;

    // isGroupDoRes
    private String isGroupDoRes;

    // 参考区域
    private String areaName;

    // 标准值
    private BigDecimal technicAmt;

    // 下限值
    private BigDecimal downAmt;

    // 上限值
    private BigDecimal upAmt;

    // 税率(%)
    private BigDecimal taxRate;

    // 税金单价
    private BigDecimal taxPrice;

    // 已完工程量不含税单价
    private BigDecimal finPrice;

    // 已完工程税率(%)
    private BigDecimal finTaxRate;

    // 已完工程税金单价
    private BigDecimal finTaxPrice;

    // 剩余工程不含税单价
    private BigDecimal remPrice;

    // 剩余工程税率(%)
    private BigDecimal remTaxRate;

    // 剩余工程税金单价
    private BigDecimal remTaxPrice;

    // 已完工程量数量
    private BigDecimal finQty;

    // 剩余工程数量
    private BigDecimal remQty;

    // 工序编号
    private String techNon;

    // 计算公式
    private String formulaStr;

    // 计算公式
    private String formulaStr2;

    // 关联清单名称
    private String workName;

    // 参考值查询
    private String pp1;

    // technicType
    private String technicType;

    // 引用标准清单ID
    private String standardWorkID;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    //自己的清单id
    private String myBillID;

    public ZxBuYgjResTechnics() {
    }

    public ZxBuYgjResTechnics(String billID) {
        this.billID = billID;
    }

    public ZxBuYgjResTechnics(String billID, String orgID, String standardTechID) {
        this.billID = billID;
        this.orgID = orgID;
        this.standardTechID = standardTechID;
    }

    public String getMyBillID() {
        return myBillID;
    }

    public void setMyBillID(String myBillID) {
        this.myBillID = myBillID;
    }

    public String getZxBuYgjResTechnicsId() {
        return zxBuYgjResTechnicsId == null ? "" : zxBuYgjResTechnicsId.trim();
    }

    public void setZxBuYgjResTechnicsId(String zxBuYgjResTechnicsId) {
        this.zxBuYgjResTechnicsId = zxBuYgjResTechnicsId == null ? null : zxBuYgjResTechnicsId.trim();
    }

    public String getName() {
        return name == null ? "" : name.trim();
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getBillID() {
        return billID == null ? "" : billID.trim();
    }

    public void setBillID(String billID) {
        this.billID = billID == null ? null : billID.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public int getOrderFlag() {
        return orderFlag;
    }

    public void setOrderFlag(int orderFlag) {
        this.orderFlag = orderFlag;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getStandardTechID() {
        return standardTechID == null ? "" : standardTechID.trim();
    }

    public void setStandardTechID(String standardTechID) {
        this.standardTechID = standardTechID == null ? null : standardTechID.trim();
    }

    public int getTechType() {
        return techType;
    }

    public void setTechType(int techType) {
        this.techType = techType;
    }

    public BigDecimal getComPrice() {
        return comPrice;
    }

    public void setComPrice(BigDecimal comPrice) {
        this.comPrice = comPrice;
    }

    public String getTechOrder() {
        return techOrder == null ? "" : techOrder.trim();
    }

    public void setTechOrder(String techOrder) {
        this.techOrder = techOrder == null ? null : techOrder.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public String getBudgetBookID() {
        return budgetBookID == null ? "" : budgetBookID.trim();
    }

    public void setBudgetBookID(String budgetBookID) {
        this.budgetBookID = budgetBookID == null ? null : budgetBookID.trim();
    }

    public String getIsGroup() {
        return isGroup == null ? "" : isGroup.trim();
    }

    public void setIsGroup(String isGroup) {
        this.isGroup = isGroup == null ? null : isGroup.trim();
    }

    public String getIsGroupDoRes() {
        return isGroupDoRes == null ? "" : isGroupDoRes.trim();
    }

    public void setIsGroupDoRes(String isGroupDoRes) {
        this.isGroupDoRes = isGroupDoRes == null ? null : isGroupDoRes.trim();
    }

    public String getAreaName() {
        return areaName == null ? "" : areaName.trim();
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName == null ? null : areaName.trim();
    }

    public BigDecimal getTechnicAmt() {
        return technicAmt;
    }

    public void setTechnicAmt(BigDecimal technicAmt) {
        this.technicAmt = technicAmt;
    }

    public BigDecimal getDownAmt() {
        return downAmt;
    }

    public void setDownAmt(BigDecimal downAmt) {
        this.downAmt = downAmt;
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public BigDecimal getTaxPrice() {
        return taxPrice;
    }

    public void setTaxPrice(BigDecimal taxPrice) {
        this.taxPrice = taxPrice;
    }

    public BigDecimal getFinPrice() {
        return finPrice;
    }

    public void setFinPrice(BigDecimal finPrice) {
        this.finPrice = finPrice;
    }

    public BigDecimal getFinTaxRate() {
        return finTaxRate;
    }

    public void setFinTaxRate(BigDecimal finTaxRate) {
        this.finTaxRate = finTaxRate;
    }

    public BigDecimal getFinTaxPrice() {
        return finTaxPrice;
    }

    public void setFinTaxPrice(BigDecimal finTaxPrice) {
        this.finTaxPrice = finTaxPrice;
    }

    public BigDecimal getRemPrice() {
        return remPrice;
    }

    public void setRemPrice(BigDecimal remPrice) {
        this.remPrice = remPrice;
    }

    public BigDecimal getRemTaxRate() {
        return remTaxRate;
    }

    public void setRemTaxRate(BigDecimal remTaxRate) {
        this.remTaxRate = remTaxRate;
    }

    public BigDecimal getRemTaxPrice() {
        return remTaxPrice;
    }

    public void setRemTaxPrice(BigDecimal remTaxPrice) {
        this.remTaxPrice = remTaxPrice;
    }

    public BigDecimal getFinQty() {
        return finQty;
    }

    public void setFinQty(BigDecimal finQty) {
        this.finQty = finQty;
    }

    public BigDecimal getRemQty() {
        return remQty;
    }

    public void setRemQty(BigDecimal remQty) {
        this.remQty = remQty;
    }

    public String getTechNon() {
        return techNon == null ? "" : techNon.trim();
    }

    public void setTechNon(String techNon) {
        this.techNon = techNon == null ? null : techNon.trim();
    }

    public String getFormulaStr() {
        return formulaStr == null ? "" : formulaStr.trim();
    }

    public void setFormulaStr(String formulaStr) {
        this.formulaStr = formulaStr == null ? null : formulaStr.trim();
    }

    public String getFormulaStr2() {
        return formulaStr2 == null ? "" : formulaStr2.trim();
    }

    public void setFormulaStr2(String formulaStr2) {
        this.formulaStr2 = formulaStr2 == null ? null : formulaStr2.trim();
    }

    public String getWorkName() {
        return workName == null ? "" : workName.trim();
    }

    public void setWorkName(String workName) {
        this.workName = workName == null ? null : workName.trim();
    }

    public String getPp1() {
        return pp1 == null ? "" : pp1.trim();
    }

    public void setPp1(String pp1) {
        this.pp1 = pp1 == null ? null : pp1.trim();
    }

    public String getTechnicType() {
        return technicType == null ? "" : technicType.trim();
    }

    public void setTechnicType(String technicType) {
        this.technicType = technicType == null ? null : technicType.trim();
    }

    public String getStandardWorkID() {
        return standardWorkID == null ? "" : standardWorkID.trim();
    }

    public void setStandardWorkID(String standardWorkID) {
        this.standardWorkID = standardWorkID == null ? null : standardWorkID.trim();
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

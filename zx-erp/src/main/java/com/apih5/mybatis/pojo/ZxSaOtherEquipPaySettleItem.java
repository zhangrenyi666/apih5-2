package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaOtherEquipPaySettleItem extends BasePojo {
    // 主键
    private String zxSaOtherEquipPaySettleItemId;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 税率(%)
    private String taxRate;

    // 合同类型
    private String contrType;

    // 结算期次
    private String period;

    // 所属公司排序
    private String comOrders;

    // 主表ID(旧系统字段mainId)
    private String zxSaOtherEquipPaySettleId;

    // 结算单编号
    private String billNo;

    // 合同ID（旧系统字段contractId）
    private String zxCtOtherManageId;

    // 序号
    private String orderNum;

    // 支付项ID
    private String payId;

    // 编号
    private String payNo;

    // 支付项类型
    private String payType;

    // 名称
    private String payName;

    // 单位
    private String unit;

    // 数量
    private BigDecimal qty;

    // 上期末数量
    private BigDecimal upQty;

    // 单价(元)
    private BigDecimal price;

    // 本期结算金额(元)
    private BigDecimal thisAmt;

    // 上期末结算金额(元)
    private BigDecimal upAmt;

    // 所属公司ID
    private String comId;

    // 所属公司名称
    private String comName;

    // 是否被修改
    private String isFixed;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    // 结算单主键id
    private String zxSaOtherEquipSettleId;

    public String getZxSaOtherEquipPaySettleItemId() {
        return zxSaOtherEquipPaySettleItemId == null ? "" : zxSaOtherEquipPaySettleItemId.trim();
    }

    public void setZxSaOtherEquipPaySettleItemId(String zxSaOtherEquipPaySettleItemId) {
        this.zxSaOtherEquipPaySettleItemId = zxSaOtherEquipPaySettleItemId == null ? null : zxSaOtherEquipPaySettleItemId.trim();
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

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getZxSaOtherEquipPaySettleId() {
        return zxSaOtherEquipPaySettleId == null ? "" : zxSaOtherEquipPaySettleId.trim();
    }

    public void setZxSaOtherEquipPaySettleId(String zxSaOtherEquipPaySettleId) {
        this.zxSaOtherEquipPaySettleId = zxSaOtherEquipPaySettleId == null ? null : zxSaOtherEquipPaySettleId.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
    }

    public String getOrderNum() {
        return orderNum == null ? "" : orderNum.trim();
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum == null ? null : orderNum.trim();
    }

    public String getPayId() {
        return payId == null ? "" : payId.trim();
    }

    public void setPayId(String payId) {
        this.payId = payId == null ? null : payId.trim();
    }

    public String getPayNo() {
        return payNo == null ? "" : payNo.trim();
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo == null ? null : payNo.trim();
    }

    public String getPayType() {
        return payType == null ? "" : payType.trim();
    }

    public void setPayType(String payType) {
        this.payType = payType == null ? null : payType.trim();
    }

    public String getPayName() {
        return payName == null ? "" : payName.trim();
    }

    public void setPayName(String payName) {
        this.payName = payName == null ? null : payName.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getUpQty() {
        return upQty;
    }

    public void setUpQty(BigDecimal upQty) {
        this.upQty = upQty;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public String getComId() {
        return comId == null ? "" : comId.trim();
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getIsFixed() {
        return isFixed == null ? "" : isFixed.trim();
    }

    public void setIsFixed(String isFixed) {
        this.isFixed = isFixed == null ? null : isFixed.trim();
    }

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getZxSaOtherEquipSettleId() {
        return zxSaOtherEquipSettleId;
    }

    public void setZxSaOtherEquipSettleId(String zxSaOtherEquipSettleId) {
        this.zxSaOtherEquipSettleId = zxSaOtherEquipSettleId;
    }
}

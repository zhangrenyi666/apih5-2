package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesShopEndorseDetail extends BasePojo {
    // 主键
    private String zxCtSuppliesShopEndorseDetailId;

    // 最后编辑时间
    private Date editTime;

    // 主表ID
    private String mainID;

    // 选择单据批次
    private int saveNumbers=0;

    // 序号
    private int orderNum=0;

    // 物资名称
    private String resName;

    // 物资编码
    private String resCode;

    // 物资ID
    private String resID;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 收料单(或退货单)编号
    private String stockBillNo;

    // 收料单(或退货单)ID
    private String stockBillID;

    // 是否预收
    private String precollecte;

    // 上期末累计结算数量
    private BigDecimal upTotalQty;

    // 结算数量
    private BigDecimal qty;

    // 结算期次
    private String period;

    // 合同数量
    private BigDecimal contractQty;

    // 合同清单ID
    private String contrItemID;

    // 合同ID
    private String contractID;

    // 规格型号
    private String spec;

    // 单位
    private String unit;

    // 单据日期
    private Date billDate;

    // 单据明细ID
    private String stockBillItemID;

    // 本期金额
    private BigDecimal thisAmt;

    // 本期单价
    private BigDecimal thisPrice;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCtSuppliesShopEndorseDetailId() {
        return zxCtSuppliesShopEndorseDetailId == null ? "" : zxCtSuppliesShopEndorseDetailId.trim();
    }

    public void setZxCtSuppliesShopEndorseDetailId(String zxCtSuppliesShopEndorseDetailId) {
        this.zxCtSuppliesShopEndorseDetailId = zxCtSuppliesShopEndorseDetailId == null ? null : zxCtSuppliesShopEndorseDetailId.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getMainID() {
        return mainID == null ? "" : mainID.trim();
    }

    public void setMainID(String mainID) {
        this.mainID = mainID == null ? null : mainID.trim();
    }

    public int getSaveNumbers() {
        return saveNumbers;
    }

    public void setSaveNumbers(int saveNumbers) {
        this.saveNumbers = saveNumbers;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getResName() {
        return resName == null ? "" : resName.trim();
    }

    public void setResName(String resName) {
        this.resName = resName == null ? null : resName.trim();
    }

    public String getResCode() {
        return resCode == null ? "" : resCode.trim();
    }

    public void setResCode(String resCode) {
        this.resCode = resCode == null ? null : resCode.trim();
    }

    public String getResID() {
        return resID == null ? "" : resID.trim();
    }

    public void setResID(String resID) {
        this.resID = resID == null ? null : resID.trim();
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

    public String getStockBillNo() {
        return stockBillNo == null ? "" : stockBillNo.trim();
    }

    public void setStockBillNo(String stockBillNo) {
        this.stockBillNo = stockBillNo == null ? null : stockBillNo.trim();
    }

    public String getStockBillID() {
        return stockBillID == null ? "" : stockBillID.trim();
    }

    public void setStockBillID(String stockBillID) {
        this.stockBillID = stockBillID == null ? null : stockBillID.trim();
    }

    public String getPrecollecte() {
        return precollecte == null ? "" : precollecte.trim();
    }

    public void setPrecollecte(String precollecte) {
        this.precollecte = precollecte == null ? null : precollecte.trim();
    }

    public BigDecimal getUpTotalQty() {
        return upTotalQty;
    }

    public void setUpTotalQty(BigDecimal upTotalQty) {
        this.upTotalQty = upTotalQty;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public BigDecimal getContractQty() {
        return contractQty;
    }

    public void setContractQty(BigDecimal contractQty) {
        this.contractQty = contractQty;
    }

    public String getContrItemID() {
        return contrItemID == null ? "" : contrItemID.trim();
    }

    public void setContrItemID(String contrItemID) {
        this.contrItemID = contrItemID == null ? null : contrItemID.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getSpec() {
        return spec == null ? "" : spec.trim();
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getUnit() {
        return unit == null ? "" : unit.trim();
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Date getBillDate() {
        return billDate;
    }

    public void setBillDate(Date billDate) {
        this.billDate = billDate;
    }

    public String getStockBillItemID() {
        return stockBillItemID == null ? "" : stockBillItemID.trim();
    }

    public void setStockBillItemID(String stockBillItemID) {
        this.stockBillItemID = stockBillItemID == null ? null : stockBillItemID.trim();
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(BigDecimal thisPrice) {
        this.thisPrice = thisPrice;
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

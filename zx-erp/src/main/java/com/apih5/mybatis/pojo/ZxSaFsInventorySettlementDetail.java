package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaFsInventorySettlementDetail extends BasePojo {
    // 主键
    private String zxSaFsEnumerationSettlementDetailedId;

    // 附属类结算单ID
    private String zxSaFsSettlementId;

    // 附属类结算清单ID
    private String zxSaFsInventorySettlementId;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 合同ID
    private String zxCtFsContractId;

    // 序号
    private int orderNum=0;

    // 签认单ID
    private String signedOrderID;

    // 签认单编号
    private String signedNo;

    // 签认单明细ID
    private String signedOrderItemID;

    // 合同明细ID
    private String conRevDetailId;

    // 清单编号
    private String equipCode;

    // 清单名称
    private String equipName;

    // 型号
    private String spec;

    // 计量单位
    private String unit;

    // 起租日期
    private Date startDate;

    // 含税单价(元)
    private BigDecimal contractPrice;

    // 合同数量
    private BigDecimal contractQty;

    // 含税合同金额(元)
    private BigDecimal contractAmt;

    // 变更后数量
    private BigDecimal changedQty;

    // 变更后含税金额(元)
    private BigDecimal changedAmt;

    // 本期结算数量
    private BigDecimal thisQty;

    // 本期结算单价(元)
    private BigDecimal thisPrice;

    // 本期结算含税金额(元)
    private BigDecimal thisAmt;

    // 至上期末累计结算数量
    private BigDecimal upQty;

    // 至上期末累计结算含税金额(元)
    private BigDecimal upAmt;

    // 至本期末累计结算数量
    private BigDecimal totalQty;

    // 至本期末累计结算含税金额(元)
    private BigDecimal totalAmt;

    // 最后编辑时间
    private Date editTime;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 变更后单价(元)
    private BigDecimal alterPrice;

    // 税率(%)
    private String taxRate;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    // 至上期末累计结算不含税金额(元)
    private BigDecimal upAmtNoTax;

    // 至上期末累计结算税额(元)
    private BigDecimal upAmtTax;

    // 不含税单价(元)
    private BigDecimal contractPriceNoTax;

    // 单价税金(元)
    private BigDecimal contractPriceTax;

    // 变更后不含税单价(元)
    private BigDecimal alterPriceNoTax;

    // 变更后税金(元)
    private BigDecimal alterPriceTax;

    // 不含税合同金额(元)
    private BigDecimal contractAmtNoTax;

    // 合同税额(元)
    private BigDecimal contractTax;

    // 变更后不含税金额(元)
    private BigDecimal changedAmtNoTax;

    // 变更后税额(元)
    private BigDecimal changedAmtTax;

    // 至本期末累计结算不含税金额(元)
    private BigDecimal totalAmtNoTax;

    // 至本期末累计结算税额(元)
    private BigDecimal totalAmtTax;

    //结算内容
    private String content;

    //含税单价
    private BigDecimal price;

    //不含税单价
    private BigDecimal priceNoTax;

    //税额
    private BigDecimal contractSumTax;

    //变更后含税单价
    private BigDecimal changePrice;

    //变更后不含税单价
    private BigDecimal changePriceNoTax;

    //变更后税额
    private BigDecimal changeTax;

    //原合同数量
    private BigDecimal qty;

    //原合同含税金额
    private BigDecimal contractSum;

    //原合同不含税金额
    private BigDecimal contractSumNoTax;

    private BigDecimal changeContractSum;

    private BigDecimal changeContractSumNoTax;

    private BigDecimal upTax;

    private BigDecimal totalTax;

    //是否抵扣
    private String isDeduct;

    //乙方名称
    private String secondName;
    //合同编号
    private String contractNo;

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getZxSaFsSettlementId() {
        return zxSaFsSettlementId;
    }

    public void setZxSaFsSettlementId(String zxSaFsSettlementId) {
        this.zxSaFsSettlementId = zxSaFsSettlementId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getPriceNoTax() {
        return priceNoTax;
    }

    public void setPriceNoTax(BigDecimal priceNoTax) {
        this.priceNoTax = priceNoTax;
    }

    public BigDecimal getContractSumTax() {
        return contractSumTax;
    }

    public void setContractSumTax(BigDecimal contractSumTax) {
        this.contractSumTax = contractSumTax;
    }

    public BigDecimal getChangePrice() {
        return changePrice;
    }

    public void setChangePrice(BigDecimal changePrice) {
        this.changePrice = changePrice;
    }

    public BigDecimal getChangePriceNoTax() {
        return changePriceNoTax;
    }

    public void setChangePriceNoTax(BigDecimal changePriceNoTax) {
        this.changePriceNoTax = changePriceNoTax;
    }

    public BigDecimal getChangeTax() {
        return changeTax;
    }

    public void setChangeTax(BigDecimal changeTax) {
        this.changeTax = changeTax;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getContractSum() {
        return contractSum;
    }

    public void setContractSum(BigDecimal contractSum) {
        this.contractSum = contractSum;
    }

    public BigDecimal getContractSumNoTax() {
        return contractSumNoTax;
    }

    public void setContractSumNoTax(BigDecimal contractSumNoTax) {
        this.contractSumNoTax = contractSumNoTax;
    }

    public BigDecimal getChangeContractSum() {
        return changeContractSum;
    }

    public void setChangeContractSum(BigDecimal changeContractSum) {
        this.changeContractSum = changeContractSum;
    }

    public BigDecimal getChangeContractSumNoTax() {
        return changeContractSumNoTax;
    }

    public void setChangeContractSumNoTax(BigDecimal changeContractSumNoTax) {
        this.changeContractSumNoTax = changeContractSumNoTax;
    }

    public BigDecimal getUpTax() {
        return upTax;
    }

    public void setUpTax(BigDecimal upTax) {
        this.upTax = upTax;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public String getIsDeduct() {
        return isDeduct;
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct;
    }

    public BigDecimal getUpAmtTax() {
        return upAmtTax;
    }

    public void setUpAmtTax(BigDecimal upAmtTax) {
        this.upAmtTax = upAmtTax;
    }

    public BigDecimal getContractPriceNoTax() {
        return contractPriceNoTax;
    }

    public void setContractPriceNoTax(BigDecimal contractPriceNoTax) {
        this.contractPriceNoTax = contractPriceNoTax;
    }

    public BigDecimal getContractPriceTax() {
        return contractPriceTax;
    }

    public void setContractPriceTax(BigDecimal contractPriceTax) {
        this.contractPriceTax = contractPriceTax;
    }

    public BigDecimal getAlterPriceNoTax() {
        return alterPriceNoTax;
    }

    public void setAlterPriceNoTax(BigDecimal alterPriceNoTax) {
        this.alterPriceNoTax = alterPriceNoTax;
    }

    public BigDecimal getAlterPriceTax() {
        return alterPriceTax;
    }

    public void setAlterPriceTax(BigDecimal alterPriceTax) {
        this.alterPriceTax = alterPriceTax;
    }

    public BigDecimal getContractAmtNoTax() {
        return contractAmtNoTax;
    }

    public void setContractAmtNoTax(BigDecimal contractAmtNoTax) {
        this.contractAmtNoTax = contractAmtNoTax;
    }

    public BigDecimal getContractTax() {
        return contractTax;
    }

    public void setContractTax(BigDecimal contractTax) {
        this.contractTax = contractTax;
    }

    public BigDecimal getChangedAmtNoTax() {
        return changedAmtNoTax;
    }

    public void setChangedAmtNoTax(BigDecimal changedAmtNoTax) {
        this.changedAmtNoTax = changedAmtNoTax;
    }

    public BigDecimal getChangedAmtTax() {
        return changedAmtTax;
    }

    public void setChangedAmtTax(BigDecimal changedAmtTax) {
        this.changedAmtTax = changedAmtTax;
    }

    public BigDecimal getTotalAmtNoTax() {
        return totalAmtNoTax;
    }

    public void setTotalAmtNoTax(BigDecimal totalAmtNoTax) {
        this.totalAmtNoTax = totalAmtNoTax;
    }

    public BigDecimal getTotalAmtTax() {
        return totalAmtTax;
    }

    public void setTotalAmtTax(BigDecimal totalAmtTax) {
        this.totalAmtTax = totalAmtTax;
    }

    public BigDecimal getUpAmtNoTax() {
        return upAmtNoTax;
    }

    public void setUpAmtNoTax(BigDecimal upAmtNoTax) {
        this.upAmtNoTax = upAmtNoTax;
    }

    public String getZxSaFsEnumerationSettlementDetailedId() {
        return zxSaFsEnumerationSettlementDetailedId == null ? "" : zxSaFsEnumerationSettlementDetailedId.trim();
    }

    public void setZxSaFsEnumerationSettlementDetailedId(String zxSaFsEnumerationSettlementDetailedId) {
        this.zxSaFsEnumerationSettlementDetailedId = zxSaFsEnumerationSettlementDetailedId == null ? null : zxSaFsEnumerationSettlementDetailedId.trim();
    }

    public String getZxSaFsInventorySettlementId() {
        return zxSaFsInventorySettlementId == null ? "" : zxSaFsInventorySettlementId.trim();
    }

    public void setZxSaFsInventorySettlementId(String zxSaFsInventorySettlementId) {
        this.zxSaFsInventorySettlementId = zxSaFsInventorySettlementId == null ? null : zxSaFsInventorySettlementId.trim();
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

    public String getZxCtFsContractId() {
        return zxCtFsContractId == null ? "" : zxCtFsContractId.trim();
    }

    public void setZxCtFsContractId(String zxCtFsContractId) {
        this.zxCtFsContractId = zxCtFsContractId == null ? null : zxCtFsContractId.trim();
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getSignedOrderID() {
        return signedOrderID == null ? "" : signedOrderID.trim();
    }

    public void setSignedOrderID(String signedOrderID) {
        this.signedOrderID = signedOrderID == null ? null : signedOrderID.trim();
    }

    public String getSignedNo() {
        return signedNo == null ? "" : signedNo.trim();
    }

    public void setSignedNo(String signedNo) {
        this.signedNo = signedNo == null ? null : signedNo.trim();
    }

    public String getSignedOrderItemID() {
        return signedOrderItemID == null ? "" : signedOrderItemID.trim();
    }

    public void setSignedOrderItemID(String signedOrderItemID) {
        this.signedOrderItemID = signedOrderItemID == null ? null : signedOrderItemID.trim();
    }

    public String getConRevDetailId() {
        return conRevDetailId == null ? "" : conRevDetailId.trim();
    }

    public void setConRevDetailId(String conRevDetailId) {
        this.conRevDetailId = conRevDetailId == null ? null : conRevDetailId.trim();
    }

    public String getEquipCode() {
        return equipCode == null ? "" : equipCode.trim();
    }

    public void setEquipCode(String equipCode) {
        this.equipCode = equipCode == null ? null : equipCode.trim();
    }

    public String getEquipName() {
        return equipName == null ? "" : equipName.trim();
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName == null ? null : equipName.trim();
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public BigDecimal getContractPrice() {
        return contractPrice;
    }

    public void setContractPrice(BigDecimal contractPrice) {
        this.contractPrice = contractPrice;
    }

    public BigDecimal getContractQty() {
        return contractQty;
    }

    public void setContractQty(BigDecimal contractQty) {
        this.contractQty = contractQty;
    }

    public BigDecimal getContractAmt() {
        return contractAmt;
    }

    public void setContractAmt(BigDecimal contractAmt) {
        this.contractAmt = contractAmt;
    }

    public BigDecimal getChangedQty() {
        return changedQty;
    }

    public void setChangedQty(BigDecimal changedQty) {
        this.changedQty = changedQty;
    }

    public BigDecimal getChangedAmt() {
        return changedAmt;
    }

    public void setChangedAmt(BigDecimal changedAmt) {
        this.changedAmt = changedAmt;
    }

    public BigDecimal getThisQty() {
        return thisQty;
    }

    public void setThisQty(BigDecimal thisQty) {
        this.thisQty = thisQty;
    }

    public BigDecimal getThisPrice() {
        return thisPrice;
    }

    public void setThisPrice(BigDecimal thisPrice) {
        this.thisPrice = thisPrice;
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getUpQty() {
        return upQty;
    }

    public void setUpQty(BigDecimal upQty) {
        this.upQty = upQty;
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public BigDecimal getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(BigDecimal totalQty) {
        this.totalQty = totalQty;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
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

    public BigDecimal getAlterPrice() {
        return alterPrice;
    }

    public void setAlterPrice(BigDecimal alterPrice) {
        this.alterPrice = alterPrice;
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
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

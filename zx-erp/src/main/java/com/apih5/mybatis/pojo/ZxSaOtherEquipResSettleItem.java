package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaOtherEquipResSettleItem extends BasePojo {
    // 主键
    private String zxSaOtherEquipResSettleItemId;

    // 签认单编号
    private String signedNo;

    // 签认单明细ID
    private String signedOrderItemId;

    // 起租日期
    private Date startDate;

    // 含税单价(元)
    private BigDecimal contractPrice;

    // 至上期末累计结算含税金额(元)
    private BigDecimal upAmt;

    // 变更后单价(元)
    private BigDecimal alterPrice;

    // 主表ID(旧系统字段mainId)
    private String zxSaOtherEquipResSettleId;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 合同ID（旧系统字段contractId）
    private String zxCtOtherManageId;

    // 序号
    private String orderNum;

    // 签认单ID
    private String signedOrderId;

    // 合同明细ID（旧系统字段contractItemId）
    private String zxCtOtherWorksId;

    // 清单编号
    private String equipCode;

    // 清单名称
    private String equipName;

    // 型号
    private String spec;

    // 计量单位
    private String unit;

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

    // 至本期末累计结算数量
    private BigDecimal totalQty;

    // 至本期末累计结算含税金额(元)
    private BigDecimal totalAmt;

    // 所属公司ID
    private String comId;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 税率(%)
    private String taxRate;

    // 本期结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期结算税额(元)
    private BigDecimal thisAmtTax;

    // 计算式
    private String planning;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    public String getZxSaOtherEquipResSettleItemId() {
        return zxSaOtherEquipResSettleItemId == null ? "" : zxSaOtherEquipResSettleItemId.trim();
    }

    public void setZxSaOtherEquipResSettleItemId(String zxSaOtherEquipResSettleItemId) {
        this.zxSaOtherEquipResSettleItemId = zxSaOtherEquipResSettleItemId == null ? null : zxSaOtherEquipResSettleItemId.trim();
    }

    public String getSignedNo() {
        return signedNo == null ? "" : signedNo.trim();
    }

    public void setSignedNo(String signedNo) {
        this.signedNo = signedNo == null ? null : signedNo.trim();
    }

    public String getSignedOrderItemId() {
        return signedOrderItemId == null ? "" : signedOrderItemId.trim();
    }

    public void setSignedOrderItemId(String signedOrderItemId) {
        this.signedOrderItemId = signedOrderItemId == null ? null : signedOrderItemId.trim();
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

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public BigDecimal getAlterPrice() {
        return alterPrice;
    }

    public void setAlterPrice(BigDecimal alterPrice) {
        this.alterPrice = alterPrice;
    }

    public String getZxSaOtherEquipResSettleId() {
        return zxSaOtherEquipResSettleId == null ? "" : zxSaOtherEquipResSettleId.trim();
    }

    public void setZxSaOtherEquipResSettleId(String zxSaOtherEquipResSettleId) {
        this.zxSaOtherEquipResSettleId = zxSaOtherEquipResSettleId == null ? null : zxSaOtherEquipResSettleId.trim();
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

    public String getSignedOrderId() {
        return signedOrderId == null ? "" : signedOrderId.trim();
    }

    public void setSignedOrderId(String signedOrderId) {
        this.signedOrderId = signedOrderId == null ? null : signedOrderId.trim();
    }

    public String getZxCtOtherWorksId() {
        return zxCtOtherWorksId == null ? "" : zxCtOtherWorksId.trim();
    }

    public void setZxCtOtherWorksId(String zxCtOtherWorksId) {
        this.zxCtOtherWorksId = zxCtOtherWorksId == null ? null : zxCtOtherWorksId.trim();
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

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
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

    public String getPlanning() {
        return planning == null ? "" : planning.trim();
    }

    public void setPlanning(String planning) {
        this.planning = planning == null ? null : planning.trim();
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

}

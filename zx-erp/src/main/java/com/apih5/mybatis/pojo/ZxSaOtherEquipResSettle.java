package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaOtherEquipResSettle extends BasePojo {
    // 主键
    private String zxSaOtherEquipResSettleId;

    // 税率(%)
    private String taxRate;

    // 是否抵扣
    private String isDeduct;

    // 本期清单结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期清单结算税额(元)
    private BigDecimal thisAmtTax;

    // 合同ID（旧系统字段contractId）
    private String zxCtOtherManageId;

    // 签认单编号
    private String signedNos;

    // 含税合同金额(万元)
    private BigDecimal contractAmt;

    // 本期清单结算含税金额(元)
    private BigDecimal thisAmt;

    // 项目ID
    private String orgId;

    // 项目名称
    private String orgName;

    // 机械租赁类结算表ID（旧系统字段billId）
    private String zxSaOtherEquipSettleId;

    // 签认单ID
    private String signedOrders;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 变更后含税合同金额(万元)
    private BigDecimal changeAmt;

    // 累计清单结算含税金额(元)
    private BigDecimal totalAmt;

    // 上期末清单结算金额(元)
    private BigDecimal upAmt;

    // 所属公司ID
    private String comId;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 是否首次结算
    private String isFirst;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    // 细目结算清单明细列表
    private List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList;
    public String getZxSaOtherEquipResSettleId() {
        return zxSaOtherEquipResSettleId == null ? "" : zxSaOtherEquipResSettleId.trim();
    }

    public void setZxSaOtherEquipResSettleId(String zxSaOtherEquipResSettleId) {
        this.zxSaOtherEquipResSettleId = zxSaOtherEquipResSettleId == null ? null : zxSaOtherEquipResSettleId.trim();
    }

    public String getTaxRate() {
        return taxRate == null ? "" : taxRate.trim();
    }

    public void setTaxRate(String taxRate) {
        this.taxRate = taxRate == null ? null : taxRate.trim();
    }

    public String getIsDeduct() {
        return isDeduct == null ? "" : isDeduct.trim();
    }

    public void setIsDeduct(String isDeduct) {
        this.isDeduct = isDeduct == null ? null : isDeduct.trim();
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

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
    }

    public String getSignedNos() {
        return signedNos == null ? "" : signedNos.trim();
    }

    public void setSignedNos(String signedNos) {
        this.signedNos = signedNos == null ? null : signedNos.trim();
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

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getOrgName() {
        return orgName == null ? "" : orgName.trim();
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName == null ? null : orgName.trim();
    }

    public String getZxSaOtherEquipSettleId() {
        return zxSaOtherEquipSettleId == null ? "" : zxSaOtherEquipSettleId.trim();
    }

    public void setZxSaOtherEquipSettleId(String zxSaOtherEquipSettleId) {
        this.zxSaOtherEquipSettleId = zxSaOtherEquipSettleId == null ? null : zxSaOtherEquipSettleId.trim();
    }

    public String getSignedOrders() {
        return signedOrders == null ? "" : signedOrders.trim();
    }

    public void setSignedOrders(String signedOrders) {
        this.signedOrders = signedOrders == null ? null : signedOrders.trim();
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

    public BigDecimal getChangeAmt() {
        return changeAmt;
    }

    public void setChangeAmt(BigDecimal changeAmt) {
        this.changeAmt = changeAmt;
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

    public String getIsFirst() {
        return isFirst == null ? "" : isFirst.trim();
    }

    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst == null ? null : isFirst.trim();
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

    public List<ZxSaOtherEquipResSettleItem> getZxSaOtherEquipResSettleItemList() {
        return zxSaOtherEquipResSettleItemList;
    }

    public void setZxSaOtherEquipResSettleItemList(List<ZxSaOtherEquipResSettleItem> zxSaOtherEquipResSettleItemList) {
        this.zxSaOtherEquipResSettleItemList = zxSaOtherEquipResSettleItemList;
    }
}

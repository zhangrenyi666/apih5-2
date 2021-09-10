package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaOtherEquipPaySettle extends BasePojo {
    // 主键
    private String zxSaOtherEquipPaySettleId;

    // 其他支付项上期末累计
    private BigDecimal checkUpOtherAmt;

    // 本期支付项结算不含税金额(元)
    private BigDecimal thisAmtNoTax;

    // 本期支付项结算税额(元)
    private BigDecimal thisAmtTax;

    // 合同类型
    private String contrType;

    // 本期支付项结算金额(元)
    private BigDecimal thisAmt;

    // 本期进退场费
    private BigDecimal inOutAmt;

    // 上期末奖罚
    private BigDecimal upFineAmt;

    // 本期其它结算支付项
    private BigDecimal otherAmt;

    // 所属公司名称
    private String comName;

    // 项目ID
    private String orgId;

    // 项目名称
    private String orgName;

    // 合同ID（旧系统字段contractId）
    private String zxCtOtherManageId;

    // 结算表ID(旧系统字段billId)
    private String zxSaOtherEquipSettleId;

    // 结算单编号
    private String billNo;

    // 结算期次
    private String period;

    // 累计支付项结算金额(元)
    private BigDecimal totalAmt;

    // 上期末进退场费
    private BigDecimal upInOutAmt;

    // 本期奖罚
    private BigDecimal fineAmt;

    // 本期伙食费
    private BigDecimal foodAmt;

    // 上期末伙食费
    private BigDecimal upFoodAmt;

    // 上期末其它结算支付项
    private BigDecimal upOtherAmt;

    // 上期末累计支付项结算金额(元)
    private BigDecimal upAmt;

    // 所属公司ID
    private String comId;

    // 所属公司排序
    private String comOrders;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    // 支付项结算清单明细列表
    private List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList;
    public String getZxSaOtherEquipPaySettleId() {
        return zxSaOtherEquipPaySettleId == null ? "" : zxSaOtherEquipPaySettleId.trim();
    }

    public void setZxSaOtherEquipPaySettleId(String zxSaOtherEquipPaySettleId) {
        this.zxSaOtherEquipPaySettleId = zxSaOtherEquipPaySettleId == null ? null : zxSaOtherEquipPaySettleId.trim();
    }

    public BigDecimal getCheckUpOtherAmt() {
        return checkUpOtherAmt;
    }

    public void setCheckUpOtherAmt(BigDecimal checkUpOtherAmt) {
        this.checkUpOtherAmt = checkUpOtherAmt;
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

    public String getContrType() {
        return contrType == null ? "" : contrType.trim();
    }

    public void setContrType(String contrType) {
        this.contrType = contrType == null ? null : contrType.trim();
    }

    public BigDecimal getThisAmt() {
        return thisAmt;
    }

    public void setThisAmt(BigDecimal thisAmt) {
        this.thisAmt = thisAmt;
    }

    public BigDecimal getInOutAmt() {
        return inOutAmt;
    }

    public void setInOutAmt(BigDecimal inOutAmt) {
        this.inOutAmt = inOutAmt;
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

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
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

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
    }

    public String getZxSaOtherEquipSettleId() {
        return zxSaOtherEquipSettleId == null ? "" : zxSaOtherEquipSettleId.trim();
    }

    public void setZxSaOtherEquipSettleId(String zxSaOtherEquipSettleId) {
        this.zxSaOtherEquipSettleId = zxSaOtherEquipSettleId == null ? null : zxSaOtherEquipSettleId.trim();
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

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
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

    public String getComId() {
        return comId == null ? "" : comId.trim();
    }

    public void setComId(String comId) {
        this.comId = comId == null ? null : comId.trim();
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
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

    public List<ZxSaOtherEquipPaySettleItem> getZxSaOtherEquipPaySettleItemList() {
        return zxSaOtherEquipPaySettleItemList;
    }

    public void setZxSaOtherEquipPaySettleItemList(List<ZxSaOtherEquipPaySettleItem> zxSaOtherEquipPaySettleItemList) {
        this.zxSaOtherEquipPaySettleItemList = zxSaOtherEquipPaySettleItemList;
    }
}

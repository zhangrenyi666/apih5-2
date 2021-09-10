package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaOtherEquipSettleItem extends BasePojo {
    // 主键
    private String zxSaOtherEquipSettleItemId;

    // 比例
    private BigDecimal rate;

    // 本期(元)
    private String thisAmt;

    // 所属公司排序
    private String comOrders;

    // 统计项ID
    private String statisticsId;

    // 上期末金额
    private BigDecimal upAmt;

    // 所属公司ID
    private String comId;

    // 合同ID（旧系统字段contractId）
    private String zxCtOtherManageId;

    // 统计项
    private String statisticsName;

    // 开累(元)
    private String totalAmt;

    // 期次
    private String period;

    // 统计项编号
    private String statisticsNo;

    // 项目ID
    private String orgId;

    // 结算单编号
    private String billNo;

    // 统计项类型
    private String statisticsType;

    // 所属公司名称
    private String comName;

    // 主表ID（旧系统字段mainId）
    private String zxSaOtherEquipSettleId;

    // 合同保证金外键
    private String zxCtOtherManageAmtRateId;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    public String getZxSaOtherEquipSettleItemId() {
        return zxSaOtherEquipSettleItemId == null ? "" : zxSaOtherEquipSettleItemId.trim();
    }

    public void setZxSaOtherEquipSettleItemId(String zxSaOtherEquipSettleItemId) {
        this.zxSaOtherEquipSettleItemId = zxSaOtherEquipSettleItemId == null ? null : zxSaOtherEquipSettleItemId.trim();
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String getThisAmt() {
        return thisAmt == null ? "" : thisAmt.trim();
    }

    public void setThisAmt(String thisAmt) {
        this.thisAmt = thisAmt == null ? null : thisAmt.trim();
    }

    public String getComOrders() {
        return comOrders == null ? "" : comOrders.trim();
    }

    public void setComOrders(String comOrders) {
        this.comOrders = comOrders == null ? null : comOrders.trim();
    }

    public String getStatisticsId() {
        return statisticsId == null ? "" : statisticsId.trim();
    }

    public void setStatisticsId(String statisticsId) {
        this.statisticsId = statisticsId == null ? null : statisticsId.trim();
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

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
    }

    public String getStatisticsName() {
        return statisticsName == null ? "" : statisticsName.trim();
    }

    public void setStatisticsName(String statisticsName) {
        this.statisticsName = statisticsName == null ? null : statisticsName.trim();
    }

    public String getTotalAmt() {
        return totalAmt == null ? "" : totalAmt.trim();
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt == null ? null : totalAmt.trim();
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getStatisticsNo() {
        return statisticsNo == null ? "" : statisticsNo.trim();
    }

    public void setStatisticsNo(String statisticsNo) {
        this.statisticsNo = statisticsNo == null ? null : statisticsNo.trim();
    }

    public String getOrgId() {
        return orgId == null ? "" : orgId.trim();
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getStatisticsType() {
        return statisticsType == null ? "" : statisticsType.trim();
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType == null ? null : statisticsType.trim();
    }

    public String getComName() {
        return comName == null ? "" : comName.trim();
    }

    public void setComName(String comName) {
        this.comName = comName == null ? null : comName.trim();
    }

    public String getZxSaOtherEquipSettleId() {
        return zxSaOtherEquipSettleId == null ? "" : zxSaOtherEquipSettleId.trim();
    }

    public void setZxSaOtherEquipSettleId(String zxSaOtherEquipSettleId) {
        this.zxSaOtherEquipSettleId = zxSaOtherEquipSettleId == null ? null : zxSaOtherEquipSettleId.trim();
    }

    public String getZxCtOtherManageAmtRateId() {
        return zxCtOtherManageAmtRateId == null ? "" : zxCtOtherManageAmtRateId.trim();
    }

    public void setZxCtOtherManageAmtRateId(String zxCtOtherManageAmtRateId) {
        this.zxCtOtherManageAmtRateId = zxCtOtherManageAmtRateId == null ? null : zxCtOtherManageAmtRateId.trim();
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

package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaEquipSettleAuditItem extends BasePojo {
    // 主键
    private String zxSaEquipSettleAuditItemId;

    // 主表id（结算单id）
    private String zxSaEquipSettleAuditId;

    // 项目id
    private String orgID;

    // 合同id
    private String contractID;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 统计项id
    private String statisticsID;

    // 统计项编号
    private String statisticsNo;

    // 统计项名称
    private String statisticsName;

    // 统计项类型
    private String statisticsType;

    // 本期(元)
    private String thisAmt;

    // 开累(元)
    private String totalAmt;

    // 上期末金额
    private BigDecimal upAmt;

    // 比例
    private BigDecimal rate;

    // 所属公司id
    private String comID;

    // 所属公司名称
    private String comName;

    // 是否是基础数据标示
    private String baseFlag;

    // 隐藏的排序字段
    private int sort=0;

    public String getZxSaEquipSettleAuditItemId() {
        return zxSaEquipSettleAuditItemId == null ? "" : zxSaEquipSettleAuditItemId.trim();
    }

    public void setZxSaEquipSettleAuditItemId(String zxSaEquipSettleAuditItemId) {
        this.zxSaEquipSettleAuditItemId = zxSaEquipSettleAuditItemId == null ? null : zxSaEquipSettleAuditItemId.trim();
    }

    public String getZxSaEquipSettleAuditId() {
        return zxSaEquipSettleAuditId == null ? "" : zxSaEquipSettleAuditId.trim();
    }

    public void setZxSaEquipSettleAuditId(String zxSaEquipSettleAuditId) {
        this.zxSaEquipSettleAuditId = zxSaEquipSettleAuditId == null ? null : zxSaEquipSettleAuditId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
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

    public String getStatisticsID() {
        return statisticsID == null ? "" : statisticsID.trim();
    }

    public void setStatisticsID(String statisticsID) {
        this.statisticsID = statisticsID == null ? null : statisticsID.trim();
    }

    public String getStatisticsNo() {
        return statisticsNo == null ? "" : statisticsNo.trim();
    }

    public void setStatisticsNo(String statisticsNo) {
        this.statisticsNo = statisticsNo == null ? null : statisticsNo.trim();
    }

    public String getStatisticsName() {
        return statisticsName == null ? "" : statisticsName.trim();
    }

    public void setStatisticsName(String statisticsName) {
        this.statisticsName = statisticsName == null ? null : statisticsName.trim();
    }

    public String getStatisticsType() {
        return statisticsType == null ? "" : statisticsType.trim();
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType == null ? null : statisticsType.trim();
    }

    public String getThisAmt() {
        return thisAmt == null ? "" : thisAmt.trim();
    }

    public void setThisAmt(String thisAmt) {
        this.thisAmt = thisAmt == null ? null : thisAmt.trim();
    }

    public String getTotalAmt() {
        return totalAmt == null ? "" : totalAmt.trim();
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt == null ? null : totalAmt.trim();
    }

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
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

    public String getBaseFlag() {
        return baseFlag == null ? "" : baseFlag.trim();
    }

    public void setBaseFlag(String baseFlag) {
        this.baseFlag = baseFlag == null ? null : baseFlag.trim();
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

}

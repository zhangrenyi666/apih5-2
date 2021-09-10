package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxSaFsStatisticsDetail extends BasePojo {
    // 主键
    private String zxSaFsStatisticsDetailId;

    // 附属类结算ID
    private String zxSaFsSettlementId;

    // 项目ID
    private String orgID;

    // 合同ID
    private String zxCtFsContractId;

    // 结算期次
    private String period;

    // 结算单编号
    private String billNo;

    // 统计项ID
    private String statisticsID;

    // 统计项编号
    private String statisticsNo;

    // 统计项名称
    private String statisticsName;

    // 统计项类型
    private String statisticsType;

    // 本期支付项结算金额(元)
    private String thisAmt;

    // 累计支付项结算金额(元)
    private String totalAmt;

    // 上期末累计支付项结算金额(元)
    private BigDecimal upAmt;

    // 比例
    private BigDecimal rate;

    // 最后编辑时间
    private Date editTime;

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 备注
    private String remarks;

    private BigDecimal amt;
    private BigDecimal tAmt;

    private int sortFlag;



    // 排序
    private int sort=0;

    public int getSortFlag() {
        return sortFlag;
    }

    public void setSortFlag(int sortFlag) {
        this.sortFlag = sortFlag;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public BigDecimal gettAmt() {
        return tAmt;
    }

    public void settAmt(BigDecimal tAmt) {
        this.tAmt = tAmt;
    }

    public String getZxSaFsStatisticsDetailId() {
        return zxSaFsStatisticsDetailId == null ? "" : zxSaFsStatisticsDetailId.trim();
    }

    public void setZxSaFsStatisticsDetailId(String zxSaFsStatisticsDetailId) {
        this.zxSaFsStatisticsDetailId = zxSaFsStatisticsDetailId == null ? null : zxSaFsStatisticsDetailId.trim();
    }

    public String getZxSaFsSettlementId() {
        return zxSaFsSettlementId == null ? "" : zxSaFsSettlementId.trim();
    }

    public void setZxSaFsSettlementId(String zxSaFsSettlementId) {
        this.zxSaFsSettlementId = zxSaFsSettlementId == null ? null : zxSaFsSettlementId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getZxCtFsContractId() {
        return zxCtFsContractId == null ? "" : zxCtFsContractId.trim();
    }

    public void setZxCtFsContractId(String zxCtFsContractId) {
        this.zxCtFsContractId = zxCtFsContractId == null ? null : zxCtFsContractId.trim();
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
        return thisAmt;
    }

    public void setThisAmt(String thisAmt) {
        this.thisAmt = thisAmt;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
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

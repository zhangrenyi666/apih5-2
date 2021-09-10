package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesShopSettlementItem extends BasePojo {
    // 主键
    private String zxCtSuppliesShopSettlementItemId;

    // 最后编辑时间
    private Date editTime;

    // 主表ID
    private String mainID;

    // 项目ID
    private String orgID;

    // 统计项类型
    private String statisticsType;

    // 统计项编号
    private String statisticsNo;

    // 统计项ID
    private String statisticsID;

    // 统计项
    private String statisticsName;

    // 所属公司排序
    private String comOrders;

    // 所属公司名称
    private String comName;

    // 所属公司ID
    private String comID;

    // 上期末金额
    private BigDecimal upAmt;

    // 期次
    private String period;

    // 开累(元)
    private String totalAmt;

    // 结算单编号
    private String billNo;

    // 合同ID
    private String contractID;

    // 比例
    private BigDecimal rate;

    // 本期(元)
    private String thisAmt;

    // 备注
    private String remarks;
    
    private String zxCtSuppliesShopSettlementId;

    // 排序
    private int sort=0;

    public String getZxCtSuppliesShopSettlementId() {
		return zxCtSuppliesShopSettlementId;
	}

	public void setZxCtSuppliesShopSettlementId(String zxCtSuppliesShopSettlementId) {
		this.zxCtSuppliesShopSettlementId = zxCtSuppliesShopSettlementId;
	}

	public String getZxCtSuppliesShopSettlementItemId() {
        return zxCtSuppliesShopSettlementItemId == null ? "" : zxCtSuppliesShopSettlementItemId.trim();
    }

    public void setZxCtSuppliesShopSettlementItemId(String zxCtSuppliesShopSettlementItemId) {
        this.zxCtSuppliesShopSettlementItemId = zxCtSuppliesShopSettlementItemId == null ? null : zxCtSuppliesShopSettlementItemId.trim();
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

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getStatisticsType() {
        return statisticsType == null ? "" : statisticsType.trim();
    }

    public void setStatisticsType(String statisticsType) {
        this.statisticsType = statisticsType == null ? null : statisticsType.trim();
    }

    public String getStatisticsNo() {
        return statisticsNo == null ? "" : statisticsNo.trim();
    }

    public void setStatisticsNo(String statisticsNo) {
        this.statisticsNo = statisticsNo == null ? null : statisticsNo.trim();
    }

    public String getStatisticsID() {
        return statisticsID == null ? "" : statisticsID.trim();
    }

    public void setStatisticsID(String statisticsID) {
        this.statisticsID = statisticsID == null ? null : statisticsID.trim();
    }

    public String getStatisticsName() {
        return statisticsName == null ? "" : statisticsName.trim();
    }

    public void setStatisticsName(String statisticsName) {
        this.statisticsName = statisticsName == null ? null : statisticsName.trim();
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

    public BigDecimal getUpAmt() {
        return upAmt;
    }

    public void setUpAmt(BigDecimal upAmt) {
        this.upAmt = upAmt;
    }

    public String getPeriod() {
        return period == null ? "" : period.trim();
    }

    public void setPeriod(String period) {
        this.period = period == null ? null : period.trim();
    }

    public String getTotalAmt() {
        return totalAmt == null ? "" : totalAmt.trim();
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt == null ? null : totalAmt.trim();
    }

    public String getBillNo() {
        return billNo == null ? "" : billNo.trim();
    }

    public void setBillNo(String billNo) {
        this.billNo = billNo == null ? null : billNo.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
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

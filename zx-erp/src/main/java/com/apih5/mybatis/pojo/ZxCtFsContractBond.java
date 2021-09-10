package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtFsContractBond extends BasePojo {
    // 主键
    private String zxCtFsContractBondId;

    // 项目ID
    private String orgID;

    // 附属合同ID
    private String zxCtFsContractId;

    // 保证金编号
    private String statisticsNo;

    // 保证金
    private String statisticsName;

    // 扣除比例(%)
    private BigDecimal statisticsRate;

    // 返还条件
    private String backCondition;

    // 期限
    private String timeLimit;

    // 是否允许删除
    private String allowDelete;

    // 最后编辑时间
    private Date editTime;

    // 是否被结算引用
    private String useCount;

    // 是否被设备结算引用
    private String equipUseCount;

    // 是否被物资结算引用
    private String stockUseCount;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCtFsContractBondId() {
        return zxCtFsContractBondId == null ? "" : zxCtFsContractBondId.trim();
    }

    public void setZxCtFsContractBondId(String zxCtFsContractBondId) {
        this.zxCtFsContractBondId = zxCtFsContractBondId == null ? null : zxCtFsContractBondId.trim();
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

    public BigDecimal getStatisticsRate() {
        return statisticsRate;
    }

    public void setStatisticsRate(BigDecimal statisticsRate) {
        this.statisticsRate = statisticsRate;
    }

    public String getBackCondition() {
        return backCondition == null ? "" : backCondition.trim();
    }

    public void setBackCondition(String backCondition) {
        this.backCondition = backCondition == null ? null : backCondition.trim();
    }

    public String getTimeLimit() {
        return timeLimit == null ? "" : timeLimit.trim();
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit == null ? null : timeLimit.trim();
    }

    public String getAllowDelete() {
        return allowDelete == null ? "" : allowDelete.trim();
    }

    public void setAllowDelete(String allowDelete) {
        this.allowDelete = allowDelete == null ? null : allowDelete.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getUseCount() {
        return useCount == null ? "" : useCount.trim();
    }

    public void setUseCount(String useCount) {
        this.useCount = useCount == null ? null : useCount.trim();
    }

    public String getEquipUseCount() {
        return equipUseCount == null ? "" : equipUseCount.trim();
    }

    public void setEquipUseCount(String equipUseCount) {
        this.equipUseCount = equipUseCount == null ? null : equipUseCount.trim();
    }

    public String getStockUseCount() {
        return stockUseCount == null ? "" : stockUseCount.trim();
    }

    public void setStockUseCount(String stockUseCount) {
        this.stockUseCount = stockUseCount == null ? null : stockUseCount.trim();
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

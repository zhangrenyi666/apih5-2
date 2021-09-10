package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtSuppliesMarginRatio extends BasePojo {
    // 主键
    private String zxCtSuppliesMarginRatioId;

    // 最后编辑时间
    private Date editTime;

    // 项目ID
    private String orgID;

    // 是否允许删除
    private String allowDelete;

    // 期限
    private String timeLimit;

    // 扣除比例(%)
    private BigDecimal statisticsRate;

    // 合同ID
    private String contractID;

    // 返还条件
    private String backCondition;

    // 保证金编号
    private String statisticsNo;

    // 保证金
    private String statisticsName;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getZxCtSuppliesMarginRatioId() {
        return zxCtSuppliesMarginRatioId == null ? "" : zxCtSuppliesMarginRatioId.trim();
    }

    public void setZxCtSuppliesMarginRatioId(String zxCtSuppliesMarginRatioId) {
        this.zxCtSuppliesMarginRatioId = zxCtSuppliesMarginRatioId == null ? null : zxCtSuppliesMarginRatioId.trim();
    }

    public Date getEditTime() {
        return editTime;
    }

    public void setEditTime(Date editTime) {
        this.editTime = editTime;
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getAllowDelete() {
        return allowDelete == null ? "" : allowDelete.trim();
    }

    public void setAllowDelete(String allowDelete) {
        this.allowDelete = allowDelete == null ? null : allowDelete.trim();
    }

    public String getTimeLimit() {
        return timeLimit == null ? "" : timeLimit.trim();
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit == null ? null : timeLimit.trim();
    }

    public BigDecimal getStatisticsRate() {
        return statisticsRate;
    }

    public void setStatisticsRate(BigDecimal statisticsRate) {
        this.statisticsRate = statisticsRate;
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getBackCondition() {
        return backCondition == null ? "" : backCondition.trim();
    }

    public void setBackCondition(String backCondition) {
        this.backCondition = backCondition == null ? null : backCondition.trim();
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

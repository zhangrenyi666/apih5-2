package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxGcsgCtCoContractAmtRate extends BasePojo {
    // 主键
    private String ctCoContractAmtRateId;

    // 项目ID
    private String orgID;

    // 合同ID(contractID)
    private String ctContractId;

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

    // 所属公司ID
    private String comID;

    // 所属公司名称
    private String comName;

    // 所属公司排序
    private String comOrders;

    // 备注
    private String remarks;

    // 排序
    private int sort=0;

    public String getCtCoContractAmtRateId() {
        return ctCoContractAmtRateId == null ? "" : ctCoContractAmtRateId.trim();
    }

    public void setCtCoContractAmtRateId(String ctCoContractAmtRateId) {
        this.ctCoContractAmtRateId = ctCoContractAmtRateId == null ? null : ctCoContractAmtRateId.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
    }

    public String getCtContractId() {
        return ctContractId == null ? "" : ctContractId.trim();
    }

    public void setCtContractId(String ctContractId) {
        this.ctContractId = ctContractId == null ? null : ctContractId.trim();
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

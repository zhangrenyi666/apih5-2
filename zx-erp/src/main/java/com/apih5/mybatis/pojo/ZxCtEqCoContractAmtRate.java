package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtEqCoContractAmtRate extends BasePojo {
    // 主键
    private String zxCtEqCoContractAmtRateId;

    // 合同id
    private String contractID;

    // 保证金编号
    private String statisticsNo;

    // 项目id
    private String orgID;

    // 保证金
    private String statisticsName;

    // 扣除比例(%)
    private BigDecimal statisticsRate;

    // 返还条件
    private String backCondition;

    // 期限
    private String timeLimit;

    // 备注
    private String remark;

    // 是否允许删除
    private String allowDelete;

    // 公司名称
    private String comName;

    // 公司排序
    private String comOrders;

    public String getZxCtEqCoContractAmtRateId() {
        return zxCtEqCoContractAmtRateId == null ? "" : zxCtEqCoContractAmtRateId.trim();
    }

    public void setZxCtEqCoContractAmtRateId(String zxCtEqCoContractAmtRateId) {
        this.zxCtEqCoContractAmtRateId = zxCtEqCoContractAmtRateId == null ? null : zxCtEqCoContractAmtRateId.trim();
    }

    public String getContractID() {
        return contractID == null ? "" : contractID.trim();
    }

    public void setContractID(String contractID) {
        this.contractID = contractID == null ? null : contractID.trim();
    }

    public String getStatisticsNo() {
        return statisticsNo == null ? "" : statisticsNo.trim();
    }

    public void setStatisticsNo(String statisticsNo) {
        this.statisticsNo = statisticsNo == null ? null : statisticsNo.trim();
    }

    public String getOrgID() {
        return orgID == null ? "" : orgID.trim();
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID == null ? null : orgID.trim();
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

    public String getRemark() {
        return remark == null ? "" : remark.trim();
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public String getAllowDelete() {
        return allowDelete == null ? "" : allowDelete.trim();
    }

    public void setAllowDelete(String allowDelete) {
        this.allowDelete = allowDelete == null ? null : allowDelete.trim();
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

}

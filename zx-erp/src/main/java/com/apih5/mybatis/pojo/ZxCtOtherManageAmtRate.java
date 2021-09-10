package com.apih5.mybatis.pojo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONArray;

import com.apih5.framework.entity.BasePojo;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ZxCtOtherManageAmtRate extends BasePojo {
    // 主键
    private String zxCtOtherManageAmtRateId;

    // 期限
    private String timeLimit;

    // 合同ID
    private String contractId;

    // 保证金
    private String statisticsName;

    // 是否被结算引用
    private BigDecimal useCount;

    // 是否允许删除
    private String allowDelete;

    // 返还条件
    private String backCondition;

    // 扣除比例(%)
    private BigDecimal statisticsRate;

    // 保证金编号
    private String statisticsNo;

    // 项目ID
    private String orgId;

    // 合同管理表外键id
    private String zxCtOtherManageId;

    // 所属公司id
    private String comId;

    // 所属公司名称
    private String comName;

    // 所属公司序号
    private String comOrders;

    // 备注
    private String remark;

    // 排序
    private int sort=0;

    public String getZxCtOtherManageAmtRateId() {
        return zxCtOtherManageAmtRateId == null ? "" : zxCtOtherManageAmtRateId.trim();
    }

    public void setZxCtOtherManageAmtRateId(String zxCtOtherManageAmtRateId) {
        this.zxCtOtherManageAmtRateId = zxCtOtherManageAmtRateId == null ? null : zxCtOtherManageAmtRateId.trim();
    }

    public String getTimeLimit() {
        return timeLimit == null ? "" : timeLimit.trim();
    }

    public void setTimeLimit(String timeLimit) {
        this.timeLimit = timeLimit == null ? null : timeLimit.trim();
    }

    public String getContractId() {
        return contractId == null ? "" : contractId.trim();
    }

    public void setContractId(String contractId) {
        this.contractId = contractId == null ? null : contractId.trim();
    }

    public String getStatisticsName() {
        return statisticsName == null ? "" : statisticsName.trim();
    }

    public void setStatisticsName(String statisticsName) {
        this.statisticsName = statisticsName == null ? null : statisticsName.trim();
    }

    public BigDecimal getUseCount() {
        return useCount;
    }

    public void setUseCount(BigDecimal useCount) {
        this.useCount = useCount;
    }

    public String getAllowDelete() {
        return allowDelete == null ? "" : allowDelete.trim();
    }

    public void setAllowDelete(String allowDelete) {
        this.allowDelete = allowDelete == null ? null : allowDelete.trim();
    }

    public String getBackCondition() {
        return backCondition == null ? "" : backCondition.trim();
    }

    public void setBackCondition(String backCondition) {
        this.backCondition = backCondition == null ? null : backCondition.trim();
    }

    public BigDecimal getStatisticsRate() {
        return statisticsRate;
    }

    public void setStatisticsRate(BigDecimal statisticsRate) {
        this.statisticsRate = statisticsRate;
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

    public String getZxCtOtherManageId() {
        return zxCtOtherManageId == null ? "" : zxCtOtherManageId.trim();
    }

    public void setZxCtOtherManageId(String zxCtOtherManageId) {
        this.zxCtOtherManageId = zxCtOtherManageId == null ? null : zxCtOtherManageId.trim();
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
